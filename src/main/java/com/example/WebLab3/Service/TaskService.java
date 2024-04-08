package com.example.WebLab3.Service;

import com.example.WebLab3.Dto.TaskDto;
import com.example.WebLab3.Exception.ProjectNotFoundException;
import com.example.WebLab3.Exception.TaskNotFoundException;
import com.example.WebLab3.Mapper.ProjectMapper;
import com.example.WebLab3.Mapper.TaskMapper;
import com.example.WebLab3.Model.Project;
import com.example.WebLab3.Model.Task;
import com.example.WebLab3.Repository.ProjectRepository;
import com.example.WebLab3.Repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final TaskMapper taskMapper;

    public TaskDto createTask(Long id, TaskDto newTaskDto) {
        newTaskDto.setId(null);
        newTaskDto.setProjectId(projectRepository.findById(id).orElseThrow(ProjectNotFoundException::new).getId());
        Task newTask=taskMapper.map(newTaskDto);
        newTask = taskRepository.save(newTask);
        newTaskDto=taskMapper.map(newTask);
        return newTaskDto;
    }

    public TaskDto readTask(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);
        return taskMapper.map(task);
    }

    public List<TaskDto> readAllTaskByProject(Long id) {
        List<Task> tasks = projectRepository.findById(id).orElseThrow(ProjectNotFoundException::new).getTasks();
        return taskMapper.map(tasks);
    }

    public TaskDto readTaskByProjectIdAndTaskId(Long projectId, Long taskId) {
        Project project = projectRepository.findById(projectId).orElseThrow(ProjectNotFoundException::new);
        List<Task> tasks = project.getTasks();
        for (Task task : tasks) {
            if (task.getId().equals(taskId)) {
                return taskMapper.map(task);
            }
        }
        throw new TaskNotFoundException();
    }

    public List<TaskDto> readAllTasks() {
        return taskMapper.map(taskRepository.findAll());
    }

    public TaskDto updateTask(Long projectId, Long taskId, TaskDto upTask) {
        TaskDto taskDto = readTaskByProjectIdAndTaskId(projectId, taskId);
        upTask.setId(taskId);
        upTask.setProjectId(taskDto.getProjectId());
        Task task = taskRepository.save(taskMapper.map(upTask));
        return taskMapper.map(task);
    }

    public void deleteTask(Long projectId, Long taskId) {
        taskRepository.deleteById(readTaskByProjectIdAndTaskId(projectId, taskId).getId());
    }

    public void deleteCompletedTasksByProject(Long id) {
        Project project = projectRepository.findById(id).orElseThrow(ProjectNotFoundException::new);
        List<Task> tasks = project.getTasks();
        for (Task task : tasks) {
            if (task.isCompleted())
                taskRepository.deleteById(task.getId());
        }
    }

    public void deleteTasksByProject(Long id) {
        Project project = projectRepository.findById(id).orElseThrow(ProjectNotFoundException::new);
        List<Task> tasks = project.getTasks();
        for (Task task : tasks) {
            taskRepository.deleteById(task.getId());
        }
    }
}
