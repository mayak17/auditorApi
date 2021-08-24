package ru.vstu.AuditorApi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.vstu.AuditorApi.configurations.Constants;
import ru.vstu.AuditorApi.services.AutotestService;
import ru.vstu.AuditorApi.services.TestResult;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

//Контроллер с шаблонами
//НЕ ИСПУЛЬЗЕТСЯ В API
//Нужен, чтобы что-то попробовать без фронта
@Controller
@RequestMapping("/api")
public class MainController {

    @Autowired
    protected AutotestService autotestService;

    //Показать список семестров
    @GetMapping("/")

    public String showMain(Model model, HttpServletRequest request) {
        //Взять из сессии выбранный семестр
        String semesterCode = (String) request.getSession().getAttribute("semesterCode");

        //Собрать коды и названия семестров
        Map<String, String> semesters = new HashMap<>();
        semesters.put(Constants.hll1Code, "1й курс, второй семестр");
        semesters.put(Constants.hll2Code, "2й курс, первый семестр");

        //Сложить все в модель
        model.addAttribute("semesterCode", semesterCode);
        model.addAttribute("semesters", semesters);

        return "selectSemester";
    }

    //Применить выбранный семестр
    @PostMapping("/")
    public String semesterSelected(String semesterCode, HttpServletRequest request) {
        if (semesterCode != null) {
            //Положить в сессию выбранный семестр
            request.getSession().setAttribute("semesterCode", semesterCode);
        }
        return "redirect:/";
    }

    @GetMapping("/test")
    public String showFormForTests() {

        return "testFile";
    }

    @PostMapping("/test")
    public ResponseEntity<TestResult> test(@RequestParam MultipartFile program,
                                           @RequestParam Integer labNum,
                                           @RequestParam Integer variant,
                                           @RequestParam Integer mod) {

        if (this.autotestService.initTest(program, labNum, variant, mod)) {

            if (this.autotestService.runTests()) {
                return ResponseEntity.ok(this.autotestService.getLastTestResult());
            }

        }

        return ResponseEntity.badRequest().build();
    }

}
