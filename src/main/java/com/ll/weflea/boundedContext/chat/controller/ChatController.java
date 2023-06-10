package com.ll.weflea.boundedContext.chat.controller;

import com.ll.weflea.base.rq.Rq;
import com.ll.weflea.boundedContext.chat.dto.ChatRoomDetailDTO;
import com.ll.weflea.boundedContext.chat.entity.ChatMessage;
import com.ll.weflea.boundedContext.chat.service.ChatService;
import com.ll.weflea.boundedContext.goods.entity.Goods;
import com.ll.weflea.boundedContext.goods.service.GoodsService;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
@Slf4j
public class ChatController {

    private final ChatService chatService;
    private final MemberService memberService;
    private final GoodsService goodsService;
    private final Rq rq;

//    나의 채팅방 목록 조회
    @GetMapping("/rooms")
    @PreAuthorize("hasRole('member')")
    public String myRooms(@AuthenticationPrincipal User user, Model model) {
        String username = user.getUsername();
        List<ChatRoomDetailDTO> chatRooms = chatService.findByUsername(username);
        model.addAttribute("myNickname", rq.getMember().getNickname());
        model.addAttribute("list", chatRooms);
        return "chat/rooms";
    }


////    모든 채팅방 목록 조회 (임시)
//    @GetMapping("/rooms")
//    public String rooms(Model model){
//
//        List<ChatRoomDetailDTO> rooms = chatService.findAllRooms();
//
//        log.info("# All Chat Rooms");
//        model.addAttribute("list", rooms);
//
//        return "chat/rooms";
//    }

    //채팅방 개설
    @PostMapping("/room/{id}")
    @PreAuthorize("hasRole('member')")
    public String create(@PathVariable Long id, @AuthenticationPrincipal User user, Model model){

        Member member1 = memberService.findByUsername(user.getUsername()).orElse(null);
        Goods goods = goodsService.findById(id);
        Member member2 = goods.getMember();

        if (member1.equals(member2)) {
            return rq.historyBack("본인이 올린 글에 채팅 신청할 수 없습니다.");
        }

        if (chatService.isExistChatRoom(member1.getId(), member2.getId())) {
            return rq.historyBack("이미 판매자와의 채팅방이 존재합니다. ");
        }


        model.addAttribute("roomName", chatService.createChatRoomDetailDTO(member1, member2).getName());

        return "redirect:/chat/rooms";
    }

    //채팅방 상세
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
        model.addAttribute("admin", "관리자");

        return "chat/room";
    }



}
