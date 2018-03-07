package com.bizaia.zhongyin.module.discovery.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yan on 2017/3/20.
 */

public class AssociationData implements Serializable {

    /**
     * code : 200
     * data : {"listData":{"pageSize":5,"pageNo":1,"totalCount":2,"totalPage":1,"datas":[{"id":2,"title":"东京高尔夫俱乐部精英赛","coverUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1489830233222&di=9379b279c0e7c3634822da2e5f12167d&imgtype=0&src=http%3A%2F%2Fimg.sccnn.com%2Fbimg%2F337%2F8782.jpg","createTime":1489971418000,"favorite":false,"h5Url":"http://192.168.4.205:8090/dist/html/LiveShare.html?id=2"},{"id":1,"title":"东京高尔夫俱乐部新成员欢迎会","coverUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1489830233222&di=9379b279c0e7c3634822da2e5f12167d&imgtype=0&src=http%3A%2F%2Fimg.sccnn.com%2Fbimg%2F337%2F8782.jpg","createTime":1489863418000,"favorite":false,"h5Url":"http://192.168.4.205:8090/dist/html/LiveShare.html?id=1"}]},"focusData":[{"id":1,"adminId":1,"title":"水电费为服务费","coverUrl":"http://img4.imgtn.bdimg.com/it/u=3432487329,2901563519&fm=23&gp=0.jpg","findId":1,"sort":1,"findType":1}]}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * listData : {"pageSize":5,"pageNo":1,"totalCount":2,"totalPage":1,"datas":[{"id":2,"title":"东京高尔夫俱乐部精英赛","coverUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1489830233222&di=9379b279c0e7c3634822da2e5f12167d&imgtype=0&src=http%3A%2F%2Fimg.sccnn.com%2Fbimg%2F337%2F8782.jpg","createTime":1489971418000,"favorite":false,"h5Url":"http://192.168.4.205:8090/dist/html/LiveShare.html?id=2"},{"id":1,"title":"东京高尔夫俱乐部新成员欢迎会","coverUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1489830233222&di=9379b279c0e7c3634822da2e5f12167d&imgtype=0&src=http%3A%2F%2Fimg.sccnn.com%2Fbimg%2F337%2F8782.jpg","createTime":1489863418000,"favorite":false,"h5Url":"http://192.168.4.205:8090/dist/html/LiveShare.html?id=1"}]}
         * focusData : [{"id":1,"adminId":1,"title":"水电费为服务费","coverUrl":"http://img4.imgtn.bdimg.com/it/u=3432487329,2901563519&fm=23&gp=0.jpg","findId":1,"sort":1,"findType":1}]
         */

        private ListDataBean listData;
        private List<FocusDataBean> focusData;

        public ListDataBean getListData() {
            return listData;
        }

        public void setListData(ListDataBean listData) {
            this.listData = listData;
        }

        public List<FocusDataBean> getFocusData() {
            return focusData;
        }

        public void setFocusData(List<FocusDataBean> focusData) {
            this.focusData = focusData;
        }

        public static class ListDataBean implements Serializable {
            /**
             * pageSize : 5
             * pageNo : 1
             * totalCount : 2
             * totalPage : 1
             * datas : [{"id":2,"title":"东京高尔夫俱乐部精英赛","coverUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1489830233222&di=9379b279c0e7c3634822da2e5f12167d&imgtype=0&src=http%3A%2F%2Fimg.sccnn.com%2Fbimg%2F337%2F8782.jpg","createTime":1489971418000,"favorite":false,"h5Url":"http://192.168.4.205:8090/dist/html/LiveShare.html?id=2"},{"id":1,"title":"东京高尔夫俱乐部新成员欢迎会","coverUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1489830233222&di=9379b279c0e7c3634822da2e5f12167d&imgtype=0&src=http%3A%2F%2Fimg.sccnn.com%2Fbimg%2F337%2F8782.jpg","createTime":1489863418000,"favorite":false,"h5Url":"http://192.168.4.205:8090/dist/html/LiveShare.html?id=1"}]
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

            public static class DatasBean implements Serializable {
                /**
                 * id : 2
                 * title : 东京高尔夫俱乐部精英赛
                 * coverUrl : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1489830233222&di=9379b279c0e7c3634822da2e5f12167d&imgtype=0&src=http%3A%2F%2Fimg.sccnn.com%2Fbimg%2F337%2F8782.jpg
                 * createTime : 1489971418000
                 * favorite : false
                 * h5Url : http://192.168.4.205:8090/dist/html/LiveShare.html?id=2
                 */

                private long id;
                private String title;
                private String coverUrl;
                private String createTime;
                private boolean favorite;
                private String h5Url;
                private long orgId;
                private int type;

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public long getOrgId() {
                    return orgId;
                }

                public void setOrgId(long orgId) {
                    this.orgId = orgId;
                }

                public long getId() {
                    return id;
                }

                public void setId(long id) {
                    this.id = id;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getCoverUrl() {
                    return coverUrl;
                }

                public void setCoverUrl(String coverUrl) {
                    this.coverUrl = coverUrl;
                }

                public String getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(String createTime) {
                    this.createTime = createTime;
                }

                public boolean isFavorite() {
                    return favorite;
                }

                public void setFavorite(boolean favorite) {
                    this.favorite = favorite;
                }

                public String getH5Url() {
                    return h5Url;
                }

                public void setH5Url(String h5Url) {
                    this.h5Url = h5Url;
                }
            }
        }

        public static class FocusDataBean implements Serializable {
            /**
             * id : 1
             * adminId : 1
             * title : 水电费为服务费
             * coverUrl : http://img4.imgtn.bdimg.com/it/u=3432487329,2901563519&fm=23&gp=0.jpg
             * findId : 1
             * sort : 1
             * findType : 1
             */

            private int id;
            private int adminId;
            private String title;
            private String coverUrl;
            private int findId;
            private int sort;
            private int findType;
            private String h5Url;

            public String getH5Url() {
                return h5Url;
            }

            public void setH5Url(String h5Url) {
                this.h5Url = h5Url;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getAdminId() {
                return adminId;
            }

            public void setAdminId(int adminId) {
                this.adminId = adminId;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getCoverUrl() {
                return coverUrl;
            }

            public void setCoverUrl(String coverUrl) {
                this.coverUrl = coverUrl;
            }

            public int getFindId() {
                return findId;
            }

            public void setFindId(int findId) {
                this.findId = findId;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public int getFindType() {
                return findType;
            }

            public void setFindType(int findType) {
                this.findType = findType;
            }
        }
    }
}
