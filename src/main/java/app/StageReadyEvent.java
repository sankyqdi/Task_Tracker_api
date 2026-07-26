package app;

import javafx.stage.Stage;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

public class StageReadyEvent extends ApplicationEvent {

    private final ConfigurableApplicationContext applicationContext;

    public StageReadyEvent(Stage stage, ConfigurableApplicationContext applicationContext) {

        super(stage);
        this.applicationContext = applicationContext;

    }

    public Stage getStage() {

        return (Stage) getSource();

    }

    public ConfigurableApplicationContext getApplicationContext() {

        return applicationContext;

    }

}
