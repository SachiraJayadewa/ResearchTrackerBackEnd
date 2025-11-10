package lk.ijse.cmjd.researchtracker.milestone;

import jakarta.persistence.*;
import lk.ijse.cmjd.researchtracker.project.Project;
import lk.ijse.cmjd.researchtracker.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Milestone {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    private String title;
    private String description;
    private LocalDate dueDate;
    private Boolean isCompleted = false;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @PrePersist
    public void onCreate() {
        if (id == null) id = UUID.randomUUID().toString();
        if (isCompleted == null) isCompleted = false;
    }
}
