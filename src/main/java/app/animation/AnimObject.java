package app.animation;

import javafx.animation.Animation;
import javafx.animation.SequentialTransition;
import javafx.scene.Node;

public record AnimObject(
        Animation sequential,
        Node node
) {

    public void stop() {
        if (sequential != null) {

            sequential.stop();

        }

        node.setTranslateX(0.0);
        node.setTranslateY(0.0);
        node.setOpacity(1.0);
    }

    public void pause() {

        if (sequential != null) {

            sequential.pause();

        }

    }

    public void play() {

        if (sequential != null) {

            sequential.play();

        }

    }
}
