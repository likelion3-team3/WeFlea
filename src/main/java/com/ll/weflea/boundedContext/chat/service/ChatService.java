package com.ll.weflea.boundedContext.chat.service;

import com.ll.weflea.boundedContext.chat.dto.ChatMessageDTO;
import com.ll.weflea.boundedContext.chat.dto.ChatRoomDetailDTO;
import com.ll.weflea.boundedContext.chat.entity.ChatMessage;
import com.ll.weflea.boundedContext.chat.entity.ChatRoom;
import com.ll.weflea.boundedContext.chat.repository.ChatMessageRepository;
import com.ll.weflea.boundedContext.chat.repository.ChatRoomRepository;
import com.ll.weflea.boundedContext.member.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;


    @Transactional
    public ChatRoomDetailDTO createChatRoomDetailDTO(String name, Member sender, Member receiver) {

        ChatRoom chatRoom = createChatRoom(name, sender, receiver);

        ChatRoomDetailDTO chatRoomDetailDTO = ChatRoomDetailDTO.toChatRoomDetailDTO(chatRoom);

        return chatRoomDetailDTO;
    }

    private ChatRoom createChatRoom(String name, Member sender, Member receiver) {
        ChatRoom chatRoom = ChatRoom.create(name, sender, receiver);
        chatRoomRepository.save(chatRoom);
        return chatRoom;
    }

    public List<ChatRoomDetailDTO> findAllRooms() {
        List<ChatRoom> chatRooms = chatRoomRepository.findAll();
        List<ChatRoomDetailDTO> chatRoomDetailDtoRooms = new ArrayList<>();

        for (ChatRoom chatRoom : chatRooms) {
            chatRoomDetailDtoRooms.add(ChatRoomDetailDTO.toChatRoomDetailDTO(chatRoom));
        }

        return chatRoomDetailDtoRooms;
    }

    public ChatRoomDetailDTO findRoomById(String id) {
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(id).orElse(null);
        return ChatRoomDetailDTO.toChatRoomDetailDTO(chatRoom);
    }


    @Transactional
    public void createChatMessage(ChatMessageDTO chatMessageDTO) {

        String message = chatMessageDTO.getMessage();
        String roomId = chatMessageDTO.getRoomId();
        //필요한거 : message, Member sender, ChatRoom 객체
        //임시로 repository에 해놧음 service 구현되면 수정할 것
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId).orElse(null);
        Member sender = chatRoom.getSender();
        ChatMessage chatMessage = ChatMessage.create(message, sender, chatRoom);

        chatMessageRepository.save(chatMessage);
    }

    public List<ChatMessage> findByRoomId(String roomId) {
        return chatMessageRepository.findByChatRoom_RoomId(roomId);
    }

    public List<ChatRoom> findByNickname(String nickname) {
        return chatRoomRepository.findByChatRoom_Nickname(nickname);
    }

}
