package by.bsuir.oop.lab_3.serialization.impl;

import by.bsuir.oop.lab_3.command.impl.ShowErrorWindowCommand;
import by.bsuir.oop.lab_3.model.Transport;
import by.bsuir.oop.lab_3.serialization.AbstractSerialization;
import by.bsuir.oop.lab_3.storage.TransportStorage;
import plugins.EncryptorPlugin;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BinarySerialization extends AbstractSerialization {
    ShowErrorWindowCommand showErrorWindowCommand =
            new ShowErrorWindowCommand("ERROR", "Failed to deserialize");
    private static BinarySerialization instance;

    private BinarySerialization() {
    }

    public static BinarySerialization getInstance() {
        if (instance == null) {
            instance = new BinarySerialization();
        }
        return instance;
    }

    @Override
    public void read(File file, String key) {
        String encryptMethod = findEncryptMethodAndRewriteFile(file);
        try (Scanner scanner = new Scanner(file)) {
            if (encryptMethod.startsWith("plugins")) {
                EncryptorPlugin encryptorPlugin = findClassOfEncryptMethod(encryptMethod);
                String cipherText = scanner.nextLine();
                byte[] decryptText = encryptorPlugin.decrypt(key, cipherText.getBytes());
                try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(decryptText);
                     ObjectInput objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
                    TransportStorage.getInstance().addAll((ArrayList<Transport>) objectInputStream.readObject());
                }
            } else {
                try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file))) {
                    TransportStorage.getInstance().addAll((ArrayList<Transport>) objectInputStream.readObject());
                }
            }
        } catch (Exception e) {
            showErrorWindowCommand.execute();
        }
    }

    @Override
    public void write(File file, Class<? extends EncryptorPlugin> encryptorPlugin, String key) {
        if (encryptorPlugin != null) {
            writeWithEncryption(file, encryptorPlugin, key);
        } else {
            writeWithoutEncryption(file);
        }
    }

    private void writeWithEncryption(File file, Class<? extends EncryptorPlugin> encryptorPlugin, String key) {
        List<Transport> transportList = new ArrayList<>(TransportStorage.getInstance().getTransports());
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(byteArrayOutputStream);
             FileOutputStream fileOutputStream = new FileOutputStream(file, true)) {

            writeCipherNameToFile(file, encryptorPlugin.getName());
            oos.writeObject(transportList);
            byte[] cipherText = findCipherText(encryptorPlugin, byteArrayOutputStream.toByteArray(), key);
            fileOutputStream.write(cipherText);

        } catch (Exception exception) {
            showErrorWindowCommand.execute();
        }
    }

    private void writeWithoutEncryption(File file) {
        List<Transport> transportList = new ArrayList<>(TransportStorage.getInstance().getTransports());
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            objectOutputStream.writeObject(transportList);
        } catch (IOException e) {
            showErrorWindowCommand.execute();
        }
    }
}