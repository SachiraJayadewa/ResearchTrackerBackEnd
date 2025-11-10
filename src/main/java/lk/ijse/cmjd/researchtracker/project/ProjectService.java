package lk.ijse.cmjd.researchtracker.project;

import lk.ijse.cmjd.researchtracker.user.User;
import lk.ijse.cmjd.researchtracker.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepo;

    @Autowired
    private UserRepository userRepo;

    // ✅ Create a new project (auto-fill audit fields)
    public Project createProject(Project project) {
        project.setId(UUID.randomUUID().toString());
        project.setCreatedAt(LocalDateTime.now());
        project.setUpdatedAt(LocalDateTime.now());
        if (project.getStatus() == null) {
            project.setStatus(Status.PLANNING);
        }
        return projectRepo.save(project);
    }

    // ✅ Get all projects
    public List<Project> getAllProjects() {
        return projectRepo.findAll();
    }

    // ✅ Get a single project by ID
    public Optional<Project> getProjectById(String id) {
        return projectRepo.findById(id);
    }

    // ✅ Delete a project
    public void deleteProject(String id) {
        projectRepo.deleteById(id);
    }

    // ✅ Assign members (list of user IDs)
    public Project assignMembers(String projectId, List<String> userIds) {
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        List<User> members = userRepo.findAllById(userIds);
        project.setMembers(members);
        project.setUpdatedAt(LocalDateTime.now());

        return projectRepo.save(project);
    }

    // ✅ Assign or change Principal Investigator (PI)
    public Project assignPrincipalInvestigator(String projectId, String userId) {
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        User pi = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        project.setPi(pi);
        project.setUpdatedAt(LocalDateTime.now());

        return projectRepo.save(project);
    }

    // ✅ Update project status
    public Project updateStatus(String projectId, Status status) {
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        project.setStatus(status);
        project.setUpdatedAt(LocalDateTime.now());

        return projectRepo.save(project);
    }
}

