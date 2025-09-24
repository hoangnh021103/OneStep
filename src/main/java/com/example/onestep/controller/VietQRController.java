package com.example.onestep.controller;

import com.example.onestep.dto.request.QRRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/vietqr")
public class VietQRController {

    @PostMapping("/generate")
    public ResponseEntity<Map<String, String>> generateQR(@RequestBody QRRequest request) {
        try {
            String qrUrl = "https://img.vietqr.io/image/"
                    + request.getBankBin() + "-" + request.getAccountNo() + "-compact2.png"
                    + "?amount=" + request.getAmount()
                    + "&addInfo=" + URLEncoder.encode(request.getAddInfo(), StandardCharsets.UTF_8)
                    + "&accountName=" + URLEncoder.encode(request.getAccountName(), StandardCharsets.UTF_8);

            Map<String, String> response = new HashMap<>();
            response.put("qrUrl", qrUrl);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Failed to generate QR"));
        }
    }
}