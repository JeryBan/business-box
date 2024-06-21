package ban.jery.businessbox.config;

import ban.jery.businessbox.dto.chat.ChatEntryDTO;
import ban.jery.businessbox.service.chat.IChatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final IChatService chatService;

    public WebSocketConfig(IChatService chatService) {
        this.chatService = chatService;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new ChatWebSocketHandler(), "/chat")
                .addInterceptors(new HttpSessionHandshakeInterceptor())
                .setAllowedOrigins("*");
    }


    public class ChatWebSocketHandler extends TextWebSocketHandler {
        private List<WebSocketSession> sessions = new ArrayList<>();
        private Set<String> businessList = new HashSet<>();
        private ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public void afterConnectionEstablished(WebSocketSession session) throws Exception {
            String principal = getPrincipal(session);

            if (!businessList.contains(principal)) {
                sessions.add(session);
                businessList.add(principal);
                log.info("Websocket connection with " + principal + " established.");
            }

        }

        @Override
        public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
            ChatEntryDTO chatEntryDTO = objectMapper.readValue(message.getPayload(), ChatEntryDTO.class);
            chatEntryDTO.setCreatedAt(
                    chatService.insertChatEntry(chatEntryDTO)
                            .getCreatedAt().toString()
            );

            String jsonMessage = objectMapper.writeValueAsString(chatEntryDTO);
            for (WebSocketSession webSocketSession : sessions) {
                webSocketSession.sendMessage(new TextMessage(jsonMessage));
            }
        }

        @Override
        public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
            String principal = getPrincipal(session);

            sessions.remove(session);
            businessList.remove(principal);
            log.info(principal + " disconnected from Websocket service.");
        }

        private String getPrincipal(WebSocketSession session) {
            // work around to pass credentials through the session
            return session.getHandshakeHeaders().getValuesAsList("sec-websocket-protocol").get(0);
        }
    }

}
