package ru.vstu.AuditorApi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vstu.AuditorApi.DAO.StudentGroupsDAO;
import ru.vstu.AuditorApi.entities.StudentGroups;

import java.util.List;

@Service
public class GroupService {

    @Autowired
    private StudentGroupsDAO studentGroupsDAO;

    public GroupService(StudentGroupsDAO studentGroupsDAO) {
        this.studentGroupsDAO = studentGroupsDAO;
    }

    public StudentGroups findById(int groupId) {
            return studentGroupsDAO.findById(groupId);
    }

    public List<StudentGroups> findAllGroups() {
            return this.studentGroupsDAO.findAll();
    }
}
