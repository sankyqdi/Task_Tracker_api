package app.controller;

import app.config.View;
import app.config.WindowManager;
import app.controller.modal_window.UpdateStageController;
import app.controller.modal_window.UpdateTaskController;
import app.dto.TaskDTO;
import app.dto.TaskUpdateDTO;
import app.model.Stage;
import app.model.TaskTag;
import app.service.TaskService;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Component
@RequiredArgsConstructor
public class ShowTasksController {

    private final TaskService taskService;

    private final WindowManager windowManager;

    private final ApplicationContext context;

    private final Integer MAX_TASKS = 30;

    @Setter
    private TaskDTO activeTask;

    private Page<TaskDTO> tasks;

    private TaskDTO defaultTask = new TaskDTO(0L,
            "Имя задачи",
            "Цель задачи",
            Byte.valueOf("0"),
            Stage.COMPLETED,
            Set.of("Tag"),
            LocalDate.parse("2500-12-12"));

    @FXML
    private VBox listTasks;

    @FXML
    private Label nameTask;

    @FXML
    private Label dueDateTask;

    @FXML
    private Region stageTask;

    @FXML
    private VBox tagTask;

    @FXML
    private Label textBodyTask;

    @FXML
    public void initialize() {

        setDefaultField();
        loadTasks();
        addButtonTasks();

    }

    @FXML
    public void handleCancel(ActionEvent event) {

        setDefaultField();
        activeTask = null;

    }

    @FXML
    public void handleExecution(ActionEvent event) {

        if (activeTask != null) {

            UpdateStageController updateStageController = context.getBean(UpdateStageController.class);
            updateStageController.setDefaultStageTask(activeTask);
            updateStageController.setListener(updatedTask -> {

                refreshTaskInList(updatedTask);

                this.activeTask = updatedTask;

                showTaskDetails();

            });
            windowManager.openModalWindow(View.UPDATE_STAGE, false);

        } else {

            return;

        }


    }

    @FXML
    public void handleExitMainMenu(MouseEvent event) {

        windowManager.switchScene(View.MAIN);

    }

    @FXML
    public void handleUpdate(ActionEvent event) {

        if (activeTask != null) {

            UpdateTaskController updateTaskController = context.getBean(UpdateTaskController.class);
            updateTaskController.setTask(activeTask);
            updateTaskController.setListener(updatedTask -> {

                refreshTaskInList(updatedTask);

                this.activeTask = updatedTask;

                showTaskDetails();

            });
            windowManager.openModalWindow(View.UPDATE, false);
        }

        else {

            return;

        }



    }

    @FXML
    public void handleShowTask(MouseEvent event) {

        Node sourceNode = (Node) event.getSource();
        activeTask = (TaskDTO) sourceNode.getUserData();

        if (activeTask != null) {

            showTaskDetails();

        }

    }

    private void loadTasks() {

        tasks = taskService.getAll(null, PageRequest.of(
                0, MAX_TASKS, Sort.by("name")));

    }

    private void setDefaultField() {

        nameTask.setText(defaultTask.getName());
        textBodyTask.setText(defaultTask.getBody());
        dueDateTask.setText(defaultTask.getDueDate().toString());
        setTag(defaultTask);
        generatorIndicator(defaultTask);

    }

    private void addButtonTasks() {

        listTasks.getChildren().clear();

        for (TaskDTO task : tasks) {

            ButtonTaskController buttonTaskController = context.getBean(ButtonTaskController.class);
            Node stackPane = buttonTaskController.setStackPane(task);

            if (stackPane != null) {

                stackPane.setOnMouseClicked(this::handleShowTask);
                listTasks.getChildren().add(stackPane);

            }

        }

    }

    private void showTaskDetails() {

        nameTask.setText(activeTask.getName());
        textBodyTask.setText(activeTask.getBody());
        dueDateTask.setText(activeTask.getDueDate().toString());
        setTag();
        generatorIndicator();


    }

    private void setTag(TaskDTO task) {

        tagTask.getChildren().clear();

        List<Node> tagNodes = new ArrayList<>();

        var tags = task.getBuiltInTag();

        if (tags != null && !tags.isEmpty()) {

            for (var tag : tags) {

                if (tag == null) continue;

                TagItemController tagItemController = context.getBean(TagItemController.class);
                tagNodes.add(tagItemController.setItemTags(tag));

            }

        }

        var customTags = task.getCustomTag();

        if (customTags != null && !customTags.isEmpty()) {

            for (var tag : customTags) {

                if (tag == null) continue;

                TagItemController tagItemController = context.getBean(TagItemController.class);
                tagNodes.add(tagItemController.setItemCustomTags(tag));


            }

        }

            tagTask.getChildren().addAll(tagNodes);

    }

    private void setTag() {

        setTag(activeTask);

    }

    private void generatorIndicator(TaskDTO task) {

        Stage taskStage = task.getStage();

        switch (taskStage) {
            case CREATED:

                stageTask.setPrefWidth(20);

                break;
            case IN_PROGRESS:

                stageTask.setPrefWidth(50);

                break;
            case ABANDONED:

                ColorAdjust redTint = new ColorAdjust();
                redTint.setBrightness(0.05);
                redTint.setContrast(-0.42);
                redTint.setHue(-0.02);
                redTint.setSaturation(0.5);

                stageTask.setEffect(redTint);

                break;
            case COMPLETED:

                stageTask.setPrefWidth(140);

                break;
        }
    }

    private void generatorIndicator() {

        generatorIndicator(activeTask);

    }

    private void refreshTaskInList(TaskDTO updatedTask) {

        boolean isCompleted = updatedTask.getStage() == Stage.COMPLETED;

        TaskUpdateDTO taskUpdateDTO = TaskUpdateDTO.builder()
                .name(JsonNullable.of(updatedTask.getName()))
                .body(JsonNullable.of(updatedTask.getBody()))
                .stage(JsonNullable.of(updatedTask.getStage()))
                .dueDate(JsonNullable.of(updatedTask.getDueDate()))
                .isCompleted(JsonNullable.of(isCompleted))
                .builtInTags(JsonNullable.of(tagMerging()))
                .build();

        taskService.update(activeTask.getId(), taskUpdateDTO);

    }

    private Set<String> tagMerging() {

        Set<String> margingTag = new HashSet<>();

        if (activeTask.getCustomTag() != null && !activeTask.getCustomTag().isEmpty()) {

            margingTag.addAll(activeTask.getCustomTag());

        }

        if (activeTask.getBuiltInTag() != null && !activeTask.getBuiltInTag().isEmpty()) {

            activeTask.getBuiltInTag().stream()
                    .map(TaskTag::getFormattedTag)
                    .forEach(margingTag::add);

        }

        return margingTag;

    }

    private void generatorSizeObject() {



    }
}
