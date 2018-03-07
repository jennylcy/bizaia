package com.bizaia.zhongyin.module.mine.iml;

import com.bizaia.zhongyin.module.mine.data.CompanyHostBean;

/**
 * Created by zyz on 2017/3/6.
 */

public interface ICompanyView {
    void showCompanyHost(CompanyHostBean companyHostBean);
    void showCompanyHostError();
}
