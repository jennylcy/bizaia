package com.bizaia.zhongyin.module.common.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yan on 2017/4/28.
 */

public class SearchNavData implements Serializable{

    /**
     * code : 200
     * data : [{"id":10007,"category":"点播","subcategory":"恐怖","productTypeId":3,"contentNum":0,"createTime":1492634169168},{"id":10021,"category":"点播","subcategory":"测试","productTypeId":3,"contentNum":0,"createTime":1493134655274},{"id":10017,"category":"点播","subcategory":"耳朵","productTypeId":3,"contentNum":0,"createTime":1492634566927},{"id":10003,"category":"点播","subcategory":"英语","productTypeId":3,"contentNum":20,"createTime":1492501448000}]
     */

    private int code;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 10007
         * category : 点播
         * subcategory : 恐怖
         * productTypeId : 3
         * contentNum : 0
         * createTime : 1492634169168
         */

        private int id;
        private String category;
        private String subcategory;
        private int productTypeId;
        private int contentNum;
        private String createTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getSubcategory() {
            return subcategory;
        }

        public void setSubcategory(String subcategory) {
            this.subcategory = subcategory;
        }

        public int getProductTypeId() {
            return productTypeId;
        }

        public void setProductTypeId(int productTypeId) {
            this.productTypeId = productTypeId;
        }

        public int getContentNum() {
            return contentNum;
        }

        public void setContentNum(int contentNum) {
            this.contentNum = contentNum;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
