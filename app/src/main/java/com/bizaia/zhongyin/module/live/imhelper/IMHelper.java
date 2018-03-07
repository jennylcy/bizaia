package com.bizaia.zhongyin.module.live.imhelper;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.bizaia.zhongyin.BIZAIAApplication;
import com.bizaia.zhongyin.module.live.action.IMLoginSuccessAction;
import com.bizaia.zhongyin.module.mine.action.ForceOffLine;
import com.bizaia.zhongyin.module.mine.api.ImInfoAPI;
import com.bizaia.zhongyin.module.mine.data.ImInfo;
import com.bizaia.zhongyin.module.mine.iml.IImInfoView;
import com.bizaia.zhongyin.repository.TencentIMconfig;
import com.bizaia.zhongyin.util.GsonUtils;
import com.bizaia.zhongyin.util.RxBus;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMConnListener;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMGroupEventListener;
import com.tencent.imsdk.TIMGroupManager;
import com.tencent.imsdk.TIMGroupTipsElem;
import com.tencent.imsdk.TIMLogLevel;
import com.tencent.imsdk.TIMLogListener;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMMessageListener;
import com.tencent.imsdk.TIMRefreshListener;
import com.tencent.imsdk.TIMSdkConfig;
import com.tencent.imsdk.TIMTextElem;
import com.tencent.imsdk.TIMUserConfig;
import com.tencent.imsdk.TIMUserStatusListener;
import com.tencent.imsdk.TIMValueCallBack;

import java.util.List;

/**
 * Created by zyz on 2017/3/16.
 */

public class IMHelper implements TIMMessageListener, IImInfoView {

    private static final String TAG="IMHelper";
    private TIMConversation sysConversation;
    private TIMConversation  personConversation;
    private TIMManager timManager;
    private static IMHelper imHelper;
    private TIMSdkConfig config;
    private TIMUserConfig userConfig;

    public static final String WHEAT_INDENTIFIER_F = "Wheat_Indentifier_Req_First";//  用户连麦请求
    public static final String WHEAT_INDENTIFIER_S = "Wheat_Indentifier_Req_Second";//  主讲人同意连麦
    public static final String WHEAT_INDENTIFIER_T = "Wheat_Indentifier_Req_Third";//  用户同意连麦
    public static final String WHEAT_INDENTIFIER_C = "Wheat_Indentifier_Req_Cancel";//  用户取消连麦
    public static final String STUDENT_HANG_UP = "Student_Hang_Up_Sig";//  学生挂断
    public static final String TEACHER_HANG_UP = "Teacher_Hang_Up_Sig";//  主讲人挂断
    public  static final String STUDENT_ADD_ROOM="Student_Add_Room_Sig";// 学生加入房间
    public  static  final String STUDENT_EXIT_ROOM = "Student_Exit_Room_Sig"; //学生退出房间
    public static final String  TEACHER_FORBID_WHEAT = "Teacher_Forbid_Wheat_Sig"; //  主讲人禁止连麦
    public  static final String TEACHER_FORBID_TEXTCHAT = "Teacher_Forbid_TextChat_Sig";//主讲人禁止成员发消息
    public static final String TEACHER_ENABLED_WHEAT = "Teacher_Enabled_Wheat_Sig";// 主讲人允许连麦
    public static final String TEACHER_ENABLED_TEXTCHAT = "Teacher_Enabled_TextChat_Sig";//  主讲人允许成员发消息
    public static  final String TEACHER_ENABLED_WHEATCHAT = "Teacher_Enabled_Wheat_Chat_Sig";// 主讲人允许连麦和发消息
    public static final String TEACHER_EXIT_ROOM = "Teacher_Exit_Room_Sig";//主讲人退出房间

    private ImInfoAPI imInfoAPI;

    public static  IMHelper getInstance(){
        if(imHelper==null)
        imHelper = new IMHelper();
        return imHelper;
    }


    public void init(Context context){
        initIM(context);
    }


