package app.model;

import lombok.Getter;

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

    public String getFormattedTag() {
        return this.emoji + " " + this.displayName;
    }

    public static TaskTag fromString(String tagName) {
        try {
            return TaskTag.valueOf(tagName.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
