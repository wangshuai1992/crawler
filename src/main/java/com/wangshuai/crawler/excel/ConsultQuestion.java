package com.wangshuai.crawler.excel;

import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author watermelon
 * @create 2020-06-28
 */

@Getter
public enum ConsultQuestion {


    /**
     * 只有客服才能选择 给最终类型
     */
    SHIPPING_CONSULTATION(1
            , "Shipping Consultation"
            , "Shipping"
            , false
            , false
            , false
            ,Collections.emptySet()
            ,""),
    SHIPPING_COMPLAINT(2
            , "Shipping Complaint"
            , "Shipping"
            , false
            , false
            , false
            ,Collections.emptySet()
            ,""),
    EXCHANGE_POLICY(3
            , "Exchange policy"
            , "Aftersale"
            , false
            , false
            , false
            ,Collections.emptySet()
            ,""),
    REFUND(4
            , "Refund"
            , "Aftersale"
            , false
            , false
            , false
            ,Collections.emptySet()
            ,""),
    RETURN(5
            , "Return"
            , "Aftersale"
            , false
            , false
            , false
            ,Collections.emptySet()
            ,""),
    OUT_OF_STOCK(6
            , "Out of stock"
            , "OnSales"
            , false
            , false
            , false
            ,Collections.emptySet()
            ,""),
    ORDER_CANCELLATION(7
            , "Order cancellation"
            , "OnSales"
            , false
            , false
            , false
            ,Collections.emptySet()
            ,""),
    ORDER_CONFIRMATION(8
            , "Order confirmation"
            , "OnSales"
            , false
            , false
            , false
            ,Collections.emptySet()
            ,""),
    ORDER_MODIFICATION(9
            , "Order Modification"
            , "OnSales"
            , false
            , false
            , false
            ,Collections.emptySet()
            ,""),
    SPECIAL_SERVICE(10
            , "Special service"
            , "OnSales"
            , false
            , false
            , false
            ,Collections.emptySet()
            ,""),
    REQUEST_FOR_SHIPPING_ASAP(11
            , "Request for shipping ASAP"
            , "OnSales"
            , false
            , false
            , false
            ,Collections.emptySet()
            ,""),
    ACCOUNT_MANAGEMENT(12
            , "Account Management"
            , "Presales"
            , false
            , false
            , false
            ,Collections.emptySet()
            ,""),
    PRODUCT_DETAILS(13
            , "Product Details"
            , "Presales"
            , false
            , false
            , false
            ,Collections.emptySet()
            ,""),
    DAILY_ACTIVITIES(14
            , "Daily Activities"
            , "Presales"
            , false
            , false
            , false
            ,Collections.emptySet()
            ,""),
    PURCHASE_GUIDANCE(15
            , "Purchase Guidance"
            , "Presales"
            , false
            , false
            , false
            ,Collections.emptySet()
            ,""),
    APP_BUG_FEEDBACK(16
            , "APP bug feedback"
            , "Others"
            , false
            , false
            , false
            ,Collections.emptySet()
            ,""),
    BUSINESS_COOPERATION(17
            , "Business Cooperation"
            , "Others"
            , false
            , false
            , false
            ,Collections.emptySet()
            ,""),
    CUSTOMER_SERVICE(18
            , "Customer Service"
            , "Others"
            , false
            , false
            , false
            ,Collections.emptySet()
            ,""),
    PHYSICAL_STORE(19
            , "Physical Store"
            , "Others"
            , false
            , false
            , false
            ,Collections.emptySet()
            ,""),
    PRIVACY_SECURITY(20
            , "Privacy Security"
            , "Others"
            , false
            , false
            , false
            ,Collections.emptySet()
            ,""),
    SUGGESTION(21
            , "Suggestion"
            , "Others"
            , false
            , false
            , false
            ,Collections.emptySet()
            ,""),
    BLACKLIST_CUSTOMER(22
            , "Blacklist customer"
            , "Others"
            , false
            , false
            , false
            ,Collections.emptySet()
            ,""),
    FRAUD(23
            , "Fraud"
            , "Others"
            , false
            , false
            , false
            ,Collections.emptySet()
            ,""),
    PUSH_TICKET(24
            , "Push ticket"
            , "Others"
            , false
            , false
            , false
            ,Collections.emptySet()
            ,""),
    VAGUE_DIALOG(25
            , "vague dialog"
            , "Others"
            , false
            , false
            , false
            ,Collections.emptySet()
            ,""),

