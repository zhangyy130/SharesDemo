package com.learnice.sharesdemo.ui.register.model;

import com.learnice.sharesdemo.fi.Idone;
import com.learnice.sharesdemo.bean.tb_user;
import com.learnice.sharesdemo.ui.register.contract.RegisterContract;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Xuebin He on 2017/5/24.
 * e-mail:learnice.he@gmail.com.
 */

public class RegisterModel implements RegisterContract.Model {

    public static final String OK = "注册成功";
    public static final String USER_EXITS = "用户名已存在";
    public static final String OCCUR_EXCEPTION = "发生异常";

    @Override
    public void toRegister(String name, String pass1, final Idone idone) {
        tb_user user = new tb_user();
        user.setName(name);
        user.setPass(pass1);
        user.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    idone.done(OK);
                } else {
                    if (e.getErrorCode() == 401) {
                        idone.done(USER_EXITS);
                    } else {
                        idone.done(OCCUR_EXCEPTION);
                    }
                }
            }
        });
    }
}
