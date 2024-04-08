package com.example.WebLab3.Controller;

import com.example.WebLab3.Dto.ProjectDto;
import com.example.WebLab3.Service.ProjectService;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService projectService;

    //@Secured("ADMIN")
    @PostMapping
    public ResponseEntity<ProjectDto> createProject(@ModelAttribute ProjectDto newProject) {
        return new ResponseEntity<>(projectService.createProject(newProject), HttpStatus.CREATED);
    }

    //@Secured("ADMIN")
    @GetMapping("/createProjectForm")
    public String createProjectForm(Model model) {
        ProjectDto newProject = new ProjectDto();
        model.addAttribute("newProject", newProject);
        return "createProject";
    }

    //@Secured({"ADMIN", "USER"})
    @GetMapping("/{id}")
    public String getProjectForm(@PathVariable Long id, Model model) {
        ProjectDto projectDto = projectService.readProject(id);
        model.addAttribute("project", projectDto);
        return "getProject";
    }

    //@Secured({"ADMIN", "USER"})
    @GetMapping
    public ResponseEntity<List<ProjectDto>> getFilter(@RequestParam(name = "search", required = false) String search) {
        if (StringUtils.isBlank(search))
            return ResponseEntity.ok(projectService.readAllProjects());
        return ResponseEntity.ok(projectService.filter(search));
    }

    //@Secured({"ADMIN", "USER"})
    @GetMapping("/notClosed")
    public ResponseEntity<Map<Long, Integer>> getInf() {
        return ResponseEntity.ok(projectService.information());
    }

    //@Secured("ADMIN")
    @PostMapping("/{id}")
    public ResponseEntity<ProjectDto> updateProject(@PathVariable Long id, @ModelAttribute ProjectDto upProject) {
        return ResponseEntity.ok(projectService.updateProject(id, upProject));
    }

    @GetMapping("/{id}/updateProjectForm")
    public String updateProjectForm(Model model, @PathVariable Long id) {
        ProjectDto upProject = projectService.readProject(id);
        model.addAttribute("upProject", upProject);
        return "updateProject";
    }

    //@Secured("ADMIN")
    @PostMapping("/{id}/deleteProjectForm")
    public String deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return "redirect:/projects";
    }

    /*@GetMapping("/{id}/deleteProjectForm")
    public String deleteProjectForm(@PathVariable Long id) {
        ProjectDto deleteProject = projectService.readProject(id);

    }*/
}
