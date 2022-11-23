package br.com.crypto.service;

import br.com.crypto.api.dto.PayloadExampleResponseDTO;
import br.com.crypto.cipher.impl.AESCipher;
import br.com.crypto.cipher.impl.RSACipher;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;

@Service
public class CryptoService {

    private AESCipher aesCipher;

    private RSACipher rsaCipher;

    public CryptoService(AESCipher aesCipher, RSACipher rsaCipher) {
        this.aesCipher = aesCipher;
        this.rsaCipher = rsaCipher;
    }

    public String decryptPayload(PayloadExampleResponseDTO request) {
        byte[] decryptedData = aesCipher.decrypt(request.getPayload(), request.getIv(), request.getAlgorithmKey());
        return new String(decryptedData, StandardCharsets.UTF_8);
    }

    public PayloadExampleResponseDTO generatePayloadExample() {
        Key key = aesCipher.generateRandomSymmetricKey();
        byte[] iv = aesCipher.generateRandomIv();

        String base64Key = toBase64(key.getEncoded());
        String base64Iv = toBase64(iv);
        String encryptedPayload = Base64.getEncoder().encodeToString(aesCipher.encrypt("plainData", base64Iv, base64Key));

        PayloadExampleResponseDTO example = new PayloadExampleResponseDTO();
        example.setPayload(encryptedPayload);
        example.setIv(base64Iv);
        example.setAlgorithmKey(base64Key);
        return example;
    }

    private static String toBase64(byte[] arg) {
        return Base64.getEncoder().encodeToString(arg);
    }

}
