package app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 70)
    @Column( length = 70)
    private String name;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String body;

    @NotNull
    private byte importanceLevel;

    @NotNull
    private String stage = "Created";

    @Column(name = "created_at")
    private LocalDate createdAt = LocalDate.now();

    @Column(name = "updated_at")
    private LocalDate updatedAt = LocalDate.now();

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "is_completed")
    private boolean isCompleted = false;

    @ElementCollection(targetClass = TaskTag.class, fetch = FetchType.LAZY)
    @CollectionTable(
            name = "task_built_in_tags",
            joinColumns = @JoinColumn(name = "task_id")

    )
    @Enumerated(EnumType.STRING)
    @Column(name = "tag_name")
    private Set<TaskTag> builtInTags = new HashSet<>();

    @ElementCollection(targetClass = String.class, fetch = FetchType.LAZY)
    @CollectionTable(
            name = "task_custom_tags",
            joinColumns = @JoinColumn(name = "task_id")
    )
    @Column(name = "tag_name")
    private Set<String> customTags = new HashSet<>();

}
