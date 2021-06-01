package by.bsuir.oop.lab_3.storage;

import by.bsuir.oop.lab_3.model.Transport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;

public class TransportStorage {
    private static TransportStorage instance;
    private static ObservableList<Transport> transports;

    private TransportStorage() {
        transports = FXCollections.observableArrayList();
    }

    public static TransportStorage getInstance(){
        if (instance == null){
            instance = new TransportStorage();
        }
        return instance;
    }

    public void addTransport(Transport transport) {
        transports.add(transport);
    }

    public void removeTransport(Transport transport) {
        transports.remove(transport);
    }

    public ObservableList<Transport> getTransports() {
        return transports;
    }

    public void addAll(ArrayList<Transport> transports) {
        TransportStorage.transports.addAll(transports);
    }


}
