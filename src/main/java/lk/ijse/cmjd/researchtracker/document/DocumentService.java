package lk.ijse.cmjd.researchtracker.document;

import lk.ijse.cmjd.researchtracker.project.Project;
import lk.ijse.cmjd.researchtracker.project.ProjectRepository;
import lk.ijse.cmjd.researchtracker.user.User;
import lk.ijse.cmjd.researchtracker.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepo;

    @Autowired
    private ProjectRepository projectRepo;

    @Autowired
    private UserRepository userRepo;

    public Document uploadDocument(Document document, String projectId, String userId) {
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        User uploadedBy = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        document.setId(UUID.randomUUID().toString());
        document.setProject(project);
        document.setUploadedBy(uploadedBy);
        document.setUploadedAt(LocalDateTime.now());
        return documentRepo.save(document);
    }

    public List<Document> getAllDocuments() {
        return documentRepo.findAll();
    }

    public List<Document> getDocumentsByProject(String projectId) {
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        return documentRepo.findByProject(project);
    }

    public Optional<Document> getDocumentById(String id) {
        return documentRepo.findById(id);
    }

    public void deleteDocument(String id) {
        documentRepo.deleteById(id);
    }
}

