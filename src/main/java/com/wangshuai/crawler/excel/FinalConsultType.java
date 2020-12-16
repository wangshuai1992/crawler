package com.wangshuai.crawler.excel;

import lombok.Getter;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author typhoon
 **/
@Getter
public enum FinalConsultType {

    //Shipping Related
    SHIPPING_CONSULTATION_NORMAL(1000,
            "/",
            "Shipping Related",
            "Shipping Consultation",
            "Shipping consultation (within normal timeline)"),
    ABNORMAL_SHIPPING_STATUS(1001,
            "/",
            "Shipping Related",
            "Shipping Consultation",
            "Abnormal shipping status (no update for long time)"),
    RTO(1002,
            "/",
            "Shipping Related",
            "Shipping Consultation",
            "RTO"),
    COURIER_COMPANIES(1003,
            "/",
            "Shipping Related",
            "Shipping Consultation",
            "Courier Companies"),
    SCOPE_OF_DELIVERY(1004,
            "Scope of delivery",
            "Shipping Related",
            "Shipping Consultation",
            "Shipping and Dispatch"),
    DELIVERY_TIME(1005,
            "Shipping/Delivery Time",
            "Shipping Related",
            "Shipping Consultation",
            "Shipping and Dispatch"),

    SHIPPING_FEE(1006,
            "/",
            "Shipping Related",
            "Shipping Consultation",
            "Shipping fee or tax fee"),

    CUSTOM_CLEARANCE(1007,
            "/",
            "Shipping Related",
            "Shipping Consultation",
            "Customs clearance consultation"),

    SELF_PICK(1008,
            "/",
            "Shipping Related",
            "Shipping Consultation",
            "Self-pick"),

    SHOWING_LOST(1009,
            "/",
            "Shipping Related",
            "Shipping Consultation",
            "Website showing lost"),

    CONTACT_COURIER(1010,
            "/",
            "Shipping Related",
            "Shipping Consultation",
            "Contact info for courier"),

    APP_SHIPPING_STATUS(1011,
            "/",
            "Shipping Related",
            "Shipping Consultation",
            "Abnormal shipping status on APP"),

    WRONG_PARCEL(1012,
            "Wrong parcel",
            "Shipping Related",
            "Shipping Complaint",
            "Forward"),

    EXTRA_FEE(1013,
            "Extra fee charged by the courier man",
            "Shipping Related",
            "Shipping Complaint",
            "Forward"),

    FAKE_DELIVERY(1014,
            "Fake delivery",
            "Shipping Related",
            "Shipping Complaint",
            "Forward"),

    PACKAGE_BROKEN(1015,
            "The outer package is broken",
            "Shipping Related",
            "Shipping Complaint",
            "Forward"),

    SERVICE_POOR(1016,
            "The service of the courier boy is poor",
            "Shipping Related",
            "Shipping Complaint",
            "Forward"),

    DISTURBED_BY_MSG(1017,
            "Disturbed by text messages",
            "Shipping Related",
            "Shipping Complaint",
            "Forward"),

    ASK_EAN(1018,
            "ask for EAN when pick up(Dehlivery)",
            "Shipping Related",
            "Shipping Complaint",
            "Reverse shipping"),

    FAKE_PICKUP_STATUS(1019,
            "fake pickup status",
            "Shipping Related",
            "Shipping Complaint",
            "Reverse shipping"),

    PICKUP_CONSULT(1020,
            "Pickup consultation",
            "Shipping Related",
            "Shipping Complaint",
            "Reverse shipping"),

    PICKUP_DELAY(1021,
            "pickup delay",
            "Shipping Related",
            "Shipping Complaint",
            "Reverse shipping"),

    PICKUP_FAILED(1022,
            "Pickup failed",
            "Shipping Related",
            "Shipping Complaint",
            "Reverse shipping"),

    PICKUP_CANCEL(1023,
            "pickup was cancelled by the courier",
            "Shipping Related",
            "Shipping Complaint",
            "Reverse shipping"),

    SELF_MAIL(1024,
            "Mail by myself issue",
            "Shipping Related",
            "Shipping Complaint",
            "Reverse shipping"),

    SHIPPING_OTHERS(1025,
            "/",
            "Shipping Related",
            "Others",
            "/"),

    //Aftersales
    EXCHANGE_POLICY(2000,
            "/",
            "Aftersales",
            "Exchange policy",
            "/"),

