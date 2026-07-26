package app.controller;

import app.config.View;
import app.config.WindowManager;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.util.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;


@Component
@RequiredArgsConstructor
public class MainMenuController {

    private final WindowManager windowManager;

    private static final double DEFAULT_BUTTON_WIDTH = 220.0;
    private static final double DEFAULT_BUTTON_HEIGHT = 60.0;

    private int clickCount = 0;
    private Timer resetTimer;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private StackPane newTaskButton;

    @FXML
    private ImageView newTaskFrame;

    @FXML
    private StackPane showAllTasksButton;

    @FXML
    private ImageView showTasksFrame;

    @FXML
    private StackPane exitButton;

    @FXML
    private ImageView exitFrame;

    @FXML
    private Label labelTime;

    @FXML
    private Label labelDate;

    @FXML
    public void initialize() {
        var fontResource = getClass().getResource("/fonts/FARSH_MARSH.ttf");

        if (fontResource != null) {

            Font loadedFont = Font.loadFont(fontResource.toExternalForm(), 12);

            if (loadedFont == null) {

                System.err.println("❌ Ошибка парсинга файла FARSH_MARSH.ttf");

            }
        } else {

            System.err.println("❌ Файл /fonts/FARSH_MARSH.ttf не найден в resources");

        }

        setTimeAndDate();
    }

    private void setTimeAndDate() {

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE, MMM d", Locale.ENGLISH);

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalDateTime now = LocalDateTime.now();
            labelTime.setText(now.format(timeFormatter));
            labelDate.setText(now.format(dateFormatter).toUpperCase());
        }), new KeyFrame(Duration.seconds(1)));

        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();

    }

    @FXML
    public void handleNewTaskClick() {

        windowManager.openModalWindow(View.CREATE, false);

    }

    @FXML
    public void handleShowTasksClick() {

        windowManager.switchScene(View.TASKS);

    }

    @FXML
    public void handleBarsuckClick() {

        int REQUIRED_CLICKS = 5;

        clickCount++;

        if (resetTimer != null) {

            resetTimer.cancel();

        }


        if (clickCount >= REQUIRED_CLICKS) {

            clickCount = 0;
            barsukHell();
            return;

        }

        resetTimer = new Timer();
        resetTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                clickCount = 0;
            }
        }, 1500);

    }

    private void barsukHell() {

        bindCardToRoot(newTaskButton, newTaskFrame);
        bindCardToRoot(showAllTasksButton, showTasksFrame);
        bindCardToRoot(exitButton, exitFrame);

        PauseTransition delay = new PauseTransition(Duration.seconds(5));
        delay.setOnFinished(event -> {
            unbindCard(newTaskButton, newTaskFrame);
            unbindCard(showAllTasksButton, showTasksFrame);
            unbindCard(exitButton, exitFrame);

            rootPane.requestLayout();
        });
        delay.play();
    }

    private void bindCardToRoot(StackPane card, ImageView frame) {

        card.prefWidthProperty().bind(rootPane.widthProperty().multiply(0.219));
        card.prefHeightProperty().bind(rootPane.heightProperty().multiply(0.081));

        frame.fitWidthProperty().bind(card.widthProperty());
        frame.fitHeightProperty().bind(card.heightProperty());

    }

    private void unbindCard(StackPane card, ImageView frame) {

        card.prefWidthProperty().unbind();
        card.prefHeightProperty().unbind();
        frame.fitWidthProperty().unbind();
        frame.fitHeightProperty().unbind();

        card.setPrefWidth(DEFAULT_BUTTON_WIDTH);
        card.setPrefHeight(DEFAULT_BUTTON_HEIGHT);
        frame.setFitWidth(DEFAULT_BUTTON_WIDTH);
        frame.setFitHeight(DEFAULT_BUTTON_HEIGHT);

    }

}
