package lk.ijse.cmjd.researchtracker.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    // ✅ ADMIN only: create project
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Project createProject(@RequestBody Project project) {
        return projectService.createProject(project);
    }

    // ✅ ADMIN or MEMBER: view all
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MEMBER')")
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    // ✅ ADMIN or MEMBER: view by id
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MEMBER')")
    public Optional<Project> getProjectById(@PathVariable String id) {
        return projectService.getProjectById(id);
    }

    // ✅ ADMIN only: delete
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteProject(@PathVariable String id) {
        projectService.deleteProject(id);
        return "Project deleted successfully!";
    }

    // ✅ ADMIN only: assign members
    @PostMapping("/{id}/assign")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Project assignMembers(@PathVariable String id, @RequestBody List<String> userIds) {
        return projectService.assignMembers(id, userIds);
    }
}
