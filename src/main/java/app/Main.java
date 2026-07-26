package app;

import javafx.application.Application;
import javafx.scene.text.Font;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Main {

    public static void main(String[] args) {

        Font.loadFont(Main.class.getResourceAsStream("/fonts/PermanentMarker-Regular.ttf"), 14);
        Application.launch(JavaFxApplication.class, args);

    }
}
