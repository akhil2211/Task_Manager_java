package com.example.Controller;

import com.example.Model.TaskRequest;
import com.example.Service.PmService;
import com.example.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/v1/pm")
public class PmController {

    private final TaskService taskService;
    private final PmService pmService;
    @Autowired
    public PmController(TaskService taskService, PmService pmService) {
        this.taskService = taskService;
        this.pmService = pmService;
    }
    @PostMapping("/create")
    public ResponseEntity<String> createTask(@RequestBody TaskRequest taskRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pmService.createTask(taskRequest));
    }
    @PostMapping("/{taskId}/editTask")
    public ResponseEntity<String> editTask(@PathVariable Integer taskId, @RequestBody Map<String,Integer> editRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(pmService.editTask(taskId,editRequest.get("newTaskHolderId")));
    }

}
