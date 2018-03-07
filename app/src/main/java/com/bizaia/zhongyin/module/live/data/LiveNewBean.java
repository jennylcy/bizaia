package com.bizaia.zhongyin.module.live.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zyz on 2017/3/7.
 */

public class LiveNewBean implements Serializable {


    /**
     * code : 200
     * data : {"liveFocusList":[{"id":4,"title":"推荐了推荐了推荐了","coverUrl":"http://pic62.nipic.com/file/20150319/12632424_132215178296_2.jpg","liveId":22}],"liveList":{"pageSize":10,"pageNo":1,"totalCount":7,"totalPage":1,"datas":[{"id":12,"title":"直播-金牌高考数学复习","coverUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1488797902235&di=885430d13d39dabae198c1463163e684&imgtype=0&src=http%3A%2F%2Fimg1.gamedog.cn%2F2013%2F12%2F10%2F124-1312101455380.jpg","cateName":"外语","startTime":1491537600000,"price":80,"lecturer":"李老师","coursewareStreamAddress":{"cid":"7cd03dd6830848e4944078a29a82833c","pushUrl":"rtmp://zyph.forcetechcloud.com/lzy/7cd03dd6830848e4944078a29a82833c","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/7cd03dd6830848e4944078a29a82833c.flv?md5=caf3815648779802751ea1ce15e8f2e1&time=58e75bad","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/7cd03dd6830848e4944078a29a82833c.m3u8?md5=a0d7d00280d950af943c7d906935d613&time=58e75bad"},"cameraStreamAddress":{"cid":"ab9924949aca4a43bee6da210b8f94ea","pushUrl":"rtmp://zyph.forcetechcloud.com/lzy/ab9924949aca4a43bee6da210b8f94ea","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/ab9924949aca4a43bee6da210b8f94ea.flv?md5=66b510ae3f795a4d62f0106e79bb037b&time=58e75bad","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/ab9924949aca4a43bee6da210b8f94ea.m3u8?md5=0a90b8743ca6189fb21b5c5aed2237f1&time=58e75bad"},"chatroomId":"@TGS#354A2ZOEJ","maxAudience":0,"status":"0","createTime":1491557321165,"organizationName":"a中樱","organizationLogo":"http://up.qqjia.com/z/01/tu4488_33.jpg","h5Url":"http://192.168.4.205:8090/dist/html/LiveShare.html?id=12","del":false},{"id":22,"title":"直播课程-测试测试测试测试","coverUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1488797902235&di=885430d13d39dabae198c1463163e684&imgtype=0&src=http%3A%2F%2Fimg1.gamedog.cn%2F2013%2F12%2F10%2F124-1312101455380.jpg","cateName":"外语","startTime":1491537600000,"price":80,"lecturer":"陈虎","coursewareStreamAddress":{"cid":"ca90b935227e429493c5dc706e42cd58","pushUrl":"rtmp://scympush.forcetech.net:1935/lscym/lei","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/ca90b935227e429493c5dc706e42cd58.flv?md5=3b8a8b2890814ad2d21204be6ee11b5b&time=58e6fb2e","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/ca90b935227e429493c5dc706e42cd58.m3u8?md5=7782a23996acad21802dc9e791b2ddb8&time=58e6fb2e"},"cameraStreamAddress":{"cid":"7427c4fe15764217b3804899064f2720","pushUrl":"rtmp://zyph.forcetechcloud.com/lzy/7427c4fe15764217b3804899064f2720","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/7427c4fe15764217b3804899064f2720.flv?md5=b1da86ffbe2ab562b57621f73b3d5397&time=58e6fcca","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/7427c4fe15764217b3804899064f2720.m3u8?md5=c9325658edda0dbcc0c81dc06ab7f8b2&time=58e6fcca"},"chatroomId":"@TGS#35VYPJOET","maxAudience":50,"status":"1","createTime":1490971746000,"organizationName":"a中樱","organizationLogo":"http://up.qqjia.com/z/01/tu4488_33.jpg","h5Url":"http://192.168.4.205:8090/dist/html/LiveShare.html?id=22","del":false},{"id":16,"title":"直播课程-雅思冲刺班","coverUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1488797902237&di=41ea28bcd2d2bda13d7ef2f1ed355dfa&imgtype=0&src=http%3A%2F%2Fimg5.duitang.com%2Fuploads%2Fitem%2F201301%2F16%2F20130116095533_RXXay.jpeg","cateName":"外语","startTime":1491556162247,"price":99.99,"lecturer":"李老师","coursewareStreamAddress":{"cid":"ca90b935227e429493c5dc706e42cd58","pushUrl":"rtmp://scympush.forcetech.net:1935/lscym/lei","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/ca90b935227e429493c5dc706e42cd58.flv?md5=3b8a8b2890814ad2d21204be6ee11b5b&time=58e6fb2e","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/ca90b935227e429493c5dc706e42cd58.m3u8?md5=7782a23996acad21802dc9e791b2ddb8&time=58e6fb2e"},"cameraStreamAddress":{"cid":"7427c4fe15764217b3804899064f2720","pushUrl":"rtmp://zyph.forcetechcloud.com/lzy/7427c4fe15764217b3804899064f2720","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/7427c4fe15764217b3804899064f2720.flv?md5=b1da86ffbe2ab562b57621f73b3d5397&time=58e6fcca","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/7427c4fe15764217b3804899064f2720.m3u8?md5=c9325658edda0dbcc0c81dc06ab7f8b2&time=58e6fcca"},"chatroomId":"@TGS#35VYPJOET","maxAudience":100,"status":"2","createTime":1489025445000,"organizationName":"a中樱","organizationLogo":"http://up.qqjia.com/z/01/tu4488_33.jpg","h5Url":"http://192.168.4.205:8090/dist/html/LiveShare.html?id=16","del":false},{"id":3,"title":"直播课程-英语口语培训","coverUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1488797902235&di=885430d13d39dabae198c1463163e684&imgtype=0&src=http%3A%2F%2Fimg1.gamedog.cn%2F2013%2F12%2F10%2F124-1312101455380.jpg","cateName":"外语","startTime":1491574970000,"price":200,"lecturer":"田中","coursewareStreamAddress":{"cid":"ca90b935227e429493c5dc706e42cd58","pushUrl":"rtmp://scympush.forcetech.net:1935/lscym/lei","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/ca90b935227e429493c5dc706e42cd58.flv?md5=3b8a8b2890814ad2d21204be6ee11b5b&time=58e6fb2e","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/ca90b935227e429493c5dc706e42cd58.m3u8?md5=7782a23996acad21802dc9e791b2ddb8&time=58e6fb2e"},"cameraStreamAddress":{"cid":"7427c4fe15764217b3804899064f2720","pushUrl":"rtmp://zyph.forcetechcloud.com/lzy/7427c4fe15764217b3804899064f2720","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/7427c4fe15764217b3804899064f2720.flv?md5=b1da86ffbe2ab562b57621f73b3d5397&time=58e6fcca","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/7427c4fe15764217b3804899064f2720.m3u8?md5=c9325658edda0dbcc0c81dc06ab7f8b2&time=58e6fcca"},"chatroomId":"@TGS#35VYPJOET","maxAudience":60,"status":"2","createTime":1489025445000,"organizationName":"a中樱","organizationLogo":"http://up.qqjia.com/z/01/tu4488_33.jpg","h5Url":"http://192.168.4.205:8090/dist/html/LiveShare.html?id=3","del":false},{"id":15,"title":"直播课程-雅思冲刺班","coverUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1488797902237&di=41ea28bcd2d2bda13d7ef2f1ed355dfa&imgtype=0&src=http%3A%2F%2Fimg5.duitang.com%2Fuploads%2Fitem%2F201301%2F16%2F20130116095533_RXXay.jpeg","cateName":"外语","startTime":1491579388000,"price":99.99,"lecturer":"李老师","coursewareStreamAddress":{"cid":"ca90b935227e429493c5dc706e42cd58","pushUrl":"rtmp://scympush.forcetech.net:1935/lscym/lei","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/ca90b935227e429493c5dc706e42cd58.flv?md5=3b8a8b2890814ad2d21204be6ee11b5b&time=58e6fb2e","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/ca90b935227e429493c5dc706e42cd58.m3u8?md5=7782a23996acad21802dc9e791b2ddb8&time=58e6fb2e"},"cameraStreamAddress":{"cid":"7427c4fe15764217b3804899064f2720","pushUrl":"rtmp://zyph.forcetechcloud.com/lzy/7427c4fe15764217b3804899064f2720","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/7427c4fe15764217b3804899064f2720.flv?md5=b1da86ffbe2ab562b57621f73b3d5397&time=58e6fcca","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/7427c4fe15764217b3804899064f2720.m3u8?md5=c9325658edda0dbcc0c81dc06ab7f8b2&time=58e6fcca"},"chatroomId":"@TGS#35VYPJOET","maxAudience":100,"status":"2","createTime":1489025445000,"organizationName":"a中樱","organizationLogo":"http://up.qqjia.com/z/01/tu4488_33.jpg","h5Url":"http://192.168.4.205:8090/dist/html/LiveShare.html?id=15","del":false},{"id":2,"title":"直播课程-数学高考复习","coverUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1488797902235&di=885430d13d39dabae198c1463163e684&imgtype=0&src=http%3A%2F%2Fimg1.gamedog.cn%2F2013%2F12%2F10%2F124-1312101455380.jpg","cateName":"外语","startTime":1491554144370,"price":80,"lecturer":"田中","coursewareStreamAddress":{"cid":"ca90b935227e429493c5dc706e42cd58","pushUrl":"rtmp://scympush.forcetech.net:1935/lscym/lei","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/ca90b935227e429493c5dc706e42cd58.flv?md5=3b8a8b2890814ad2d21204be6ee11b5b&time=58e6fb2e","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/ca90b935227e429493c5dc706e42cd58.m3u8?md5=7782a23996acad21802dc9e791b2ddb8&time=58e6fb2e"},"cameraStreamAddress":{"cid":"7427c4fe15764217b3804899064f2720","pushUrl":"rtmp://zyph.forcetechcloud.com/lzy/7427c4fe15764217b3804899064f2720","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/7427c4fe15764217b3804899064f2720.flv?md5=b1da86ffbe2ab562b57621f73b3d5397&time=58e6fcca","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/7427c4fe15764217b3804899064f2720.m3u8?md5=c9325658edda0dbcc0c81dc06ab7f8b2&time=58e6fcca"},"chatroomId":"@TGS#35VYPJOET","maxAudience":50,"status":"2","createTime":1489025445000,"organizationName":"a中樱","organizationLogo":"http://up.qqjia.com/z/01/tu4488_33.jpg","h5Url":"http://192.168.4.205:8090/dist/html/LiveShare.html?id=2","del":false},{"id":13,"title":"直播课程-英语口语培训","coverUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1488797902235&di=885430d13d39dabae198c1463163e684&imgtype=0&src=http%3A%2F%2Fimg1.gamedog.cn%2F2013%2F12%2F10%2F124-1312101455380.jpg","cateName":"外语","startTime":1491574970000,"price":200,"lecturer":"田中","coursewareStreamAddress":{"cid":"ca90b935227e429493c5dc706e42cd58","pushUrl":"rtmp://scympush.forcetech.net:1935/lscym/lei","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/ca90b935227e429493c5dc706e42cd58.flv?md5=3b8a8b2890814ad2d21204be6ee11b5b&time=58e6fb2e","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/ca90b935227e429493c5dc706e42cd58.m3u8?md5=7782a23996acad21802dc9e791b2ddb8&time=58e6fb2e"},"cameraStreamAddress":{"cid":"7427c4fe15764217b3804899064f2720","pushUrl":"rtmp://zyph.forcetechcloud.com/lzy/7427c4fe15764217b3804899064f2720","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/7427c4fe15764217b3804899064f2720.flv?md5=b1da86ffbe2ab562b57621f73b3d5397&time=58e6fcca","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/7427c4fe15764217b3804899064f2720.m3u8?md5=c9325658edda0dbcc0c81dc06ab7f8b2&time=58e6fcca"},"chatroomId":"@TGS#35VYPJOET","maxAudience":60,"status":"1","createTime":1489025445000,"organizationName":"a中樱","organizationLogo":"http://up.qqjia.com/z/01/tu4488_33.jpg","h5Url":"http://192.168.4.205:8090/dist/html/LiveShare.html?id=13","del":false}],"offset":0}}
     */

