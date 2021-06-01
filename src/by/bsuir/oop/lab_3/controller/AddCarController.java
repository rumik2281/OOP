package by.bsuir.oop.lab_3.controller;

import by.bsuir.oop.lab_3.Main;
import by.bsuir.oop.lab_3.command.impl.ShowErrorWindowCommand;
import by.bsuir.oop.lab_3.model.Energy;
import by.bsuir.oop.lab_3.model.Car;
import by.bsuir.oop.lab_3.storage.TransportStorage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class AddCarController {
    @FXML
    private TextField inputLengthField;
    @FXML
    private TextField inputWeightField;
    @FXML
    private TextField inputNumberOfPassengersField;
    @FXML
    private TextField inputNumOfDoorsField;
    @FXML
    private TextField inputBrandField;
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
            int numOfDoors = Integer.parseInt(inputNumOfDoorsField.getText());
            int numberOfPassengers = Integer.parseInt(inputNumberOfPassengersField.getText());

            Car car = new Car(length, weight, Energy.valueOf(switchEnergyField.getValue().toUpperCase()), numberOfPassengers, numOfDoors, inputBrandField.getText());
            TransportStorage.getInstance().addTransport(car);
            Main.closeModalWindow();
        } catch (NumberFormatException e) {
            ShowErrorWindowCommand showErrorWindowCommand = new ShowErrorWindowCommand("ERROR", "Check is valid the data");
            showErrorWindowCommand.execute();
        }
    }

}
