package app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum Stage {

    CREATED("Created", "🆕"),
    IN_PROGRESS("In Progress", "🔄"),
    ABANDONED("Abandoned", "❌"),
    COMPLETED("Completed", "✅");

    private final String displayName;
    private final String emoji;


}