    /**
     * 用户可以看见
     */
    NEED_TO_CANCEL_ORDER(1001
            , "Need to cancel the order"
            , "Order Management"
            , true
            , true
            , false
            ,Stream.of(OrderState.PREPARING.getStates(), OrderState.SHIPPED.getStates(), OrderState.COD_SUBMIT.getStates())
            .flatMap(item -> item.stream())
            .collect(Collectors.toSet())
            ,"Please describe your concern in detail and upload related pictures which help to locate the issue."),
    NEED_TO_CHANGE_ITEM(1002
            , "Need to change item"
            , "Order Management"
            , true
            , true
            , true
            ,Stream.of(OrderState.PREPARING.getStates(), OrderState.SHIPPED.getStates(), OrderState.COD_SUBMIT.getStates())
            .flatMap(item -> item.stream())
            .collect(Collectors.toSet())
            ,""),
    NEED_TO_CHANGE_SHIPPING_INFO(1003
            , "Need to change the shipping address or contact info"
            , "Order Management"
            , true
            , true
            , false
            ,Stream.of(OrderState.PREPARING.getStates(), OrderState.SHIPPED.getStates(), OrderState.COD_SUBMIT.getStates())
            .flatMap(item -> item.stream())
            .collect(Collectors.toSet())
            ,""),
    ORDER_OTHERS(1004
            , "Others"
            , "Order Management"
            , true
            , true
            , false
            ,Stream.of(OrderState.PREPARING.getStates(), OrderState.SHIPPED.getStates(), OrderState.COD_SUBMIT.getStates())
            .flatMap(item -> item.stream())
            .collect(Collectors.toSet())
            ,""),

    //Account issue
    BIND_PHONE_NUMBER_ISSUE(1005
            , "Bind phone number issue"
            , "Account issue"
            , true
            , false
            , true
            , Collections.emptySet()
            ,""),
    BALANCE_WITHDRAWAL_ISSUE(1006
            , "Balance withdraw issue "
            , "Account issue"
            , true
            , false
            , true
            ,Collections.emptySet()
            ,""),
    COUPON_ISSUE(1007
            , "Coupon issue"
            , "Account issue"
            , true
            , false
            , true
            ,Collections.emptySet()
            ,""),
    ACCOUNT_OTHERS(1008
            , "Others"
            , "Account issue"
            , true
            , false
            , false
            ,Collections.emptySet()
            ,""),

