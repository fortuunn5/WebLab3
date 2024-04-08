package com.example.WebLab3.Mapper;

import com.example.WebLab3.Dto.ProjectDto;
import com.example.WebLab3.Model.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectMapper {
    private final TaskMapper taskMapper;
    public ProjectDto map(Project project) {
        ProjectDto projectDto = ProjectDto.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .tasks(taskMapper.map(project.getTasks()))
                .build();
        return projectDto;
    }
    public List<ProjectDto> map(List<Project> projects) {
        List<ProjectDto> projectDtoList = new ArrayList<>();
        for(int i=0; i< projects.size(); i++) {
            projectDtoList.add(map(projects.get(i)));
        }
        return projectDtoList;
    }

    public Project map(ProjectDto projectDto) {
        Project project = Project.builder()
                .id(projectDto.getId())
                .name(projectDto.getName())
                .description(projectDto.getDescription())
                .startDate(projectDto.getStartDate())
                .endDate(projectDto.getEndDate())
                .tasks(taskMapper.mapp(projectDto.getTasks()))
                .build();
        return project;
    }
}
