package app;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class JavaFxApplication extends Application {

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() {

        this.applicationContext = new SpringApplicationBuilder()
                .sources(Main.class)
                .run();

    }

    @Override
    public void start(Stage stage) {

        applicationContext.publishEvent(new StageReadyEvent(stage, applicationContext));

    }

    @Override
    public void stop() {

        this.applicationContext.close();
        Platform.exit();

    }



}
