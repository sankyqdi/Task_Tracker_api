package app.controller.service;

import app.dto.TaskCreateDTO;
import app.dto.TaskDTO;
import app.dto.TaskParamsDTO;
import app.dto.TaskUpdateDTO;
import app.mapper.TaskMapper;
import app.model.Stage;
import app.model.Task;
import app.model.TaskTag;
import app.repository.TaskRepository;
import app.service.TaskService;
import app.specification.TaskSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskSpecification taskSpecification;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskService taskService;

    private Task task;
    private TaskDTO taskDTO;

    @BeforeEach
    void setUp() {
        task = new Task();
        task.setId(1L);
        task.setName("Test Task");
        task.setBody("Task Description");
        task.setImportanceLevel((byte) 5);
        task.setStage(Stage.CREATED);
        task.setDueDate(LocalDate.now().plusDays(1));

        taskDTO = new TaskDTO(
                1L,
                "Test Task",
                "Task Description",
                (byte) 5,
                Stage.CREATED,
                Set.of("CustomTag"),
                LocalDate.now().plusDays(1)
        );
    }

    @Test
    @DisplayName("getById должен возвращать TaskDTO при успешном поиске")
    void getById_Success() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskMapper.map(task)).thenReturn(taskDTO);

        TaskDTO result = taskService.getById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Test Task");
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("getById должен выбрасывать ResponseStatusException(404), если задача не найдена")
    void getById_NotFound_ThrowsException() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.getById(1L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("404 NOT_FOUND");
    }

    @Test
    @DisplayName("create должен успешно сохранять и обогащать теги задачи")
    void create_Success() {
        TaskCreateDTO createDTO = new TaskCreateDTO(
                "New Task",
                "New Body",
                (byte) 3,
                LocalDate.now().plusDays(2),
                Set.of("BUG", "MyCustomTag")
        );

        Task newTask = new Task();
        newTask.setName("New Task");

        when(taskMapper.map(createDTO)).thenReturn(newTask);
        when(taskRepository.save(any(Task.class))).thenReturn(newTask);
        when(taskMapper.map(newTask)).thenReturn(taskDTO);

        TaskDTO result = taskService.create(createDTO);

        assertThat(result).isNotNull();
        verify(taskRepository, times(1)).save(newTask);
        assertThat(newTask.getBuiltInTags()).contains(TaskTag.BUG);
        assertThat(newTask.getCustomTags()).contains("MyCustomTag");
    }

    @Test
    @DisplayName("getAll должен возвращать страницу задач по спецификации")
    void getAll_Success() {
        TaskParamsDTO params = new TaskParamsDTO();
        Pageable pageable = PageRequest.of(0, 10);
        Specification<Task> spec = (root, query, cb) -> cb.conjunction();

        when(taskSpecification.build(params)).thenReturn(spec);
        when(taskRepository.findAll(eq(spec), eq(pageable))).thenReturn(new PageImpl<>(List.of(task)));
        when(taskMapper.map(task)).thenReturn(taskDTO);

        Page<TaskDTO> result = taskService.getAll(params, pageable);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        verify(taskRepository, times(1)).findAll(spec, pageable);
    }

    @Test
    @DisplayName("update должен корректно обновлять полей задачи через PATCH")
    void update_Success() {
        TaskUpdateDTO updateDTO = TaskUpdateDTO.builder()
                .name(JsonNullable.of("Updated Name"))
                .stage(JsonNullable.of(Stage.IN_PROGRESS))
                .build();

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(task)).thenReturn(task);
        when(taskMapper.map(task)).thenReturn(taskDTO);

        TaskDTO result = taskService.update(1L, updateDTO);

        assertThat(result).isNotNull();
        verify(taskMapper, times(2)).update(updateDTO, task);
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    @DisplayName("delete должен удалять задачу при её существовании")
    void delete_Success() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        taskService.delete(1L);

        verify(taskRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("enrichTaskTags должен разделять встроенные теги (enum) от кастомных")
    void enrichTaskTags_SeparatesBuiltInAndCustomTags() {
        Set<String> rawTags = Set.of("FEATURE", "SECURITY", "CustomBugFix");
        Task targetTask = new Task();

        taskService.enrichTaskTags(targetTask, rawTags);

        assertThat(targetTask.getBuiltInTags()).containsExactlyInAnyOrder(TaskTag.FEATURE, TaskTag.SECURITY);
        assertThat(targetTask.getCustomTags()).containsExactly("CustomBugFix");
    }
}
