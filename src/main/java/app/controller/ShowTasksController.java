package app.controller;

import app.config.View;
import app.config.WindowManager;
import app.dto.TaskDTO;
import app.model.Stage;
import app.service.TaskService;
import app.utils.LoadFXML;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;


@Component
@RequiredArgsConstructor
public class ShowTasksController {

    private final TaskService taskService;

    private final WindowManager windowManager;

    private final ApplicationContext context;

    private final Integer MAX_TASKS = 30;

    private Page<TaskDTO> tasks;

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

        loadTasks();
        addButtonTasks();

    }

    private void loadTasks() {

        tasks = taskService.getAll(null, PageRequest.of(
                0, MAX_TASKS, Sort.by("name")));

    }

    public void addButtonTasks() {

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

    @FXML
    public void handleCancel(ActionEvent event) {



    }

    @FXML
    public void handleExecution(ActionEvent event) {

    }

    @FXML
    public void handleExitMainMenu(MouseEvent event) {

        windowManager.switchScene(View.MAIN);

    }

    @FXML
    public void handleUpdate(ActionEvent event) {



    }

    @FXML
    public void handleShowTask(MouseEvent event) {

        Node sourceNode = (Node) event.getSource();
        TaskDTO task = (TaskDTO) sourceNode.getUserData();

        if (task != null) {

            showTaskDetails(task);

        }

    }

    private void showTaskDetails(TaskDTO task) {

        nameTask.setText(task.getName());
        textBodyTask.setText(task.getBody());
        dueDateTask.setText(task.getDueDate().toString());
        setTag(task);
        generatorIndicator(task.getStage());


    }

    private void setTag(TaskDTO task) {

        TagItemController tagItemController = context.getBean(TagItemController.class);

        var tags = task.getBuiltInTag();
        var customTags = task.getCustomTag();

        List<Node> tagNodes = new ArrayList<>(tagItemController.setItemTags(tags));
        tagNodes.addAll(tagItemController.setItemCustomTags(customTags));

        for (Node node : tagNodes) {

            tagTask.getChildren().add(node);

        }

    }

    private void generatorIndicator(Stage taskStage) {

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
}
