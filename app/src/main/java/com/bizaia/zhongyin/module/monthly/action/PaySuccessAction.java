package com.bizaia.zhongyin.module.monthly.action;

import com.bizaia.zhongyin.module.pay.data.OrderData;

/**
 * Created by yan on 2017/4/21.
 */

public class PaySuccessAction {
    public Object payData;
    public OrderData orderData;

    public PaySuccessAction( ) {
    }
    public PaySuccessAction(Object payData) {
        this.payData = payData;
    }
    public PaySuccessAction(OrderData orderData) {
        this.orderData = orderData;
    }

    public PaySuccessAction(Object payData, OrderData orderData) {
        this.payData = payData;
        this.orderData = orderData;
    }
}
