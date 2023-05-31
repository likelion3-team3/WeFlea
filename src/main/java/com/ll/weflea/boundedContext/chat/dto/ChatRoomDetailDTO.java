package com.ll.weflea.boundedContext.chat.dto;

import com.ll.weflea.boundedContext.chat.entity.ChatMessage;
import com.ll.weflea.boundedContext.chat.entity.ChatRoom;
import com.ll.weflea.boundedContext.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomDetailDTO {
    private Long chatRoomId;

    private Member sender;
    private Member receiver;
    private String roomId;
    private String name;
    private String lastMessage;

    public static ChatRoomDetailDTO toChatRoomDetailDTO(ChatRoom chatRoom){
        ChatRoomDetailDTO chatRoomDetailDTO = new ChatRoomDetailDTO();

        List<ChatMessage> chatList = chatRoom.getChatList();

        chatRoomDetailDTO.setChatRoomId(chatRoom.getId());
        chatRoomDetailDTO.setSender(chatRoom.getSender());
        chatRoomDetailDTO.setReceiver(chatRoom.getReceiver());
        chatRoomDetailDTO.setRoomId(chatRoom.getRoomId());
        chatRoomDetailDTO.setName(chatRoom.getRoomName());

        if (chatList.size() == 0) {
            chatRoomDetailDTO.setLastMessage("입장해서 채팅하세요!");
        }

        else {
            chatRoomDetailDTO.setLastMessage(chatList.get(chatList.size() - 1).getMessage());
        }


        return chatRoomDetailDTO;
    }

}
