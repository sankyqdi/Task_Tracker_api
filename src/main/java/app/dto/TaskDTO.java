package app.dto;

import app.model.Stage;
import app.model.TaskTag;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
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

}