    LESS_REFUND(2001,
            "/",
            "Aftersales",
            "Refund",
            "Less refund amount"),

    ITEM_NOT_RECEIVE(2002,
            "Item not received",
            "Aftersales",
            "Refund",
            "Lost items"),

    MULTI_PARCEL(2004,
            "Multiple parcels received part",
            "Aftersales",
            "Refund",
            "Lost items"),

    REFUND_CHANNEL(2005,
            "Refund channel",
            "Aftersales",
            "Refund",
            "Refund consultation"),

    REFUND_DONE(2006,
            "Not received but refund has done successfully",
            "Aftersales",
            "Refund",
            "Refund consultation"),

    THIRD_FAILED(2007,
            "Third failed",
            "Aftersales",
            "Refund",
            "Refund consultation"),

    ASK_REFUND(2008,
            "/",
            "Aftersales",
            "Refund",
            "Return delivered ask for refund"),

    RETURN_IN_TRANSIT(2009,
            "/",
            "Aftersales",
            "Refund",
            "Return in transit"),

    EMPTY_PARCEL(2010,
            "/",
            "Aftersales",
            "Refund",
            "Empty parcel"),

    OUT_OF_STOCK(2011,
            "/",
            "Aftersales",
            "Refund",
            "Out of Stock"),

    PARTIAL_DAMAGE(2012,
            "Partial damage",
            "Aftersales",
            "Return",
            "Damaged product"),

    TOTAL_DAMAGE(2013,
            "Totally damage",
            "Aftersales",
            "Return",
            "Damaged product"),

    FUNCTION_BAD(2014,
            "Not function well (3C product)",
            "Aftersales",
            "Return",
            "Damaged product"),

    PICTURE_NOT_MATCH(2015,
            "Picture_title_description does not match",
            "Aftersales",
            "Return",
            "Product Description issue"),

    RETURN_ADDRESS_CONSULT(2016,
            "Return address consultation",
            "Aftersales",
            "Return",
            "Return Request"),

    HOW_TO_RETURN(2017,
            "How to return",
            "Aftersales",
            "Return",
            "Return Request"),

    RVP(2018,
            "RVP available or not",
            "Aftersales",
            "Return",
            "Return Request"),

    ASK_RETURN_SHIPPING_FEE_1(2019,
            "Ask for return shipping fee (not Wholee mistake)",
            "Aftersales",
            "Return",
            "Return shipping fee"),

    ASK_RETURN_SHIPPING_FEE_2(2020,
            "Ask for return shipping fee (Wholee mistake)",
            "Aftersales",
            "Return",
            "Return shipping fee"),

    PRODUCT_DIFF(2021,
            "Product is different",
            "Aftersales",
            "Return",
            "Wrong product"),

    SKU_DIFF(2022,
            "SKU is different",
            "Aftersales",
            "Return",
            "Wrong product"),

    QUALITY_POOR(2023,
            "/",
            "Aftersales",
            "Return",
            "Customer thinks the quality is poor"),

    SIZE_DIFF(2024,
            "blame difference with size label and product",
            "Aftersales",
            "Return",
            "Size issue"),

    NOT_FIT(2025,
            "tried but not fit",
            "Aftersales",
            "Return",
            "Size issue"),

    SIZE_OTHERS(2026,
            "others",
            "Aftersales",
            "Return",
            "Size issue"),

    RETURN_OTHERS(2027,
            "/",
            "Aftersales",
            "Others",
            "/"),

    //Onsales
    ONSALE_OUT_OF_STOCK(3000,
            "/",
            "Onsales",
            "Out of stock",
            "/"),

    ONSALE_SHIPPED(3001,
            "/",
            "Onsales",
            "Order cancellation",
            "Shipped"),

    ONSALE_UNSHIPPED(3002,
            "/",
            "Onsales",
            "Order cancellation",
            "Unshipped"),

    CONFIRM_ORDER(3004,
            "/",
            "Onsales",
            "Order confirmation",
            "Confirm order details"),

    CONFIRM_PERSONAL_INFO(3005,
            "/",
            "Onsales",
            "Order confirmation",
            "Confirm personal information"),

    CONFIRM_SUBMITTED(3006,
            "/",
            "Onsales",
            "Order confirmation",
            "Confirm whether the order was successfully submitted"),

    MODIFY_ADDRESS_SHIPPED(3007,
            "Shipped",
            "Onsales",
            "Order modification",
            "Modify the address and contact info"),

