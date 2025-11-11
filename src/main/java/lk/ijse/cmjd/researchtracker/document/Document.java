package lk.ijse.cmjd.researchtracker.document;

import jakarta.persistence.*;
import lk.ijse.cmjd.researchtracker.project.Project;
import lk.ijse.cmjd.researchtracker.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Document {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    private String title;
    private String description;
    private String urlOrPath;

    @ManyToOne
    @JoinColumn(name = "uploaded_by", nullable = false)
    private User uploadedBy;

    private LocalDateTime uploadedAt;

    @PrePersist
    public void onCreate() {
        if (id == null) id = UUID.randomUUID().toString();
        if (uploadedAt == null) uploadedAt = LocalDateTime.now();
    }
}

