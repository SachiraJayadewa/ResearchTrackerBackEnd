package lk.ijse.cmjd.researchtracker.project;

import lk.ijse.cmjd.researchtracker.user.User;
import lk.ijse.cmjd.researchtracker.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepo;

    @Autowired
    private UserRepository userRepo;

    public Project createProject(Project project) {
        project.setId(UUID.randomUUID().toString());
        return projectRepo.save(project);
    }

    public List<Project> getAllProjects() {
        return projectRepo.findAll();
    }

    public Optional<Project> getProjectById(String id) {
        return projectRepo.findById(id);
    }

    public void deleteProject(String id) {
        projectRepo.deleteById(id);
    }

    public Project assignMembers(String projectId, List<String> userIds) {
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        List<User> members = userRepo.findAllById(userIds);
        project.setMembers(members);
        return projectRepo.save(project);
    }
}
