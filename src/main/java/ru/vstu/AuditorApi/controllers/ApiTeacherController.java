package ru.vstu.AuditorApi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vstu.AuditorApi.entities.Teacher;
import ru.vstu.AuditorApi.services.TeacherService;

//JSON API
@RestController
@RequestMapping("/api")
public class ApiTeacherController {

    @Autowired
    private TeacherService teacherService;

    /**
     * Список всех преподавателей
     * @return
     */
    @GetMapping("/teachers")
    public ResponseEntity<Iterable> getTeachers() {

        //Получить всех учителей
        Iterable<Teacher> allTeachers = this.teacherService.getAllTeachers();

        //Вернуть Json
        return ResponseEntity.ok(allTeachers);
    }

}
