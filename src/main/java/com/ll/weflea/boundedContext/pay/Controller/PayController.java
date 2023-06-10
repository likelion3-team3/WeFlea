package com.ll.weflea.boundedContext.pay.Controller;

import com.ll.weflea.base.rq.Rq;
import com.ll.weflea.boundedContext.chat.dto.ChatMessageDTO;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

//        model.addAttribute("method", (String) jsonObject.get("method"));
//        model.addAttribute("orderName", (String) jsonObject.get("orderName"));
//
//        if (((String) jsonObject.get("method")) != null) {
//            if (((String) jsonObject.get("method")).equals("카드")) {
//                model.addAttribute("cardNumber", (String) ((JSONObject) jsonObject.get("card")).get("number"));
//            } else if (((String) jsonObject.get("method")).equals("가상계좌")) {
//                model.addAttribute("accountNumber", (String) ((JSONObject) jsonObject.get("virtualAccount")).get("accountNumber"));
//            } else if (((String) jsonObject.get("method")).equals("계좌이체")) {
//                model.addAttribute("bank", (String) ((JSONObject) jsonObject.get("transfer")).get("bank"));
//            } else if (((String) jsonObject.get("method")).equals("휴대폰")) {
//                model.addAttribute("customerMobilePhone", (String) ((JSONObject) jsonObject.get("mobilePhone")).get("customerMobilePhone"));
//            }
//        } else {
//            model.addAttribute("code", (String) jsonObject.get("code"));
//            model.addAttribute("message", (String) jsonObject.get("message"));
//        }

        //판매자에게 포인트 충전
        payService.chargePoint((long) goods.getPrice(), goods.getMember());

        //채팅방에 "안전결제가 완료되었습니다" 메시지 전송
        Member sender = memberService.findByUsername(user.getUsername()).orElse(null);
        String chatRoomId = chatService.findExistChatRoom(sender.getId(), goods.getMember().getId()).getRoomId();
        ChatMessageDTO messageDTO = new ChatMessageDTO();
        messageDTO.setMessage("안전결제가 완료되었습니다.");
        messageDTO.setWriter("관리자");
        messageDTO.setRoomId(chatRoomId);
        chatService.createChatMessage(messageDTO);
        goodsService.updateStatus(goodsId, "안전결제중");


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
