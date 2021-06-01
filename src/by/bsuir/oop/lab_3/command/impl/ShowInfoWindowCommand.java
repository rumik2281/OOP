package by.bsuir.oop.lab_3.command.impl;

import by.bsuir.oop.lab_3.command.Command;
import javafx.scene.control.Alert;

public class ShowInfoWindowCommand implements Command {
    private String text;

    public ShowInfoWindowCommand(String text){
        this.text = text;
    }

    @Override
    public void execute() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("INFO");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }
}
