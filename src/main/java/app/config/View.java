package app.config;

import lombok.Getter;

@Getter
public enum View {
    MAIN("/view/main_menu.fxml", "Главное окно"),
    TASKS("/view/show.fxml", "Список задач"),
    CREATE("/view/create.fxml", "Создание задач"),
    UPDATE("/view/update.fxml", "Обновление задач");

    private String path;
    private String nameScene;

    View(String path, String nameScene) {

        this.nameScene = nameScene;
        this.path = path;

    }
}
