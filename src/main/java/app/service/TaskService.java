package app.service;

import app.dto.TaskCreatedDTO;
import app.dto.TaskDTO;
import app.dto.TaskParamsDTO;
import app.dto.TaskUpdateDTO;
import app.mapper.TaskMapper;
import app.model.Task;
import app.model.TaskTag;
import app.repository.TaskRepository;
import app.specification.TaskSpecification;
import lombok.AllArgsConstructor;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskService implements TaskServiceInterface {

    private final TaskRepository taskRepository;
    private final TaskSpecification taskSpecification;
    private final TaskMapper taskMapper;

    @Transactional(readOnly = true)
    public List<TaskDTO> getAll(TaskParamsDTO params) {

        Specification<Task> spec = taskSpecification.build(params);
        return taskRepository.findAll(spec)
                .stream()
                .map(taskMapper::map)
                .toList();
    }


    public TaskDTO getById(Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, " Task from " + id + " not Found"
                ));

        return taskMapper.map(task);

    }

    public TaskDTO create(TaskCreatedDTO taskCreatedDTO) {

        Task task = taskMapper.map(taskCreatedDTO);
        enrichTaskTag(task, taskCreatedDTO.getTags());

        taskRepository.save(task);

        return taskMapper.map(task);

    }

    public TaskDTO update(Long id, TaskUpdateDTO taskUpdateDTO) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, " Task from " + id + " not Found"
        ));

        taskMapper.update(taskUpdateDTO, task);

        enrichTaskJsonNullable(task, taskUpdateDTO.getName(),
                taskUpdateDTO.getBody(), taskUpdateDTO.getStage(),
                taskUpdateDTO.getDueDate(), taskUpdateDTO.getIsCompleted(),
                taskUpdateDTO.getBuiltInTags());
        taskRepository.save(task);

        return taskMapper.map(task);

    }

    public void delete(Long id) {

        taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, " Task from " + id + " not Found"
                ));

        taskRepository.deleteById(id);

    }

    public void enrichTaskTag(Task task, Set<String> tags) {

        if (tags != null) {

            Set<TaskTag> tagSet = tags.stream()
                    .map(TaskTag::fromString)
                    .filter(java.util.Objects::nonNull)
                    .collect(Collectors.toSet());

            task.setBuiltInTags(tagSet);

            Set<String> customTah = tags.stream()
                    .filter(tag -> TaskTag.fromString(tag) == null)
                    .collect(Collectors.toSet());

            task.setCustomTags(customTah);

        }
    }

    public void enrichTaskJsonNullable(Task task,
                                       JsonNullable<String> nameNullable,
                                       JsonNullable<String> bodyNullable,
                                       JsonNullable<String> stageNullable,
                                       JsonNullable<LocalDate> dueDateNullable,
                                       JsonNullable<Boolean> isCompletedNullable,
                                       JsonNullable<Set<String>> builtTagsNullable) {


        if (nameNullable != null && nameNullable.isPresent()) {

            task.setName(nameNullable.get());

        }

        if (bodyNullable != null && bodyNullable.isPresent()) {

            task.setBody(bodyNullable.get());

        }

        if (stageNullable != null && stageNullable.isPresent()) {
            task.setStage(stageNullable.get());
        }

        if (dueDateNullable != null && dueDateNullable.isPresent()) {
            task.setDueDate(dueDateNullable.get());
        }

        if (isCompletedNullable != null && isCompletedNullable.isPresent()) {
            task.setCompleted(isCompletedNullable.get());
        }

        if (builtTagsNullable != null && builtTagsNullable.isPresent()) {

            Set<String> newTag = builtTagsNullable.get();

            if (newTag == null) {

                task.getBuiltInTags().clear();
                task.getCustomTags().clear();

            } else {

                enrichTaskTag(task, newTag);
            }
        }
    }
}