    //Promotions/Activities
    USER_DAILY_ACTIVITIES(1009
            , "Daily Activities"
            , "Promotions/Activities"
            , true
            , false
            , true
            ,Stream.of(OrderState.UNPAID.getStates()
                , OrderState.PREPARING.getStates()
                , OrderState.SHIPPED.getStates()
                , OrderState.DELIVERED.getStates()
                , OrderState.CANCELED.getStates()
                , OrderState.EXPIRED.getStates()
                , OrderState.REJECTED.getStates()
                , OrderState.COD_SUBMIT.getStates())
            .flatMap(item -> item.stream())
            .collect(Collectors.toSet())
            ,""),
    CERTAIN_FESTIVAL_PROMOTION(1010
            , "Certain Festival promotions"
            , "Promotions/Activities"
            , true
            , false
            , true
            ,Stream.of(OrderState.UNPAID.getStates()
            , OrderState.PREPARING.getStates()
            , OrderState.SHIPPED.getStates()
            , OrderState.DELIVERED.getStates()
            , OrderState.CANCELED.getStates()
            , OrderState.EXPIRED.getStates()
            , OrderState.REJECTED.getStates()
            , OrderState.COD_SUBMIT.getStates())
            .flatMap(item -> item.stream())
            .collect(Collectors.toSet())
            ,""),
    PROMOTION_OTHERS(1011
            , "Others"
            , "Promotions/Activities"
            , true
            , false
            , true
            ,Stream.of(OrderState.UNPAID.getStates()
            , OrderState.PREPARING.getStates()
            , OrderState.SHIPPED.getStates()
            , OrderState.DELIVERED.getStates()
            , OrderState.CANCELED.getStates()
            , OrderState.EXPIRED.getStates()
            , OrderState.REJECTED.getStates()
            , OrderState.COD_SUBMIT.getStates())
            .flatMap(item -> item.stream())
            .collect(Collectors.toSet())
            ,""),


