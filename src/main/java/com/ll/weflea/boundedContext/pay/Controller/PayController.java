package com.ll.weflea.boundedContext.pay.Controller;

import com.ll.weflea.base.rq.Rq;
import com.ll.weflea.boundedContext.chat.dto.ChatMessageDTO;
import com.ll.weflea.boundedContext.chat.dto.ChatRoomDetailDTO;
import com.ll.weflea.boundedContext.chat.entity.ChatRoom;
import com.ll.weflea.boundedContext.chat.service.ChatService;
import com.ll.weflea.boundedContext.goods.entity.Goods;
import com.ll.weflea.boundedContext.goods.entity.Status;
import com.ll.weflea.boundedContext.goods.service.GoodsService;
import com.ll.weflea.boundedContext.member.entity.Member;
import com.ll.weflea.boundedContext.member.service.MemberService;
import com.ll.weflea.boundedContext.pay.Service.PayService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Controller
@RequiredArgsConstructor
@RequestMapping("/pay")
public class PayController {

    private final GoodsService goodsService;
    private final MemberService memberService;
    private final PayService payService;
    private final ChatService chatService;
    private final Rq rq;
    private final SimpMessagingTemplate template;

    @GetMapping("/{goodsId}")
    public String payPage(@PathVariable Long goodsId, @AuthenticationPrincipal User user, Model model) {

        Member member = memberService.findByUsername(user.getUsername()).orElse(null);
        Goods goods = goodsService.findById(goodsId);

        if (member.getId().equals(goods.getMember().getId())) {
            return rq.historyBack("본인이 올린 상품을 안전결제할 수 없습니다.");
        }

        if (goods.getStatus().equals(Status.거래완료) || goods.getStatus().equals(Status.거래중) || goods.getStatus().equals(Status.안전결제중)) {
            return rq.historyBack("이미 거래 중이거나 거래완료된 상품입니다.");
        }


        model.addAttribute("goods", goods);
        model.addAttribute("customer", member);

        return "user/pay/payment";
    }

    @GetMapping("success/{goodsId}")
    public String paymentResult(
            Model model,
            @PathVariable Long goodsId,
            @RequestParam(value = "orderId") String orderId,
            @RequestParam(value = "amount") Integer amount,
            @RequestParam(value = "paymentKey") String paymentKey,
            @AuthenticationPrincipal User user) throws Exception {

        Goods goods = goodsService.findById(goodsId);

        if (orderId.startsWith("sample-") && amount != goods.getPrice()) {
            throw new RuntimeException("해킹의심 : 결제 요청 금액이 올바르지 않습니다!");
        }

        String secretKey = "test_sk_kZLKGPx4M3MJ2NWNnLRVBaWypv1o:";

        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedBytes = encoder.encode(secretKey.getBytes("UTF-8"));
        String authorizations = "Basic " + new String(encodedBytes, 0, encodedBytes.length);

        URL url = new URL("https://api.tosspayments.com/v1/payments/" + paymentKey);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Authorization", authorizations);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        JSONObject obj = new JSONObject();
        obj.put("orderId", orderId);
        obj.put("amount", amount);

        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(obj.toString().getBytes("UTF-8"));

        int code = connection.getResponseCode();
        boolean isSuccess = code == 200 ? true : false;
        model.addAttribute("isSuccess", isSuccess);

        InputStream responseStream = isSuccess ? connection.getInputStream() : connection.getErrorStream();

        Reader reader = new InputStreamReader(responseStream, StandardCharsets.UTF_8);
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(reader);
        responseStream.close();

        //채팅방에 "안전결제가 완료되었습니다" 메시지 전송
        Member sender = memberService.findByUsername(user.getUsername()).orElse(null);
        goodsService.updateStatusAndBuyer(goodsId, "안전결제중", sender);

        ChatMessageDTO messageDTO = new ChatMessageDTO();
        messageDTO.setMessage(sender.getNickname() + "님이 안전결제를 완료하셨습니다.");
        messageDTO.setWriter("관리자");

        ChatRoom chatRoom = chatService.findExistChatRoom(sender.getId(), goods.getMember().getId());

        if (chatRoom != null) {
            messageDTO.setRoomId(chatRoom.getRoomId());
        } else {
            ChatRoomDetailDTO chatRoomDetailDTO = chatService.createChatRoomDetailDTO(sender, goods.getMember());
            messageDTO.setRoomId(chatRoomDetailDTO.getRoomId());
        }

        chatService.createChatMessage(messageDTO);
        template.convertAndSend("/sub/chat/room/" + messageDTO.getRoomId(), messageDTO);
        return rq.redirectWithMsg("/user/weflea/detail/" + goodsId, "안전결제가 완료되었습니다.");
    }

    @GetMapping(value = "fail")
    public String paymentResult(
            Model model,
            @RequestParam(value = "message") String message,
            @RequestParam(value = "code") Integer code
    ) throws Exception {

        model.addAttribute("code", code);
        model.addAttribute("message", message);

        return "user/pay/fail";
    }
}
