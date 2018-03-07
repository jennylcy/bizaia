package com.bizaia.zhongyin.module.mine.data;

import java.io.Serializable;

/**
 * Created by yan on 2017/4/26.
 */

public class AreaCountryAreaData implements Serializable{
    public String country;
    public String area;

    public AreaCountryAreaData(String country, String area) {
        this.country = country;
        this.area = area;
    }
}
