package com.work.pinkweb.pay.service;

import com.alipay.api.AlipayApiException;
import com.work.pinkweb.Entity.Order;

/**
 * @Description: 支付 service
 */
public interface AliPayService {
    /**
     * 支付宝支付接口
     * @param order
     * @return
     * @throws AlipayApiException
     */
    String aliPay(Order order) throws AlipayApiException;
}