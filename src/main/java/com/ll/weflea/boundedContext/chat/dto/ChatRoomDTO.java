package com.ll.weflea.boundedContext.chat.dto;

import com.ll.weflea.boundedContext.member.entity.Member;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomDTO {

    private String roomId;
    private String name;

    private Member sender;
    private Member receiver;


    public static ChatRoomDTO create(String name, Member sender, Member receiver){
        ChatRoomDTO room = new ChatRoomDTO();

        room.roomId = UUID.randomUUID().toString();
        room.name = name;
        room.sender = sender;
        room.receiver = receiver;

        return room;
    }
}