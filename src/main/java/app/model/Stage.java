package app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum Stage {

    CREATED("Created", "🆕"),
    IN_PROGRESS("In Progress", "🔄"),
    ABANDONED("Abandoned", "❌"),
    COMPLETED("Completed", "✅");

    private final String displayName;
    private final String emoji;

    public static List<String> getAll() {

        return Stream.of(Stage.values())
                .map(Stage::getStringStage)
                .toList();

    }

    public String getStringStage() {

        return this.emoji + " " + this.displayName;

    }

    public static Stage getStageFromString(String stage) {

        if (stage != null && !stage.trim().isEmpty()) {

            String stageName = stage.replaceFirst("[^a-zA-Z_]", "").trim().toUpperCase()
                    .replace(" ", "_");


            try {

                return Stage.valueOf(stageName);

            } catch (IllegalArgumentException e) {

                //Нужно что-то придумать. + логи
                return null;

            }
        }

        return null;

    }


}
