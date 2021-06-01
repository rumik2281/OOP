package by.bsuir.oop.lab_3.serialization.impl;

import by.bsuir.oop.lab_3.command.impl.ShowErrorWindowCommand;
import by.bsuir.oop.lab_3.model.Transport;
import by.bsuir.oop.lab_3.serialization.AbstractSerialization;
import by.bsuir.oop.lab_3.storage.TransportStorage;
import plugins.EncryptorPlugin;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class XMLSerialization extends AbstractSerialization {
    ShowErrorWindowCommand showErrorWindowCommand =
            new ShowErrorWindowCommand("ERROR", "Failed to deserialize");
    private static XMLSerialization instance;

    private XMLSerialization() {
    }

    public static XMLSerialization getInstance() {
        if (instance == null) {
            instance = new XMLSerialization();
        }
        return instance;
    }

    @Override
    public void read(File file, String key) {
        String encryptMethod = findEncryptMethodAndRewriteFile(file);
        try (XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(file)))) {
            if (encryptMethod.startsWith("plugins")) {
                byte[] bytes = (byte[]) decoder.readObject();
                EncryptorPlugin encryptorPlugin = findClassOfEncryptMethod(encryptMethod);
                byte[] decrypt = encryptorPlugin.decrypt(key, bytes);
                try (ByteArrayInputStream inputStream = new ByteArrayInputStream(decrypt);
                     ObjectInputStream objIn = new ObjectInputStream(inputStream)) {
                    TransportStorage.getInstance().addAll((ArrayList<Transport>) objIn.readObject());
                }
            } else {
                TransportStorage.getInstance().addAll((ArrayList<Transport>) decoder.readObject());
            }
        } catch (FileNotFoundException e) {
            showErrorWindowCommand.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(File file, Class<? extends EncryptorPlugin> encryptorPlugin, String key) {
        if (!key.equals("")) {
            writeWithEncryption(file, encryptorPlugin, key);
        } else {
            writeWithoutEncryption(file);
        }
    }

    private void writeWithoutEncryption(File file) {
        List<Transport> transports = new ArrayList<>(TransportStorage.getInstance().getTransports());
        try (XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(file)))) {
            encoder.writeObject(transports);
        } catch (FileNotFoundException e) {
            showErrorWindowCommand.execute();
        }
    }

    private void writeWithEncryption(File file, Class<? extends EncryptorPlugin> encryptorPlugin, String key) {
        List<Transport> transportList = new ArrayList<>(TransportStorage.getInstance().getTransports());
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(byteArrayOutputStream);
             XMLEncoder xmlEncoder = new XMLEncoder(new FileOutputStream(file, true))) {

            writeCipherNameToFile(file, encryptorPlugin.getName());
            oos.writeObject(transportList);
            byte[] cipherText = findCipherText(encryptorPlugin, byteArrayOutputStream.toByteArray(), key);
            xmlEncoder.writeObject(cipherText);

        } catch (FileNotFoundException exception) {
            ShowErrorWindowCommand errorWindowCommand = new ShowErrorWindowCommand("Error/File", "File not found");
            errorWindowCommand.execute();
        } catch (IOException exception) {
            showErrorWindowCommand.execute();
        } catch (Exception e) {
            ShowErrorWindowCommand errorWindowCommand = new ShowErrorWindowCommand("Error", "Failed to encrypt text");
            errorWindowCommand.execute();
        }
    }

}
