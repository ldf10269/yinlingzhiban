package com.kezibot.service;

import com.kezibot.model.ChatLog;
import com.kezibot.model.User;
import com.kezibot.repository.ChatLogRepository;
import com.kezibot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatLogService {
    @Autowired
    private ChatLogRepository chatLogRepository;

    @Autowired
    private UserRepository userRepository;

    public ChatLog saveChatLog(Long userId, String message, boolean isBot) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        ChatLog chatLog = new ChatLog();
        chatLog.setUser(user);
        chatLog.setMessage(message);
        chatLog.setIsBot(isBot);

        return chatLogRepository.save(chatLog);
    }

    public List<ChatLog> getChatLogsByUser(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        return chatLogRepository.findByUserOrderByTimestampDesc(user);
    }

    public void clearChatLogsByUser(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        List<ChatLog> chatLogs = chatLogRepository.findByUser(user);
        chatLogRepository.deleteAll(chatLogs);
    }
}
