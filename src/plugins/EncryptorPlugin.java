package plugins;

public interface EncryptorPlugin {

    byte[] encrypt(String key, byte[] text) throws Exception;

    byte[] decrypt(String key, byte[] cipherText) throws Exception;
}
