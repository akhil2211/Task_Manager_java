package com.example.Service;

import com.example.Model.*;
import com.example.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service

public class PmService {
    private final AssignmentRepo assignmentRepo;
    private final TaskRepo taskRepo;

    private final UserRepository userRepository;

       private final TaskHistoryRepo taskHistoryRepo;

    @Autowired
    public PmService(ProjectRepo projectRepo, TaskRepo taskRepo, UserRepository userRepository, CategoryRepo categoryRepo, PriorityRepo priorityRepo, AssignmentRepo assignmentRepo, TaskCategoryRepo taskCategoryRepo, TaskPriorityRepo taskPriorityRepo, AssignmentRepo assignmentRepo1, TaskHistoryRepo taskHistoryRepo) {
        this.assignmentRepo = assignmentRepo1;
        this.taskRepo = taskRepo;
        this.userRepository = userRepository;
        this.taskHistoryRepo = taskHistoryRepo;
    }
}
