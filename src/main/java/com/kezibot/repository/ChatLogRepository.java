package com.kezibot.repository;

import com.kezibot.model.ChatLog;
import com.kezibot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatLogRepository extends JpaRepository<ChatLog, Long> {
    List<ChatLog> findByUser(User user);
    List<ChatLog> findByUserOrderByTimestampDesc(User user);
}
