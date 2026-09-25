package app.service;

import app.dto.TaskCreateDTO;
import app.dto.TaskDTO;
import app.dto.TaskParamsDTO;
import app.dto.TaskUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskServiceInterface {
    Page<TaskDTO> getAll(TaskParamsDTO params, Pageable pageable);
    TaskDTO getById(Long id);
    TaskDTO create(TaskCreateDTO taskCreateDTO);
    TaskDTO update(Long id, TaskUpdateDTO taskUpdateDTO);
    void delete(Long id);
}
