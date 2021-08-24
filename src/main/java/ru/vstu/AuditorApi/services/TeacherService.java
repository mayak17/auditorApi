package ru.vstu.AuditorApi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vstu.AuditorApi.DAO.TeacherDAO;
import ru.vstu.AuditorApi.entities.Teacher;

//Обертка над таблицей преподавателей
@Service
public class TeacherService {


    @Autowired
    private TeacherDAO teacherDAO;


    /**
     * Вернуть всех преподавателей
     * @return
     */
    public Iterable<Teacher> getAllTeachers() {
        return this.teacherDAO.findAll();
    }
}
