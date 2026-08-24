package app;

import app.config.WindowManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class PrimaryStageInitializer implements ApplicationListener<StageReadyEvent> {

    private final WindowManager windowManager;

    @Override
    public void onApplicationEvent(StageReadyEvent event) {

        try {
            Stage stage = event.getStage();
            windowManager.setPrimaryStage(stage);
            ConfigurableApplicationContext context = event.getApplicationContext();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main_menu.fxml"));

            //Test animation
//          FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/blanks/pop_up_window/updateTask.fxml"));

            loader.setControllerFactory(context::getBean);

            Parent root = loader.load();

            Scene scene = new Scene(root, windowManager.getWidth(), windowManager.getHeight());

            //Test animation
//            Scene scene = new Scene(root, 480, 392);
            stage.setTitle("Task Tracker");
            stage.setScene(scene);
//      stage.setMinWidth(600);
//      stage.setMinHeight(400);
//      stage.setResizable(false);
//      stage.setMaximized(true);
            stage.show();

        } catch (IOException e) {

            throw new RuntimeException("Ошибка загрузки /view/blanks/pop_up_window/updateTask.fxml", e);

        }
    }
}
