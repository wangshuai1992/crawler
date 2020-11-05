package com.wangshuai.crawler.manager.klarna;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * KlarnaManager
 *
 * @author wangshuai
 * @version V1.0
 * @date 2020-08-30 22:49
 */
@Service
@Slf4j
public class KlarnaManager {

    private static JSONObject info;

    private static JSONArray orderLines;

    private static JSONObject billingAddress;

    private static JSONObject customer;

    @Value("${klarna.auth}")
    private String klarnaAuth;

    static {
        info = new JSONObject();
//        info.put("locale", "en-gb");
//        info.put("purchase_country", "GB");
//        info.put("purchase_currency", "GBP");
//        info.put("country", "GB");
//        info.put("city", "London");
//        info.put("given_name", "Doe");
//        info.put("family_name", "John");
//        info.put("email", "john@doe.com");
//        info.put("phone", "01895808221");
//        info.put("street_address", "13 New Burlington St");
//        info.put("street_address2", "Apt 214");
//        info.put("postal_code", "W13 3BG");
//        info.put("date_of_birth", "1970-01-01");
//        info.put("gender", "male");
//        info.put("title", "Mr");

        info.put("locale", "en-DE");
        info.put("purchase_country", "DE");
        info.put("purchase_currency", "EUR");
        info.put("country", "DE");
        info.put("city", "Neuss");
        info.put("given_name", "Approved");
        info.put("family_name", "Testperson-de");
        info.put("email", "john@doe.com");
        info.put("phone", "01895808221");
        info.put("street_address", "Hellersbergstraße");
        info.put("street_address2", "");
        info.put("postal_code", "41460");
        info.put("date_of_birth", "1960-07-07");
        info.put("gender", "male");
        info.put("title", "Mr");

        info.put("order_amount", "50000");

        customer = JSON.parseObject("{\n" +
                "        \"given_name\": \"" + info.getString("given_name") + "\",\n" +
                "        \"family_name\": \"" + info.getString("family_name") + "\",\n" +
                "        \"date_of_birth\": \"" + info.getString("date_of_birth") + "\",\n" +
                "        \"gender\": \"" + info.getString("gender") + "\"\n" +
                "    }");

        billingAddress = JSON.parseObject("{\n" +
                "        \"given_name\": \"" + info.getString("given_name") + "\",\n" +
                "        \"family_name\": \"" + info.getString("family_name") + "\",\n" +
                "        \"email\": \"" + info.getString("email") + "\",\n" +
                "        \"phone\": \"" + info.getString("phone") + "\",\n" +
                "        \"street_address\": \"" + info.getString("street_address") + "\",\n" +
                "        \"street_address2\": \"" + info.getString("street_address2") + "\",\n" +
                "        \"postal_code\": \"" + info.getString("postal_code") + "\",\n" +
                "        \"city\": \"" + info.getString("city") + "\",\n" +
                "        \"country\": \"" + info.getString("country") + "\"\n" +
                "    }");

        orderLines = JSON.parseArray("[{\n" +
                "      \"type\": \"physical\",\n" +
                "      \"reference\": \"19-402-USA\",\n" +
                "      \"name\": \"Red T-Shirt\",\n" +
                "      \"quantity\": 1,\n" +
                "      \"quantity_unit\": \"pcs\",\n" +
                "      \"unit_price\": " + info.getString("order_amount")+ ",\n" +
                "      \"tax_rate\": 0.01,\n" +
                "      \"total_amount\": " + info.getString("order_amount") + ",\n" +
                "      \"total_discount_amount\": 0,\n" +
                "      \"total_tax_amount\": 1,\n" +
                "      \"merchant_data\": \"{\\\"marketplace_seller_info\\\":[{\\\"product_category\\\":\\\"Women's Fashion\\\",\\\"product_name\\\":\\\"Women Sweatshirt\\\"}]}\",\n" +
                "      \"product_url\": \"https://www.example.com/products/f2a8d7e34\",\n" +
                "      \"image_url\": \"https://www.exampleobjects.com/logo.png\",\n" +
                "      \"product_identifiers\": {\n" +
                "        \"category_path\": \"Electronics Store > Computers & Tablets > Desktops\",\n" +
                "        \"global_trade_item_number\": \"735858293167\",\n" +
                "        \"manufacturer_part_number\": \"BOXNUC5CPYH\",\n" +
                "        \"brand\": \"Intel\"\n" +
                "      },\n" +
                "      \"shipping_attributes\": {\n" +
                "        \"weight\": 1000,\n" +
                "        \"tags\": [\n" +
                "          \"string\"\n" +
                "        ]\n" +
                "      }\n" +
                "    }]");

    }

    private String host = "https://api.playground.klarna.com/";
    @Resource
    private RestTemplate restTemplate;