    private void initIM(final Context context){
        Log.e(TAG, "initIM: -----init" );
        config = new TIMSdkConfig(TencentIMconfig.SDK_APPID)
                .enableCrashReport(false)
                .enableLogPrint(true)
                .setAccoutType(TencentIMconfig.ACCOUNT_TYPE+"")
                .setLogLevel(TIMLogLevel.DEBUG)
                .setLogListener(new TIMLogListener() {
                    @Override
                    public void log(int i, String s, String s1) {
//                        Log.e(TAG, "log: ------------------->"+i+"----->"+s+"---->"+s1 );
                    }
                })
                .setLogPath(Environment.getExternalStorageDirectory().getPath() + "/justfortest/");

        timManager =  TIMManager.getInstance();
        timManager.init(context,config);
        timManager.addMessageListener(this);
        imInfoAPI = new ImInfoAPI(this);
        userConfig = new TIMUserConfig()
                //设置群组资料拉取字段
//                .setGroupSettings(initGroupSettings())
                //设置资料关系链拉取字段
//                .setFriendshipSettings(initFriendshipSettings())
                //设置用户状态变更事件监听器
                .setUserStatusListener(new TIMUserStatusListener() {
                    @Override
                    public void onForceOffline() {
                        //被其他终端踢下线
                        Log.e(TAG, "onForceOffline: -------->onForceOffline" );
                        RxBus.getInstance().post(new ForceOffLine());
                    }

                    @Override
                    public void onUserSigExpired() {
                        //用户签名过期了，需要刷新userSig重新登录SDK
                        Log.e(TAG, "onForceOffline: -------->onForceOffline" );

                    }
                })
                //设置连接状态事件监听器
                .setConnectionListener(new TIMConnListener() {
                    @Override
                    public void onConnected() {
                        Log.e(TAG, "onConnected: -------->onConnected" );
                    }

                    @Override
                    public void onDisconnected(int code, String desc) {
                        Log.e(TAG, "onDisconnected: -------->onDisconnected" );
                    }

                    @Override
                    public void onWifiNeedAuth(String name) {
                        Log.e(TAG, "onWifiNeedAuth: -------->"+name );
                    }
                })
                //设置群组事件监听器
                .setGroupEventListener(new TIMGroupEventListener() {
                    @Override
                    public void onGroupTipsEvent(TIMGroupTipsElem elem) {
                        Log.e(TAG, "onGroupTipsEvent: ----type---->"+elem.getTipsType() );
                    }
                })
                //设置会话刷新监听器
                .setRefreshListener(new TIMRefreshListener() {
                    @Override
                    public void onRefresh() {

                    }

                    @Override
                    public void onRefreshConversation(List<TIMConversation> conversations) {

                    }
                });

//        timManager.setConnectionListener(new TIMConnListener() {//连接监听器
//            @Override
//            public void onConnected() {//连接建立
//                Log.e("onConnected","---------------->");
//            }
//
//            @Override
//            public void onDisconnected(int code, String desc) {//连接断开
//                //接口返回了错误码code和错误描述desc，可用于定位连接断开原因
//                //错误码code含义请参见错误码表
//                Log.e("disconnect","---------------->"+code+"----"+desc);
//            }
//
//            @Override
//            public void onWifiNeedAuth(String s) {
//                Log.e("WifiNeedAuth","---------------->"+s);
//            }
//        });
//
//        timManager.setLogListener(new TIMLogListener() {
//            @Override
//            public void log(int level, String tag, String msg) {
//                //可以通过此回调将sdk的log输出到自己的日志系统中
//            }
//        });
        imInfoAPI.getImInfo();
        timManager.setUserConfig(userConfig);
    }



    //  im登录
    private void imLogin(String userSig,String userName){
        Log.e("onConnected","---------------->"+userSig+"------"+userName);
        if(userName!=null&&!userName.equals(timManager.getLoginUser())) {
            TIMManager.getInstance().login(userName, userSig, new TIMCallBack() {
                @Override
                public void onError(int code, String desc) {
                    //错误码code和错误描述desc，可用于定位请求失败原因
                    //错误码code列表请参见错误码表
                    Log.e("imloginError","-------------->"+code+"-------------"+desc);
                }

                @Override
                public void onSuccess() {
                         Log.e("imloginSuccess","--------------> success");
                         RxBus.getInstance().post(new IMLoginSuccessAction("success"));
                }
            });

//            TIMUser user = new TIMUser();
//            user.setAccountType(AlipayConfig.ACCOUNT_TYPE + "");
//            user.setAppIdAt3rd(AlipayConfig.SDK_APPID + "");
//            user.setIdentifier(userName);
//            timManager.login(
//                    AlipayConfig.SDK_APPID,                   //sdkAppId，由腾讯分配
//                    user,
//                    userSig,                    //用户帐号签名，由私钥加密获得，具体请参考文档
//                    new TIMCallBack() {//回调接口
//                        @Override
//                        public void onSuccess() {//登录成功
//
//                            Log.e("imlogin","--------------> success");
//
//                            RxBus.getInstance().post(new IMLoginSuccessAction("success"));
//                        }
//
//                        @Override
//                        public void onError(int code, String desc) {//登录失败
//                            Log.e("imError","-------------->"+code+"-------------"+desc);
//                            //错误码code和错误描述desc，可用于定位请求失败原因
//                            //错误码code含义请参见错误码表
//
//                        }
//                    });
        }
    }

