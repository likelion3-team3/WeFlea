package com.ll.weflea.boundedContext.chat.controller;

import com.ll.weflea.boundedContext.chat.dto.ChatRoomDetailDTO;
import com.ll.weflea.boundedContext.chat.entity.ChatRoom;
import com.ll.weflea.boundedContext.chat.service.ChatService;
import com.ll.weflea.boundedContext.member.entity.Member;
import com.ll.weflea.boundedContext.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
@Slf4j
public class ChatController {

    private final ChatService chatService;
    private final MemberRepository memberRepository;

    //나의 채팅방 목록 조회
    @GetMapping("/myRooms")
    public String myRooms(@AuthenticationPrincipal User user, Model model) {
        String nickname = user.getUsername();
        List<ChatRoom> chatRooms = chatService.findByNickname(nickname);
        model.addAttribute("list", chatRooms);
        return "chat/rooms";
    }


    //모든 채팅방 목록 조회 (임시)
    @GetMapping("/rooms")
    public String rooms(Model model){

        List<ChatRoomDetailDTO> rooms = chatService.findAllRooms();

        log.info("# All Chat Rooms");
        model.addAttribute("list", rooms);

        return "chat/rooms";
    }

    //채팅방 개설
    @PostMapping("/room")
    public String create(@RequestParam String name, Model model){
        //@AuthenticationPrincipal 매개변수 작업 필요
        Member member1 = memberRepository.findById(1L).orElse(null);
        Member member2 = memberRepository.findById(2L).orElse(null);

        log.info("# Create Chat Room , name: " + name);

        model.addAttribute("roomName", chatService.createChatRoomDetailDTO(name, member1, member2).getName());

        return "redirect:/chat/rooms";
    }

    //채팅방 상세
    @GetMapping("/room")
    public void getRoom(String roomId, Model model){

        log.info("# get Chat Room, roomID : " + roomId);

        model.addAttribute("room", chatService.findRoomById(roomId));
    }



}
