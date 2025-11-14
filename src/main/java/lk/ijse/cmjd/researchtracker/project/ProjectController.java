package lk.ijse.cmjd.researchtracker.project;

import lk.ijse.cmjd.researchtracker.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    //  ADMIN only: create project
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Project createProject(@RequestBody Project project) {
        return projectService.createProject(project);
    }

    //  ADMIN or MEMBER: get all projects
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MEMBER')")
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    //  ADMIN or MEMBER: get project by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MEMBER')")
    public Optional<Project> getProjectById(@PathVariable String id) {
        return projectService.getProjectById(id);
    }

    //  ADMIN only: delete project
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteProject(@PathVariable String id) {
        projectService.deleteProject(id);
        return "Project deleted successfully!";
    }

    //  ADMIN only: assign members
    @PostMapping("/{id}/assign")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Project assignMembers(@PathVariable String id, @RequestBody List<String> userIds) {
        return projectService.assignMembers(id, userIds);
    }

    //  ADMIN only: assign/change Principal Investigator
    @PostMapping("/{id}/assign-pi/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Project assignPI(@PathVariable String id, @PathVariable String userId) {
        return projectService.assignPrincipalInvestigator(id, userId);
    }

    //  ADMIN only: update project status
    @PutMapping("/{id}/status")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Project updateStatus(@PathVariable String id, @RequestParam Status status) {
        return projectService.updateStatus(id, status);
    }

    // -------------------------
    // New: Project Members API
    // -------------------------

    /**
     * Add a member to a project.
     * - ADMIN can add to any project.
     * - PI can add only to projects where they are the PI.
     */
    @PostMapping("/{projectId}/members/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','PI')")
    public ResponseEntity<String> addMember(@PathVariable String projectId, @PathVariable String userId) {
        projectService.addMemberToProject(projectId, userId);
        return ResponseEntity.ok("Member added successfully");
    }

    /**
     * Remove a member from a project.
     * - ADMIN can remove from any project.
     * - PI can remove only from projects where they are the PI.
     */
    @DeleteMapping("/{projectId}/members/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','PI')")
    public ResponseEntity<String> removeMember(@PathVariable String projectId, @PathVariable String userId) {
        projectService.removeMemberFromProject(projectId, userId);
        return ResponseEntity.ok("Member removed successfully");
    }

    /**
     * Get members of a project.
     * - ADMIN can view any project's members.
     * - PI can view members if they are the PI of the project.
     * - MEMBER can view if they are a member of the project.
     */
    @GetMapping("/{projectId}/members")
    @PreAuthorize("hasAnyAuthority('ADMIN','PI','MEMBER')")
    public List<User> getProjectMembers(@PathVariable String projectId) {
        return projectService.getMembers(projectId);
    }
}
