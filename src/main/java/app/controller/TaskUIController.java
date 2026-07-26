package app.controller;

import app.config.View;
import app.config.WindowManager;
import app.dto.TaskCreatedDTO;
import app.model.TaskTag;
import app.service.TaskService;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class TaskUIController {

    private final TaskService taskService;

    @FXML
    private TextField nameInput;

    @FXML
    private TextField bodyInput;

    @FXML
    private TextField levelInput;

    @FXML
    private TextField dueDateInput;

    @FXML
    private ComboBox<String> tagComboBox;

    @FXML
    public void handleAddTask() {

        String taskName = nameInput.getText();
        String taskBody = bodyInput.getText();
        Byte taskLevel = Byte.parseByte(levelInput.getText());
        LocalDate taskDueDate = LocalDate.parse(dueDateInput.getText());
        Set<String> selectedTag = Collections.singleton(tagComboBox.getValue());

        if (!taskName.trim().isEmpty()) {

            taskService.create(new TaskCreatedDTO(taskName, taskBody, taskLevel, taskDueDate, selectedTag));
            nameInput.clear();

        }
    }

    @FXML
    public void initialize() {

        tagComboBox.getItems().addAll(TaskTag.getAllTag());

        tagComboBox.setEditable(true);

    }

}
