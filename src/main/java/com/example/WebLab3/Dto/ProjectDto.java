package com.example.WebLab3.Dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {
    private Long id;
    private String name;
    private String description;
    @Builder.Default
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date startDate = new Date();

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date endDate;
    @Builder.Default
    private List<TaskDto> tasks = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectDto projectDto = (ProjectDto) o;
        return id.equals(projectDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
