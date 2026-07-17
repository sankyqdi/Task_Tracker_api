package app.dto;

import app.model.TaskTag;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class TaskDTO {

    private Long id;

    private String name;

    private String body;

    private Byte importanceLevel;

    private String stage;

    private Set<TaskTag> builtInTag;

    private Set<String> customTag;

}