    public void imLoginout(){
        timManager.logout(new TIMCallBack() {
            @Override
            public void onError(int code, String desc) {

                //错误码code和错误描述desc，可用于定位请求失败原因
                //错误码code列表请参见错误码表
                Log.e("imlogoutError","-------------->"+code+"-------------"+desc);
            }

            @Override
            public void onSuccess() {
                //登出成功
                Log.e("imlogoutSuccess","-------------->");
            }
        });

    }

    public void initPersonSend(String person){
//        if(personConversation==null)
            personConversation = timManager.getConversation(
                TIMConversationType.C2C,      //会话类型：
                person);
    }



    public void sendPersonMsg(TIMMessage msg,TIMValueCallBack callBack){
        if(personConversation!=null)
        personConversation.sendMessage(msg, callBack);
    }


    public void initSysSend(String homeId){
        if(homeId==null)
            return;
//        if(sysConversation==null)
        sysConversation = timManager.getConversation(
                TIMConversationType.Group,      //会话类型：群组
                homeId);
    }



    public void sendSysMsg(TIMMessage msg,TIMValueCallBack timCallBack ){
        if(sysConversation!=null)
        sysConversation.sendMessage(msg, timCallBack);
    }


    public void creatGroup(String name,String id){
//        TIMGroupManager.getInstance().createGroup(
//                "Private",          //群组类型: 目前仅支持私有群
//                null,               //待加入群组的用户列表
//                name,
//                id,//群组名称
//                new TIMValueCallBack<String>() {
//                    @Override
//                    public void onError(int i, String s) {
//                        //创建失败
//                    }
//
//                    @Override
//                    public void onSuccess(String s) {
//                        //创建成功
//                    }
//                });
    }


    public  void joinGroup(String groupId, TIMCallBack timCallBack ){
        TIMGroupManager.getInstance().applyJoinGroup(groupId, "",timCallBack);
    }

    public  void initPerson(String lectureId){
        initPersonSend(lectureId);
    }

    public void quitGroup(String groupId, TIMCallBack timCallBack ){
        TIMGroupManager.getInstance().quitGroup(groupId,timCallBack
        );
    }

