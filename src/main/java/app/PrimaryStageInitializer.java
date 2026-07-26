package app;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class PrimaryStageInitializer implements ApplicationListener<StageReadyEvent> {



    @Override
    public void onApplicationEvent(StageReadyEvent event) {

        try {
            Stage stage = event.getStage();
            ConfigurableApplicationContext context = event.getApplicationContext();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main_menu.fxml"));
            loader.setControllerFactory(context::getBean);

            Parent root = loader.load();


            Scene scene = new Scene(root, 800, 600);
            stage.setTitle("Task Tracker");
            stage.setScene(scene);
//      stage.setMinWidth(600);
//      stage.setMinHeight(400);
//      stage.setResizable(false);
//      stage.setMaximized(true);
            stage.show();

        } catch (IOException e) {

            throw new RuntimeException("Ошибка загрузки /view/main_menu.fxml", e);

        }
    }
}
