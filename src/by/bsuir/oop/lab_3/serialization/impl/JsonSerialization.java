package by.bsuir.oop.lab_3.serialization.impl;

import by.bsuir.oop.lab_3.command.impl.ShowErrorWindowCommand;
import by.bsuir.oop.lab_3.model.Transport;
import by.bsuir.oop.lab_3.serialization.AbstractSerialization;
import by.bsuir.oop.lab_3.storage.TransportStorage;
import com.fasterxml.jackson.databind.ObjectMapper;
import plugins.EncryptorPlugin;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.SocketOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonSerialization extends AbstractSerialization {
    ShowErrorWindowCommand showErrorWindowCommand =
            new ShowErrorWindowCommand("ERROR", "Failed to deserialize");
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static JsonSerialization instance;

    private JsonSerialization() {
    }

    public static JsonSerialization getInstance() {
        if (instance == null) {
            instance = new JsonSerialization();
        }
        return instance;
    }

    @Override
    public void read(File file, String key) {
        String cipherMethodName = findEncryptMethodAndRewriteFile(file);

        try (Reader reader = new FileReader(file)) {
            if (cipherMethodName.startsWith("plugins")) {

                byte[] encryptText = MAPPER.readValue(reader, byte[].class);

                EncryptorPlugin encryptorPlugin = findClassOfEncryptMethod(cipherMethodName);
                byte[] decryptText = encryptorPlugin.decrypt(key, encryptText);


                try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(decryptText);
                     ObjectInput objectInputStream = new ObjectInputStream(byteArrayInputStream)) {

                    ArrayList<Transport> list = (ArrayList<Transport>) objectInputStream.readObject();
                    TransportStorage.getInstance().addAll(list);
                }
            } else {

                List<Object> transports = MAPPER.reader(Transport.class).readValues(reader).readAll();
                for (Object transport : transports) {
                    TransportStorage.getInstance().addTransport((Transport) transport);
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
             Writer writer = new FileWriter(file, true)) {

            writeCipherNameToFile(file, encryptorPlugin.getName());
            oos.writeObject(transportList);
            byte[] cipherText = findCipherText(encryptorPlugin, byteArrayOutputStream.toByteArray(), key);
            MAPPER.writer().writeValue(writer, cipherText);
        } catch (Exception e) {
            showErrorWindowCommand.execute();
        }
    }

    private void writeWithoutEncryption(File file) {
        List<Transport> transports = new ArrayList<>(TransportStorage.getInstance().getTransports());
        try {
            Writer writer = new FileWriter(file);
            for (Transport transport : transports) {
                MAPPER.writerWithDefaultPrettyPrinter().writeValues(writer).write(transport);
            }
        } catch (IOException e) {
            showErrorWindowCommand.execute();
        }
    }
}