package com.kezibot.controller;

import com.kezibot.model.ApiKey;
import com.kezibot.service.ApiKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/apikeys")
public class ApiKeyController {
    @Autowired
    private ApiKeyService apiKeyService;

    @PostMapping("/generate")
    public ResponseEntity<ApiKey> generateApiKey(@RequestParam Long userId) {
        try {
            ApiKey apiKey = apiKeyService.generateApiKey(userId);
            return ResponseEntity.ok(apiKey);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateApiKey(@RequestParam String apiKey) {
        try {
            boolean isValid = apiKeyService.validateApiKey(apiKey);
            return ResponseEntity.ok(isValid);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(false);
        }
    }

    @DeleteMapping("/revoke")
    public ResponseEntity<String> revokeApiKey(@RequestParam Long userId) {
        try {
            apiKeyService.revokeApiKey(userId);
            return ResponseEntity.ok("API密钥已撤销");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("撤销API密钥失败");
        }
    }
}
