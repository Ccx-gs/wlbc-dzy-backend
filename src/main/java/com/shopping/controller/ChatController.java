package com.shopping.controller;

import com.shopping.common.Result;
import com.shopping.common.UserHolder;
import com.shopping.entity.ChatRecord;
import com.shopping.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/ask")
    public Result<Map<String, String>> ask(@RequestBody ChatRequest request) {
        Long userId = UserHolder.getUser().getId();
        String reply = chatService.ask(userId, request.getUserMsg(), request.getHistoryList());
        
        Map<String, String> result = new HashMap<>();
        result.put("reply", reply);
        return Result.ok(result);
    }

    @GetMapping("/history")
    public Result<List<ChatRecord>> getHistory() {
        Long userId = UserHolder.getUser().getId();
        List<ChatRecord> history = chatService.getHistory(userId);
        return Result.ok(history);
    }

    @GetMapping("/session")
    public Result<List<Map<String, String>>> loadSession() {
        Long userId = UserHolder.getUser().getId();
        List<Map<String, String>> session = chatService.loadSession(userId);
        return Result.ok(session);
    }

    @DeleteMapping("/history")
    public Result<Void> clearHistory() {
        Long userId = UserHolder.getUser().getId();
        chatService.clearHistory(userId);
        return Result.ok();
    }

    public static class ChatRequest {
        private String userMsg;
        private List<Map<String, String>> historyList;

        public String getUserMsg() {
            return userMsg;
        }

        public void setUserMsg(String userMsg) {
            this.userMsg = userMsg;
        }

        public List<Map<String, String>> getHistoryList() {
            return historyList;
        }

        public void setHistoryList(List<Map<String, String>> historyList) {
            this.historyList = historyList;
        }
    }
}