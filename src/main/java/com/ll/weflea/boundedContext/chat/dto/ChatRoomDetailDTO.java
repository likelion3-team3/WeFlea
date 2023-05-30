package com.ll.weflea.boundedContext.chat.dto;

import com.ll.weflea.boundedContext.chat.entity.ChatRoom;
import com.ll.weflea.boundedContext.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomDetailDTO {
    private Long chatRoomId;

    private Member sender;
    private Member receiver;
    private String roomId;
    private String name;

    public static ChatRoomDetailDTO toChatRoomDetailDTO(ChatRoom chatRoom){
        ChatRoomDetailDTO chatRoomDetailDTO = new ChatRoomDetailDTO();

        chatRoomDetailDTO.setChatRoomId(chatRoom.getId());
        chatRoomDetailDTO.setSender(chatRoom.getSender());
        chatRoomDetailDTO.setReceiver(chatRoom.getReceiver());
        chatRoomDetailDTO.setRoomId(chatRoom.getRoomId());
        chatRoomDetailDTO.setName(chatRoom.getRoomName());

        return chatRoomDetailDTO;
    }

}
