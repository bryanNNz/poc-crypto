package br.com.crypto.api.controller;

import br.com.crypto.api.dto.PayloadExampleResponseDTO;
import br.com.crypto.service.CryptoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class CryptoController {

    private CryptoService cryptoService;

    public CryptoController(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    @GetMapping("/encrypt")
    public ResponseEntity<PayloadExampleResponseDTO> getRandomPayload() {
        return ResponseEntity.ok(cryptoService.generatePayloadExample());
    }

    @PostMapping("/decrypt")
    public ResponseEntity<String> decryptPayload(@RequestBody PayloadExampleResponseDTO request) {
        return ResponseEntity.ok(cryptoService.decryptPayload(request));
    }

}
