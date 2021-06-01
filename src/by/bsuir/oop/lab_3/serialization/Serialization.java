package by.bsuir.oop.lab_3.serialization;

import plugins.EncryptorPlugin;

import java.io.File;

public interface Serialization {
    void read(File file, String key);
    void write(File file, Class<? extends EncryptorPlugin> encryptorPlugin, String key);
}