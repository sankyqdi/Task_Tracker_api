package app.controller;

import app.dto.TaskDTO;
import app.service.TaskService;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ShowTasksController {

    private final TaskService taskService;

    private final ApplicationContext context;

    private final Integer MAX_TASKS = 30;

    private Page<TaskDTO> tasks;

    @FXML
    private VBox listTasks;

    @FXML
    public void initialize() {

        loadTasks();
        addButtonTasks();

    }

    public void loadTasks() {

        tasks = taskService.getAll(null, PageRequest.of(
                0, MAX_TASKS, Sort.by("name")));


    }

    public void addButtonTasks() {

        listTasks.getChildren().clear();

        for (TaskDTO task : tasks) {

            ButtonTaskController buttonTaskController = context.getBean(ButtonTaskController.class);
            listTasks.getChildren().add(buttonTaskController.setStackPane(task));

        }

    }
}
