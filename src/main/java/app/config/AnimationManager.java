package app.config;

import app.animation.AnimObject;
import javafx.animation.Animation;
import javafx.animation.SequentialTransition;
import javafx.scene.Node;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AnimationManager {

    private Map<String, AnimObject> animations = new ConcurrentHashMap<>();

    private AnimationManager() {}

    private static class AnimationManagerHolder {

        private static final AnimationManager INSTANCE = new AnimationManager();

    }

    public static AnimationManager getInstance() {

        return AnimationManagerHolder.INSTANCE;

    }

    public void registerAnimation(String id, Animation sequential, Node node) {

        unregisterAnimation(id);

        AnimObject animObject = new AnimObject(sequential, node);

        animations.put(id, animObject);

        sequential.setOnFinished(event -> {

            if (sequential.getCycleCount() == Animation.INDEFINITE) {



            } else {

                animations.remove(id);

            }

        });



    }

    public void unregisterAnimation(String id) {

        AnimObject date = animations.remove(id);

        if (date != null) {

            date.stop();

        }
    }

    public void stopAllAnimations() {

        for (AnimObject animObject : animations.values()) {

            animObject.stop();

        }

        animations.clear();

    }

    public void pauseAnimation(String id) {

        if (animations.containsKey(id)) {

            animations.get(id).pause();

        }
    }

    public void pauseAllAnimation() {

        for (AnimObject animObject : animations.values()) {

            animObject.pause();

        }

    }

    public void resumeAnimation(String id) {

        if (animations.containsKey(id)) {

            animations.get(id).play();

        }
    }

    public void resumeAllAnimation() {

        for (AnimObject animObject : animations.values()) {

            animObject.play();

        }

    }

    public boolean isAnimationRunning(String id) {

        AnimObject data = animations.get(id);

        if (data != null && data.sequential() != null) {

            return data.sequential().getStatus() == Animation.Status.RUNNING;

        }

        return false;

    }

    public AnimObject getAnimation(String id) {

        return animations.get(id);

    }

}
