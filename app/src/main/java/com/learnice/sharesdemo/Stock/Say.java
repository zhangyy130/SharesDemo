package com.learnice.sharesdemo.Stock;

/**
 * Created by Xuebin He on 2016/6/20.
 */
public class Say {
    private String userName;
    private String sharesType;
    private String sharesName;
    private String content;
    private String contentTime;

    public Say(String userName, String sharesType, String sharesName, String content, String contentTime) {
        this.userName = userName;
        this.sharesType = sharesType;
        this.sharesName = sharesName;
        this.content = content;
        this.contentTime = contentTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentTime() {
        return contentTime;
    }

    public void setContentTime(String contentTime) {
        this.contentTime = contentTime;
    }

    public String getSharesName() {
        return sharesName;
    }

    public void setSharesName(String sharesName) {
        this.sharesName = sharesName;
    }

    public String getSharesType() {
        return sharesType;
    }

    public void setSharesType(String sharesType) {
        this.sharesType = sharesType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Say{" +
                "content='" + content + '\'' +
                ", userName='" + userName + '\'' +
                ", sharesType='" + sharesType + '\'' +
                ", sharesName='" + sharesName + '\'' +
                ", contentTime='" + contentTime + '\'' +
                '}';
    }
}
