package lk.ijse.cmjd.researchtracker.document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    // ✅ Upload document (Admin or PI)
    @PostMapping("/{projectId}/upload/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PI')")
    public Document uploadDocument(@PathVariable String projectId,
                                   @PathVariable String userId,
                                   @RequestBody Document document) {
        return documentService.uploadDocument(document, projectId, userId);
    }

    // ✅ View all documents (Admin or Member)
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PI', 'MEMBER')")
    public List<Document> getAllDocuments() {
        return documentService.getAllDocuments();
    }

    // ✅ View documents for a project
    @GetMapping("/project/{projectId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PI', 'MEMBER')")
    public List<Document> getDocumentsByProject(@PathVariable String projectId) {
        return documentService.getDocumentsByProject(projectId);
    }

    // ✅ View single document
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PI', 'MEMBER')")
    public Optional<Document> getDocumentById(@PathVariable String id) {
        return documentService.getDocumentById(id);
    }

    // ✅ Delete document (Admin only)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteDocument(@PathVariable String id) {
        documentService.deleteDocument(id);
        return "Document deleted successfully!";
    }
}

