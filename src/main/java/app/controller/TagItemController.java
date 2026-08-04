package app.controller;

import app.model.TaskTag;
import app.utils.LoadFXML;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
@Scope("prototype")
@RequiredArgsConstructor
public class TagItemController {

    private final String pathTags = "/view/blanks/tags/default_icon_tag.fxml";
    private final String pathCustomTags = "/view/blanks/tags/custom_icon_tag.fxml";

    @FXML
    private AnchorPane itemTag;

    @FXML
    private Label stringTag;

    private void setTextTag(String textTag) {

        stringTag.setText(textTag);

    }

    public List<Node> setItemTags(Set<TaskTag> tags) {

        List<Node> nodes = new ArrayList<>();

        for (TaskTag tag : tags) {

            Node node = LoadFXML.loadFxmlBlank(pathTags, this);
            setTextTag(tag.getFormattedTag());

            nodes.add(node);

        }

        return nodes;

    }

    public List<Node> setItemCustomTags(Set<String> tags) {

        List<Node> nodes = new ArrayList<>();

        for (String tag : tags) {

            Node node = LoadFXML.loadFxmlBlank(pathCustomTags, this);
            setTextTag(tag);

            nodes.add(node);

        }

        return nodes;

    }
}
