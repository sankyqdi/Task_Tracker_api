package app.mapper;

import app.dto.TaskCreatedDTO;
import app.dto.TaskDTO;
import app.dto.TaskUpdateDTO;
import app.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(uses = JsonNullableMapper.class,
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
        )
public interface TaskMapper {

    TaskDTO map(Task task);

    Task map(TaskCreatedDTO taskCreatedDTO);

    TaskDTO update(TaskUpdateDTO taskUpdateDTO, @MappingTarget Task model);


}
