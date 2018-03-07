package com.bizaia.zhongyin.repository.data;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;


/**
 * Created by yan on 2017/3/15.
 */

@Entity
public class MonthlyNewestBean {
    /**
     * id : 1
     * title : 财会月刊
     * coverUrl : http://www.ckyk.cn/upload_files/20161125/3857386.jpg
     * fileUrl : http://www.ckyk.cn/upload_files/20161125/3857386.jpg
     * price : 200
     * description : springmvc中，可以用ReuqestMethod属性来控制 POST 或 GET 方式提交表单 或访问url，
     * chapterTitles : [{"id":4,"title":"山东工商学院金融学院4","coverUrl":"http://www.ckyk.cn/upload_files/20170306/5674555.jpg","area":"中国","author":"张三","advertiseUrl":"http://c.k429fma.com/s/1/110/0.html?s=40&t=1&v=U1NYv9SB5C0CJAR6dnA.&c=262&cg=3484&b=10831&uid=501553&sz=9800090","hrefUrl":"http://c.k429fma.com/s/1/110/0.html?s=40&t=1&v=U1NYv9SB5C0CJAR6dnA.&c=262&cg=3484&b=10831&uid=501553&sz=9800090","createTime":1488977598000,"monthlyId":1,"browseNum":100,"commentNum":120,"content":"习近平到四川代表团参加审议】3月8日上午，中共中央总书记、国家主席、中央军委主席习近平来到十二届全国人大五次会议四川代表团参加审议。"},{"id":3,"title":"山东工商学院金融学3","coverUrl":"http://www.ckyk.cn/upload_files/20170306/5674555.jpg","area":"中国","author":"张三","advertiseUrl":"http://c.k429fma.com/s/1/110/0.html?s=40&t=1&v=U1NYv9SB5C0CJAR6dnA.&c=262&cg=3484&b=10831&uid=501553&sz=9800090","hrefUrl":"http://c.k429fma.com/s/1/110/0.html?s=40&t=1&v=U1NYv9SB5C0CJAR6dnA.&c=262&cg=3484&b=10831&uid=501553&sz=9800090","createTime":1488973998000,"monthlyId":1,"browseNum":100,"commentNum":120,"content":"习近平到四川代表团参加审议】3月8日上午，中共中央总书记、国家主席、中央军委主席习近平来到十二届全国人大五次会议四川代表团参加审议。"}]
     */
    @Id
    private long id;
    private String title;
    private String coverUrl;
    private String fileUrl;
    private double price;
    private boolean isBuy;
    private String description;
    private String downloadPath;
    private String dataJS1;
    private String dataJS2;
    private String userId;
    private String shareUrl;
    @Generated(hash = 1091401673)
    public MonthlyNewestBean(long id, String title, String coverUrl, String fileUrl, double price, boolean isBuy, String description, String downloadPath, String dataJS1, String dataJS2, String userId, String shareUrl) {
        this.id = id;
        this.title = title;
        this.coverUrl = coverUrl;
        this.fileUrl = fileUrl;
        this.price = price;
        this.isBuy = isBuy;
        this.description = description;
        this.downloadPath = downloadPath;
        this.dataJS1 = dataJS1;
        this.dataJS2 = dataJS2;
        this.userId = userId;
        this.shareUrl = shareUrl;
    }
    @Generated(hash = 613317829)
    public MonthlyNewestBean() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getCoverUrl() {
        return this.coverUrl;
    }
    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }
    public String getFileUrl() {
        return this.fileUrl;
    }
    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
    public double getPrice() {
        return this.price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public boolean getIsBuy() {
        return this.isBuy;
    }
    public void setIsBuy(boolean isBuy) {
        this.isBuy = isBuy;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getDownloadPath() {
        return this.downloadPath;
    }
    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
    }
    public String getDataJS1() {
        return this.dataJS1;
    }
    public void setDataJS1(String dataJS1) {
        this.dataJS1 = dataJS1;
    }
    public String getDataJS2() {
        return this.dataJS2;
    }
    public void setDataJS2(String dataJS2) {
        this.dataJS2 = dataJS2;
    }
    public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getShareUrl() {
        return this.shareUrl;
    }
    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

}
