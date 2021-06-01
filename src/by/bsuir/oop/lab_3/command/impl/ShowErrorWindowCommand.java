package by.bsuir.oop.lab_3.command.impl;

import by.bsuir.oop.lab_3.command.Command;
import javafx.scene.control.Alert;

public class ShowErrorWindowCommand implements Command {
    private String title;
    private String text;

    public ShowErrorWindowCommand(String title, String text){
        this.title = title;
        this.text = text;
    }

    @Override
    public void execute() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }
}
