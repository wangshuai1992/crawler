package com.wangshuai.crawler.controller;

import com.alibaba.fastjson.JSONObject;
import com.wangshuai.crawler.manager.klarna.KlarnaManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * KlarnaController
 *
 * @author wangshuai
 * @version V1.0
 * @since 2017-08-27 13:24
 */
@Controller
@Slf4j
public class KlarnaController {

    @Resource
    private KlarnaManager klarnaManager;

    @RequestMapping("/klarna")
    public String index(ModelMap modelMap) {
        return "klarna/test";
    }

    @RequestMapping("/klarna/confirm")
    public String confirm(ModelMap modelMap) {
        return "klarna/confirm";
    }

    @RequestMapping("/klarna/create-session.json")
    @ResponseBody
    public JSONObject createSession() {
        return klarnaManager.createSession();
    }

    @RequestMapping("/klarna/place-order.json")
    @ResponseBody
    public JSONObject placeOrder(@RequestParam("authorizationToken") String authorizationToken) {
        return klarnaManager.placeOrder(authorizationToken);
    }

    @RequestMapping("/klarna/capture-order.json")
    @ResponseBody
    public JSONObject captureOrder(@RequestParam("orderId") String orderId) {
        return klarnaManager.captureOrder(orderId);
    }

    @RequestMapping("/klarna/place-order-by-customer-token.json")
    @ResponseBody
    public JSONObject placeOrderByCustomerToken(@RequestParam("customerToken") String customerToken) {
        return klarnaManager.placeOrderByCustomerToken(customerToken);
    }

    @RequestMapping("/klarna/create-customer-token.json")
    @ResponseBody
    public JSONObject createCustomerToken(@RequestParam("authorizationToken") String authorizationToken) {
        return klarnaManager.createCustomerToken(authorizationToken);
    }

    @RequestMapping("/klarna/refund-order.json")
    @ResponseBody
    public JSONObject refundOrder(@RequestParam("orderId") String orderId) {
        return klarnaManager.refundOrder(orderId);
    }

}
