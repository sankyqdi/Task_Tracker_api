package app.controller;

import app.model.TaskTag;
import app.utils.LoadFXML;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("prototype")
public class TagItemController {

    private final String pathTags = "/view/blanks/tags/default_icon_tag.fxml";
    private final String pathCustomTags = "/view/blanks/tags/custom_icon_tag.fxml";

    @FXML
    private Label stringTag;

    private void setTextTag(String textTag) {

        stringTag.setText(textTag);

    }

    public Node setItemTags(TaskTag tag) {

            Node node = LoadFXML.loadFxmlBlank(pathTags, this);

            setTextTag(tag.getFormattedTag());

        return node;

    }

    public Node setItemTags(TaskTag tag, double width, double height) {

        Node node = LoadFXML.loadFxmlBlank(pathTags, this);

        if (node instanceof javafx.scene.layout.Region region) {

            region.setPrefWidth(width);
            region.setPrefHeight(height);

        }

        stringTag.setText(tag.getFormattedTag());

        return node;

    }

    public Node setItemTags(TaskTag tag, double width, double height, String id) {

        Node node = LoadFXML.loadFxmlBlank(pathTags, this);

        if (node instanceof javafx.scene.layout.Region region) {

            region.setPrefWidth(width);
            region.setPrefHeight(height);

        }
        node.setId(id);

        stringTag.setText(tag.getFormattedTag());

        return node;

    }

    public Node setItemCustomTags(String tag) {

        Node node = LoadFXML.loadFxmlBlank(pathCustomTags, this);

        setTextTag(tag);


        return node;

    }

    public Node setItemCustomTags(String tag, double width, double height) {

        Node node = LoadFXML.loadFxmlBlank(pathCustomTags, this);


        if (node instanceof javafx.scene.layout.Region region) {

            region.setPrefWidth(width);
            region.setPrefHeight(height);

        }

        setTextTag(tag);

        return node;

    }

    public Node setItemCustomTags(String tag, double width, double height, String id) {

        Node node = LoadFXML.loadFxmlBlank(pathCustomTags, this);

        if (node instanceof javafx.scene.layout.Region region) {

            region.setPrefWidth(width);
            region.setPrefHeight(height);

        }

        node.setId(id);

        setTextTag(tag);

        return node;

    }

}