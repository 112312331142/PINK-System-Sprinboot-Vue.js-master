package com.work.pinkweb.Candidate.Controller;

import groovy.util.logging.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * ChatbotController类用于处理与聊天机器人相关的HTTP请求
 * 它使用了Spring框架的@RestController注解，使其成为一个Web控制器
 * @CrossOrigin("*")注解允许跨域请求，以便前端应用可以与这个控制器交互
 * @Slf4j注解用于日志记录
 */
@RestController
@CrossOrigin("*")
@Slf4j
public class ChatbotController {

    /**
     * ChatClient的实例，用于与聊天机器人服务进行通信
     */
    @Qualifier
    private final ChatClient chatClient;

    /**
     * ChatbotController的构造函数
     * @param chatClient 一个ChatClient实例，用于与聊天机器人服务进行通信
     */
    public ChatbotController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    /**
     * 处理流式聊天请求的端点
     * 它接收一个ChatRequest对象作为请求体，并返回一个ServerSentEvent的Flux，用于流式传输聊天响应
     * @param request 包含用户ID和消息的ChatRequest对象
     * @return 一个ServerSentEvent的Flux，用于流式传输聊天响应
     */
    @PostMapping(value = "/chat/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> streamChat(@RequestBody ChatRequest request) {
        //用户id
        String userId = request.userId();

        //通过chatClient发送用户消息，并将响应流转换为ServerSentEvent对象
        return chatClient.prompt(request.message())
                .stream().content().map(content -> ServerSentEvent.builder(content)
                        .event("message").build())
                //问题回答结速标识,以便前端消息展示处理
                .concatWithValues(ServerSentEvent.builder("[DONE]").build())
                .onErrorResume(e -> Flux.just(ServerSentEvent.builder("Error: "
                        + e.getMessage()).event("error").build()));
    }

    /**
     * ChatRequest记录类，用于表示聊天请求
     * 它包含用户ID和消息文本
     */
    record ChatRequest(String userId, String message) {

    }
}
