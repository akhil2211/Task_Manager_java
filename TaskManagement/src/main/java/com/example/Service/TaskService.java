package com.example.Service;
import com.example.Model.*;
import com.example.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepo taskRepo;
       private final TaskHistoryRepo taskHistoryRepo;

    @Autowired

    public TaskService(TaskRepo taskRepo, ProjectRepo projectRepo, CategoryRepo categoryRepo, TaskCategoryRepo taskCategoryRepo, PriorityRepo priorityRepo, TaskPriorityRepo taskPriorityRepo, AssignmentRepo assignmentRepo, UserRepository userRepository, TaskHistoryRepo taskHistoryRepo) {
        this.taskRepo = taskRepo;
        this.taskHistoryRepo = taskHistoryRepo;
    }

    public Iterable<Task> getAllTasks() {
        return taskRepo.findAll();
    }

      public List<String> getTaskHistory(Integer taskId) {

      return taskHistoryRepo.getHistory(taskId);

    }

    public Task getTaskByTaskCode(String t_code) {
        return taskRepo.findByTaskCode(t_code);
    }

    public List<Task> getTaskByProject(Integer project_id) {
        return taskRepo.findByProject(project_id);
    }

    public List<Task> getTaskbyStatus(String tStatus) {
        return taskRepo.findByTaskStatus(tStatus);
    }


    public String editTaskStatus(Integer taskId, String newTaskStatus) {
        Task task=taskRepo.findById(taskId).orElse(null);
        task.setT_status(newTaskStatus);
        task.setModifiedAt(Timestamp.valueOf(LocalDateTime.now()));
        taskRepo.save(task);
        return "Task Status Changed";
    }
}