    private int code;
    private String msg;
    /**
     * liveFocusList : [{"id":4,"title":"推荐了推荐了推荐了","coverUrl":"http://pic62.nipic.com/file/20150319/12632424_132215178296_2.jpg","liveId":22}]
     * liveList : {"pageSize":10,"pageNo":1,"totalCount":7,"totalPage":1,"datas":[{"id":12,"title":"直播-金牌高考数学复习","coverUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1488797902235&di=885430d13d39dabae198c1463163e684&imgtype=0&src=http%3A%2F%2Fimg1.gamedog.cn%2F2013%2F12%2F10%2F124-1312101455380.jpg","cateName":"外语","startTime":1491537600000,"price":80,"lecturer":"李老师","coursewareStreamAddress":{"cid":"7cd03dd6830848e4944078a29a82833c","pushUrl":"rtmp://zyph.forcetechcloud.com/lzy/7cd03dd6830848e4944078a29a82833c","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/7cd03dd6830848e4944078a29a82833c.flv?md5=caf3815648779802751ea1ce15e8f2e1&time=58e75bad","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/7cd03dd6830848e4944078a29a82833c.m3u8?md5=a0d7d00280d950af943c7d906935d613&time=58e75bad"},"cameraStreamAddress":{"cid":"ab9924949aca4a43bee6da210b8f94ea","pushUrl":"rtmp://zyph.forcetechcloud.com/lzy/ab9924949aca4a43bee6da210b8f94ea","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/ab9924949aca4a43bee6da210b8f94ea.flv?md5=66b510ae3f795a4d62f0106e79bb037b&time=58e75bad","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/ab9924949aca4a43bee6da210b8f94ea.m3u8?md5=0a90b8743ca6189fb21b5c5aed2237f1&time=58e75bad"},"chatroomId":"@TGS#354A2ZOEJ","maxAudience":0,"status":"0","createTime":1491557321165,"organizationName":"a中樱","organizationLogo":"http://up.qqjia.com/z/01/tu4488_33.jpg","h5Url":"http://192.168.4.205:8090/dist/html/LiveShare.html?id=12","del":false},{"id":22,"title":"直播课程-测试测试测试测试","coverUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1488797902235&di=885430d13d39dabae198c1463163e684&imgtype=0&src=http%3A%2F%2Fimg1.gamedog.cn%2F2013%2F12%2F10%2F124-1312101455380.jpg","cateName":"外语","startTime":1491537600000,"price":80,"lecturer":"陈虎","coursewareStreamAddress":{"cid":"ca90b935227e429493c5dc706e42cd58","pushUrl":"rtmp://scympush.forcetech.net:1935/lscym/lei","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/ca90b935227e429493c5dc706e42cd58.flv?md5=3b8a8b2890814ad2d21204be6ee11b5b&time=58e6fb2e","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/ca90b935227e429493c5dc706e42cd58.m3u8?md5=7782a23996acad21802dc9e791b2ddb8&time=58e6fb2e"},"cameraStreamAddress":{"cid":"7427c4fe15764217b3804899064f2720","pushUrl":"rtmp://zyph.forcetechcloud.com/lzy/7427c4fe15764217b3804899064f2720","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/7427c4fe15764217b3804899064f2720.flv?md5=b1da86ffbe2ab562b57621f73b3d5397&time=58e6fcca","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/7427c4fe15764217b3804899064f2720.m3u8?md5=c9325658edda0dbcc0c81dc06ab7f8b2&time=58e6fcca"},"chatroomId":"@TGS#35VYPJOET","maxAudience":50,"status":"1","createTime":1490971746000,"organizationName":"a中樱","organizationLogo":"http://up.qqjia.com/z/01/tu4488_33.jpg","h5Url":"http://192.168.4.205:8090/dist/html/LiveShare.html?id=22","del":false},{"id":16,"title":"直播课程-雅思冲刺班","coverUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1488797902237&di=41ea28bcd2d2bda13d7ef2f1ed355dfa&imgtype=0&src=http%3A%2F%2Fimg5.duitang.com%2Fuploads%2Fitem%2F201301%2F16%2F20130116095533_RXXay.jpeg","cateName":"外语","startTime":1491556162247,"price":99.99,"lecturer":"李老师","coursewareStreamAddress":{"cid":"ca90b935227e429493c5dc706e42cd58","pushUrl":"rtmp://scympush.forcetech.net:1935/lscym/lei","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/ca90b935227e429493c5dc706e42cd58.flv?md5=3b8a8b2890814ad2d21204be6ee11b5b&time=58e6fb2e","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/ca90b935227e429493c5dc706e42cd58.m3u8?md5=7782a23996acad21802dc9e791b2ddb8&time=58e6fb2e"},"cameraStreamAddress":{"cid":"7427c4fe15764217b3804899064f2720","pushUrl":"rtmp://zyph.forcetechcloud.com/lzy/7427c4fe15764217b3804899064f2720","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/7427c4fe15764217b3804899064f2720.flv?md5=b1da86ffbe2ab562b57621f73b3d5397&time=58e6fcca","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/7427c4fe15764217b3804899064f2720.m3u8?md5=c9325658edda0dbcc0c81dc06ab7f8b2&time=58e6fcca"},"chatroomId":"@TGS#35VYPJOET","maxAudience":100,"status":"2","createTime":1489025445000,"organizationName":"a中樱","organizationLogo":"http://up.qqjia.com/z/01/tu4488_33.jpg","h5Url":"http://192.168.4.205:8090/dist/html/LiveShare.html?id=16","del":false},{"id":3,"title":"直播课程-英语口语培训","coverUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1488797902235&di=885430d13d39dabae198c1463163e684&imgtype=0&src=http%3A%2F%2Fimg1.gamedog.cn%2F2013%2F12%2F10%2F124-1312101455380.jpg","cateName":"外语","startTime":1491574970000,"price":200,"lecturer":"田中","coursewareStreamAddress":{"cid":"ca90b935227e429493c5dc706e42cd58","pushUrl":"rtmp://scympush.forcetech.net:1935/lscym/lei","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/ca90b935227e429493c5dc706e42cd58.flv?md5=3b8a8b2890814ad2d21204be6ee11b5b&time=58e6fb2e","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/ca90b935227e429493c5dc706e42cd58.m3u8?md5=7782a23996acad21802dc9e791b2ddb8&time=58e6fb2e"},"cameraStreamAddress":{"cid":"7427c4fe15764217b3804899064f2720","pushUrl":"rtmp://zyph.forcetechcloud.com/lzy/7427c4fe15764217b3804899064f2720","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/7427c4fe15764217b3804899064f2720.flv?md5=b1da86ffbe2ab562b57621f73b3d5397&time=58e6fcca","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/7427c4fe15764217b3804899064f2720.m3u8?md5=c9325658edda0dbcc0c81dc06ab7f8b2&time=58e6fcca"},"chatroomId":"@TGS#35VYPJOET","maxAudience":60,"status":"2","createTime":1489025445000,"organizationName":"a中樱","organizationLogo":"http://up.qqjia.com/z/01/tu4488_33.jpg","h5Url":"http://192.168.4.205:8090/dist/html/LiveShare.html?id=3","del":false},{"id":15,"title":"直播课程-雅思冲刺班","coverUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1488797902237&di=41ea28bcd2d2bda13d7ef2f1ed355dfa&imgtype=0&src=http%3A%2F%2Fimg5.duitang.com%2Fuploads%2Fitem%2F201301%2F16%2F20130116095533_RXXay.jpeg","cateName":"外语","startTime":1491579388000,"price":99.99,"lecturer":"李老师","coursewareStreamAddress":{"cid":"ca90b935227e429493c5dc706e42cd58","pushUrl":"rtmp://scympush.forcetech.net:1935/lscym/lei","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/ca90b935227e429493c5dc706e42cd58.flv?md5=3b8a8b2890814ad2d21204be6ee11b5b&time=58e6fb2e","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/ca90b935227e429493c5dc706e42cd58.m3u8?md5=7782a23996acad21802dc9e791b2ddb8&time=58e6fb2e"},"cameraStreamAddress":{"cid":"7427c4fe15764217b3804899064f2720","pushUrl":"rtmp://zyph.forcetechcloud.com/lzy/7427c4fe15764217b3804899064f2720","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/7427c4fe15764217b3804899064f2720.flv?md5=b1da86ffbe2ab562b57621f73b3d5397&time=58e6fcca","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/7427c4fe15764217b3804899064f2720.m3u8?md5=c9325658edda0dbcc0c81dc06ab7f8b2&time=58e6fcca"},"chatroomId":"@TGS#35VYPJOET","maxAudience":100,"status":"2","createTime":1489025445000,"organizationName":"a中樱","organizationLogo":"http://up.qqjia.com/z/01/tu4488_33.jpg","h5Url":"http://192.168.4.205:8090/dist/html/LiveShare.html?id=15","del":false},{"id":2,"title":"直播课程-数学高考复习","coverUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1488797902235&di=885430d13d39dabae198c1463163e684&imgtype=0&src=http%3A%2F%2Fimg1.gamedog.cn%2F2013%2F12%2F10%2F124-1312101455380.jpg","cateName":"外语","startTime":1491554144370,"price":80,"lecturer":"田中","coursewareStreamAddress":{"cid":"ca90b935227e429493c5dc706e42cd58","pushUrl":"rtmp://scympush.forcetech.net:1935/lscym/lei","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/ca90b935227e429493c5dc706e42cd58.flv?md5=3b8a8b2890814ad2d21204be6ee11b5b&time=58e6fb2e","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/ca90b935227e429493c5dc706e42cd58.m3u8?md5=7782a23996acad21802dc9e791b2ddb8&time=58e6fb2e"},"cameraStreamAddress":{"cid":"7427c4fe15764217b3804899064f2720","pushUrl":"rtmp://zyph.forcetechcloud.com/lzy/7427c4fe15764217b3804899064f2720","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/7427c4fe15764217b3804899064f2720.flv?md5=b1da86ffbe2ab562b57621f73b3d5397&time=58e6fcca","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/7427c4fe15764217b3804899064f2720.m3u8?md5=c9325658edda0dbcc0c81dc06ab7f8b2&time=58e6fcca"},"chatroomId":"@TGS#35VYPJOET","maxAudience":50,"status":"2","createTime":1489025445000,"organizationName":"a中樱","organizationLogo":"http://up.qqjia.com/z/01/tu4488_33.jpg","h5Url":"http://192.168.4.205:8090/dist/html/LiveShare.html?id=2","del":false},{"id":13,"title":"直播课程-英语口语培训","coverUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1488797902235&di=885430d13d39dabae198c1463163e684&imgtype=0&src=http%3A%2F%2Fimg1.gamedog.cn%2F2013%2F12%2F10%2F124-1312101455380.jpg","cateName":"外语","startTime":1491574970000,"price":200,"lecturer":"田中","coursewareStreamAddress":{"cid":"ca90b935227e429493c5dc706e42cd58","pushUrl":"rtmp://scympush.forcetech.net:1935/lscym/lei","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/ca90b935227e429493c5dc706e42cd58.flv?md5=3b8a8b2890814ad2d21204be6ee11b5b&time=58e6fb2e","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/ca90b935227e429493c5dc706e42cd58.m3u8?md5=7782a23996acad21802dc9e791b2ddb8&time=58e6fb2e"},"cameraStreamAddress":{"cid":"7427c4fe15764217b3804899064f2720","pushUrl":"rtmp://zyph.forcetechcloud.com/lzy/7427c4fe15764217b3804899064f2720","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/7427c4fe15764217b3804899064f2720.flv?md5=b1da86ffbe2ab562b57621f73b3d5397&time=58e6fcca","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/7427c4fe15764217b3804899064f2720.m3u8?md5=c9325658edda0dbcc0c81dc06ab7f8b2&time=58e6fcca"},"chatroomId":"@TGS#35VYPJOET","maxAudience":60,"status":"1","createTime":1489025445000,"organizationName":"a中樱","organizationLogo":"http://up.qqjia.com/z/01/tu4488_33.jpg","h5Url":"http://192.168.4.205:8090/dist/html/LiveShare.html?id=13","del":false}],"offset":0}
     */

