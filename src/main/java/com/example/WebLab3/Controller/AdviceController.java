package com.example.WebLab3.Controller;

import com.example.WebLab3.Exception.ProjectNotFoundException;
import com.example.WebLab3.Exception.TaskNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AdviceController {

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity NotFound(ProjectNotFoundException e) {
        return new ResponseEntity("Project not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity TaskNotFound(TaskNotFoundException e) {
        return new ResponseEntity("Task not found", HttpStatus.NOT_FOUND);
    }
}
