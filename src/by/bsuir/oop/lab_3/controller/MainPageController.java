package by.bsuir.oop.lab_3.controller;

import by.bsuir.oop.lab_3.Main;
import by.bsuir.oop.lab_3.command.impl.ShowErrorWindowCommand;
import by.bsuir.oop.lab_3.command.impl.ShowInfoWindowCommand;
import by.bsuir.oop.lab_3.model.Transport;
import by.bsuir.oop.lab_3.serialization.Serialization;
import by.bsuir.oop.lab_3.serialization.impl.BinarySerialization;
import by.bsuir.oop.lab_3.serialization.impl.JsonSerialization;
import by.bsuir.oop.lab_3.serialization.impl.XMLSerialization;
import by.bsuir.oop.lab_3.storage.TransportStorage;
import by.bsuir.oop.lab_3.util.PluginLoader;
import javafx.fxml.FXML;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import plugins.EncryptorPlugin;

import java.io.File;
import java.io.IOException;

public class MainPageController {
    private static final TransportStorage transportList = TransportStorage.getInstance();
    private Serialization serialization;
    private Class<? extends EncryptorPlugin> encryptorClass;

    @FXML
    private TableView<Transport> table;
    @FXML
    private TableColumn<Transport, String> classTableColumn;
    @FXML
    private TableColumn<Transport, Integer> lengthTableColumn;
    @FXML
    private TableColumn<Transport, Integer> weightTableColumn;
    @FXML
    private TableColumn<Transport, String> energyTableColumn;
    @FXML
    private RadioMenuItem serializeRadioButton;
    @FXML
    private RadioMenuItem aesCipherMenu;
    @FXML
    private RadioMenuItem blowfishCipherMenu;
    @FXML
    private RadioMenuItem noCipherMenu;
    @FXML
    private TextField inputKeyField;


    @FXML
    void initialize() {
        classTableColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        lengthTableColumn.setCellValueFactory(new PropertyValueFactory<>("length"));
        weightTableColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));
        energyTableColumn.setCellValueFactory(new PropertyValueFactory<>("energy"));
        table.setItems(transportList.getTransports());

        aesCipherMenu.setOnAction(e -> inputKeyField.setVisible(true));
        blowfishCipherMenu.setOnAction(e -> inputKeyField.setVisible(true));
        noCipherMenu.setOnAction(e -> {
            inputKeyField.clear();
            inputKeyField.setVisible(false);
        });
    }

    @FXML
    void addSpaceShipButtonAction() {
        openModalWindow("helicopter.fxml", "Helicopter");
    }

    @FXML
    void addCarButtonAction() {
        openModalWindow("car.fxml", "Car");
    }

    @FXML
    void addMotorcycleButtonAction() {
        openModalWindow("motorcycle.fxml", "Motorcycle");
    }

    @FXML
    void addPlaneButtonAction() {
        openModalWindow("plane.fxml", "Plane");
    }

    @FXML
    void addShipButtonAction() {
        openModalWindow("ship.fxml", "Ship");
    }

    @FXML
    void binaryButtonAction() {
        serialization = BinarySerialization.getInstance();
        chooseActionWithFile();
    }

    @FXML
    void customButtonAction() {
        serialization = JsonSerialization.getInstance();
        chooseActionWithFile();
    }

    @FXML
    void xmlButtonAction() {
        serialization = XMLSerialization.getInstance();
        chooseActionWithFile();
    }

    @FXML
    void keyPressedAction(KeyEvent event) {
        Transport selectedTransport = table.getSelectionModel().getSelectedItem();
        if (event.getCode() == KeyCode.DELETE && selectedTransport != null) {
            transportList.removeTransport(selectedTransport);
            table.refresh();
        }
    }

    @FXML
    void mouseClickedAction(MouseEvent event) {
        Transport selectedTransport = table.getSelectionModel().getSelectedItem();
        if (event.getButton() == MouseButton.PRIMARY && selectedTransport != null) {
            ShowInfoWindowCommand showInfoWindowCommand = new ShowInfoWindowCommand(selectedTransport.toString());
            showInfoWindowCommand.execute();
        }
    }

    private void chooseActionWithFile() {
        if (serializeRadioButton.isSelected()) {
            chooseEncryptionMethod();
            serializeToFile();
        } else {
            deserializeFile();
        }
    }

    private void chooseEncryptionMethod() {
        PluginLoader pluginLoader = new PluginLoader();
        if (aesCipherMenu.isSelected()) {
            encryptorClass = pluginLoader.receivePlugin("AesCipher");
        } else if (blowfishCipherMenu.isSelected()) {
            encryptorClass = pluginLoader.receivePlugin("BlowfishCipher");
        }
    }

    private void serializeToFile() {
        if (!noCipherMenu.isSelected() && (inputKeyField.getLength() == 0 || inputKeyField.getLength() > 10)) {
            ShowErrorWindowCommand showErrorWindowCommand =
                    new ShowErrorWindowCommand("ERROR", "Check the key.\nIt`s should be under 10 symbols");
            showErrorWindowCommand.execute();
        } else {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showSaveDialog(Main.getStage());
            if (file != null) {
                serialization.write(file, encryptorClass, inputKeyField.getText());
                inputKeyField.clear();
            }
        }
    }

    private void deserializeFile() {
        if (!noCipherMenu.isSelected() && (inputKeyField.getLength() == 0 || inputKeyField.getLength() > 10)) {
            ShowErrorWindowCommand showErrorWindowCommand =
                    new ShowErrorWindowCommand("ERROR", "Check the key.\nIt`s should be under 10 symbols");
            showErrorWindowCommand.execute();
        } else {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(Main.getStage());
            if (file != null) {
                serialization.read(file, inputKeyField.getText());
                inputKeyField.clear();
            }
        }
    }

    private void openModalWindow(String path, String title) {
        try {
            Main.showModalWindow(path, title);
        } catch (IOException e) {
            ShowErrorWindowCommand showErrorWindowCommand =
                    new ShowErrorWindowCommand("ERROR", "Can`t open the window");
            showErrorWindowCommand.execute();
        }
    }
}