package com.example.WebLab3.Repository;

import com.example.WebLab3.Model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String search, String text);

    @Query("select project.id as id, \n" +
            "(select count(*) from Task task\n" +
            "where not task.isCompleted and task.project.id= project.id) as count \n" +
            "from Project project")
    List<Map<String, Object>> map();
}
