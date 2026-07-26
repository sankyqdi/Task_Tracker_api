package app.service;

import app.dto.TaskCreatedDTO;
import app.dto.TaskDTO;
import app.dto.TaskParamsDTO;
import app.dto.TaskUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TaskServiceInterface {
    Page<TaskDTO> getAll(TaskParamsDTO params, Pageable pageable);
    TaskDTO getById(Long id);
    TaskDTO create(TaskCreatedDTO taskCreatedDTO);
    TaskDTO update(Long id, TaskUpdateDTO taskUpdateDTO);
    void delete(Long id);
}
