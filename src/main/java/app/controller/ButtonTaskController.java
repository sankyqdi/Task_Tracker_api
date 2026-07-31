package app.controller;

import app.dto.TaskDTO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Scope("prototype")
@RequiredArgsConstructor
public class ButtonTaskController {

    private final String PATH_FXML_BLANK_EASY = "/view/blanks/button_easy_level.fxml";
    private final String PATH_FXML_BLANK_NORMAL = "/view/blanks/button_normal_level.fxml";
    private final String PATH_FXML_BLANK_HARD = "/view/blanks/button_hard_level.fxml";
    private final String PATH_FXML_BLANK_VERY_HARD = "/view/blanks/button_very_hard_level.fxml";

    @FXML
    private Label nameTask;

    @FXML
    private Label levelTask;

    public Node setStackPane (TaskDTO task) {

        Node stackPane = loadFxmlBlank(getPathFxml(task));

        if (stackPane != null) {

            setTask(task);

        }

        return stackPane;

    }

    private String getPathFxml(TaskDTO task) {

        return switch (task.getImportanceLevel()) {

            case 1,2,3 -> PATH_FXML_BLANK_EASY;
            case 4,5,6 -> PATH_FXML_BLANK_NORMAL;
            case 7,8 -> PATH_FXML_BLANK_HARD;
            case 9,10 -> PATH_FXML_BLANK_VERY_HARD;
            default -> throw new IllegalArgumentException("Invalid importance level");

        };
    }

    private void setTask(TaskDTO task) {

        nameTask.setText(task.getName());
        levelTask.setText(String.valueOf(task.getImportanceLevel()));

    }

    private Node loadFxmlBlank(String pathFxml) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(pathFxml));

            loader.setController(this);

            return loader.load();

        } catch (IOException e) {

            e.printStackTrace();
            return null;

        }
    }
}
