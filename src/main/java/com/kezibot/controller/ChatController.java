package com.kezibot.controller;

import com.kezibot.model.ChatLog;
import com.kezibot.service.ChatLogService;
import com.volcengine.ark.runtime.model.completion.chat.ChatCompletionRequest;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessage;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessageRole;
import com.volcengine.ark.runtime.service.ArkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Value("${kezibot.api.base-url}")
    private String apiBaseUrl;

    @Value("${kezibot.api.key}")
    private String apiKey;

    @Autowired
    private ChatLogService chatLogService;

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage( @RequestParam Long userId,  @RequestBody String message ) {
        try {
            // 保存用户消息
            chatLogService.saveChatLog(userId, message, false);

            RestTemplate restTemplate = new RestTemplate();

            String apiKey = "cedf22c8-f2d0-4f37-b82f-f393448520f0";
            ArkService service = ArkService.builder().apiKey(apiKey).build();
            System.out.println("\n----- standard request -----");
            final List<ChatMessage> messages = new ArrayList<>();
            final ChatMessage systemMessage = ChatMessage.builder().role(ChatMessageRole.SYSTEM).content("你是豆包，是由字节跳动开发的 AI 人工智能助手").build();
            final ChatMessage userMessage = ChatMessage.builder().role(ChatMessageRole.USER).content(message).build();
            messages.add(systemMessage);
            messages.add(userMessage);

            ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                    .model("ep-20241219165417-57pkg")
                    .messages(messages)
                    .build();

            final String[] content = {null};
            service.createChatCompletion(chatCompletionRequest).getChoices().forEach(choice -> content[0] = (String) choice.getMessage().getContent());

            // 保存机器人响应
            //chatLogService.saveChatLog(userId, response, true);

            return ResponseEntity.ok(content[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("消息发送失败: " + e.getMessage());
        }
    }

    @GetMapping("/history")
    public ResponseEntity<List<ChatLog>> getChatHistory(@RequestParam Long userId) {
        try {
            List<ChatLog> chatLogs = chatLogService.getChatLogsByUser(userId);
            return ResponseEntity.ok(chatLogs);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/clear")
    public ResponseEntity<String> clearChatHistory(@RequestParam Long userId) {
        try {
            chatLogService.clearChatLogsByUser(userId);
            return ResponseEntity.ok("聊天记录已清空");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("清空聊天记录失败: " + e.getMessage());
        }
    }
}
