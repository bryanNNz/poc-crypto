package br.com.crypto.cipher;

public interface ICipher {

    byte[] encrypt(String plainData, String iv, String key);

    byte[] decrypt(String base64Data, String iv, String key);

}
