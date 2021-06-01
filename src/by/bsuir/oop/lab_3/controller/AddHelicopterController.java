package by.bsuir.oop.lab_3.controller;

import by.bsuir.oop.lab_3.Main;
import by.bsuir.oop.lab_3.command.impl.ShowErrorWindowCommand;
import by.bsuir.oop.lab_3.model.Energy;
import by.bsuir.oop.lab_3.model.Transport;
import by.bsuir.oop.lab_3.model.Helicopter;
import by.bsuir.oop.lab_3.storage.TransportStorage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class AddHelicopterController {
    @FXML
    private TextField inputLengthField;
    @FXML
    private TextField inputWeightField;
    @FXML
    private TextField inputFuelVolumeField;
    @FXML
    private TextField inputMaxFlightAltitudeField;
    @FXML
    private ChoiceBox<String> switchEnergyField;

    @FXML
    void initialize(){
        for (Energy energy : Energy.values()) {
            switchEnergyField.getItems().add(energy.name());
        }
    }

    @FXML
    void cancelButtonAction(ActionEvent event) {
        Main.closeModalWindow();
    }

    @FXML
    void submitButtonAction(ActionEvent event) {
        try{
            int length = Integer.parseInt(inputLengthField.getText());
            int weight = Integer.parseInt(inputWeightField.getText());
            int volume = Integer.parseInt(inputFuelVolumeField.getText());
            int maxFlightAltitude = Integer.parseInt(inputMaxFlightAltitudeField.getText());

            Transport helicopter = new Helicopter(length, weight,
                    Energy.valueOf(switchEnergyField.getValue().toUpperCase()),
                    maxFlightAltitude, volume);

            TransportStorage.getInstance().addTransport(helicopter);
            Main.closeModalWindow();
        } catch (NumberFormatException e) {
            ShowErrorWindowCommand showErrorWindowCommand = new ShowErrorWindowCommand("ERROR", "Check is valid the data");
            showErrorWindowCommand.execute();
        }
    }
}
