package app.controller;

import app.dto.TaskCreateDTO;
import app.dto.TaskDTO;
import app.model.Stage;
import app.service.TaskService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.autoconfigure.JacksonAutoConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import tools.jackson.databind.json.JsonMapper;

@ActiveProfiles("test")
@WebMvcTest(TaskController.class)
@Import(JacksonAutoConfiguration.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonMapper jsonMapper;

    @MockitoBean
    private TaskService taskService;

    @Test
    @DisplayName("GET /{id} возвращает 200 OK и объект задачи")
    void getById_Returns200() throws Exception {
        TaskDTO dto = new TaskDTO(
                1L,
                "Task 1",
                "Body 1",
                (byte) 5,
                Stage.CREATED,
                Set.of("Backend"),
                LocalDate.now().plusDays(1)
        );

        when(taskService.getById(1L)).thenReturn(dto);

        mockMvc.perform(get("/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Task 1"));
    }

    @Test
    @DisplayName("GET /{id} возвращает 404 NOT FOUND, если задача не найдена")
    void getById_NotFound_Returns404() throws Exception {
        when(taskService.getById(99L))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not Found"));

        mockMvc.perform(get("/{id}", 99L))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST / создает новую задачу и возвращает status 201 CREATED")
    void create_Returns201() throws Exception {
        TaskCreateDTO createDTO = new TaskCreateDTO(
                "New Task",
                "Description",
                (byte) 5,
                LocalDate.now().plusDays(5),
                Set.of("FEATURE")
        );

        TaskDTO createdTask = new TaskDTO(
                1L,
                "New Task",
                "Description",
                (byte) 5,
                Stage.CREATED,
                Set.of("FEATURE"),
                LocalDate.now().plusDays(5)
        );

        when(taskService.create(any(TaskCreateDTO.class))).thenReturn(createdTask);

        mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(createDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("New Task"));
    }

    @Test
    @DisplayName("POST / с некорректными данными возвращает 400 BAD REQUEST (валидация)")
    void create_InvalidDTO_Returns400() throws Exception {
        // Пустое имя и недопустимый уровень важности (>10)
        TaskCreateDTO invalidDTO = new TaskCreateDTO(
                "",
                "Description",
                (byte) 15,
                LocalDate.now().minusDays(1),
                Set.of()
        );

        mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(invalidDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.name").exists())
                .andExpect(jsonPath("$.errors.importanceLevel").exists());
    }

    @Test
    @DisplayName("DELETE /{id} возвращает 204 NO CONTENT")
    void delete_Returns204() throws Exception {
        doNothing().when(taskService).delete(1L);

        mockMvc.perform(delete("/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}
