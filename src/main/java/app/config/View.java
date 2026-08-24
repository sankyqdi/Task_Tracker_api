package app.config;

import lombok.Getter;

@Getter
public enum View {
    MAIN("/view/main_menu.fxml", "Главное окно"),
    TASKS("/view/show.fxml", "Список задач"),
    UPDATE_STAGE("/view/blanks/pop_up_window/updateStageTask.fxml", "Панель изменения стадии"),
    CREATE("/view/create.fxml", "Создание задач"),
    UPDATE("/view/blanks/pop_up_window/updateTask.fxml", "Обновление задач");

    private String path;
    private String nameScene;

    View(String path, String nameScene) {

        this.nameScene = nameScene;
        this.path = path;

    }
}
