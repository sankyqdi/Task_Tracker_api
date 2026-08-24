package app.animation;

import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class FallAnimation {

    public static SequentialTransition animateMoveFadeAndResetLeft(Node node) {
        // 1. Уменьшаем размер и смещаем узел в левую позицию
        node.setScaleX(0.7);
        node.setScaleY(0.7);
        node.setTranslateX(-15.0); // Смещение влево относительно центра

        // 2. Траектория падения из левой точки вниз
        Path path = new Path();
        path.getElements().add(new MoveTo(0, 0));
        path.getElements().add(new CubicCurveTo(0, 30, 12, 45, 15, 60));

        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.seconds(4));
        pathTransition.setPath(path);
        pathTransition.setNode(node);

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), node);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.3), node);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        // 3. Возвращаем сдвиг и размер при перезапуске цикла
        Timeline resetPosition = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(node.translateXProperty(), -15.0),
                        new KeyValue(node.scaleXProperty(), 0.7),
                        new KeyValue(node.scaleYProperty(), 0.7)
                )
        );

        SequentialTransition sequence = new SequentialTransition(
                pathTransition,
                fadeOut,
                resetPosition,
                fadeIn
        );

        sequence.setCycleCount(Animation.INDEFINITE);
        return sequence;
    }

    public static SequentialTransition animateMoveFadeAndResetRight(Node node) {
        Path path = new Path();
        path.getElements().add(new MoveTo(0, 0));
        path.getElements().add(new CubicCurveTo(0, 100, 5, 200, 9, 270));

        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.seconds(4));
        pathTransition.setPath(path);
        pathTransition.setNode(node);

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), node);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.3), node);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        SequentialTransition sequence = new SequentialTransition(
                pathTransition,
                fadeOut,
                fadeIn
        );

        sequence.setCycleCount(Animation.INDEFINITE);
        return sequence;
    }

    public static Timeline smoothReturnToStart(Node node) {
        if (node == null) return new Timeline();

        double currentX = node.getTranslateX();
        double currentY = node.getTranslateY();
        double currentOpacity = node.getOpacity();
        double currentScaleX = node.getScaleX();
        double currentScaleY = node.getScaleY();

        Timeline bounceTimeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(node.translateXProperty(), currentX),
                        new KeyValue(node.translateYProperty(), currentY),
                        new KeyValue(node.opacityProperty(), currentOpacity),
                        new KeyValue(node.scaleXProperty(), currentScaleX),
                        new KeyValue(node.scaleYProperty(), currentScaleY)
                ),
                new KeyFrame(Duration.millis(200),
                        new KeyValue(node.translateXProperty(), currentX * 0.3),
                        new KeyValue(node.translateYProperty(), currentY * 0.3),
                        new KeyValue(node.scaleXProperty(), 0.9),
                        new KeyValue(node.scaleYProperty(), 0.9)
                ),
                new KeyFrame(Duration.millis(350),
                        new KeyValue(node.translateXProperty(), -currentX * 0.05),
                        new KeyValue(node.translateYProperty(), -currentY * 0.05),
                        new KeyValue(node.scaleXProperty(), 1.05),
                        new KeyValue(node.scaleYProperty(), 1.05)
                ),
                new KeyFrame(Duration.millis(550),
                        new KeyValue(node.translateXProperty(), 0),
                        new KeyValue(node.translateYProperty(), 0),
                        new KeyValue(node.opacityProperty(), 1.0),
                        new KeyValue(node.scaleXProperty(), 1.0),
                        new KeyValue(node.scaleYProperty(), 1.0)
                )
        );

        bounceTimeline.setCycleCount(1);
        return bounceTimeline;
    }
}
