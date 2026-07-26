package app.config;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Setter;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class WindowManager {

    private ApplicationContext context;
    @Setter
    private Stage primaryStage;

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


}
