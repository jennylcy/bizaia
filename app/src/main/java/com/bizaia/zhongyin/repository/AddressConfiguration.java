package com.bizaia.zhongyin.repository;

/**
 * Created by yan on 2016/12/12.
 */

public class AddressConfiguration {

    public static final String MAIN_PATH = "https://api.bizaia.com/api/";

//    public static final String MAIN_PATH = "http://192.168.50.113:8080/api/";
//    public static final String MAIN_PATH = "http://192.168.4.205:8090/";
//    public static final String MAIN_PATH = "http://192.168.50.113:8080/";
//    public static final String MAIN_PATH = "http://192.168.4.211:8080/bizaia-api/";

    //discovery
    public static final String URL_NEWS_LIST = "v1/api/lectureList";
    public static final String URL_RECOMMEND_NEWS_LIST = "v1/api/recommendNewsList";
    public static final String URL_GET_ASSOCIATION_LIST = "v1/api/findRecommendList";
    public static final String URL_GET_RECRUIT_LIST = "v1/api/getRecruitList";
    public static final String URL_GET_LECTURE_LIST = "v1/api/lectureList";
    public static final String URL_GET_LECTURE_DETAIL = "v1/api/lectureDetail";
    public static final String URL_GET_ORDER_NUM = "v1/api/getOrderNum";
    public static final String URL_GET_FIND_CATE_LIST = "v1/api/getFindCateList";
    public static final String URL_GET_FINDS = "v1/api/getFindEs";
    public static final String URL_WHETHER_TO_BUY = "v1/api/whetherToBuy";
    public static final String URL_GET_CHAPTER_LIST = "v1/api/chapterList";
    public static final String URL_GET_CHAPTER_COMMENT_LIST= "v1/api/chapterCommentList";
    public static final String URL_CHAPTER_COMMENT= "v1/api/chapterComment";
    //video
    public static final String URL_VIDEO_LIST = "v1/api/vodList";
    public static final String URL_RECOMMEND_VIDEO_LIST = "v1/api/recommendVodList";
    public static final String URL_VOD_DETAILS = "v1/api/vodDetails";
    //monthly
    public static final String URL_MONTHLY_NEWEST = "v1/api/monthlyNewest";
    public static final String URL_MONTHLY_PRICE_LIST = "v1/api/monthlyPriceList";
    public static final String URL_MONTHLY_LIST_Data = "v1/api/monthlyList";
    //setting
    public static final String URL_PUSH_SWITCH = "v1/api/notifi";
    public static final String URL_ADD_PROPOSAL = "v1/api/addProposal";
    public static final String URL_GET_AREA_LIST = "v1/api/areaList";
    //pay
    public static final String URL_BALANCE_PAY = "v1/api/balancePay";
    public static final String URL_WEIXIN_GET_ORDER = "v1/api/getOrderNum";

    public static final String URL_GET_CATE_LIST = "v1/api/getCateList";
    public static final String URL_GET_VODES = "v1/api/getVodEs";
    public static final String URL_GET_LIVEES = "v1/api/getLiveEs";
    public static final String URL_GET_MONTHLYES = "v1/api/getMonthlyEs";

    public static final String LOGIN = "v1/api/login";

    public static final String OTHER_LOGIN = "v1/api/tripartiteLogin";
    public static final String LOG_OUT = "v1/api/logout";

    public static final String REGISTER = "v1/api/registered";

    public static final String LIVE_RECOM = "v1/api/liveRecommendList";

    public static final String LIVE_NEW = "v1/api/liveList";

    public static final String LIVE_DETAIL = "v1/api/liveDetail";

    public static final String COMPANY_HOST = "v1/api/getOrgInfo";

    public static final String CHANGE_PHONE = "v1/api/modifyMobile";

    public static final String CHANGE_NICK_NAME = "v1/api/modifyNickname";

    public static final String GET_MAIL_CODE = "v1/api/sendEmailVerificationCode";

    public static final String CHANGE_MAIL = "v1/api/modifyEmail";

