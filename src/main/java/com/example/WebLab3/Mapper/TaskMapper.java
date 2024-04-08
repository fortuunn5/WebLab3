package com.example.WebLab3.Mapper;

import com.example.WebLab3.Dto.TaskDto;
import com.example.WebLab3.Exception.ProjectNotFoundException;
import com.example.WebLab3.Model.Task;
import com.example.WebLab3.Repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskMapper {
    private final ProjectRepository projectRepository;
    public TaskDto map(Task task) {
        TaskDto taskDto = TaskDto.builder()
                .id(task.getId())
                .name(task.getName())
                .description(task.getDescription())
                .plannedDate(task.getPlannedDate())
                .isCompleted(task.isCompleted())
                .projectId(task.getProject().getId())
                .build();
        return taskDto;
    }
    public List<TaskDto> map(List<Task> tasks) {
        List<TaskDto> taskDtoList = new ArrayList<>();
        for(int i=0; i< tasks.size(); i++) {
            taskDtoList.add(map(tasks.get(i)));
        }
        return taskDtoList;
    }

    public List<Task> mapp(List<TaskDto> taskDtoList) {
        List<Task> tasks = new ArrayList<>();
        for(int i=0; i< tasks.size(); i++) {
            tasks.add(map(taskDtoList.get(i)));
        }
        return tasks;
    }

    public Task map(TaskDto taskDto) {
        Task task = Task.builder()
                .id(taskDto.getId())
                .name(taskDto.getName())
                .description(taskDto.getDescription())
                .plannedDate(taskDto.getPlannedDate())
                .isCompleted(taskDto.isCompleted())
                .project(projectRepository.findById(taskDto.getProjectId()).orElseThrow(ProjectNotFoundException::new))
                .build();
        return task;
    }
}
