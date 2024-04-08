package com.example.WebLab3.Controller;

import com.example.WebLab3.Dto.TaskDto;
import com.example.WebLab3.Service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/projects")
public class TaskController {
    private final TaskService taskService;

    //@Secured({"ADMIN", "USER"})
    @PostMapping("/{projectId}/tasks")
    public String createTask(@PathVariable Long projectId, @ModelAttribute TaskDto newTask) {
        taskService.createTask(projectId, newTask);
        return "redirect:/projects/" + projectId + "/tasks";
        //return new ResponseEntity<TaskDto>(taskService.createTask(projectId, newTask), HttpStatus.CREATED);
    }

    @GetMapping("/{projectId}/tasks/createTaskForm")
    public String createTaskForm(@PathVariable Long projectId, Model model) {
        TaskDto newTask = new TaskDto();
        newTask.setProjectId(projectId);
        model.addAttribute("newTask", newTask);
        return "createTask";
    }


    //@Secured({"ADMIN", "USER"})
    @GetMapping("/{projectId}/tasks/{taskId}")
    public String getTaskForm(@PathVariable Long projectId, @PathVariable Long taskId, Model model) {
        TaskDto taskDto = taskService.readTaskByProjectIdAndTaskId(projectId, taskId);
        model.addAttribute("task", taskDto);
        return "getTask";
        //return ResponseEntity.ok(taskService.readTaskByProjectIdAndTaskId(projectId, taskId));
    }



    //@Secured({"ADMIN", "USER"})
    @GetMapping("/{projectId}/tasks")
    public ResponseEntity<List<TaskDto>> getAllTasks(@PathVariable Long projectId) {
        return ResponseEntity.ok(taskService.readAllTaskByProject(projectId));
    }

    //@Secured({"ADMIN", "USER"})
    @PostMapping("/{projectId}/tasks/{taskId}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable Long projectId, @PathVariable Long taskId, @ModelAttribute TaskDto upTask) {
        return ResponseEntity.ok(taskService.updateTask(projectId, taskId, upTask));
    }

    @GetMapping("/{projectId}/tasks/{taskId}/updateTaskForm")
    public String updateTaskForm(@PathVariable Long projectId, @PathVariable Long taskId, Model model) {
        TaskDto upTask = taskService.readTaskByProjectIdAndTaskId(projectId, taskId);
        model.addAttribute("upTask", upTask);
        return "updateTask";
    }

    //@Secured({"ADMIN", "USER"})
    @PostMapping("/{projectId}/tasks/{taskId}/deleteTaskForm")
    public String deleteTask(@PathVariable Long projectId, @PathVariable Long taskId) {
        taskService.deleteTask(projectId, taskId);
        return "redirect:/projects/" + projectId + "/tasks";
        //return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    //@Secured({"ADMIN", "USER"})
    @DeleteMapping("/{projectId}/tasks")
    public ResponseEntity deleteCompletedTasks(@PathVariable Long projectId) {
        taskService.deleteCompletedTasksByProject(projectId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