    PAGE_IS_BLACK_OR_INCOMPLETE(1012
            , "Page is blank or incomplete"
            , "APP bug"
            , true
            , false
            , true
            ,Stream.of(OrderState.UNPAID.getStates()
            , OrderState.PREPARING.getStates()
            , OrderState.SHIPPED.getStates()
            , OrderState.DELIVERED.getStates()
            , OrderState.CANCELED.getStates()
            , OrderState.EXPIRED.getStates()
            , OrderState.REJECTED.getStates()
            , OrderState.COD_SUBMIT.getStates())
            .flatMap(item -> item.stream())
            .collect(Collectors.toSet())
            ,""),
    ABNORMAL_PRICE(1013
            ,"Abnormal price"
            , "APP bug"
            ,true
            ,false
            ,true
            ,Stream.of(OrderState.UNPAID.getStates()
            , OrderState.PREPARING.getStates()
            , OrderState.SHIPPED.getStates()
            , OrderState.DELIVERED.getStates()
            , OrderState.CANCELED.getStates()
            , OrderState.EXPIRED.getStates()
            , OrderState.REJECTED.getStates()
            , OrderState.COD_SUBMIT.getStates())
            .flatMap(item -> item.stream())
            .collect(Collectors.toSet())
            ,""),
    UNABLE_TO_RECEIVE_VERIFICATION_CODE(1014
            ,"Unable to receive verification code"
            , "APP bug"
            ,true
            ,false
            ,true
            ,Stream.of(OrderState.UNPAID.getStates()
            , OrderState.PREPARING.getStates()
            , OrderState.SHIPPED.getStates()
            , OrderState.DELIVERED.getStates()
            , OrderState.CANCELED.getStates()
            , OrderState.EXPIRED.getStates()
            , OrderState.REJECTED.getStates()
            , OrderState.COD_SUBMIT.getStates())
            .flatMap(item -> item.stream())
            .collect(Collectors.toSet())
            ,""),
    PAGE_POPS_UP_ERRORS(1015
            ,"Page pops up errors"
            , "APP bug"
            ,true
            ,false
            ,true
            ,Stream.of(OrderState.UNPAID.getStates()
            , OrderState.PREPARING.getStates()
            , OrderState.SHIPPED.getStates()
            , OrderState.DELIVERED.getStates()
            , OrderState.CANCELED.getStates()
            , OrderState.EXPIRED.getStates()
            , OrderState.REJECTED.getStates()
            , OrderState.COD_SUBMIT.getStates())
            .flatMap(item -> item.stream())
            .collect(Collectors.toSet())
            ,""),
    PAYMENT_ISSUE(1016
            ,"Payment issue"
            , "APP bug"
            ,true
            ,true
            ,true
            ,Stream.of(OrderState.UNPAID.getStates()
            , OrderState.PREPARING.getStates()
            , OrderState.SHIPPED.getStates()
            , OrderState.DELIVERED.getStates()
            , OrderState.CANCELED.getStates()
            , OrderState.EXPIRED.getStates()
            , OrderState.REJECTED.getStates()
            , OrderState.COD_SUBMIT.getStates())
            .flatMap(item -> item.stream())
            .collect(Collectors.toSet())
            ,""),
    APP_BUG_OTHERS(1017
            ,"Others"
            , "APP bug"
            ,true
            ,false
            ,true
            ,Stream.of(OrderState.UNPAID.getStates()
            , OrderState.PREPARING.getStates()
            , OrderState.SHIPPED.getStates()
            , OrderState.DELIVERED.getStates()
            , OrderState.CANCELED.getStates()
            , OrderState.EXPIRED.getStates()
            , OrderState.REJECTED.getStates()
            , OrderState.COD_SUBMIT.getStates())
            .flatMap(item -> item.stream())
            .collect(Collectors.toSet())
            ,""),
    //Shipping Problems
    LONG_SHIPMENT(1018
            ,"Overdue/long Shipment"
            , "Shipping Problems"
            ,true
            ,true
            ,false
            ,Stream.of(OrderState.SHIPPED.getStates()
            , OrderState.DELIVERED.getStates()
            , OrderState.CANCELED.getStates()
            , OrderState.REJECTED.getStates())
            .flatMap(item -> item.stream())
            .collect(Collectors.toSet())
            ,""),
    CUSTOMS_CLEARANCE(1019
            ,"Customs clearance"
            , "Shipping Problems"
            ,true
            ,true
            ,false
            ,Stream.of(OrderState.SHIPPED.getStates()
            , OrderState.DELIVERED.getStates()
            , OrderState.CANCELED.getStates()
            , OrderState.REJECTED.getStates())
            .flatMap(item -> item.stream())
            .collect(Collectors.toSet())
            ,""),
    NEED_PRODUCT_ASAP(1020
            ,"Need product as soon as possible"
            , "Shipping Problems"
            ,true
            ,true
            ,false
            ,Stream.of(OrderState.SHIPPED.getStates()
            , OrderState.DELIVERED.getStates()
            , OrderState.CANCELED.getStates()
            , OrderState.REJECTED.getStates())
            .flatMap(item -> item.stream())
            .collect(Collectors.toSet())
            ,""),
    TRACKING_LOST(1021
            ,"Tracking status is \"Lost\" or   \"Damaged\""
            , "Shipping Problems"
            ,true
            ,true
            ,false
            ,Stream.of(OrderState.SHIPPED.getStates()
            , OrderState.DELIVERED.getStates()
            , OrderState.CANCELED.getStates()
            , OrderState.REJECTED.getStates())
            .flatMap(item -> item.stream())
            .collect(Collectors.toSet())
            ,""),
    TRACKING_RTO(1022
            ,"Tracking status is \"RTO_Notified\" or   \"RTO\""
            , "Shipping Problems"
            ,true
            ,true
            ,false
            ,Stream.of(OrderState.SHIPPED.getStates()
            , OrderState.DELIVERED.getStates()
            , OrderState.CANCELED.getStates()
            , OrderState.REJECTED.getStates())
            .flatMap(item -> item.stream())
            .collect(Collectors.toSet())
            ,""),
    NOT_RECEIVED(1023
            ,"It says \"delivered\" but I have not   received it"
            , "Shipping Problems"
            ,true
            ,true
            ,false
            ,Stream.of(OrderState.SHIPPED.getStates()
            , OrderState.DELIVERED.getStates()
            , OrderState.CANCELED.getStates()
            , OrderState.REJECTED.getStates())
            .flatMap(item -> item.stream())
            .collect(Collectors.toSet())
            ,""),
    EXTRA_CHARGE(1024
            ,"Extra change by courier boy"
            , "Shipping Problems"
            ,true
            ,true
            ,true
            ,Stream.of(OrderState.SHIPPED.getStates()
            , OrderState.DELIVERED.getStates()
            , OrderState.CANCELED.getStates()
            , OrderState.REJECTED.getStates())
            .flatMap(item -> item.stream())
            .collect(Collectors.toSet())
            ,""),
    RETURN_PICKUP(1025
            ,"Return Pickup issues"
            , "Shipping Problems"
            ,true
            ,true
            ,true
            ,Stream.of(OrderState.SHIPPED.getStates()
            , OrderState.DELIVERED.getStates()
            , OrderState.CANCELED.getStates()
            , OrderState.REJECTED.getStates())
            .flatMap(item -> item.stream())
            .collect(Collectors.toSet())
            ,""),
    RETURN_BY_MYSELF(1026
            ,"Return Mail by myself issue"
            , "Shipping Problems"
            ,true
            ,true
            ,true
            ,Stream.of(OrderState.SHIPPED.getStates()
            , OrderState.DELIVERED.getStates()
            , OrderState.CANCELED.getStates()
            , OrderState.REJECTED.getStates())
            .flatMap(item -> item.stream())
            .collect(Collectors.toSet())
            ,""),
    SHIPPING_PROBLEM_OTHERS(1027
            ,"Others"
            , "Shipping Problems"
            ,true
            ,true
            ,false
            ,Stream.of(OrderState.SHIPPED.getStates()
            , OrderState.DELIVERED.getStates()
            , OrderState.CANCELED.getStates()
            , OrderState.REJECTED.getStates())
            .flatMap(item -> item.stream())
            .collect(Collectors.toSet())
            ,""),

