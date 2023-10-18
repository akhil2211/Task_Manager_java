package com.example.Service;

import com.example.Model.*;
import com.example.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service

public class PmService {

    private final ProjectRepo projectRepo;

    private final TaskRepo taskRepo;

    private final UserRepository userRepository;

    private final CategoryRepo categoryRepo;

    private final PriorityRepo priorityRepo;

    private final AssignmentRepo assignmentRepo;
    private final TaskCategoryRepo taskCategoryRepo;

    private final TaskPriorityRepo taskPriorityRepo;

    private final TaskHistoryRepo taskHistoryRepo;

    @Autowired
    public PmService(ProjectRepo projectRepo, TaskRepo taskRepo, UserRepository userRepository, CategoryRepo categoryRepo, PriorityRepo priorityRepo, AssignmentRepo assignmentRepo, TaskCategoryRepo taskCategoryRepo, TaskPriorityRepo taskPriorityRepo, TaskHistoryRepo taskHistoryRepo) {
        this.projectRepo = projectRepo;
        this.taskRepo = taskRepo;
        this.userRepository = userRepository;
        this.categoryRepo = categoryRepo;
        this.priorityRepo = priorityRepo;
        this.assignmentRepo = assignmentRepo;
        this.taskCategoryRepo = taskCategoryRepo;
        this.taskPriorityRepo = taskPriorityRepo;
        this.taskHistoryRepo = taskHistoryRepo;
    }

    public String createTask(TaskRequest task) {
        Task taskdata = new Task();
        taskdata.setT_code(task.getT_code());
        taskdata.setT_title(task.getT_title());
        taskdata.setT_description(task.getT_description());
        taskdata.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        taskdata.setDuedate((Date) task.getDuedate());
        taskdata.setT_status(task.getT_status());
        Project project = projectRepo.findById(task.getProject_id()).orElse(null);
        taskdata.setProject(project);
        taskRepo.save(taskdata);


        Assignment assignment = new Assignment();
        User assignto = userRepository.findById(task.getAssignedto()).orElse(null);
        assignment.setAssigned_to(assignto);

        User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer currentUserId = userDetails.getId();

        User assignee = userRepository.findById(currentUserId).orElse(null);
        assignment.setAssignee_id(assignee);
        assignment.setTask(taskdata);
        assignmentRepo.save(assignment);


        TaskCategory taskCategory = new TaskCategory();
        Category cat = categoryRepo.findById(task.getC_id()).orElse(null);
        taskCategory.setTask(taskdata);
        taskCategory.setCategory(cat);
        taskCategoryRepo.save(taskCategory);

        TaskPriority taskPriority = new TaskPriority();
        Priority prior = priorityRepo.findById(task.getPriority_id()).orElse(null);
        taskPriority.setTask(taskdata);
        taskPriority.setPriority(prior);
        taskPriorityRepo.save(taskPriority);

        return "Task Created Successfully";
    }
    public String editTask(Integer newTaskId, Integer taskHolderId) {
        Task task= taskRepo.findById(newTaskId).orElse(null);
        User user=userRepository.findById(taskHolderId).orElse(null);
        TaskHistory taskHistory=new TaskHistory();
        taskHistory.setTask(task);
        Assignment assignment=assignmentRepo.findAssignmentByTask(newTaskId).orElse(null);
        taskHistory.setPrevioususer(assignment.getAssigned_to());
        taskHistory.setUnassigned_at(Timestamp.valueOf(LocalDateTime.now()));
        taskHistoryRepo.save(taskHistory);
        assignment.setAssigned_to(user);
        assignmentRepo.save(assignment);
        return "Task Edited";

    }
}
