package app.model;

import lombok.Getter;

import java.util.stream.Stream;
import java.util.List;

@Getter
public enum TaskTag {
    BUG("Bug", "🐛"),
    FEATURE("Feature", "✨"),
    ENHANCEMENT("Enhancement", "⚡"),
    DOCUMENTATION("Documentation", "📚"),
    REFACTORING("Refactoring", "♻️"),
    TESTING("Testing", "🧪"),
    PERFORMANCE("Performance", "⚙️"),
    SECURITY("Security", "🔒"),
    URGENT("Urgent", "⚠️"),
    BLOCKED("Blocked", "🚫"),
    REVIEW("Review", "👀"),
    DEPLOYMENT("Deployment", "🚀"),
    BACKEND("Backend", "⬅️"),
    FRONTEND("Frontend", "➡️"),
    DEVOPS("DevOps", "🛠️");


    private final String displayName;
    private final String emoji;

    TaskTag(String displayName, String emoji) {
        this.displayName = displayName;
        this.emoji = emoji;
    }

    public static List<String> getAllTag() {

        return Stream.of(TaskTag.values())
                .map(TaskTag::getFormattedTag)
                .toList();

    }

    public String getFormattedTag() {
        return this.emoji + " " + this.displayName;
    }

    public static TaskTag fromString(String tagName) {

        if (tagName == null || tagName.isBlank()) {

            return null;

        }

        String cleanName = tagName.replaceAll("[^a-zA-Z]", "").trim().toUpperCase();

        try {

            return TaskTag.valueOf(cleanName);

        } catch (IllegalArgumentException e) {

            return null;

        }
    }
}