        //Return
    HOW_TO_RETURN(1028
            ,"How to return",
            "Return"
            ,true
            ,true
            ,true
            ,Stream.of(OrderState.SHIPPED.getStates()
            , OrderState.DELIVERED.getStates()
            , OrderState.CANCELED.getStates()
            , OrderState.REJECTED.getStates())
            .flatMap(item -> item.stream())
            .collect(Collectors.toSet())
            ,""),

    RETURN_PRODUCT_ISSUE(1029
            ,"Return due to product issue",
            "Return"
            ,true
            ,true
            ,true
            ,Stream.of(OrderState.SHIPPED.getStates()
            , OrderState.DELIVERED.getStates()
            , OrderState.CANCELED.getStates()
            , OrderState.REJECTED.getStates())
            .flatMap(item -> item.stream())
            .collect(Collectors.toSet())
            ,""),
    RETURN_NOT_NEED(1030
            ,"Return  as I do not need   it",
            "Return"
            ,true
            ,true
            ,false
            ,Stream.of(OrderState.SHIPPED.getStates()
            , OrderState.DELIVERED.getStates()
            , OrderState.CANCELED.getStates()
            , OrderState.REJECTED.getStates())
            .flatMap(item -> item.stream())
            .collect(Collectors.toSet())
            ,""),
    RETURN_REFUND_REJECTED(1031
            ,"Return refund rejected",
            "Return"
            ,true
            ,true
            ,false
            ,Stream.of(OrderState.SHIPPED.getStates()
            , OrderState.DELIVERED.getStates()
            , OrderState.CANCELED.getStates()
            , OrderState.REJECTED.getStates())
            .flatMap(item -> item.stream())
            .collect(Collectors.toSet())
            ,""),
    NON_RETURNABLE_ITEM(1032
            ,"Non-returnable product",
            "Return"
            ,true
            ,true
            ,true
            ,Stream.of(OrderState.SHIPPED.getStates()
            , OrderState.DELIVERED.getStates()
            , OrderState.CANCELED.getStates()
            , OrderState.REJECTED.getStates())
            .flatMap(item -> item.stream())
            .collect(Collectors.toSet())
            ,""),
    UNABLE_RETURN_TIME_LIMIT(1033
            ,"Unable to return over 9 days of policy",
            "Return"
            ,true
            ,true
            ,false
            ,Stream.of(OrderState.SHIPPED.getStates()
            , OrderState.DELIVERED.getStates()
            , OrderState.CANCELED.getStates()
            , OrderState.REJECTED.getStates())
            .flatMap(item -> item.stream())
            .collect(Collectors.toSet())
            ,""),
    RETURN_SHIPPING_FEE(1034
            ,"Return shipping fee",
            "Return"
            ,true
            ,true
            ,true
            ,Stream.of(OrderState.SHIPPED.getStates()
            , OrderState.DELIVERED.getStates()
            , OrderState.CANCELED.getStates()
            , OrderState.REJECTED.getStates())
            .flatMap(item -> item.stream())
            .collect(Collectors.toSet())
            ,""),
    RETURN_OTHERS(1035
            ,"Others",
            "Return"
            ,true
            ,true
            ,false
            ,Stream.of(OrderState.SHIPPED.getStates()
            , OrderState.DELIVERED.getStates()
            , OrderState.CANCELED.getStates()
            , OrderState.REJECTED.getStates())
            .flatMap(item -> item.stream())
            .collect(Collectors.toSet())
            ,""),

