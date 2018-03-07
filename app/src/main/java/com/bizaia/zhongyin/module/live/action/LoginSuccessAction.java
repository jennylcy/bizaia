package com.bizaia.zhongyin.module.live.action;

/**
 * Created by yan on 2017/4/21.
 */

public class LoginSuccessAction {
    boolean isTeacher;
    public boolean isLogIn=true;

    public LoginSuccessAction(boolean isTeacher) {
        this.isTeacher = isTeacher;
    }

    public boolean isTeacher() {
        return isTeacher;
    }

    public LoginSuccessAction setLogIn(boolean isLogIn) {
        this.isLogIn = isLogIn;
        return this;
    }
}
