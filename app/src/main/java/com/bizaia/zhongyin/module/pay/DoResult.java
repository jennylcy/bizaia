package com.bizaia.zhongyin.module.pay;

/**
 * 处理完结果之后回调
 */
public interface DoResult {
    //与服务确认支付成功
    void confirmSuccess();

    //网络异常或者json返回有问题
    void confirmNetWorkError();

    //用户取消支付
    void customerCanceled();

    //授权支付
    void confirmFuturePayment();

    //订单支付验证无效
    void invalidPaymentConfiguration();
}