    MODIFY_ADDRESS_UNSHIPPED(3008,
            "Unshipped",
            "Onsales",
            "Order modification",
            "Modify the address and contact info"),

    MODIFY_PRODUCT_SHIPPED(3009,
            "Shipped",
            "Onsales",
            "Order modification",
            "Modify the product"),

    MODIFY_PRODUCT_UNSHIPPED(3010,
            "Unshipped",
            "Onsales",
            "Order modification",
            "Modify the product"),

    DISPATCH_SOMEDAY(3011,
            "/",
            "Onsales",
            "Special service",
            "Dispatch in a certain day"),

    EXPRESS_LGS_SERVICE(3012,
            "/",
            "Onsales",
            "Special service",
            "Express logistic service"),

    INVOICE(3013,
            "/",
            "Onsales",
            "Special service",
            "Invoice"),

    PREP_NOT_DONE(3014,
            "/",
            "Onsales",
            "Request for shipping ASAP",
            "Preparation not done"),

    PREP_DONE_UNSHIPPED(3016,
            "/",
            "Onsales",
            "Request for shipping ASAP",
            "Preparation done but not shipped"),

    ONSALE_OTHERS(3017,
            "/",
            "Onsales",
            "Others",
            "/"),

    //Presales
    CHANGE_ACCOUNT(4000,
            "Change Account",
            "Presales",
            "Account Management",
            "Account Settings"),

    CHANGE_COUNTRY_CURRENCY(4001,
            "Change country/currency",
            "Presales",
            "Account Management",
            "Account Settings"),

    UPDATE_PASS(4002,
            "Forget/change password",
            "Presales",
            "Account Management",
            "Account Settings"),

    LOGIN_FAILED(4003,
            "Login failed",
            "Presales",
            "Account Management",
            "Account Settings"),

    DEACTIVE_ACCOUNT(4004,
            "Deactivate account",
            "Presales",
            "Account Management",
            "Account Settings"),

    UNSUBSCRIBE_EMAIL(4005,
            "Unsubscribe Email",
            "Presales",
            "Account Management",
            "Account Settings"),

    REGISTER_ACCOUNT(4006,
            "Register an account",
            "Presales",
            "Account Management",
            "Account Settings"),

    PRIME_FEE(4007,
            "Wholee prime fee",
            "Presales",
            "Account Management",
            "Wholee Prime Membership"),

    UNSUBSCRIBE_MEMBER(4008,
            "Unsubscribe Wholee member",
            "Presales",
            "Account Management",
            "Wholee Prime Membership"),

    CANCEL_MEMBER(4009,
            "Cancel the membership",
            "Presales",
            "Account Management",
            "Wholee Prime Membership"),

    ADD_ITEM2CART(4010,
            "How to add item to cart",
            "Presales",
            "Account Management",
            "Cart"),

    CHECK_CART(4011,
            "How to check the Cart",
            "Presales",
            "Account Management",
            "Cart"),

    REBIND_PHONE(4012,
            "/",
            "Presales",
            "Account Management",
            "Phone number re-bind"),

    BALANCE_CHECK(4013,
            "Balance details checking or top-up",
            "Presales",
            "Account Management",
            "Balance"),

    HOW_TO_USE_BALANCE(4014,
            "How to use balance",
            "Presales",
            "Account Management",
            "Balance"),

    COUPON_ISSUE(4015,
            "Coupon issues",
            "Presales",
            "Account Management",
            "Coupon"),

    // code重复 移到后面去
//    TO_USE_COUPON(4015,
//            "How to use coupon",
//            "Presales",
//            "Account Management",
//            "Coupon"),

    WITHDRAW_RULE(4016,
            "Withdraw rules",
            "Presales",
            "Account Management",
            "Withdraw"),

    WITHDRAW_PENDING(4017,
            "Withdraw status shows pending",
            "Presales",
            "Account Management",
            "Withdraw"),

    WITHDRAW_FAILED(4018,
            "Withdraw failed",
            "Presales",
            "Account Management",
            "Withdraw"),

    SUCCESS_NOT_GET(4019,
            "Withdraw succeed but customer does not get it",
            "Presales",
            "Account Management",
            "Withdraw"),

    WITHDRAW_BY_OTHERS(4020,
            "Withdraw by others",
            "Presales",
            "Account Management",
            "Withdraw"),

    BRAND(4021,
            "/",
            "Presales",
            "Product Details",
            "Brand"),

