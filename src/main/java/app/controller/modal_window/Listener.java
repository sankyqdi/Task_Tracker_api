package app.controller.modal_window;

import app.dto.TaskDTO;

public interface Listener {

    public void onTaskUpdated(TaskDTO updatedTask);

}
