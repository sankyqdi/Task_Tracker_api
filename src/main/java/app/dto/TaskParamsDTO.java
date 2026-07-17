package app.dto;

import app.model.TaskTag;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class TaskParamsDTO {

    private String name;

    private String stage;

    private LocalDate dateBefore;

    private Byte importanceLevel;

    private Boolean isCompleted;

    private Set<TaskTag> builtInTags;

    private Set<String> customTags;

}
