package lk.ijse.cmjd.researchtracker.project;

import jakarta.persistence.*;
import lk.ijse.cmjd.researchtracker.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    @Id
    private String id;

    // Coursework: Project title
    private String title;

    // Coursework: Short description of the project
    @Column(length = 1000)
    private String summary;

    // Coursework: Project status (PLANNING, ACTIVE, etc.)
    @Enumerated(EnumType.STRING)
    private Status status;

    // Coursework: Linked Principal Investigator
    @ManyToOne
    @JoinColumn(name = "pi_id")
    private User pi;

    // Coursework: Comma-separated tags (e.g., “AI, environment”)
    private String tags;

    // Coursework: Start and end dates
    private LocalDate startDate;
    private LocalDate endDate;

    // Coursework: Audit fields
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Retained: Members relationship (useful for team assignment)
    @ManyToMany
    @JoinTable(
            name = "project_members",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> members;

    // Auto-generate timestamps & ID
    @PrePersist
    protected void onCreate() {
        this.id = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.status == null) this.status = Status.PLANNING;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
