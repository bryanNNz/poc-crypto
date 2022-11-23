package br.com.crypto.api.dto;

import java.io.Serializable;

public class PayloadExampleResponseDTO implements Serializable {

    private String iv;

    private String algorithmKey;

    private String payload;

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public String getAlgorithmKey() {
        return algorithmKey;
    }

    public void setAlgorithmKey(String algorithmKey) {
        this.algorithmKey = algorithmKey;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
