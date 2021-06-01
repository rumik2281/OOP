package by.bsuir.oop.lab_3.serialization;

import by.bsuir.oop.lab_3.command.impl.ShowErrorWindowCommand;
import by.bsuir.oop.lab_3.util.PluginLoader;
import plugins.EncryptorPlugin;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;

public abstract class AbstractSerialization implements Serialization {

    protected void writeCipherNameToFile(File file, String cipherName) {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(cipherName + "\n");
        } catch (IOException e) {
            ShowErrorWindowCommand errorWindowCommand = new ShowErrorWindowCommand("Error", "Failed to write cipher name to file");
            errorWindowCommand.execute();
        }
    }

    protected byte[] findCipherText(Class<? extends EncryptorPlugin> encryptorPlugin, byte[] sourceText, String key) throws Exception {
        EncryptorPlugin encryptor = encryptorPlugin.newInstance();
        return encryptor.encrypt(key, sourceText);
    }

    protected EncryptorPlugin findClassOfEncryptMethod(String cipherName) throws InstantiationException, IllegalAccessException {
        PluginLoader pluginLoader = new PluginLoader();
        cipherName = cipherName.substring(8);
        return pluginLoader.receivePlugin(cipherName).newInstance();
    }

    protected String findEncryptMethodAndRewriteFile(File file) {
        StringBuilder cipherText = new StringBuilder();
        String encryptMethod = "";
        try (Scanner scanner = new Scanner(file)) {
            try {
                encryptMethod = scanner.nextLine();
            } catch (Exception ignore) {

            }
            if (encryptMethod.equals("{")) {
                cipherText.append("{\n");
            }
            while (scanner.hasNext()) {
                cipherText.append(scanner.nextLine()).append("\n");
            }
            Writer writer = new FileWriter(file);
            writer.write(cipherText.toString());
            writer.close();
        } catch (IOException e) {
            ShowErrorWindowCommand errorWindowCommand = new ShowErrorWindowCommand("Error", "Failed to receive encrypt method or rewrite file");
            errorWindowCommand.execute();
        }
        return encryptMethod;
    }

}
