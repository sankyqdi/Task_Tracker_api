package app.config;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class WindowManager {

    private ApplicationContext context;
    @Setter
    private Stage primaryStage;

    @Getter
    private int width = 1200;

    @Getter
    private int height = 800;

    public WindowManager(ApplicationContext context) {

        this.context = context;

    }

    public void switchScene(View view) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(view.getPath()));
            loader.setControllerFactory(asClass -> context.getBean(asClass));
            Parent root = loader.load();

            if (primaryStage.getScene() == null) {

                primaryStage.setScene(new Scene(root));

            } else {

                primaryStage.getScene().setRoot(root);

            }

            primaryStage.setTitle(view.getNameScene());
            primaryStage.show();

        } catch (IOException e) {

            e.printStackTrace();

        }
    }


    public void openModalWindow(View view, boolean isResizable) {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(view.getPath()));
            loader.setControllerFactory(asClass -> context.getBean(asClass));
            Parent root = loader.load();

            Stage modalStage = new Stage();

            modalStage.setTitle(view.getNameScene());

            modalStage.setScene(new Scene(root));

            modalStage.initOwner(primaryStage);

            modalStage.initModality(Modality.APPLICATION_MODAL);

            modalStage.setResizable(isResizable);

            modalStage.showAndWait();

        } catch (IOException e){

            e.printStackTrace();

        }
    }

    public void setSizeScene(int width, int height) {

        if (width < 100 || height < 100) {

            return;

        }

        this.width = width;
        this.height = height;

    }


}
