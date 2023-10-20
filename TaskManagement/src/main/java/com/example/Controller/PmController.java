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

    private final PmService pmService;
    @Autowired
    public PmController(TaskService taskService, PmService pmService) {
         this.pmService = pmService;
    }



}
