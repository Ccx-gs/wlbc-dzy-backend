package com.shopping.config;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AITool {

    private final AIConfig aiConfig;
    private final ObjectMapper objectMapper;

    private static final String SYSTEM_PROMPT = 
        "你是一个专业的数码电子产品商城智能客服助手，你的名字叫小智。\n" +
        "你的职责是解答用户关于数码商城购物的相关问题，包括：\n" +
        "1. 商品参数、价格、库存查询\n" +
        "2. 下单支付流程\n" +
        "3. 订单状态查询\n" +
        "4. 物流配送信息\n" +
        "5. 售后退换货政策\n" +
        "6. 收货地址管理\n" +
        "7. 积分余额查询与使用说明\n" +
        "8. 优惠券查询（可用券数量、面额、门槛、有效期）\n" +
        "\n" +
        "商城主营商品分类：\n" +
        "- 数码电子\n" +
        "- 手机通讯\n" +
        "- 电脑整机&外设\n" +
        "- 影音数码\n" +
        "- 可穿戴设备\n" +
        "- 摄影摄像\n" +
        "- 智能家居\n" +
        "- 智能大家电\n" +
        "- 智能小家电\n" +
        "- 手机配件\n" +
        "\n" +
        "重要规则：\n" +
        "1. 仅能回答商城购物相关问题，其他问题（游戏、影视、八卦、生活服务等）请礼貌拒绝\n" +
        "2. 如果用户询问无关内容，统一回复：'不好意思，我仅提供商城数码商品购物相关咨询，其他问题无法为您解答'\n" +
        "3. 回答必须简洁通俗，贴合电商客服口吻，不要输出冗长内容\n" +
        "4. 必须使用下方提供的商品参考资料进行回答，禁止编造商城不存在的商品、价格、库存信息\n" +
        "5. 如果参考资料中没有用户询问的商品，请告知用户：'抱歉，暂无该款数码产品'\n" +
        "\n" +
        "商品参考资料：\n";

    public String chat(String userMsg, List<Map<String, String>> historyList, String productInfo) {
        List<Map<String, Object>> messages = new ArrayList<>();

        Map<String, Object> systemMessage = new HashMap<>();
        systemMessage.put("role", "system");
        systemMessage.put("content", SYSTEM_PROMPT + (productInfo != null ? productInfo : "暂无商品信息"));
        messages.add(systemMessage);

        if (historyList != null && !historyList.isEmpty()) {
            for (Map<String, String> history : historyList) {
                Map<String, Object> msg = new HashMap<>();
                msg.put("role", history.get("role"));
                msg.put("content", history.get("content"));
                messages.add(msg);
            }
        }

        Map<String, Object> userMessage = new HashMap<>();
        userMessage.put("role", "user");
        userMessage.put("content", userMsg);
        messages.add(userMessage);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", aiConfig.getModel());
        requestBody.put("messages", messages);
        requestBody.put("max_tokens", aiConfig.getMaxTokens());
        requestBody.put("temperature", 0.7);
        requestBody.put("plain_text", true);

        try {
            String jsonBody = objectMapper.writeValueAsString(requestBody);
            
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(aiConfig.getApiUrl()))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + aiConfig.getApiKey())
                    .timeout(java.time.Duration.ofMillis(aiConfig.getTimeout()))
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return parseResponse(response.body());
            } else {
                log.error("AI API request failed, status code: {}, response: {}", response.statusCode(), response.body());
                throw new RuntimeException("AI服务暂时不可用，请稍后重试");
            }
        } catch (JsonProcessingException e) {
            log.error("AI request body serialization failed", e);
            throw new RuntimeException("请求参数错误");
        } catch (IOException | InterruptedException e) {
            log.error("AI API call failed", e);
            throw new RuntimeException("网络请求超时，请稍后重试");
        }
    }

    private String parseResponse(String responseBody) {
        try {
            JsonNode root = objectMapper.readTree(responseBody);
            JsonNode choices = root.get("choices");
            if (choices != null && choices.isArray() && !choices.isEmpty()) {
                JsonNode firstChoice = choices.get(0);
                JsonNode message = firstChoice.get("message");
                if (message != null) {
                    JsonNode content = message.get("content");
                    if (content != null) {
                        return content.asText().trim();
                    }
                }
            }
            log.error("AI response parsing failed, response: {}", responseBody);
            return "抱歉，我暂时无法理解您的问题，请换一种方式提问";
        } catch (JsonProcessingException e) {
            log.error("AI response JSON parsing failed", e);
            return "抱歉，服务暂时出现异常，请稍后重试";
        }
    }
}