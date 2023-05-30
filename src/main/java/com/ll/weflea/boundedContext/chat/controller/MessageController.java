package com.ll.weflea.boundedContext.chat.controller;

import com.ll.weflea.boundedContext.chat.dto.ChatMessageDTO;
import com.ll.weflea.boundedContext.chat.entity.ChatMessage;
import com.ll.weflea.boundedContext.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MessageController {

    private final SimpMessagingTemplate template;
    private final ChatService chatService;

    @MessageMapping(value = "/chat/enter")
    public void enter(ChatMessageDTO message){

        String roomId = message.getRoomId();

        List<ChatMessage> messages = chatService.findByRoomId(roomId);

        if (messages.size() == 0) {
            message.setMessage(message.getWriter() + "님이 물건 구매를 원합니다.");
        }

        else {
            for (ChatMessage chatMessage : messages) {
                log.info("메세지 = {}", chatMessage.getMessage());

                ChatMessageDTO chatMessageDTO = ChatMessageDTO.create(roomId, message.getWriter(), chatMessage.getMessage());
                message.setWriter(chatMessageDTO.getWriter());
                message.setMessage(chatMessageDTO.getMessage());
                template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
            }
        }


    }

    @MessageMapping(value = "/chat/message")
    public void message(ChatMessageDTO message){

        chatService.createChatMessage(message);
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }
}
