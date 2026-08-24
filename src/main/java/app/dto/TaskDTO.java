package app.dto;

import app.model.Stage;
import app.model.TaskTag;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.dialect.function.array.AbstractArrayTrimFunction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class TaskDTO {

    private Long id;

    private String name;

    private String body;

    private Byte importanceLevel;

    private Stage stage;

    private Set<TaskTag> builtInTag;

    private Set<String> customTag;

    private LocalDate dueDate;

    public TaskDTO(Long id, String name, String body, Byte importanceLevel,
                   Stage stage, Set<String> customTag, LocalDate dueDate) {

        this.id = id;
        this.name = name;
        this.body = body;
        this.importanceLevel = importanceLevel;
        this.stage = stage;
        this.customTag = customTag;
        this.dueDate = dueDate;

    }

    public List<String> getCustomTags() {

        return new ArrayList<>(customTag);

    }

    public List<TaskTag> getBuiltInTags() {

        return new ArrayList<>(builtInTag);

    }

}