    //Refund
    LESS_REFUND_AMOUNT(1036
            ,"Received less refund amount",
            "Refund"
            ,true
            ,true
            ,false
            ,Stream.of(OrderState.SHIPPED.getStates()
            , OrderState.DELIVERED.getStates()
            , OrderState.CANCELED.getStates()
            , OrderState.REJECTED.getStates())
            .flatMap(item -> item.stream())
            .collect(Collectors.toSet())
            ,""),
    ITEM_MISSING(1037
            ,"Item or parts missing",
            "Refund"
            ,true
            ,true
            ,true
            ,Stream.of(OrderState.SHIPPED.getStates()
            , OrderState.DELIVERED.getStates()
            , OrderState.CANCELED.getStates()
            , OrderState.REJECTED.getStates())
            .flatMap(item -> item.stream())
            .collect(Collectors.toSet())
            ,""),
    REFUND_NOT_RECEIVED(1038
            ,"APP shows \"refunded\" but I have not   received it",
            "Refund"
            ,true
            ,true
            ,false
            ,Stream.of(OrderState.SHIPPED.getStates()
            , OrderState.DELIVERED.getStates()
            , OrderState.CANCELED.getStates()
            , OrderState.REJECTED.getStates())
            .flatMap(item -> item.stream())
            .collect(Collectors.toSet())
            ,""),
    DELIVERED_NOT_REFUND(1039
            ,"Return delivered but no refund",
            "Refund"
            ,true
            ,true
            ,false
            ,Stream.of(OrderState.SHIPPED.getStates()
            , OrderState.DELIVERED.getStates()
            , OrderState.CANCELED.getStates()
            , OrderState.REJECTED.getStates())
            .flatMap(item -> item.stream())
            .collect(Collectors.toSet())
            ,""),
    TAX_REFUND(1040
            ,"Tax refund",
            "Refund"
            ,true
            ,true
            ,true
            ,Stream.of(OrderState.SHIPPED.getStates()
            , OrderState.DELIVERED.getStates()
            , OrderState.CANCELED.getStates()
            , OrderState.REJECTED.getStates())
            .flatMap(item -> item.stream())
            .collect(Collectors.toSet())
            ,""),
    EMPTY_PARCEL(1041
            ,"Empty parcel",
            "Refund"
            ,true
            ,true
            ,true
            ,Stream.of(OrderState.SHIPPED.getStates()
            , OrderState.DELIVERED.getStates()
            , OrderState.CANCELED.getStates()
            , OrderState.REJECTED.getStates())
            .flatMap(item -> item.stream())
            .collect(Collectors.toSet())
            ,""),
    REFUND_OTHERS(1042
            ,"Others",
            "Refund"
            ,true
            ,true
            ,false
            ,Stream.of(OrderState.SHIPPED.getStates()
            , OrderState.DELIVERED.getStates()
            , OrderState.CANCELED.getStates()
            , OrderState.REJECTED.getStates())
            .flatMap(item -> item.stream())
            .collect(Collectors.toSet())
            ,""),

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
     * 一级问题的String
     */
    private String levelOne;

