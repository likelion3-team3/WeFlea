package com.ll.weflea.boundedContext.pay.Service;

import com.ll.weflea.boundedContext.member.entity.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PayService {

    @Transactional
    public void chargePoint(Long point, Member seller) {
        seller.updatePoint(point);
    }


}
