package app.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;
import java.net.URL;

public class LoadFXML {

    /**
     * Загружает FXML и привязывает к нему переданный контроллер.
     *
     * @param pathFxml   путь к FXML-файлу в ресурсах
     * @param controller экземпляр контроллера
     * @return корневой Node или null в случае ошибки
     */
    public static Node loadFxmlBlank(String pathFxml, Object controller) {
        try {
            URL location = LoadFXML.class.getResource(pathFxml);
            if (location == null) {
                System.err.println("FXML file not found at: " + pathFxml);
                return null;
            }

            FXMLLoader loader = new FXMLLoader(location);
            loader.setController(controller);

            return loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