    SIZE(4022,
            "/",
            "Presales",
            "Product Details",
            "Size"),

    COLOR(4023,
            "/",
            "Presales",
            "Product Details",
            "Color"),

    EXCHANGE_RATE(4024,
            "/",
            "Presales",
            "Product Details",
            "Exchange rate specification"),

    MATERIAL(4025,
            "/",
            "Presales",
            "Product Details",
            "Material"),

    PACKAGE(4026,
            "/",
            "Presales",
            "Product Details",
            "Package"),

    PLACE_ORIGIN(4027,
            "/",
            "Presales",
            "Product Details",
            "Place of origin"),

    PRODUCT_INSTRUCTION(4028,
            "/",
            "Presales",
            "Product Details",
            "Product instructions"),

    PRICE_GUARANTEE(4029,
            "Price guarantee",
            "Presales",
            "Product Details",
            "Service guarantee"),

    QUALITY_STATEMENT(4030,
            "Quality statement",
            "Presales",
            "Product Details",
            "Service guarantee"),

    RETURN_EXCHANGE_POLICY(4031,
            "Return and Exchange Policy",
            "Presales",
            "Product Details",
            "Service guarantee"),

    STOCK(4032,
            "/",
            "Presales",
            "Product Details",
            "Stock"),

    BIG_PROMOTION(4033,
            "/",
            "Presales",
            "Promotion Sales",
            "Big promotion for Festival"),

    DISCOUNT(4034,
            "Discount",
            "Presales",
            "Promotion Sales",
            "Daily Activities"),

    FLUSH_SALE(4035,
            "Flash Sale",
            "Presales",
            "Promotion Sales",
            "Daily Activities"),

    FRIENDS_DEAL(4036,
            "Friends deal",
            "Presales",
            "Promotion Sales",
            "Daily Activities"),

    PURCHASE_CHANNEL(4037,
            "/",
            "Presales",
            "Purchase Guidance",
            "Purchase channels and processes"),

    PAYMENT_METHOD(4038,
            "/",
            "Presales",
            "Purchase Guidance",
            "Payment Methods"),

    PRESALE_OTHERS(4039,
            "/",
            "Presales",
            "Others",
            "/"),

    TO_USE_COUPON(4040,
        "How to use coupon",
        "Presales",
        "Account Management",
        "Coupon"),

    //Others
    BLANK_PAGE(5000,
            "Blank page appears",
            "Others",
            "APP bug feedback",
            "Abnormal page"),

    APP_CRASH(5001,
            "App crash",
            "Others",
            "APP bug feedback",
            "Abnormal page"),

    NO_RESPONSE(5002,
            "No response when clicking on the button on the page",
            "Others",
            "APP bug feedback",
            "Abnormal page"),

    INF_NOT_COMPLETE(5003,
            "Page does not show complete info",
            "Others",
            "APP bug feedback",
            "Abnormal page"),

    ERROR_PAGE(5004,
            "Error appears on a page",
            "Others",
            "APP bug feedback",
            "Abnormal page"),

    APPLY_RETURN_ERROR(5005,
            " System error appears when clicking on \"apply return\" button.",
            "Others",
            "APP bug feedback",
            "Regular order can not apply for return"),

    BUTTON_UNAVAILABLE(5006,
            "Added him to whitelist, but \"apply button is still grey.",
            "Others",
            "APP bug feedback",
            "Regular order can not apply for return"),

    UNABLE_CANCEL_RVP(5007,
            "Unable to cancel pickup request",
            "Others",
            "APP bug feedback",
            "Regular order can not apply for return"),

    NOT_ORDER_RETURN(5008,
            "Customer wants to return the order but cannot find the order",
            "Others",
            "APP bug feedback",
            "Regular order can not apply for return"),

    FAIL_RE_RVP(5009,
            "Fail to re-apply pickup",
            "Others",
            "APP bug feedback",
            "Regular order can not apply for return"),

    APP_DISCOUNT(5010,
            "Discount",
            "Others",
            "APP bug feedback",
            "APP-Sales Activity Bug"),

    APP_FLUSH_SALE(5011,
            "Flash Sale",
            "Others",
            "APP bug feedback",
            "APP-Sales Activity Bug"),

    APP_FRIEND_DEAL(5012,
            "Friends deal",
            "Others",
            "APP bug feedback",
            "APP-Sales Activity Bug"),

