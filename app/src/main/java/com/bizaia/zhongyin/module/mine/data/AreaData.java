package com.bizaia.zhongyin.module.mine.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yan on 2017/3/21.
 */

public class AreaData implements Serializable {

    /**
     * code : 200
     * data : [{"id":10000,"country":"中国","provinceList":[{"id":10021,"province":"三亚"},{"id":10002,"province":"北京"},{"id":10001,"province":"四川"},{"id":10011,"province":"河北"},{"id":10010,"province":"河南"},{"id":10020,"province":"海南"},{"id":10019,"province":"深圳"},{"id":10009,"province":"湖北"},{"id":10003,"province":"湖南"},{"id":10012,"province":"重庆"}]},{"id":10029,"country":"埃及","provinceList":[]},{"id":10004,"country":"日本","provinceList":[{"id":10005,"province":"东京"}]},{"id":10013,"country":"美国","provinceList":[{"id":10018,"province":"加里奥利亚"},{"id":10017,"province":"芝加哥"}]},{"id":10028,"country":"菲律宾","provinceList":[]},{"id":10007,"country":"越南","provinceList":[{"id":10008,"province":"胡志明市"}]}]
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

    public static class DataBean implements Serializable {
        /**
         * id : 10000
         * country : 中国
         * provinceList : [{"id":10021,"province":"三亚"},{"id":10002,"province":"北京"},{"id":10001,"province":"四川"},{"id":10011,"province":"河北"},{"id":10010,"province":"河南"},{"id":10020,"province":"海南"},{"id":10019,"province":"深圳"},{"id":10009,"province":"湖北"},{"id":10003,"province":"湖南"},{"id":10012,"province":"重庆"}]
         */

        private long id;
        private String country;
        private List<ProvinceListBean> provinceList;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public List<ProvinceListBean> getProvinceList() {
            return provinceList;
        }

        public void setProvinceList(List<ProvinceListBean> provinceList) {
            this.provinceList = provinceList;
        }

        public static class ProvinceListBean implements Serializable {
            /**
             * id : 10021
             * province : 三亚
             */

            private long id;
            private String province;
            private boolean isSelect;

            public boolean isSelect() {
                return isSelect;
            }

            public void setSelect(boolean select) {
                isSelect = select;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }
        }
    }
}
