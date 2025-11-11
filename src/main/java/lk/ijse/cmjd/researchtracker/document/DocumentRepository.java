package lk.ijse.cmjd.researchtracker.document;

import lk.ijse.cmjd.researchtracker.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, String> {
    List<Document> findByProject(Project project);
}