    APP_PAYMENT_ERROR(5013,
            "Payment error",
            "Others",
            "APP bug feedback",
            "Payment issue"),

    REPEATED_PAYMENT(5014,
            "Repeated payment",
            "Others",
            "APP bug feedback",
            "Payment issue"),

    BALANCE_PREEZE(5015,
            "Balance freeze",
            "Others",
            "APP bug feedback",
            "Payment issue"),

    CANT_USE_COUPON(5016,
            "Customer follows the coupon rules but cannot use it",
            "Others",
            "APP bug feedback",
            "Payment issue"),

    ORDER_NOT_SYNCED(5017,
            "Payment success but order is not synced",
            "Others",
            "APP bug feedback",
            "Order synchronization"),

    PAYMENT_FAILE_AMOUNT_DEDUCT(5018,
            "Payment failed but customer got SMS that the amount was deducted",
            "Others",
            "APP bug feedback",
            "Order synchronization"),

    PIC_CANT_LOAD(5019,
            "Picture cannot load",
            "Others",
            "APP bug feedback",
            "Other Bugs"),

    CAT_ISSUE(5020,
            "Shopping cart issue",
            "Others",
            "APP bug feedback",
            "Other Bugs"),

    DIFF_LGS_STATUS(5021,
            "Difference with logistic status",
            "Others",
            "APP bug feedback",
            "Other Bugs"),

    NOT_RECEIVE_VERIFY_CODE(5022,
            "Cannot receive verification code",
            "Others",
            "APP bug feedback",
            "Withdraw failure"),

    NO_SEND_BTN(5023,
            "There is no \"send\" button",
            "Others",
            "APP bug feedback",
            "Withdraw failure"),

    PHONE_NUMBER_ERROR(5024,
            "When trying to bind phone number, no button shows on page to go further steps",
            "Others",
            "APP bug feedback",
            "Withdraw failure"),

    ERROR_500(5025,
            "Error 500",
            "Others",
            "APP bug feedback",
            "Withdraw failure"),

    NO_WITHDRAW_BTN(5026,
            "No withdraw button",
            "Others",
            "APP bug feedback",
            "Withdraw failure"),

    BUZZ_COOPERATION(5027,
            "/",
            "Others",
            "Business Cooperation",
            "/"),

    OTHERS_SERVICE_CHANNEL(5028,
            "/",
            "Others",
            "Customer Service",
            "Service Channel"),

    OTHERS_SERVICE_TIME(5029,
            "/",
            "Others",
            "Customer Service",
            "Service Time"),

    PHYSICAL_STORE(5030,
            "/",
            "Others",
            "Physical Store",
            "/"),

    PRIVATE_SECURITY(5031,
            "/",
            "Others",
            "Privacy Security",
            "/"),

    SUGGESTION(5032,
            "/",
            "Others",
            "Suggestion",
            "/"),

    BLACKLIST_CUSTOMER(5033,
            "/",
            "Others",
            "Blacklist customer",
            "/"),

    FRAUD(5034,
            "/",
            "Others",
            "Fraud",
            "/"),

    NO_REPLY(5035,
            "/",
            "Others",
            "Push ticket",
            "No reply"),

    UNSATISFIED_RESOLUTION(5036,
            "/",
            "Others",
            "Push ticket",
            "Not satisfied with resolution"),

    EXPRESSION_THANKS(5037,
            "/",
            "Others",
            "Expression of thanks",
            "/"),

    OTHERS_OTHERS(5038,
            "/",
            "Others",
            "Others",
            "/"),
    ;

    /**
     * code值
     */
    private Integer value;

    /**
     * 文案
     */
    private String text;

    /**
     * 一级类型
     */
    private String level1;

    /**
     * 二级类型
     */
    private String level2;

    /**
     * 三级类型
     */
    private String level3;

    private static final Map<Integer,FinalConsultType> map = new HashMap<>();

    FinalConsultType(Integer value, String text, String level1, String level2, String level3) {
        this.value = value;
        this.text = text;
        this.level1 = level1;
        this.level2 = level2;
        this.level3 = level3;
    }

    static {
        EnumSet.allOf(FinalConsultType.class).forEach(item -> map.put(item.getValue(),item));
    }


    public static final FinalConsultType of(Integer code) {
        return map.get(code);
    }

    public static final String getDescByCode(Integer code) {
        FinalConsultType finalType = of(code);
        return null == finalType ? null : finalType.getText();
    }

}