    /**
     * 用户是否可见
     */
    private Boolean isShowToUser;

    private Boolean needOrder;

    private Boolean needImages;

    private Set<String> orderStates;

    private String description;

    private static List<ConsultQuestion> USER_QUESTION_LIST = new ArrayList<>();

    private static List<ConsultQuestion> ALL_QUESTION_LIST = new ArrayList<>();

    private static final Map<Integer,ConsultQuestion> map = new HashMap<>();

    private static final Map<String, List<ConsultQuestion>> mapByQuestionType = new HashMap<>();

    ConsultQuestion(Integer value, String text, String levelOne, Boolean isShowToUser, Boolean needOrder, Boolean needImages, Set<String> orderStates, String description) {
        this.value = value;
        this.text = text;
        this.levelOne = levelOne;
        this.isShowToUser = isShowToUser;
        this.needOrder = needOrder;
        this.needImages = needImages;
        this.orderStates = orderStates;
        this.description = description;
    }

    public static ConsultQuestion getEnum(Integer code) {
        for (ConsultQuestion value : ConsultQuestion.values()) {
            if (value.value.equals(code)) {
                return value;
            }
        }
        return null;
    }


    public final static List<ConsultQuestion> userQuestionList() {
        return USER_QUESTION_LIST;
    }

    public final static List<ConsultQuestion> allQuestionList() {
        return ALL_QUESTION_LIST;
    }

    static {
        EnumSet.allOf(ConsultQuestion.class).forEach(item ->
                {
                    map.put(item.getValue(),item);
                    ALL_QUESTION_LIST.add(item);
                    if(Boolean.TRUE.equals(item.getIsShowToUser())) {
                        USER_QUESTION_LIST.add(item);
                    }

                    List<ConsultQuestion> list = mapByQuestionType.getOrDefault(item.getLevelOne(), new ArrayList<>());
                    list.add(item);
                    mapByQuestionType.put(item.getLevelOne(), list);
                }
        );
    }

    public static final ConsultQuestion ofUserShow(Integer code) {
        return map.get(code);
    }

    public static final String getDescByCode(Integer code) {
        ConsultQuestion question = map.get(code);
        return null == question ? null : question.getText();
    }

    public static final List<ConsultQuestion> getByQuestionType(String questionType) {
        List<ConsultQuestion> list = mapByQuestionType.get(questionType);
        return list == null ? new ArrayList<>() : list;
    }

    public interface IState {

        String getCode();
    }


    public enum OrderState{




        PREPARING("Preparing",Arrays.asList("preparing")),
        SHIPPED("Shipped",Arrays.asList("shipped")),
        COD_SUBMIT("COD Submit",Arrays.asList("preparing")),
        UNPAID("Unpaid",Arrays.asList("unpaid")),
        DELIVERED("Delivered",Arrays.asList("delivered")),
        //RETURN_REFUND("Return/Refund"),
        CANCELED("Canceled",Arrays.asList("cancel")),
        EXPIRED("Expired",Arrays.asList("expired")),
        REJECTED("Rejected",Arrays.asList("rejection")),

        ;

        private @Getter String code;

        private @Getter List<String> states;

        OrderState(String code,List<String> states) {
            this.code = code;
            this.states = states;
        }
    }

    public static void main(String[] args) {
        Stream.of(OrderState.UNPAID.getStates()).flatMap(item -> item.stream()).toString();
    }

}
