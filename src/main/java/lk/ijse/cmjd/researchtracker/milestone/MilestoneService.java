package lk.ijse.cmjd.researchtracker.milestone;

import lk.ijse.cmjd.researchtracker.project.Project;
import lk.ijse.cmjd.researchtracker.project.ProjectRepository;
import lk.ijse.cmjd.researchtracker.user.User;
import lk.ijse.cmjd.researchtracker.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MilestoneService {

    @Autowired
    private MilestoneRepository milestoneRepo;

    @Autowired
    private ProjectRepository projectRepo;

    @Autowired
    private UserRepository userRepo;

    public Milestone createMilestone(Milestone milestone, String projectId, String createdById) {
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        User createdBy = userRepo.findById(createdById)
                .orElseThrow(() -> new RuntimeException("User not found"));

        milestone.setId(UUID.randomUUID().toString());
        milestone.setProject(project);
        milestone.setCreatedBy(createdBy);
        milestone.setIsCompleted(false);
        return milestoneRepo.save(milestone);
    }

    public List<Milestone> getAllMilestones() {
        return milestoneRepo.findAll();
    }

    public List<Milestone> getMilestonesByProject(String projectId) {
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        return milestoneRepo.findByProject(project);
    }

    public Optional<Milestone> getMilestoneById(String id) {
        return milestoneRepo.findById(id);
    }

    public Milestone markAsCompleted(String milestoneId) {
        Milestone milestone = milestoneRepo.findById(milestoneId)
                .orElseThrow(() -> new RuntimeException("Milestone not found"));
        milestone.setIsCompleted(true);
        return milestoneRepo.save(milestone);
    }

    public void deleteMilestone(String id) {
        milestoneRepo.deleteById(id);
    }
}
