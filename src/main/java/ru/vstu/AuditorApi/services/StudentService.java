package ru.vstu.AuditorApi.services;

import lombok.RequiredArgsConstructor;
import org.hibernate.cfg.annotations.Nullability;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vstu.AuditorApi.DAO.StudentDAO;
import ru.vstu.AuditorApi.entities.Student;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {

    @Autowired
    private StudentDAO studentDAO;

    public Student findById(int StudentId) throws Exception {
        if(Optional.ofNullable(studentDAO.findById(StudentId)).isPresent())
            return studentDAO.findById(StudentId);
        else
            throw new Exception("Can not find such student");
    }

    public List<Student> findByGroupId(int groupId) {
        return studentDAO.findAllByGroupId(groupId);
    }

}
