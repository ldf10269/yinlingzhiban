package com.kezibot.service;

import com.kezibot.model.ApiKey;
import com.kezibot.model.User;
import com.kezibot.repository.ApiKeyRepository;
import com.kezibot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * API密钥管理服务
 * 提供API密钥的生成、验证和撤销功能
 */
@Service
public class ApiKeyService {
    @Autowired
    private ApiKeyRepository apiKeyRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * 为指定用户生成新的API密钥
     * 
     * @param userId 用户ID
     * @return 生成的API密钥对象
     * @throws RuntimeException 如果用户不存在
     */
    public ApiKey generateApiKey(Long userId) {
        // 根据用户ID查找用户，如果不存在则抛出异常
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        // 删除用户已存在的API密钥，确保每个用户只有一个有效的API密钥
        apiKeyRepository.findByUser(user).ifPresent(apiKeyRepository::delete);

        // 创建新的API密钥对象
        ApiKey apiKey = new ApiKey();
        apiKey.setUser(user);
        // 使用UUID生成唯一的API密钥
        apiKey.setApiKey(UUID.randomUUID().toString());

        // 保存并返回新的API密钥
        return apiKeyRepository.save(apiKey);
    }

    /**
     * 验证API密钥是否有效
     * 
     * @param apiKeyString 待验证的API密钥字符串
     * @return 是否有效
     */
    public boolean validateApiKey(String apiKeyString) {
        return apiKeyRepository.findByApiKey(apiKeyString).isPresent();
    }

    /**
     * 撤销指定用户的API密钥
     * 
     * @param userId 用户ID
     * @throws RuntimeException 如果用户不存在
     */
    public void revokeApiKey(Long userId) {
        // 根据用户ID查找用户，如果不存在则抛出异常
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        // 如果存在，删除用户的API密钥
        apiKeyRepository.findByUser(user).ifPresent(apiKeyRepository::delete);
    }
}