    //  学生加入房间消息
    public void sendAddSuccess(TIMValueCallBack timValueCallBack){
        TIMMessage timMessage = new TIMMessage();
        TIMTextElem elem = new TIMTextElem();
        elem.setText(IMHelper.STUDENT_ADD_ROOM);
        timMessage.addElement(elem);
        IMHelper.getInstance().sendPersonMsg(timMessage,timValueCallBack);
    }
    //  用户请求连麦
    public void requestIndentifier(String path,String name,String id,TIMValueCallBack timValueCallBack){
        TIMMessage timMessage = new TIMMessage();
        TIMTextElem elem = new TIMTextElem();
        String content = GsonUtils.transformMsg(path,IMHelper.WHEAT_INDENTIFIER_F,name,id);
        elem.setText(content);
        Log.e(TAG, "requestIndentifier: -------->"+content );
        timMessage.addElement(elem);
        IMHelper.getInstance().sendPersonMsg(timMessage,timValueCallBack);
    }
    //  老师同意连麦请求
    public void agreeIndentifier(TIMValueCallBack timValueCallBack){
        TIMMessage timMessage = new TIMMessage();
        TIMTextElem elem = new TIMTextElem();
        elem.setText(IMHelper.WHEAT_INDENTIFIER_S);
        timMessage.addElement(elem);
        IMHelper.getInstance().sendPersonMsg(timMessage,timValueCallBack);
    }
    //  学生同意连麦请求
    public void stuAgreeIndentifier(TIMValueCallBack timValueCallBack){
        TIMMessage timMessage = new TIMMessage();
        TIMTextElem elem = new TIMTextElem();
        elem.setText(IMHelper.WHEAT_INDENTIFIER_T);
        timMessage.addElement(elem);
        IMHelper.getInstance().sendPersonMsg(timMessage, timValueCallBack);
    }
    //  学生取消连麦请求
    public void stuDisAgreeIndentifier(String path,String name,String id,TIMValueCallBack timValueCallBack){
        TIMMessage timMessage = new TIMMessage();
        TIMTextElem elem = new TIMTextElem();
        String content = GsonUtils.transformMsg(path,IMHelper.WHEAT_INDENTIFIER_C,name,id);
        elem.setText(content);
        Log.e(TAG, "stuDisAgreeIndentifier: -------->"+content );
        timMessage.addElement(elem);
        IMHelper.getInstance().sendPersonMsg(timMessage,timValueCallBack);
    }
    //  学生挂断
    public void stuHangUpIndentifier(String path,String name,String id,TIMValueCallBack timValueCallBack){
        TIMMessage timMessage = new TIMMessage();
        TIMTextElem elem = new TIMTextElem();
        String content = GsonUtils.transformMsg(path,IMHelper.STUDENT_HANG_UP,name,id);
        elem.setText(content);
        Log.e(TAG, "stuHangUpIndentifier: -------->"+content );
        timMessage.addElement(elem);
        IMHelper.getInstance().sendPersonMsg(timMessage,timValueCallBack);
    }
    //  教师挂断
    public void hostHangUpIndentifier(TIMValueCallBack timValueCallBack){
        TIMMessage timMessage = new TIMMessage();
        TIMTextElem elem = new TIMTextElem();
        elem.setText(IMHelper.TEACHER_HANG_UP);
        timMessage.addElement(elem);
        IMHelper.getInstance().sendPersonMsg(timMessage, timValueCallBack);
    }
    //  学生退出房间
    public void stuExitIndentifier(TIMValueCallBack timValueCallBack){
        TIMMessage timMessage = new TIMMessage();
        TIMTextElem elem = new TIMTextElem();
        elem.setText(IMHelper.STUDENT_EXIT_ROOM);
        timMessage.addElement(elem);
        IMHelper.getInstance().sendPersonMsg(timMessage, timValueCallBack);
    }
    //  教师退出房间
    public void hostExitIndentifier(TIMValueCallBack timValueCallBack,String content){
        TIMMessage timMessage = new TIMMessage();
        TIMTextElem elem = new TIMTextElem();
        elem.setText(content);
        timMessage.addElement(elem);
        IMHelper.getInstance().sendSysMsg(timMessage, timValueCallBack);
    }
    //  主讲人禁止连麦
    public void hostForbidIndentifier(TIMValueCallBack timValueCallBack){
        TIMMessage timMessage = new TIMMessage();
        TIMTextElem elem = new TIMTextElem();
        elem.setText(IMHelper.TEACHER_FORBID_WHEAT);
        timMessage.addElement(elem);
        IMHelper.getInstance().sendSysMsg(timMessage, timValueCallBack);
    }
    //  主讲人禁止发送消息
    public void hostForbidText(TIMValueCallBack timValueCallBack){
        TIMMessage timMessage = new TIMMessage();
        TIMTextElem elem = new TIMTextElem();
        elem.setText(IMHelper.TEACHER_FORBID_TEXTCHAT);
        timMessage.addElement(elem);
        IMHelper.getInstance().sendSysMsg(timMessage, timValueCallBack);
    }
    //  主讲人允许申请连麦
    public void hostAgreeIndentifier(TIMValueCallBack timValueCallBack){
        TIMMessage timMessage = new TIMMessage();
        TIMTextElem elem = new TIMTextElem();
        elem.setText(IMHelper.TEACHER_ENABLED_WHEAT);
        timMessage.addElement(elem);
        IMHelper.getInstance().sendSysMsg(timMessage, timValueCallBack);
    }
    //  主讲人允许发消息
    public void hostAgreeText(TIMValueCallBack timValueCallBack){
        TIMMessage timMessage = new TIMMessage();
        TIMTextElem elem = new TIMTextElem();
        elem.setText(IMHelper.TEACHER_ENABLED_TEXTCHAT);
        timMessage.addElement(elem);
        IMHelper.getInstance().sendSysMsg(timMessage, timValueCallBack);
    }
    //  主讲人允许发消息
    public void hostAgreeAll(TIMValueCallBack timValueCallBack){
        TIMMessage timMessage = new TIMMessage();
        TIMTextElem elem = new TIMTextElem();
        elem.setText(IMHelper.TEACHER_ENABLED_WHEATCHAT);
        timMessage.addElement(elem);
        IMHelper.getInstance().sendSysMsg(timMessage, timValueCallBack);
    }
    @Override
    public boolean onNewMessages(List<TIMMessage> list) {
        for(int i=0;i<list.size();i++)
        RxBus.getInstance().post(list.get(i));
        return false;
    }

    @Override
    public void showImInfo(ImInfo data) {
        BIZAIAApplication.getInstance().setUserIM(data);

       imLogin(BIZAIAApplication.getInstance().getMemberSig(),
                BIZAIAApplication.getInstance().getMemberAccount());
    }

    @Override
    public void showImInfoError() {

    }
}
