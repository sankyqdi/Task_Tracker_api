package app.mapper;

import app.dto.TaskCreatedDTO;
import app.dto.TaskDTO;
import app.dto.TaskUpdateDTO;
import app.model.Task;
import org.mapstruct.*;

@Mapper(uses = JsonNullableMapper.class,
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
        )
public interface TaskMapper {

    @Mapping(source = "builtInTags", target = "builtInTag")
    @Mapping(source = "customTags", target = "customTag")
    TaskDTO map(Task task);

    @Mapping(target = "builtInTags", ignore = true)
    @Mapping(target = "customTags", ignore = true)
    Task map(TaskCreatedDTO taskCreatedDTO);

    @Mapping(target = "builtInTags", ignore = true)
    @Mapping(target = "customTags", ignore = true)
    void update(TaskUpdateDTO taskUpdateDTO, @MappingTarget Task model);


}
