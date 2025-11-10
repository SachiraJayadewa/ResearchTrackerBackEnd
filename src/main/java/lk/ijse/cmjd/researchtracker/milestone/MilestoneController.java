package lk.ijse.cmjd.researchtracker.milestone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/milestones")
public class MilestoneController {

    @Autowired
    private MilestoneService milestoneService;

    // ✅ Create milestone (Admin or PI)
    @PostMapping("/{projectId}/create/{createdById}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PI')")
    public Milestone createMilestone(@PathVariable String projectId,
                                     @PathVariable String createdById,
                                     @RequestBody Milestone milestone) {
        return milestoneService.createMilestone(milestone, projectId, createdById);
    }

    // ✅ View all milestones (Admin or Member)
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MEMBER', 'PI')")
    public List<Milestone> getAllMilestones() {
        return milestoneService.getAllMilestones();
    }

    // ✅ View milestones for a specific project
    @GetMapping("/project/{projectId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MEMBER', 'PI')")
    public List<Milestone> getMilestonesByProject(@PathVariable String projectId) {
        return milestoneService.getMilestonesByProject(projectId);
    }

    // ✅ View milestone by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MEMBER', 'PI')")
    public Optional<Milestone> getMilestoneById(@PathVariable String id) {
        return milestoneService.getMilestoneById(id);
    }

    // ✅ Mark milestone as completed
    @PutMapping("/{id}/complete")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PI')")
    public Milestone markAsCompleted(@PathVariable String id) {
        return milestoneService.markAsCompleted(id);
    }

    // ✅ Delete milestone
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteMilestone(@PathVariable String id) {
        milestoneService.deleteMilestone(id);
        return "Milestone deleted successfully!";
    }
}

