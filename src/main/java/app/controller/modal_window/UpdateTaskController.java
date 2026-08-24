package app.controller.modal_window;

import app.animation.AnimObject;
import app.animation.FallAnimation;
import app.config.AnimationManager;
import app.controller.TagItemController;
import app.dto.TaskDTO;
import app.model.Stage;
import app.model.TaskTag;
import app.records.NameTag;
import app.utils.SeparatorObject;
import app.utils.counter.Counter;
import javafx.animation.Animation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class UpdateTaskController {

    private static final AnimationManager animationManager = AnimationManager.getInstance();

    private final ApplicationContext context;

    private List<NameTag> nameTags = new ArrayList<>();

    private List<String> tweenNames = new ArrayList<>();

    @Setter
    private TaskDTO task;

    @Setter
    private Listener listener;

    @FXML
    private ComboBox<String> checkStage;

    @FXML
    private ComboBox<String> checkTag;

    @FXML
    private DatePicker dueDate;

    @FXML
    private TextField fieldBody;

    @FXML
    private TextField fieldName;

    @FXML
    private VBox tags;

    @FXML
    private ScrollPane scroll;

    @FXML
    public void initialize() {

        tags.setClip(null);
        scroll.skinProperty().addListener((obs, oldSkin, newSkin) -> {
            if (newSkin != null) {
                Node viewport = scroll.lookup(".viewport");
                if (viewport != null) {
                    viewport.setClip(null);
                }
            }
        });

        if (task != null) {

            fieldName.setText(task.getName());
            fieldBody.setText(task.getBody());
            dueDate.setValue(task.getDueDate());
            checkStage.getItems().addAll(Stage.getAll());
            checkStage.setValue(task.getStage().getStringStage());
            checkTag.getItems().addAll(TaskTag.getAllTag());

        }

        registerObject();

    }

    private void registerObject() {

        //Регистрация объектов из Task и перенос в list, для последующего запуска анимации.

        if (!task.getBuiltInTags().isEmpty()) {

            Counter counter = new Counter();

            for (var tag : task.getBuiltInTags()) {

                NameTag nameTag = new NameTag(tag.getFormattedTag(), counter.generateId("built"));
                nameTags.add(nameTag);

            }

        }

        if (!task.getCustomTags().isEmpty()) {


            Counter counter = new Counter();

            for (var tag : task.getCustomTags()) {

                NameTag nameTag = new NameTag(tag, counter.generateId("custom"));
                nameTags.add(nameTag);

            }

        }

        if (!nameTags.isEmpty()) {

            setTags();
            assignAnimation();


        }

        //Присвоить каждому свой идентификатор имени и по нему управлять анимацией
        //Использовать record как основа для группировки этих идентификаторов

    }

    private void assignAnimation() {

        List<Node> tagObject = tags.getChildren();

        List<Node> leftAnimation = SeparatorObject.separatorList(tagObject, 2, 0);

        List<Node> rightAnimation = SeparatorObject.separatorList(tagObject, 2, 1);

        for (Node node : leftAnimation) {

            Animation animation = FallAnimation.animateMoveFadeAndResetLeft(node);

            animationManager.registerAnimation(node.getId(), animation, node);

            animation.play();

            System.out.println("((((((((((((((((((((((((((((((((((()))))))))))))))))))))))))))))))))))");

        }

        for (Node node : rightAnimation) {

            Animation animation =  FallAnimation.animateMoveFadeAndResetRight(node);

            animationManager.registerAnimation(node.getId(), animation, node);

            animation.play();

            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        }

    }

    public void setTask() {

        task.setName(fieldName.getText());
        task.setBody(fieldBody.getText());
        task.setDueDate(dueDate.getValue());

        if (checkStage.getValue() != null) {

            task.setStage(Stage.getStageFromString(checkStage.getValue()));

        }

//        task.setTag(TaskTag.fromString(checkTag.getValue()));

    }

    @FXML
    public void handleSave(ActionEvent event) {

        if (listener != null && task != null) {

            setTask();
            listener.onTaskUpdated(task);

        }

        Node source = (Node) event.getSource();

        javafx.stage.Stage stage = (javafx.stage.Stage) source.getScene().getWindow();

        stage.close();

    }

    @FXML
    public void handleAppearanceList(MouseEvent event) {

        if (tags.getChildren().isEmpty()) return;

        Counter counter = new Counter();
        animationManager.pauseAllAnimation();

        for (Node node : tags.getChildren()) {

            String tweenName = counter.generateId("dafaultPosition");
            tweenNames.add(tweenName);

            Animation animation = FallAnimation.smoothReturnToStart(node);
            animationManager.registerAnimation(tweenName, animation, node);
            animation.play();

        }

    }

    @FXML
    public void handleExitAppearanceList(MouseEvent event) {

        if (tweenNames.isEmpty()) return;

        for (var nameObject : tweenNames) {

            animationManager.unregisterAnimation(nameObject);

        }

        tweenNames.clear();

        animationManager.resumeAllAnimation();

    }

    private void setTags() {

        tags.getChildren().clear();

        List<Node> tagNodes = new ArrayList<>();

        for (var nameTag : nameTags) {

            if (nameTag == null) continue;

            TagItemController tagItemController = context.getBean(TagItemController.class);
            tagNodes.add(tagItemController.setItemCustomTags(nameTag.tags(), 184, 44, nameTag.nameObject()));

        }

        tags.getChildren().addAll(tagNodes);

    }

}

