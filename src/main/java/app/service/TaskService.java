package app.service;

import app.dto.TaskCreatedDTO;
import app.dto.TaskDTO;
import app.dto.TaskParamsDTO;
import app.dto.TaskUpdateDTO;
import app.mapper.TaskMapper;
import app.model.Stage;
import app.model.Task;
import app.model.TaskTag;
import app.repository.TaskRepository;
import app.specification.TaskSpecification;
import lombok.AllArgsConstructor;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskService implements TaskServiceInterface {

    private final TaskRepository taskRepository;
    private final TaskSpecification taskSpecification;
    private final TaskMapper taskMapper;

    @Transactional
    public Page<TaskDTO> getAll(TaskParamsDTO params, Pageable pageable) {

        Specification<Task> spec = taskSpecification.build(params);
        return taskRepository.findAll(spec, pageable)
                .map(taskMapper::map);
    }


    public TaskDTO getById(Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, " Task from " + id + " not Found"
                ));

        return taskMapper.map(task);

    }

    @Transactional
    public TaskDTO create(TaskCreatedDTO taskCreatedDTO) {

        Task task = taskMapper.map(taskCreatedDTO);
        enrichTaskTags(task, taskCreatedDTO.getTags());

        taskRepository.save(task);

        return taskMapper.map(task);

    }

    @Transactional
    public TaskDTO update(Long id, TaskUpdateDTO taskUpdateDTO) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, " Task from " + id + " not Found"
        ));

        taskMapper.update(taskUpdateDTO, task);

        taskMapper.update(taskUpdateDTO, task);

        if (taskUpdateDTO.getBuiltInTags() != null && taskUpdateDTO.getBuiltInTags().isPresent()) {

            Set<String> newTags = taskUpdateDTO.getBuiltInTags().get();

            if (newTags == null) {

                task.getBuiltInTags().clear();
                task.getCustomTags().clear();

            } else {

                enrichTaskTags(task, newTags);

            }
        }

        taskRepository.save(task);

        return taskMapper.map(task);

    }

    @Transactional
    public void delete(Long id) {

        taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, " Task from " + id + " not Found"
                ));

        taskRepository.deleteById(id);

    }

    public void enrichTaskTags(Task task, Set<String> tags) {
        if (tags != null) {
            Set<TaskTag> builtIn = new HashSet<>();
            Set<String> custom = new HashSet<>();

            for (String rawTag : tags) {
                TaskTag parsedTag = TaskTag.fromString(rawTag);
                if (parsedTag != null) {
                    builtIn.add(parsedTag);
                } else {
                    custom.add(rawTag);
                }
            }

            task.setBuiltInTags(builtIn);
            task.setCustomTags(custom);
        }
    }

//    public void enrichTaskJsonNullable(Task task,
//                                       JsonNullable<String> nameNullable,
//                                       JsonNullable<String> bodyNullable,
//                                       JsonNullable<Stage> stageNullable,
//                                       JsonNullable<LocalDate> dueDateNullable,
//                                       JsonNullable<Boolean> isCompletedNullable,
//                                       JsonNullable<Set<String>> builtTagsNullable) {
//
//        if (builtTagsNullable != null && builtTagsNullable.isPresent()) {
//
//            Set<String> newTag = builtTagsNullable.get();
//
//            if (newTag == null) {
//
//                task.getBuiltInTags().clear();
//                task.getCustomTags().clear();
//
//            } else {
//
//                enrichTaskTag(task, newTag);
//            }
//        }
//    }

    public long getTaskCount() {

        return taskRepository.count();

    }
}