    public JSONObject createSession() {
        String url = host + "/payments/v1/sessions";
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", klarnaAuth);
        requestHeaders.add("Content-Type", "application/json");
        JSONObject body = new JSONObject();
        body.put("locale", info.getString("locale"));
        body.put("order_amount", info.getString("order_amount"));
        body.put("order_lines", orderLines);
        body.put("customer", customer);
        body.put("purchase_country", info.getString("purchase_country"));
        body.put("purchase_currency", info.getString("purchase_currency"));
        HttpEntity<JSONObject> requestEntity = new HttpEntity<>(body, requestHeaders);
        ResponseEntity<JSONObject> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, JSONObject.class);
        return responseEntity.getBody();
    }

    public JSONObject placeOrder(String authorizationToken) {
        String url = host + "/payments/v1/authorizations/" + authorizationToken + "/order";
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", klarnaAuth);
        requestHeaders.add("Content-Type", "application/json");
        JSONObject body = new JSONObject();
        body.put("locale", info.getString("locale"));
        body.put("order_amount", info.getString("order_amount"));
        body.put("order_lines", orderLines);
        body.put("customer", customer);
        body.put("purchase_country", info.getString("purchase_country"));
        body.put("purchase_currency", info.getString("purchase_currency"));
        body.put("auto_capture", true);
        HttpEntity<JSONObject> requestEntity = new HttpEntity<>(body, requestHeaders);
        ResponseEntity<JSONObject> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, JSONObject.class);
        return responseEntity.getBody();
    }

    public JSONObject createCustomerToken(String authorizationToken) {
        String url = host + "/payments/v1/authorizations/" + authorizationToken + "/customer-token";
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", klarnaAuth);
        requestHeaders.add("Content-Type", "application/json");
        JSONObject body = new JSONObject();
        body.put("locale", info.getString("locale"));
        body.put("purchase_country", info.getString("purchase_country"));
        body.put("purchase_currency", info.getString("purchase_currency"));
        body.put("billing_address", billingAddress);
        body.put("description", "For subscription.");
        body.put("intended_use", "SUBSCRIPTION");
        body.put("customer", customer);
//        body.put("customer", JSON.parseObject("{\n" +
//                "        \"date_of_birth\": \"" + info.getString("date_of_birth") + "\",\n" +
//                "        \"gender\": \"" + info.getString("gender") + "\"\n" +
//                "    }"));
//        body.put("merchant_urls", JSON.parseObject("{\n" +
//                "        \"confirmation\": \"http://127.0.0.1:8132/klarna/confirm\"\n" +
//                "    }"));
        HttpEntity<JSONObject> requestEntity = new HttpEntity<>(body, requestHeaders);
        ResponseEntity<JSONObject> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, JSONObject.class);
        log.info("====== customer token : {} =========", responseEntity.getBody().getString("token_id"));
        return responseEntity.getBody();
    }

    public JSONObject placeOrderByCustomerToken(String customerToken) {
        String url = host + "/customer-token/v1/tokens/" + customerToken + "/order";
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", klarnaAuth);
        requestHeaders.add("Content-Type", "application/json");
        JSONObject body = new JSONObject();
        body.put("order_amount", info.getIntValue("order_amount") - 1);
        body.put("order_tax_amount", "1");
        body.put("order_lines", orderLines);
        body.put("purchase_currency", info.getString("purchase_currency"));
        body.put("auto_capture", true);
        HttpEntity<JSONObject> requestEntity = new HttpEntity<>(body, requestHeaders);
        ResponseEntity<JSONObject> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, JSONObject.class);
        return responseEntity.getBody();
    }

    public JSONObject refundOrder(String orderId) {
        String url = host + "/ordermanagement/v1/orders/" + orderId + "/refunds";
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", klarnaAuth);
        requestHeaders.add("Content-Type", "application/json");
        JSONObject body = new JSONObject();
        body.put("refunded_amount", info.getIntValue("order_amount"));
        HttpEntity<JSONObject> requestEntity = new HttpEntity<>(body, requestHeaders);
        ResponseEntity<JSONObject> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, JSONObject.class);
        return responseEntity.getBody();
    }

    public JSONObject captureOrder(String orderId) {
        String url = host + "/ordermanagement/v1/orders/" + orderId + "/captures";
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", klarnaAuth);
        requestHeaders.add("Content-Type", "application/json");
        JSONObject body = new JSONObject();
        body.put("captured_amount", info.getIntValue("order_amount"));
        HttpEntity<JSONObject> requestEntity = new HttpEntity<>(body, requestHeaders);
        ResponseEntity<JSONObject> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, JSONObject.class);
        return responseEntity.getBody();
    }
}
