package app.service;

import app.dto.TaskCreatedDTO;
import app.dto.TaskDTO;
import app.dto.TaskParamsDTO;
import app.dto.TaskUpdateDTO;

import java.util.List;

public interface TaskServiceInterface {
    List<TaskDTO> getAll(TaskParamsDTO params);
    TaskDTO getById(Long id);
    TaskDTO create(TaskCreatedDTO taskCreatedDTO);
    TaskDTO update(Long id, TaskUpdateDTO taskUpdateDTO);
    void delete(Long id);
}
