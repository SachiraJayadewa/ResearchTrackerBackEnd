package lk.ijse.cmjd.researchtracker.project;

import lk.ijse.cmjd.researchtracker.user.User;
import lk.ijse.cmjd.researchtracker.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepo;

    @Autowired
    private UserRepository userRepo;

    //  Create a new project (auto-fill audit fields)
    public Project createProject(Project project) {
        project.setId(UUID.randomUUID().toString());
        project.setCreatedAt(LocalDateTime.now());
        project.setUpdatedAt(LocalDateTime.now());
        if (project.getStatus() == null) {
            project.setStatus(Status.PLANNING);
        }
        return projectRepo.save(project);
    }

    //  Get all projects
    public List<Project> getAllProjects() {
        return projectRepo.findAll();
    }

    //  Get a single project by ID
    public Optional<Project> getProjectById(String id) {
        return projectRepo.findById(id);
    }

    //  Delete a project
    public void deleteProject(String id) {
        projectRepo.deleteById(id);
    }

    //  Assign members (list of user IDs)
    public Project assignMembers(String projectId, List<String> userIds) {
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        List<User> members = userRepo.findAllById(userIds);
        project.setMembers(members);
        project.setUpdatedAt(LocalDateTime.now());

        return projectRepo.save(project);
    }

    //  Assign or change Principal Investigator (PI)
    public Project assignPrincipalInvestigator(String projectId, String userId) {
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        User pi = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        project.setPi(pi);
        project.setUpdatedAt(LocalDateTime.now());

        return projectRepo.save(project);
    }

    //  Update project status
    public Project updateStatus(String projectId, Status status) {
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        project.setStatus(status);
        project.setUpdatedAt(LocalDateTime.now());

        return projectRepo.save(project);
    }

    // -------------------------
    // New: members management
    // -------------------------

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) throw new RuntimeException("Unauthenticated");
        String username = auth.getName();
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Authenticated user not found"));
    }

    /**
     * Add a member to the project.
     * - ADMIN can add to any project.
     * - PI can add only if they are the project's PI.
     */
    public void addMemberToProject(String projectId, String userId) {
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        User userToAdd = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        User current = getCurrentUser();

        // If current user is PI, verify ownership
        if (current.getRole() != null && current.getRole().name().equals("PI")) {
            if (project.getPi() == null || !project.getPi().getId().equals(current.getId())) {
                throw new RuntimeException("PI can only modify their own projects");
            }
        }

        // Add if not already present
        if (project.getMembers() == null) project.setMembers(new ArrayList<>());
        boolean already = project.getMembers().stream().anyMatch(u -> u.getId().equals(userToAdd.getId()));
        if (!already) {
            project.getMembers().add(userToAdd);
            project.setUpdatedAt(LocalDateTime.now());
            projectRepo.save(project);
        }
    }

    /**
     * Remove a member from the project.
     * - ADMIN can remove from any project.
     * - PI can remove only if they are the project's PI.
     */
    public void removeMemberFromProject(String projectId, String userId) {
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        User current = getCurrentUser();

        // If current user is PI, verify ownership
        if (current.getRole() != null && current.getRole().name().equals("PI")) {
            if (project.getPi() == null || !project.getPi().getId().equals(current.getId())) {
                throw new RuntimeException("PI can only modify their own projects");
            }
        }

        if (project.getMembers() != null) {
            project.getMembers().removeIf(u -> u.getId().equals(userId));
            project.setUpdatedAt(LocalDateTime.now());
            projectRepo.save(project);
        }
    }

    /**
     * Return members of a project.
     * - ADMIN can see any project's members.
     * - PI can see if they are project's PI.
     * - MEMBER can see if they are in the project.
     */
    public List<User> getMembers(String projectId) {
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        User current = getCurrentUser();

        // ADMIN can see everything
        if (current.getRole() != null && current.getRole().name().equals("ADMIN")) {
            return project.getMembers() == null ? List.of() : project.getMembers();
        }

        // PI: only if they are the PI
        if (current.getRole() != null && current.getRole().name().equals("PI")) {
            if (project.getPi() != null && project.getPi().getId().equals(current.getId())) {
                return project.getMembers() == null ? List.of() : project.getMembers();
            } else {
                throw new RuntimeException("PI can only view members for their own projects");
            }
        }

        // MEMBER: only if they belong to the project
        if (current.getRole() != null && current.getRole().name().equals("MEMBER")) {
            boolean isMember = project.getMembers() != null &&
                    project.getMembers().stream().anyMatch(u -> u.getId().equals(current.getId()));
            if (isMember) {
                return project.getMembers();
            } else {
                throw new RuntimeException("You are not a member of this project");
            }
        }

        // Default: deny
        throw new RuntimeException("Access denied");
    }
}
