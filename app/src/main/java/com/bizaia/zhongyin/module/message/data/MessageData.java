package com.bizaia.zhongyin.module.message.data;

import java.util.List;

/**
 * Created by yan on 2017/6/5.
 */

public class MessageData {

    /**
     * code : 200
     * data : {"pageSize":10,"pageNo":1,"totalCount":50,"totalPage":5,"datas":[{"id":10004824,"createTs":"2017-06-05 07:09:27","title":"您关注的企业上新啦","body":"您关注的企业1905添加了直播:刘龙庆的直播间，赶紧去看看吧","type":"live","pid":10000109,"url":""},{"id":10004819,"createTs":"2017-06-05 06:49:11","title":"您关注的企业上新啦","body":"您关注的企业1905添加了直播:tttt，赶紧去看看吧","type":"live","pid":10000108,"url":""},{"id":10004814,"createTs":"2017-06-05 06:13:11","title":"您关注的企业上新啦","body":"您关注的企业1905添加了直播:炜哥，赶紧去看看吧","type":"live","pid":10000107,"url":""},{"id":10004807,"createTs":"2017-06-05 04:28:33","title":"您关注的企业上新啦","body":"您关注的企业1905添加了直播:mac，赶紧去看看吧","type":"live","pid":10000106,"url":""},{"id":10004802,"createTs":"2017-06-05 03:14:36","title":"您关注的企业上新啦","body":"您关注的企业1905添加了直播:严教授的直播，赶紧去看看吧","type":"live","pid":10000105,"url":""},{"id":10004789,"createTs":"2017-06-05 02:49:26","title":"您关注的企业上新啦","body":"您关注的企业1905添加了直播:测试不要通过审核，赶紧去看看吧","type":"live","pid":10000099,"url":""},{"id":10004776,"createTs":"2017-06-05 01:45:38","title":"您关注的企业上新啦","body":"您关注的企业1905添加了直播:阿甘的直播间，赶紧去看看吧","type":"live","pid":10000101,"url":""},{"id":10004771,"createTs":"2017-06-05 01:43:13","title":"您关注的企业上新啦","body":"您关注的企业1905添加了直播:6.5直播测试，赶紧去看看吧","type":"live","pid":10000100,"url":""},{"id":10004723,"createTs":"2017-06-03 17:57:42","title":"您关注的企业上新啦","body":"您关注的企业1905添加了讲座:【PSA讲座】塚本由晴：建筑设计的生态型转变，赶紧去看看吧","type":"lecture","pid":10000078,"url":""},{"id":10004766,"createTs":"2017-06-03 15:59:12","title":"您关注的企业上新啦","body":"您关注的企业1905添加了讲座:当代中国的个体与道德自我，赶紧去看看吧","type":"lecture","pid":10000080,"url":""}]}
     */

    private int code;
    private String msg;
    private DataBean data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * pageSize : 10
         * pageNo : 1
         * totalCount : 50
         * totalPage : 5
         * datas : [{"id":10004824,"createTs":"2017-06-05 07:09:27","title":"您关注的企业上新啦","body":"您关注的企业1905添加了直播:刘龙庆的直播间，赶紧去看看吧","type":"live","pid":10000109,"url":""},{"id":10004819,"createTs":"2017-06-05 06:49:11","title":"您关注的企业上新啦","body":"您关注的企业1905添加了直播:tttt，赶紧去看看吧","type":"live","pid":10000108,"url":""},{"id":10004814,"createTs":"2017-06-05 06:13:11","title":"您关注的企业上新啦","body":"您关注的企业1905添加了直播:炜哥，赶紧去看看吧","type":"live","pid":10000107,"url":""},{"id":10004807,"createTs":"2017-06-05 04:28:33","title":"您关注的企业上新啦","body":"您关注的企业1905添加了直播:mac，赶紧去看看吧","type":"live","pid":10000106,"url":""},{"id":10004802,"createTs":"2017-06-05 03:14:36","title":"您关注的企业上新啦","body":"您关注的企业1905添加了直播:严教授的直播，赶紧去看看吧","type":"live","pid":10000105,"url":""},{"id":10004789,"createTs":"2017-06-05 02:49:26","title":"您关注的企业上新啦","body":"您关注的企业1905添加了直播:测试不要通过审核，赶紧去看看吧","type":"live","pid":10000099,"url":""},{"id":10004776,"createTs":"2017-06-05 01:45:38","title":"您关注的企业上新啦","body":"您关注的企业1905添加了直播:阿甘的直播间，赶紧去看看吧","type":"live","pid":10000101,"url":""},{"id":10004771,"createTs":"2017-06-05 01:43:13","title":"您关注的企业上新啦","body":"您关注的企业1905添加了直播:6.5直播测试，赶紧去看看吧","type":"live","pid":10000100,"url":""},{"id":10004723,"createTs":"2017-06-03 17:57:42","title":"您关注的企业上新啦","body":"您关注的企业1905添加了讲座:【PSA讲座】塚本由晴：建筑设计的生态型转变，赶紧去看看吧","type":"lecture","pid":10000078,"url":""},{"id":10004766,"createTs":"2017-06-03 15:59:12","title":"您关注的企业上新啦","body":"您关注的企业1905添加了讲座:当代中国的个体与道德自我，赶紧去看看吧","type":"lecture","pid":10000080,"url":""}]
         */

        private int pageSize;
        private int pageNo;
        private int totalCount;
        private int totalPage;
        private List<DatasBean> datas;

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getPageNo() {
            return pageNo;
        }

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public List<DatasBean> getDatas() {
            return datas;
        }

        public void setDatas(List<DatasBean> datas) {
            this.datas = datas;
        }

        public static class DatasBean {
            /**
             * id : 10004824
             * createTs : 2017-06-05 07:09:27
             * title : 您关注的企业上新啦
             * body : 您关注的企业1905添加了直播:刘龙庆的直播间，赶紧去看看吧
             * type : live
             * pid : 10000109
             * url :
             */

            private Long id;
            private String createTs;
            private String title;
            private String body;
            private String type;
            private Long pid;
            private String url;
            private boolean isRead;
            private int position;
            private long orgId;

            public long getOrgId() {
                return orgId;
            }

            public void setOrgId(long orgId) {
                this.orgId = orgId;
            }

            public int getPosition() {
                return position;
            }

            public void setPosition(int position) {
                this.position = position;
            }

            public boolean isRead() {
                return isRead;
            }

            public void setRead(boolean read) {
                isRead = read;
            }

            public Long getId() {
                return id;
            }

            public void setId(Long id) {
                this.id = id;
            }

            public String getCreateTs() {
                return createTs;
            }

            public void setCreateTs(String createTs) {
                this.createTs = createTs;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getBody() {
                return body;
            }

            public void setBody(String body) {
                this.body = body;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public Long getPid() {
                return pid;
            }

            public void setPid(Long pid) {
                this.pid = pid;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
