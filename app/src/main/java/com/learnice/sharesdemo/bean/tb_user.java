package com.learnice.sharesdemo.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Xuebin He on 2017/5/24.
 * e-mail:learnice.he@gmail.com.
 */

public class tb_user extends BmobObject {
    private String name;
    private String pass;
    private String image;

    public tb_user() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}
