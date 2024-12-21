package com.kezibot.repository;

import com.kezibot.model.ApiKey;
import com.kezibot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {
    Optional<ApiKey> findByUser(User user);
    Optional<ApiKey> findByApiKey(String apiKey);
}
