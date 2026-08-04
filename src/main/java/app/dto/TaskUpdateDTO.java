package app.dto;

import app.model.Stage;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;
import java.util.Set;

import java.time.LocalDate;

@Getter
@Setter
public class TaskUpdateDTO {

    private JsonNullable<String> name = JsonNullable.undefined();

    private JsonNullable<String> body = JsonNullable.undefined();

    private JsonNullable<Stage> stage = JsonNullable.undefined();

    private JsonNullable<LocalDate> dueDate = JsonNullable.undefined();

    private JsonNullable<Boolean> isCompleted = JsonNullable.undefined();

    private JsonNullable<Set<String>> builtInTags = JsonNullable.undefined();

}
