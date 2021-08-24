package ru.vstu.AuditorApi.services;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.Charset;
import java.util.UUID;

@Service
public class AutotestService {

    @Value("${autotest.wintest.folder}")
    protected String wintestFolder;
    @Value("${autotest.wintest.script}")
    protected String wintestScript;
    @Value("${autotest.programs.folder}")
    protected String programsFolder;
    @Value("${autotest.result.folder}")
    protected String resultFolder;

    protected String lastProgramName;
    protected Integer lastLabNum;
    protected Integer lastVariant;
    protected Integer lastMod;

    public Boolean initTest(MultipartFile program, Integer labNum, Integer variant, Integer mod) {
        this.lastProgramName = program.getOriginalFilename();
        this.lastLabNum = labNum;
        this.lastVariant = variant;
        this.lastMod = mod;

        if (checkFolders() && prepareFileForTest(program)) {
            return true;
        }

        return false;
    }

    //Проверить, все ли нужные папки и файлы есть на сервере
    protected boolean checkFolders() {

        System.out.println("Check files and folders");
        ProcessBuilder processBuilder = new ProcessBuilder();
        int exitCode = 1;

        try {
            String checkWintestFolder = String.format("[ -d %s ] && [ -f %s ] && [ -d %s ] && [ -d %s ] && echo \"check is ok\" ",
                    this.wintestFolder, this.wintestScript, this.programsFolder, this.resultFolder);

            processBuilder.command("bash", "-c", checkWintestFolder);

            Process process = processBuilder.start();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            exitCode = process.waitFor();
            System.out.println("\nExited with error code : " + exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return exitCode == 0;
    }

    //Сохранить файл в папку для экзешников, которые нужно протестировать
    protected boolean prepareFileForTest(MultipartFile program) {
        System.out.println("Transfer " + program.getOriginalFilename());
        File programToTest = new File(this.programsFolder + "/" + program.getOriginalFilename());
        try {
            program.transferTo(programToTest);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        System.out.println("Transfer is ok");
        return true;
    }

    //Прогнать тесты
    public boolean runTests() {

        System.out.println("Run tests for " + this.lastProgramName);

        ProcessBuilder processBuilder = new ProcessBuilder();
        String command = String.format("%s %s %d %d %d", this.wintestScript, this.lastProgramName, this.lastLabNum, this.lastVariant, this.lastMod);
        processBuilder.command("bash", "-c", command);
        String response = "";
        int exitCode = 1;
        try {
            Process process = processBuilder.start();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            exitCode = process.waitFor();
            System.out.println("\nExited with error code : " + exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return exitCode == 0;
    }

    public TestResult getLastTestResult() {

        //Задать данные о последнем тесте
        TestResult result = new TestResult();
        result.setProgramName(this.lastProgramName);
        result.setLabNum(this.lastLabNum);
        result.setVariant(this.lastVariant);
        result.setMod(this.lastMod);

        //Пойти в папку out на сервере и достать содержимое сгенерированных файлов
        //для нужной программы

        File expectedResultFile = new File(this.resultFolder + "/" + this.lastProgramName + "_expected");
        File realResultFile = new File(this.resultFolder + "/" + this.lastProgramName + "_real");

        if (expectedResultFile.exists() && realResultFile.exists()) {

            try {
                //Прочитать все из файла _expected
                BufferedReader br = new BufferedReader(new FileReader(expectedResultFile, Charset.forName("windows-1251")));
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();

                while (line != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                    line = br.readLine();
                }
                //Записать в результат
                result.setExpected(sb.toString());

                //Прочитать все из файла _real
                br = new BufferedReader(new FileReader(realResultFile, Charset.forName("windows-1251")));
                sb = new StringBuilder();
                line = br.readLine();
                while (line != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                    line = br.readLine();
                }
                //Записать результат
                result.setReal(sb.toString());

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return result;
    }
}