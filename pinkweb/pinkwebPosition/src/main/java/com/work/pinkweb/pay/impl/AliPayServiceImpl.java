package com.work.pinkweb.pay.impl;

import com.alipay.api.AlipayApiException;
import com.work.pinkweb.Entity.Order;
import com.work.pinkweb.pay.Alipay;
import com.work.pinkweb.pay.service.AliPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: 支付service 实现类
 */
@Service
public class AliPayServiceImpl implements AliPayService {

    @Autowired
    private Alipay alipay;

    @Override
    public String aliPay(Order order) throws AlipayApiException {
        return alipay.pay(order);
    }
}