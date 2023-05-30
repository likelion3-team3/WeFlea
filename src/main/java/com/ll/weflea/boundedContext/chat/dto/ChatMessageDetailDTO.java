package com.ll.weflea.boundedContext.chat.dto;

import com.ll.weflea.boundedContext.chat.entity.ChatMessage;
import com.ll.weflea.boundedContext.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDetailDTO {

    private Long chatMessageId;
    private Long chatRoomId;

    private String roomId;
    private Member sender;
    private String contents;

    public static ChatMessageDetailDTO toChatMessageDetailDTO(ChatMessage chatMessage){
        ChatMessageDetailDTO chatMessageDetailDTO = new ChatMessageDetailDTO();

        chatMessageDetailDTO.setChatMessageId(chatMessage.getId());
        chatMessageDetailDTO.setChatRoomId(chatMessage.getChatRoom().getId());
        chatMessageDetailDTO.setRoomId(chatMessage.getChatRoom().getRoomId());
        chatMessageDetailDTO.setSender(chatMessage.getSender());
        chatMessageDetailDTO.setContents(chatMessage.getMessage());

        return chatMessageDetailDTO;

    }

}
