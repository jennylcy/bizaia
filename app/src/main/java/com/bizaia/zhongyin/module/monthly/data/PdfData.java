package com.bizaia.zhongyin.module.monthly.data;

import java.io.Serializable;

/**
 * Created by yan on 2017/3/13.
 */

public class PdfData implements Serializable {
    public PdfData(MonthlyJSData.DataBean.MonthlyNewestBean monthlyNewestBean) {
        this.monthlyNewestBean = monthlyNewestBean;
    }

    public MonthlyJSData.DataBean.MonthlyNewestBean monthlyNewestBean;

    public MonthlyJSData.DataBean.MonthlyNewestBean getMonthlyNewestBean() {
        return monthlyNewestBean;
    }

    public void setMonthlyNewestBean(MonthlyJSData.DataBean.MonthlyNewestBean monthlyNewestBean) {
        this.monthlyNewestBean = monthlyNewestBean;
    }
}
