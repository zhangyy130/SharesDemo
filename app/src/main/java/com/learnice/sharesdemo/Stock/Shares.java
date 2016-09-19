package com.learnice.sharesdemo.Stock;

/**
 * Created by Xuebin He on 2016/6/19.
 */
public class Shares {
    private int id;
    private String sharesType;
    private String sharesName;

    public Shares(int id, String sharesType, String sharesName) {
        this.id = id;
        this.sharesType = sharesType;
        this.sharesName = sharesName;
    }

    public Shares(String sharesType, String sharesName) {
        this.sharesType = sharesType;
        this.sharesName = sharesName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Shares{" +
                "id=" + id +
                ", sharesType='" + sharesType + '\'' +
                ", sharesName='" + sharesName + '\'' +
                '}';
    }
}