    private DataEntity data;

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

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity implements Serializable{
        /**
         * pageSize : 10
         * pageNo : 1
         * totalCount : 7
         * totalPage : 1
         * datas : [{"id":12,"title":"直播-金牌高考数学复习","coverUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1488797902235&di=885430d13d39dabae198c1463163e684&imgtype=0&src=http%3A%2F%2Fimg1.gamedog.cn%2F2013%2F12%2F10%2F124-1312101455380.jpg","cateName":"外语","startTime":1491537600000,"price":80,"lecturer":"李老师","coursewareStreamAddress":{"cid":"7cd03dd6830848e4944078a29a82833c","pushUrl":"rtmp://zyph.forcetechcloud.com/lzy/7cd03dd6830848e4944078a29a82833c","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/7cd03dd6830848e4944078a29a82833c.flv?md5=caf3815648779802751ea1ce15e8f2e1&time=58e75bad","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/7cd03dd6830848e4944078a29a82833c.m3u8?md5=a0d7d00280d950af943c7d906935d613&time=58e75bad"},"cameraStreamAddress":{"cid":"ab9924949aca4a43bee6da210b8f94ea","pushUrl":"rtmp://zyph.forcetechcloud.com/lzy/ab9924949aca4a43bee6da210b8f94ea","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/ab9924949aca4a43bee6da210b8f94ea.flv?md5=66b510ae3f795a4d62f0106e79bb037b&time=58e75bad","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/ab9924949aca4a43bee6da210b8f94ea.m3u8?md5=0a90b8743ca6189fb21b5c5aed2237f1&time=58e75bad"},"chatroomId":"@TGS#354A2ZOEJ","maxAudience":0,"status":"0","createTime":1491557321165,"organizationName":"a中樱","organizationLogo":"http://up.qqjia.com/z/01/tu4488_33.jpg","h5Url":"http://192.168.4.205:8090/dist/html/LiveShare.html?id=12","del":false},{"id":22,"title":"直播课程-测试测试测试测试","coverUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1488797902235&di=885430d13d39dabae198c1463163e684&imgtype=0&src=http%3A%2F%2Fimg1.gamedog.cn%2F2013%2F12%2F10%2F124-1312101455380.jpg","cateName":"外语","startTime":1491537600000,"price":80,"lecturer":"陈虎","coursewareStreamAddress":{"cid":"ca90b935227e429493c5dc706e42cd58","pushUrl":"rtmp://scympush.forcetech.net:1935/lscym/lei","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/ca90b935227e429493c5dc706e42cd58.flv?md5=3b8a8b2890814ad2d21204be6ee11b5b&time=58e6fb2e","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/ca90b935227e429493c5dc706e42cd58.m3u8?md5=7782a23996acad21802dc9e791b2ddb8&time=58e6fb2e"},"cameraStreamAddress":{"cid":"7427c4fe15764217b3804899064f2720","pushUrl":"rtmp://zyph.forcetechcloud.com/lzy/7427c4fe15764217b3804899064f2720","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/7427c4fe15764217b3804899064f2720.flv?md5=b1da86ffbe2ab562b57621f73b3d5397&time=58e6fcca","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/7427c4fe15764217b3804899064f2720.m3u8?md5=c9325658edda0dbcc0c81dc06ab7f8b2&time=58e6fcca"},"chatroomId":"@TGS#35VYPJOET","maxAudience":50,"status":"1","createTime":1490971746000,"organizationName":"a中樱","organizationLogo":"http://up.qqjia.com/z/01/tu4488_33.jpg","h5Url":"http://192.168.4.205:8090/dist/html/LiveShare.html?id=22","del":false},{"id":16,"title":"直播课程-雅思冲刺班","coverUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1488797902237&di=41ea28bcd2d2bda13d7ef2f1ed355dfa&imgtype=0&src=http%3A%2F%2Fimg5.duitang.com%2Fuploads%2Fitem%2F201301%2F16%2F20130116095533_RXXay.jpeg","cateName":"外语","startTime":1491556162247,"price":99.99,"lecturer":"李老师","coursewareStreamAddress":{"cid":"ca90b935227e429493c5dc706e42cd58","pushUrl":"rtmp://scympush.forcetech.net:1935/lscym/lei","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/ca90b935227e429493c5dc706e42cd58.flv?md5=3b8a8b2890814ad2d21204be6ee11b5b&time=58e6fb2e","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/ca90b935227e429493c5dc706e42cd58.m3u8?md5=7782a23996acad21802dc9e791b2ddb8&time=58e6fb2e"},"cameraStreamAddress":{"cid":"7427c4fe15764217b3804899064f2720","pushUrl":"rtmp://zyph.forcetechcloud.com/lzy/7427c4fe15764217b3804899064f2720","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/7427c4fe15764217b3804899064f2720.flv?md5=b1da86ffbe2ab562b57621f73b3d5397&time=58e6fcca","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/7427c4fe15764217b3804899064f2720.m3u8?md5=c9325658edda0dbcc0c81dc06ab7f8b2&time=58e6fcca"},"chatroomId":"@TGS#35VYPJOET","maxAudience":100,"status":"2","createTime":1489025445000,"organizationName":"a中樱","organizationLogo":"http://up.qqjia.com/z/01/tu4488_33.jpg","h5Url":"http://192.168.4.205:8090/dist/html/LiveShare.html?id=16","del":false},{"id":3,"title":"直播课程-英语口语培训","coverUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1488797902235&di=885430d13d39dabae198c1463163e684&imgtype=0&src=http%3A%2F%2Fimg1.gamedog.cn%2F2013%2F12%2F10%2F124-1312101455380.jpg","cateName":"外语","startTime":1491574970000,"price":200,"lecturer":"田中","coursewareStreamAddress":{"cid":"ca90b935227e429493c5dc706e42cd58","pushUrl":"rtmp://scympush.forcetech.net:1935/lscym/lei","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/ca90b935227e429493c5dc706e42cd58.flv?md5=3b8a8b2890814ad2d21204be6ee11b5b&time=58e6fb2e","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/ca90b935227e429493c5dc706e42cd58.m3u8?md5=7782a23996acad21802dc9e791b2ddb8&time=58e6fb2e"},"cameraStreamAddress":{"cid":"7427c4fe15764217b3804899064f2720","pushUrl":"rtmp://zyph.forcetechcloud.com/lzy/7427c4fe15764217b3804899064f2720","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/7427c4fe15764217b3804899064f2720.flv?md5=b1da86ffbe2ab562b57621f73b3d5397&time=58e6fcca","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/7427c4fe15764217b3804899064f2720.m3u8?md5=c9325658edda0dbcc0c81dc06ab7f8b2&time=58e6fcca"},"chatroomId":"@TGS#35VYPJOET","maxAudience":60,"status":"2","createTime":1489025445000,"organizationName":"a中樱","organizationLogo":"http://up.qqjia.com/z/01/tu4488_33.jpg","h5Url":"http://192.168.4.205:8090/dist/html/LiveShare.html?id=3","del":false},{"id":15,"title":"直播课程-雅思冲刺班","coverUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1488797902237&di=41ea28bcd2d2bda13d7ef2f1ed355dfa&imgtype=0&src=http%3A%2F%2Fimg5.duitang.com%2Fuploads%2Fitem%2F201301%2F16%2F20130116095533_RXXay.jpeg","cateName":"外语","startTime":1491579388000,"price":99.99,"lecturer":"李老师","coursewareStreamAddress":{"cid":"ca90b935227e429493c5dc706e42cd58","pushUrl":"rtmp://scympush.forcetech.net:1935/lscym/lei","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/ca90b935227e429493c5dc706e42cd58.flv?md5=3b8a8b2890814ad2d21204be6ee11b5b&time=58e6fb2e","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/ca90b935227e429493c5dc706e42cd58.m3u8?md5=7782a23996acad21802dc9e791b2ddb8&time=58e6fb2e"},"cameraStreamAddress":{"cid":"7427c4fe15764217b3804899064f2720","pushUrl":"rtmp://zyph.forcetechcloud.com/lzy/7427c4fe15764217b3804899064f2720","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/7427c4fe15764217b3804899064f2720.flv?md5=b1da86ffbe2ab562b57621f73b3d5397&time=58e6fcca","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/7427c4fe15764217b3804899064f2720.m3u8?md5=c9325658edda0dbcc0c81dc06ab7f8b2&time=58e6fcca"},"chatroomId":"@TGS#35VYPJOET","maxAudience":100,"status":"2","createTime":1489025445000,"organizationName":"a中樱","organizationLogo":"http://up.qqjia.com/z/01/tu4488_33.jpg","h5Url":"http://192.168.4.205:8090/dist/html/LiveShare.html?id=15","del":false},{"id":2,"title":"直播课程-数学高考复习","coverUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1488797902235&di=885430d13d39dabae198c1463163e684&imgtype=0&src=http%3A%2F%2Fimg1.gamedog.cn%2F2013%2F12%2F10%2F124-1312101455380.jpg","cateName":"外语","startTime":1491554144370,"price":80,"lecturer":"田中","coursewareStreamAddress":{"cid":"ca90b935227e429493c5dc706e42cd58","pushUrl":"rtmp://scympush.forcetech.net:1935/lscym/lei","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/ca90b935227e429493c5dc706e42cd58.flv?md5=3b8a8b2890814ad2d21204be6ee11b5b&time=58e6fb2e","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/ca90b935227e429493c5dc706e42cd58.m3u8?md5=7782a23996acad21802dc9e791b2ddb8&time=58e6fb2e"},"cameraStreamAddress":{"cid":"7427c4fe15764217b3804899064f2720","pushUrl":"rtmp://zyph.forcetechcloud.com/lzy/7427c4fe15764217b3804899064f2720","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/7427c4fe15764217b3804899064f2720.flv?md5=b1da86ffbe2ab562b57621f73b3d5397&time=58e6fcca","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/7427c4fe15764217b3804899064f2720.m3u8?md5=c9325658edda0dbcc0c81dc06ab7f8b2&time=58e6fcca"},"chatroomId":"@TGS#35VYPJOET","maxAudience":50,"status":"2","createTime":1489025445000,"organizationName":"a中樱","organizationLogo":"http://up.qqjia.com/z/01/tu4488_33.jpg","h5Url":"http://192.168.4.205:8090/dist/html/LiveShare.html?id=2","del":false},{"id":13,"title":"直播课程-英语口语培训","coverUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1488797902235&di=885430d13d39dabae198c1463163e684&imgtype=0&src=http%3A%2F%2Fimg1.gamedog.cn%2F2013%2F12%2F10%2F124-1312101455380.jpg","cateName":"外语","startTime":1491574970000,"price":200,"lecturer":"田中","coursewareStreamAddress":{"cid":"ca90b935227e429493c5dc706e42cd58","pushUrl":"rtmp://scympush.forcetech.net:1935/lscym/lei","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/ca90b935227e429493c5dc706e42cd58.flv?md5=3b8a8b2890814ad2d21204be6ee11b5b&time=58e6fb2e","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/ca90b935227e429493c5dc706e42cd58.m3u8?md5=7782a23996acad21802dc9e791b2ddb8&time=58e6fb2e"},"cameraStreamAddress":{"cid":"7427c4fe15764217b3804899064f2720","pushUrl":"rtmp://zyph.forcetechcloud.com/lzy/7427c4fe15764217b3804899064f2720","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/7427c4fe15764217b3804899064f2720.flv?md5=b1da86ffbe2ab562b57621f73b3d5397&time=58e6fcca","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/7427c4fe15764217b3804899064f2720.m3u8?md5=c9325658edda0dbcc0c81dc06ab7f8b2&time=58e6fcca"},"chatroomId":"@TGS#35VYPJOET","maxAudience":60,"status":"1","createTime":1489025445000,"organizationName":"a中樱","organizationLogo":"http://up.qqjia.com/z/01/tu4488_33.jpg","h5Url":"http://192.168.4.205:8090/dist/html/LiveShare.html?id=13","del":false}]
         * offset : 0
         */

        private LiveListEntity liveList;
        /**
         * id : 4
         * title : 推荐了推荐了推荐了
         * coverUrl : http://pic62.nipic.com/file/20150319/12632424_132215178296_2.jpg
         * liveId : 22
         */

        private List<LiveFocusListEntity> liveFocusList;

        public LiveListEntity getLiveList() {
            return liveList;
        }

        public void setLiveList(LiveListEntity liveList) {
            this.liveList = liveList;
        }

        public List<LiveFocusListEntity> getLiveFocusList() {
            return liveFocusList;
        }

        public void setLiveFocusList(List<LiveFocusListEntity> liveFocusList) {
            this.liveFocusList = liveFocusList;
        }

        public static class LiveListEntity implements Serializable{
            private int pageSize;
            private int pageNo;
            private int totalCount;
            private int totalPage;
            private int offset;
            /**
             * id : 12
             * title : 直播-金牌高考数学复习
             * coverUrl : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1488797902235&di=885430d13d39dabae198c1463163e684&imgtype=0&src=http%3A%2F%2Fimg1.gamedog.cn%2F2013%2F12%2F10%2F124-1312101455380.jpg
             * cateName : 外语
             * startTime : 1491537600000
             * price : 80
             * lecturer : 李老师
             * coursewareStreamAddress : {"cid":"7cd03dd6830848e4944078a29a82833c","pushUrl":"rtmp://zyph.forcetechcloud.com/lzy/7cd03dd6830848e4944078a29a82833c","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/7cd03dd6830848e4944078a29a82833c.flv?md5=caf3815648779802751ea1ce15e8f2e1&time=58e75bad","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/7cd03dd6830848e4944078a29a82833c.m3u8?md5=a0d7d00280d950af943c7d906935d613&time=58e75bad"}
             * cameraStreamAddress : {"cid":"ab9924949aca4a43bee6da210b8f94ea","pushUrl":"rtmp://zyph.forcetechcloud.com/lzy/ab9924949aca4a43bee6da210b8f94ea","pullFlvUrl":"http://zypl.forcetechcloud.com:38080/lzy/ab9924949aca4a43bee6da210b8f94ea.flv?md5=66b510ae3f795a4d62f0106e79bb037b&time=58e75bad","pullM3u8Url":"http://zypl.forcetechcloud.com:38080/lzy/ab9924949aca4a43bee6da210b8f94ea.m3u8?md5=0a90b8743ca6189fb21b5c5aed2237f1&time=58e75bad"}
             * chatroomId : @TGS#354A2ZOEJ
             * maxAudience : 0
             * status : 0
             * createTime : 1491557321165
             * organizationName : a中樱
             * organizationLogo : http://up.qqjia.com/z/01/tu4488_33.jpg
             * h5Url : http://192.168.4.205:8090/dist/html/LiveShare.html?id=12
             * del : false
             */

            private List<DatasEntity> datas;

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

            public int getOffset() {
                return offset;
            }

            public void setOffset(int offset) {
                this.offset = offset;
            }

            public List<DatasEntity> getDatas() {
                return datas;
            }

            public void setDatas(List<DatasEntity> datas) {
                this.datas = datas;
            }

            public static class DatasEntity implements Serializable{
                private long id;
                private String title;
                private String coverUrl;
                private String cateName;
                private String startTime;
                private double price;
                private String lecturer;
                /**
                 * cid : 7cd03dd6830848e4944078a29a82833c
                 * pushUrl : rtmp://zyph.forcetechcloud.com/lzy/7cd03dd6830848e4944078a29a82833c
                 * pullFlvUrl : http://zypl.forcetechcloud.com:38080/lzy/7cd03dd6830848e4944078a29a82833c.flv?md5=caf3815648779802751ea1ce15e8f2e1&time=58e75bad
                 * pullM3u8Url : http://zypl.forcetechcloud.com:38080/lzy/7cd03dd6830848e4944078a29a82833c.m3u8?md5=a0d7d00280d950af943c7d906935d613&time=58e75bad
                 */

                private CoursewareStreamAddressEntity coursewareStreamAddress;
                /**
                 * cid : ab9924949aca4a43bee6da210b8f94ea
                 * pushUrl : rtmp://zyph.forcetechcloud.com/lzy/ab9924949aca4a43bee6da210b8f94ea
                 * pullFlvUrl : http://zypl.forcetechcloud.com:38080/lzy/ab9924949aca4a43bee6da210b8f94ea.flv?md5=66b510ae3f795a4d62f0106e79bb037b&time=58e75bad
                 * pullM3u8Url : http://zypl.forcetechcloud.com:38080/lzy/ab9924949aca4a43bee6da210b8f94ea.m3u8?md5=0a90b8743ca6189fb21b5c5aed2237f1&time=58e75bad
                 */

                private CameraStreamAddressEntity cameraStreamAddress;
                private String chatroomId;
                private int maxAudience;
                private String status;
                private String createTime;
                private String organizationName;
                private String organizationLogo;
                private String shareUrl;
                private boolean del;
                private String pageViewNum;
                private String buyNum;
                private int pushPlatformType;
                private boolean pay;

                public String getBuyNum() {
                    return buyNum;
                }

                public void setBuyNum(String buyNum) {
                    this.buyNum = buyNum;
                }

                public String getPageViewNum() {
                    return pageViewNum;
                }

                public void setPageViewNum(String pageViewNum) {
                    this.pageViewNum = pageViewNum;
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

                public String getCateName() {
                    return cateName;
                }

                public void setCateName(String cateName) {
                    this.cateName = cateName;
                }

                public String getStartTime() {
                    return startTime;
                }

                public void setStartTime(String startTime) {
                    this.startTime = startTime;
                }

                public double getPrice() {
                    return price;
                }

                public void setPrice(double price) {
                    this.price = price;
                }

                public String getLecturer() {
                    return lecturer;
                }

                public void setLecturer(String lecturer) {
                    this.lecturer = lecturer;
                }

                public CoursewareStreamAddressEntity getCoursewareStreamAddress() {
                    return coursewareStreamAddress;
                }

                public void setCoursewareStreamAddress(CoursewareStreamAddressEntity coursewareStreamAddress) {
                    this.coursewareStreamAddress = coursewareStreamAddress;
                }

                public CameraStreamAddressEntity getCameraStreamAddress() {
                    return cameraStreamAddress;
                }

                public void setCameraStreamAddress(CameraStreamAddressEntity cameraStreamAddress) {
                    this.cameraStreamAddress = cameraStreamAddress;
                }

                public String getChatroomId() {
                    return chatroomId;
                }

                public void setChatroomId(String chatroomId) {
                    this.chatroomId = chatroomId;
                }

                public int getMaxAudience() {
                    return maxAudience;
                }

                public void setMaxAudience(int maxAudience) {
                    this.maxAudience = maxAudience;
                }

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }

                public String getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(String createTime) {
                    this.createTime = createTime;
                }

                public String getOrganizationName() {
                    return organizationName;
                }

                public void setOrganizationName(String organizationName) {
                    this.organizationName = organizationName;
                }

                public String getOrganizationLogo() {
                    return organizationLogo;
                }

                public void setOrganizationLogo(String organizationLogo) {
                    this.organizationLogo = organizationLogo;
                }

                public String getShareUrl() {
                    return shareUrl;
                }

                public void setShareUrl(String shareUrl) {
                    this.shareUrl = shareUrl;
                }

                public int getPushPlatformType() {
                    return pushPlatformType;
                }

                public void setPushPlatformType(int pushPlatformType) {
                    this.pushPlatformType = pushPlatformType;
                }

                public boolean isPay() {
                    return pay;
                }

                public void setPay(boolean pay) {
                    this.pay = pay;
                }

                public boolean isDel() {
                    return del;
                }

                public void setDel(boolean del) {
                    this.del = del;
                }

            }
        }
    }
}
