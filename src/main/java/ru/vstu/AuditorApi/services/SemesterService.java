package ru.vstu.AuditorApi.services;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ru.vstu.AuditorApi.configurations.Constants;
import ru.vstu.AuditorApi.entities.Semester;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Service
public class SemesterService {

    private Map<String, Semester> semesters = new HashMap<>();

    //Хардкодом нагенерить список семестров
    public SemesterService() {
        this.semesters.put(
            Constants.hll1Code,
                new Semester("1 курс, весенний", Constants.hll1Code, Constants.hll1DbName)
        );
        this.semesters.put(
                Constants.hll2Code,
                new Semester("2 курс, осенний", Constants.hll2Code, Constants.hll2DbName)
        );
    }

    public Map<String, Semester> getSemesters() {
        this.resetActiveSemester();
        return this.semesters;
    }

    public Semester getSemesterByCode(String code) {
        this.resetActiveSemester();
        return this.semesters.get(code);
    }

    public String getActiveSemesterCode() {
        String semesterCode = null;

        //Если мы в рамках http-запроса
        if (RequestContextHolder.getRequestAttributes() != null) {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpSession session = requestAttributes.getRequest().getSession();
            //Взять выбранный семестр из запроса
            semesterCode = (String) session.getAttribute("semesterCode");
        }

        if (semesterCode == null) {
            semesterCode = Constants.hll1Code;
        }

        return semesterCode;
    }

    protected void resetActiveSemester() {
        //Снять активность у всех семестров
        for (Semester semester : semesters.values()) {
            semester.setActive(false);
        }
        //Задать активность нужному семестру
        this.semesters.get(this.getActiveSemesterCode()).setActive(true);
    }
}
