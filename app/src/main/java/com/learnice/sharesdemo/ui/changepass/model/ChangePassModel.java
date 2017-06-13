package com.learnice.sharesdemo.ui.changepass.model;

import com.learnice.base_library.utils.MD5Secret;
import com.learnice.sharesdemo.bean.tb_user;
import com.learnice.sharesdemo.fi.IDO;
import com.learnice.sharesdemo.ui.changepass.contract.ChangePassContract;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Xuebin He on 2017/5/30.
 * e-mail:learnice.he@gmail.com.
 */

public class ChangePassModel implements ChangePassContract.Model {

    public static final String OK = "密码更改成功";
    public static final String MISTAKE = "密码输入错误";
    public static final String ERROR = "更改失败";

    @Override
    public void changePassWord(String userName, String s, final String s1, final IDO<String> ido) {
        BmobQuery<tb_user> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("name", userName);
        bmobQuery.addWhereEqualTo("pass", MD5Secret.getMD5String(s));
        bmobQuery.findObjects(new FindListener<tb_user>() {
            @Override
            public void done(List<tb_user> list, BmobException e) {
                if (e == null) {
                    tb_user user = new tb_user();
                    user.setPass(MD5Secret.getMD5String(s1));
                    user.update(list.get(0).getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                ido.done(OK);
                            } else {
                                ido.done(ERROR);
                            }
                        }
                    });

                } else {
                    ido.done(MISTAKE);
                }
            }
        });
    }
}
