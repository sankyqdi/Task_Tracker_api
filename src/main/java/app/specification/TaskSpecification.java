package app.specification;

import app.dto.TaskParamsDTO;
import app.model.Task;
import app.model.TaskTag;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Set;

@Component
public class TaskSpecification {

    public Specification<Task> build(TaskParamsDTO params) {

        if (params == null) {

            return (root, query, cb) -> cb.conjunction();

        }

        Specification<Task> spec = Specification.where(nameContains(params.getName()));
        spec = spec.and(levelContains(params.getImportanceLevel()));
        spec = spec.and(isCompletedContains(params.getIsCompleted()));
        spec = spec.and(stageContains(params.getStage()));
        spec = spec.and(tagsContains(params.getBuiltInTags()));
        spec = spec.and(customTagsContains(params.getCustomTags()));
        spec = spec.and(dueDateBefore(params.getDateBefore()));
        return spec;
    }

    public Specification<Task> nameContains(String title) {

        return (root, query, cb) -> {

            if (title == null || title.isEmpty()) {

                return cb.conjunction();

            }

            return cb.like(cb.lower(root.get("name")), "%" + title.toLowerCase() + "%");

        };
    }

    public Specification<Task> levelContains(Byte level) {

        return (root, query, cb) -> {

            if (level == null || level <= 0) {

                return cb.conjunction();

            }

            return cb.equal(root.get("importanceLevel"), level);

        };
    }

    public Specification<Task> isCompletedContains(Boolean isCompleted) {

        return (root, query, cb) -> {

            if (isCompleted == null) {

                return cb.conjunction();

            }

            return cb.equal(root.get("isCompleted"), isCompleted);

        };
    }

    public Specification<Task> stageContains(String stage) {

        return (root, query, cb) -> {

            if (stage == null || stage.isEmpty()) {

                return cb.conjunction();

            }

            return cb.like(cb.lower(root.get("stage")), "%" + stage.toLowerCase() + "%");

        };
    }

    public Specification<Task> tagsContains(Set<TaskTag> tags) {

        return (root, query, cb) -> {

            if (tags == null || tags.isEmpty()) {

                return cb.conjunction();

            }

            query.distinct(true);
            Join<Task, TaskTag> join = root.join("builtInTags");

            return join.in(tags);

        };
    }

    public Specification<Task> customTagsContains(Set<String> tags) {

        return (root, query, cb) -> {

            if (tags == null || tags.isEmpty()) {

                return cb.conjunction();

            }

            query.distinct(true);
            Join<Task, String> join = root.join("customTags");

            return join.in(tags);

        };
    }

    public Specification<Task> dueDateBefore(LocalDate date) {

        return (root, query, cb) -> {

            if (date == null) {

                return cb.conjunction();

            }

            return cb.lessThanOrEqualTo(root.get("dueDate"), date);

        };
    }
}
