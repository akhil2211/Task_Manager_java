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
    private final ProjectRepo projectRepo;
    private final CategoryRepo categoryRepo;

    private final TaskCategoryRepo taskCategoryRepo;
    private final PriorityRepo priorityRepo;
    private final TaskPriorityRepo taskPriorityRepo;

    private final AssignmentRepo assignmentRepo;
    private final UserRepository userRepository;

    private final TaskHistoryRepo taskHistoryRepo;

    @Autowired

    public TaskService(TaskRepo taskRepo, ProjectRepo projectRepo, CategoryRepo categoryRepo, TaskCategoryRepo taskCategoryRepo, PriorityRepo priorityRepo, TaskPriorityRepo taskPriorityRepo, AssignmentRepo assignmentRepo, UserRepository userRepository, TaskHistoryRepo taskHistoryRepo) {
        this.taskRepo = taskRepo;
        this.projectRepo = projectRepo;
        this.categoryRepo = categoryRepo;
        this.taskCategoryRepo = taskCategoryRepo;
        this.priorityRepo = priorityRepo;
        this.taskPriorityRepo = taskPriorityRepo;
        this.assignmentRepo = assignmentRepo;
        this.userRepository = userRepository;
        this.taskHistoryRepo = taskHistoryRepo;
    }

    public Iterable<Task> getAllTasks() {
        return taskRepo.findAll();
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

    public String editTaskStatus(Integer taskId, String newTaskStatus) {
        Task task=taskRepo.findById(taskId).orElse(null);
        task.setT_status(newTaskStatus);
        task.setModifiedAt(Timestamp.valueOf(LocalDateTime.now()));
        taskRepo.save(task);
        return "Task Status Changed";
    }
}
