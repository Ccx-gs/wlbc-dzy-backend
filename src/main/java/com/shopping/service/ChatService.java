package com.shopping.service;

import com.shopping.entity.ChatRecord;

import java.util.List;
import java.util.Map;

public interface ChatService {
    String ask(Long userId, String userMsg, List<Map<String, String>> historyList);
    List<ChatRecord> getHistory(Long userId);
    void clearHistory(Long userId);
    List<Map<String, String>> loadSession(Long userId);
}