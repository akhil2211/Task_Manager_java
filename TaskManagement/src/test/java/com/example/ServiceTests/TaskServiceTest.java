package com.example.ServiceTests;

import com.example.CustomContextHolder.AppContextHolder;
import com.example.Model.*;
import com.example.Repository.*;
import com.example.Service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class TaskServiceTest {

    @Mock
    private TaskRepo taskRepo;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskHistoryRepo taskHistoryRepo;

    @Mock
    private ProjectRepo projectRepo;

    @Mock
    private CategoryRepo categoryRepo;

    @Mock
    private TaskCategoryRepo taskCategoryRepo;

    @Mock
    private PriorityRepo priorityRepo;

    @Mock
    private AssignmentRepo assignmentRepo;

    @Mock
    private TaskPriorityRepo taskPriorityRepo;

    @InjectMocks
    private TaskService taskService;

    private User user;
    private Task task;
    private TaskRequest taskRequest;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1);
        user.setUsername("testUser");

        task = new Task();
        task.setId(1);
        task.setT_code("T001");
        task.setT_title("Test Task");
        task.setT_description("Test Description");

        taskRequest = new TaskRequest();
        taskRequest.setT_code("T002");
        taskRequest.setT_title("New Task");
        taskRequest.setT_description("New Description");
        taskRequest.setDuedate(null);
        taskRequest.setT_status("New");
        taskRequest.setProject_id(1);
        taskRequest.setAssignedto(2);
        taskRequest.setC_id(1);
        taskRequest.setPriority_id(1);
    }

    @Test
    void testCreateTask() {
        Role role1= new Role();
        role1.setId(2);
        Organization organization1=new Organization();
        organization1.setId(1);
        User user1= User.builder()
                .firstname("Karun")
                .lastname("M")
                .username("karun")
                .password("Karun@123")
                .email("karun@gmail.com")
                .role(role1)
                .organization(organization1)
                .build();
        Role role= new Role();
        role.setId(3);
        Organization organization=new Organization();
        organization.setId(1);
        User user2= User.builder()
                .firstname("Akhil")
                .lastname("Nair")
                .username("akhil")
                .password("Akhil@123")
                .email("akhil@gmail.com")
                .role(role)
                .organization(organization)
                .build();
        MockedStatic securityContext=mockStatic(AppContextHolder.class);
        when(AppContextHolder.getUserId()).thenReturn(user1.getId());
        when(AppContextHolder.getUserId()).thenReturn(user2.getId());
        when(userRepository.getUserRole(1)).thenReturn("GM");
        when(userRepository.getUserRole(2)).thenReturn("PM");
        when(projectRepo.findById(1)).thenReturn(Optional.of(new Project()));
        when(userRepository.findById(1)).thenReturn(Optional.of(user1));
        when(userRepository.findById(2)).thenReturn(Optional.of(user2));
        String result = taskService.createTask(taskRequest);
        assertEquals("Task Created Successfully", result);
    }

    @Test
    void testGetAllTasks() {
        List<Task> mockTaskList = new ArrayList<>();
        when(taskRepo.findAll()).thenReturn(mockTaskList);

        Iterable<Task> result = taskService.getAllTasks();
        assertEquals(mockTaskList, result);
    }

    @Test
    void testEditTask() {
        when(userRepository.getUserRole(1)).thenReturn("GM");
        when(userRepository.getUserRole(2)).thenReturn("Developer");
        when(taskRepo.findById(1)).thenReturn(Optional.of(task));

        String result = taskService.editTask(1, 2);
        assertEquals("Task Holder Edited", result);
    }

    @Test
    void testGetTaskHistory() {
        List<String> mockHistory = new ArrayList<>();
        when(taskHistoryRepo.getHistory(1)).thenReturn(mockHistory);

        List<String> result = taskService.getTaskHistory(1);
        assertEquals(mockHistory, result);
    }

    @Test
    void testGetTaskByTaskCode() {
        when(taskRepo.findByTaskCode("T001")).thenReturn(task);

        Task result = taskService.getTaskByTaskCode("T001");
        assertEquals(task, result);
    }

    @Test
    void testGetTaskByProject() {
        List<Task> mockTaskList = new ArrayList<>();
        when(taskRepo.findByProject(1)).thenReturn(mockTaskList);

        List<Task> result = taskService.getTaskByProject(1);
        assertEquals(mockTaskList, result);
    }

    @Test
    void testGetTaskbyStatus() {
        List<Task> mockTaskList = new ArrayList<>();
        when(taskRepo.findByTaskStatus("New")).thenReturn(mockTaskList);

        List<Task> result = taskService.getTaskbyStatus("New");
        assertEquals(mockTaskList, result);
    }

    @Test
    void testGetTaskbyAssignee() {
        User user1=new User();
        MockedStatic securityContext=mockStatic(AppContextHolder.class);
        when(AppContextHolder.getUserId()).thenReturn(user1.getId());
        when(taskRepo.findByAssignee(1)).thenReturn(new ArrayList<>());
        List<Task> result = taskService.getTaskbyAssignee();
        assertEquals(new ArrayList<>(), result);
    }

    @Test
    void testGetTaskbyAssigned() {
        User user=new User();
        MockedStatic securityContext=mockStatic(AppContextHolder.class);
        when(AppContextHolder.getUserId()).thenReturn(user.getId());
        when(taskRepo.findByAssigned(1)).thenReturn(new ArrayList<>());
        List<Task> result = taskService.getTaskbyAssigned();
        assertEquals(new ArrayList<>(), result);
    }

    @Test
    void testEditTaskStatus() {
        when(taskRepo.findById(1)).thenReturn(Optional.of(task));
        String result = taskService.editTaskStatus(1, "Completed");
        assertEquals("Task Status Changed", result);
    }
}