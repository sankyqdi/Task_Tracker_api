package app.dto;


import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class TaskCreatedDTO {

        @NotBlank
        @Size(max = 50)
        private String name;

        @NotBlank
        private String body;

        @NotNull
        @Min(1)
        @Max(10)
        private Byte importanceLevel;

        @FutureOrPresent
        private LocalDate dueDate;

        @NotNull
        @NotEmpty
        private Set<@NotBlank String> tags;

}
