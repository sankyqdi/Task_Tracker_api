package app.controller;

import app.dto.TaskCreateDTO;
import app.dto.TaskDTO;
import app.dto.TaskParamsDTO;
import app.dto.TaskUpdateDTO;
import app.service.TaskService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<Page<TaskDTO>> index(TaskParamsDTO params, Pageable pageable) {

        Page<TaskDTO> tasks = taskService.getAll(params, pageable);
        HttpHeaders headers = new HttpHeaders();

        return ResponseEntity.ok()
                .headers(headers)
                .body(tasks);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskDTO show(@PathVariable Long id) {

        return taskService.getById(id);

    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDTO create(@Valid @RequestBody TaskCreateDTO dto) {

        return taskService.create(dto);

    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskDTO update(@Valid @RequestBody TaskUpdateDTO dto, @PathVariable Long id) {

        return taskService.update(id, dto);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {

        taskService.delete(id);

    }
}
