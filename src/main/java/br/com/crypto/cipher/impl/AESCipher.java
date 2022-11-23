package br.com.crypto.cipher.impl;

import br.com.crypto.cipher.ICipher;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;

@Component
public class AESCipher implements ICipher {

    private static Integer AES_KEY_SIZE = 256;

    private static Integer GCM_IV_LENGTH = 12;

    private static Integer GCM_TAG_LENGTH = 128;

    private static String AES_GCM_ALGORITHM = "AES/GCM/NoPadding";

    @Override
    public byte[] encrypt(String plainData, String base64Iv, String base64Key) {
        try {
            Cipher cipher =  Cipher.getInstance(AES_GCM_ALGORITHM);
            SecretKeySpec keySpec = new SecretKeySpec(getByteArrayFrom(base64Key), "AES");
            byte[] iv = getByteArrayFrom(base64Iv);
            GCMParameterSpec gcmSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmSpec);

            byte[] encryptedData = cipher.doFinal(plainData.getBytes());

            ByteBuffer byteBuffer = ByteBuffer.allocate(iv.length + encryptedData.length);
            byteBuffer.put(iv);
            byteBuffer.put(encryptedData);
            return byteBuffer.array();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public byte[] decrypt(String base64Data, String base64Iv, String base64Key) {
        try {
            Cipher cipher =  Cipher.getInstance(AES_GCM_ALGORITHM);
            byte[] decodedData = getByteArrayFrom(base64Data);
            AlgorithmParameterSpec gcmIv = new GCMParameterSpec(GCM_TAG_LENGTH, decodedData, 0, GCM_IV_LENGTH);
            SecretKeySpec keySpec = new SecretKeySpec(getByteArrayFrom(base64Key), "AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmIv);
            return cipher.doFinal(decodedData, GCM_IV_LENGTH, decodedData.length - GCM_IV_LENGTH);
        } catch (Exception e) {
           e.printStackTrace();
            return null;
        }
    }

    private byte[] getByteArrayFrom(String base64Data) {
        return Base64.getDecoder().decode(base64Data);
    }

    public Key generateRandomSymmetricKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(AES_KEY_SIZE);
            return keyGenerator.generateKey();
        } catch (Exception e) {
            return null;
        }
    }

    public byte[] generateRandomIv() {
        try {
            byte[] iv = new byte[GCM_IV_LENGTH];
            SecureRandom secureRandom = new SecureRandom();
            secureRandom.nextBytes(iv);
            return iv;
        } catch (Exception e) {
            return null;
        }
    }

}
