package com.learnice.sharesdemo.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Xuebin He on 2017/5/24.
 * e-mail:learnice.he@gmail.com.
 */

public class tb_shares extends BmobObject {
    private String userName;
    private String sharesType;
    private String sharesNumber;
    private String sharesName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSharesType() {
        return sharesType;
    }

    public void setSharesType(String sharesType) {
        this.sharesType = sharesType;
    }

    public String getSharesNumber() {
        return sharesNumber;
    }

    public void setSharesNumber(String sharesNumber) {
        this.sharesNumber = sharesNumber;
    }

    public String getSharesName() {
        return sharesName;
    }

    public void setSharesName(String sharesName) {
        this.sharesName = sharesName;
    }
}
