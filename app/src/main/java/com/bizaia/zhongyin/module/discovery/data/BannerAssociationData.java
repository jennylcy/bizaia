package com.bizaia.zhongyin.module.discovery.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yan on 2017/3/8.
 */

public class BannerAssociationData implements Serializable {
    List<AssociationData.DataBean.FocusDataBean> focusDatasBeen;

    public BannerAssociationData(List<AssociationData.DataBean.FocusDataBean> focusDatasBeen) {
        this.focusDatasBeen = focusDatasBeen;
    }

    public List<AssociationData.DataBean.FocusDataBean> getFocusDataBeen() {
        return focusDatasBeen;
    }

    public void setFocusDatasBeen(List<AssociationData.DataBean.FocusDataBean> focusDatasBeen) {
        this.focusDatasBeen = focusDatasBeen;
    }
}
