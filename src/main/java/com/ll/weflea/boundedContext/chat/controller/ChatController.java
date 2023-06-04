package com.ll.weflea.boundedContext.chat.controller;

import com.ll.weflea.boundedContext.chat.dto.ChatRoomDetailDTO;
import com.ll.weflea.boundedContext.chat.entity.ChatMessage;
import com.ll.weflea.boundedContext.chat.service.ChatService;
import com.ll.weflea.boundedContext.member.entity.Member;
import com.ll.weflea.boundedContext.member.repository.MemberRepository;
import com.ll.weflea.boundedContext.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
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

    private final MemberService memberService;

    //나의 채팅방 목록 조회
//    @GetMapping("/rooms")
//    @PreAuthorize("hasRole('member')")
//    public String myRooms(@AuthenticationPrincipal User user, Model model) {
//        String username = user.getUsername();
//        List<ChatRoomDetailDTO> chatRooms = chatService.findByUsername(username);
//        model.addAttribute("list", chatRooms);
//        return "chat/rooms";
//    }


//    모든 채팅방 목록 조회 (임시)
    @GetMapping("/rooms")
    public String rooms(Model model){

        List<ChatRoomDetailDTO> rooms = chatService.findAllRooms();

        log.info("# All Chat Rooms");
        model.addAttribute("list", rooms);

        return "chat/rooms";
    }

    //채팅방 개설
    @PostMapping("/room")
    @PreAuthorize("hasRole('member')")
    public String create(@AuthenticationPrincipal User user,@RequestParam String name, Model model){
        //@AuthenticationPrincipal 매개변수 작업 필요
        Member member1 = memberService.findByUsername(user.getUsername()).orElse(null);
        Member member2 = memberRepository.findById(2L).orElse(null);

        log.info("# Create Chat Room , name: " + name);

        model.addAttribute("roomName", chatService.createChatRoomDetailDTO(name, member1, member2).getName());

        return "redirect:/chat/rooms";
    }

    //채팅방 상세
    //db에서 기존 채팅이 있다면 채팅내역을 끌고와야됨
    @GetMapping("/room")
    @PreAuthorize("hasRole('member')")
    public String getRoom(String roomId, Model model, @AuthenticationPrincipal User user){

        log.info("# get Chat Room, roomID : " + roomId);
        List<ChatMessage> messages = chatService.findByRoomId(roomId);
        Member member = memberService.findByUsername(user.getUsername()).orElse(null);

        model.addAttribute("room", chatService.findRoomById(roomId));
        model.addAttribute("messages", messages);
        model.addAttribute("member", member);
        model.addAttribute("user", user);

        return "chat/room";
    }



}
