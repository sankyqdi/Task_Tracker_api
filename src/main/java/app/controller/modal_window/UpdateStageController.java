package app.controller.modal_window;

import app.dto.TaskDTO;
import app.model.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateStageController {

    private TaskDTO defaultTask;

    private Stage stage;

    @Setter
    private Listener listener;

    public void setDefaultStageTask(TaskDTO defaultTask) {

        this.defaultTask = defaultTask;
        this.stage = defaultTask.getStage();

    }

    @FXML
    void handleAbandonedStage(ActionEvent event) {

        defaultTask.setStage(Stage.ABANDONED);

    }

    @FXML
    void handleComplatedStage(ActionEvent event) {

        defaultTask.setStage(Stage.COMPLETED);

    }


    @FXML
    void handleProgressStage(ActionEvent event) {

        defaultTask.setStage(Stage.IN_PROGRESS);

    }

    @FXML
    void handleDefaultStage(ActionEvent event) {

        defaultTask.setStage(stage);

    }

    @FXML
    void handleSave(ActionEvent event) {

        if (listener != null && defaultTask != null) {

            listener.onTaskUpdated(defaultTask);

        }

        Node source = (Node) event.getSource();

        javafx.stage.Stage stage = (javafx.stage.Stage) source.getScene().getWindow();

        stage.close();

    }

}
