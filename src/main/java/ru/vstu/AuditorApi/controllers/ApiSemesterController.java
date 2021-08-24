package ru.vstu.AuditorApi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vstu.AuditorApi.entities.Semester;
import ru.vstu.AuditorApi.services.SemesterService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiSemesterController {


    @Autowired
    private SemesterService semesterService;

    //Вернуть список семестров
    @GetMapping("/semesters")
    public ResponseEntity<Map<String, Semester>> getSemesters() {
        return ResponseEntity.ok(this.semesterService.getSemesters());
    }

    //Получить код семестра и выбрать его в качестве активного
    //(от этого зависит, из какой БД будут браться данные)
    @PostMapping(value = "/semesters")
    public ResponseEntity<Semester> selectSemester(String semesterCode, HttpServletRequest request) {
        if (semesterCode == null) {
            semesterCode = this.semesterService.getActiveSemesterCode();
        }
        //Положить в сессию выбранный семестр
        request.getSession().setAttribute("semesterCode", semesterCode);
        //Ответ - объект семестра, который выбран
        return ResponseEntity.ok(this.semesterService.getSemesterByCode(semesterCode));
    }


}