    public static final String COMPANY_IS_ATTENTION = "v1/api/isAttention";

    public static final String COMPANY_ADD_ATTENTION = "v1/api/attention";

    public static final String COMPANY_DEL_ATTENTION = "v1/api/attention";

    public static final String COMPANY_LECTURERES = "v1/api/lecturerList";

    public static final String ADD_COLLECTION = "v1/api/favorites";

    public static final String IS_COLLECTION = "v1/api/isFavorites";

    public static final String DEL_COLLECTION = "v1/api/favorites";

    public static final String NEWS_DETAILS = "v1/api/newsDetails";

    public static final String BILL_LIST = "v1/api/getTradeRecordInfo";

    public static final String BUY_NEWS_LIST = "v1/api/getLectureOrderList";

    public static final String BUY_LIVE_LIST = "v1/api/getLiveOrderList";

    public static final String BUY_VIDEO_LIST = "v1/api/getVodOrderList";

    public static final String BUY_MONTHLY_LIST = "v1/api/getMonthlyOrderList";

    public static final String USER_ATTENTION = "v1/api/getAttentionList";

    public static final String COLLECTION_NEWS = "v1/api/getLectureFavoriteList";

    public static final String COLLECTION_VOD = "v1/api/getVodFavoriteList";

    public static final String COLLECTION_LECTURE="v1/api/getLectureFavoriteList";

    public static final String COLLECTION_ASSOCIATION= "v1/api/getAssociationFavoriteList";

    public static final String COLLECTION_RECRUIT = "v1/api/getRecruitFavoriteList";

    public static final String IM_INFO = "v1/api/getMemberSig";

    public static final String MY_LIVE_LIST="v1/api/getMyDayLiveList";

    public static final String LIVE_STATE="v1/api/getLiveState";

    public static final String LIVE_START_STREAM = "v1/api/startLive";

    public static final String LIVE_END_STREAM = "v1/api/endLive";

    public static final String USER_UPDATE_ICON ="v1/api/avatarUpload";

    public static final String UPDATE_PHONE="v1/api/modifyMobile";

    public static final String UPDATE_EMAIL="v1/api/modifyEmail";

    public static final String UPDATE_PWD="v1/api/modifyPwd";

    public static final String LIVE_THUMBUP="v1/api/liveThumbUp";

    public static final String LIVE_THUMBDOWN="v1/api/liveThumbDown";

    public static final String LIVE_IS_THUMB="v1/api/checkThumb";

    public static final String SEARCH="v1/api/search";

    public static final String SUB_DETAIL="v1/api/getSubscribeDetail";

    public static final String LIVE_NOTIF="v1/api/liveNotifi";

    public static final String LECTURE_DETAIL="v1/api/lectureDetail";

    public static final String URL_USER_MSG = "v1/api/msg/getList";

    public static final String URL_DEL_USER_MSG="v1/api/msg/del";

    public static final String URL_MEMBER_INFO="v1/api/getMemberInfo";

    public static final String URL_FIND_PHONE="v1/api/resetPwd";

    public static final String URL_CHECK_CODE="v1/api/emailCodeCheck";

    public static final String URL_CHECK_STATE_BUY="v1/api/checkWhetherToBuy";

    public static final String URL_EXCHANGE_RATE="v1/api/getExchangeRateList";

    public static final String URL_WECHAT_GPP="v1/payment/wechatGpp";

    public static final String URL_MSG_UNREAD="v1/api/msg/getUnreadCount";

    public static final String URL_MSG_READ="v1/api/msg/setRead";

    public static final String URL_DETAIL_CHAPER="v1/api/chapterDetail";

    public static final String URL_END_LIVE="v1/api/endLive";
    public static final String URL_UPDATE="v1/api/version";

    public static final String URL_MONTHLY_DETAIL="v1/api/monthlyDetail";

    public static final String URL_PLAY_STATE_SAVE="v1/api/play/add";

    public static final String URL_PLAY_STATE_GET="v1/api/play/get";
}
