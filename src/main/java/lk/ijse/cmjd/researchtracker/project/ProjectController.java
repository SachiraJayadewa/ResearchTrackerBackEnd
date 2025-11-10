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

    // ✅ ADMIN or MEMBER: get all projects
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MEMBER')")
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    // ✅ ADMIN or MEMBER: get project by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MEMBER')")
    public Optional<Project> getProjectById(@PathVariable String id) {
        return projectService.getProjectById(id);
    }

    // ✅ ADMIN only: delete project
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

    // ✅ ADMIN only: assign/change Principal Investigator
    @PostMapping("/{id}/assign-pi/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Project assignPI(@PathVariable String id, @PathVariable String userId) {
        return projectService.assignPrincipalInvestigator(id, userId);
    }

    // ✅ ADMIN only: update project status
    @PutMapping("/{id}/status")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Project updateStatus(@PathVariable String id, @RequestParam Status status) {
        return projectService.updateStatus(id, status);
    }
}
