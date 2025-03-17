package com.work.pinkweb.pay.controller;

import com.alipay.api.AlipayApiException;
import com.work.pinkweb.Entity.Order;
import com.work.pinkweb.pay.service.AliPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 支付宝沙箱测试 controller
 */
@CrossOrigin
@RestController
public class PayController {

    @Autowired
    private AliPayService aliPayService;

    /**
     * 支付宝支付 api
     *
     * @param outTradeNo
     * @param subject
     * @param totalAmount
     * @param description
     * @return
     * @throws AlipayApiException
     */
    @PostMapping(value = "/order/alipay")
    public String alipay(String outTradeNo, String subject,
                         String totalAmount, String description) throws AlipayApiException {
        Order order = new Order();
        order.setOut_trade_no(outTradeNo);
        order.setSubject(subject);
        order.setTotal_amount(totalAmount);
        order.setDescription(description);
        System.out.println(order);
        return aliPayService.aliPay(order);
    }
}
