package com.example.WebLab3.Service;

import com.example.WebLab3.Dto.ProjectDto;
import com.example.WebLab3.Exception.ProjectNotFoundException;
import com.example.WebLab3.Mapper.ProjectMapper;
import com.example.WebLab3.Model.Project;
import com.example.WebLab3.Repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final TaskService taskService;
    private final ProjectMapper projectMapper;


    public ProjectDto createProject(ProjectDto newProjectDto) {
        newProjectDto.setId(null);
        newProjectDto.setTasks(null);
        Project newProject = projectMapper.map(newProjectDto);
        newProject=projectRepository.save(newProject);
        newProjectDto=projectMapper.map(newProject);
        return newProjectDto;
    }

    public ProjectDto readProject(Long id) {
        Project project = projectRepository.findById(id).orElseThrow(ProjectNotFoundException::new);
        return projectMapper.map(project);
    }

    public List<ProjectDto> readAllProjects() {
        List<Project> projects = projectRepository.findAll();
        return projectMapper.map(projects);
    }

    public List<ProjectDto> filter(String search) {
        List<Project> projects = projectRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(search, search);
        return projectMapper.map(projects);
    }

    public ProjectDto updateProject(Long id, ProjectDto upProject) {
        Project project = projectRepository.findById(id).orElseThrow(ProjectNotFoundException::new);
        project.setName(upProject.getName());
        project.setDescription(upProject.getDescription());
        project.setStartDate(upProject.getStartDate());
        project.setEndDate(upProject.getEndDate());
        project = projectRepository.save(project);
        return projectMapper.map(project);
    }

    public void deleteProject(Long id) {
        taskService.deleteTasksByProject(id);
        projectRepository.deleteById(id);
    }

    public Map<Long, Integer> information() {
        List<Map<String, Object>> a= projectRepository.map();
        Map<Long, Integer> result = a.stream()
                .collect(Collectors.toMap
                        (s -> Long.parseLong(String.valueOf(s.get("id"))),
                         s -> Integer.parseInt(String.valueOf(s.get("count")))
                        ));
        //Map<Long, Integer> result = new HashMap<>();
        //for (Map<String, Object> stringObjectMap : a) {
        //    result.put(Long.parseLong(String.valueOf(stringObjectMap.get("id"))), Integer.parseInt(String.valueOf(stringObjectMap.get("count"))));
        //}
        return result;
    }
}
