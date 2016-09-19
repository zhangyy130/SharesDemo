package com.learnice.sharesdemo.Stock;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Xuebin He on 2016/6/18.
 */
public class Stock implements Parcelable{
    private String stockType,stockName,stockNum,nowPri,uppic,limit;
    private String traNumber,traAmount,formpri,openpri,markValue,date;
    private String minPri,maxPri;
    public Stock(String stockType, String stockName, String stockNum, String nowPri, String uppic, String limit) {
        this.stockType = stockType;
        this.stockName = stockName;
        this.stockNum = stockNum;
        this.nowPri = nowPri;
        this.uppic = uppic;
        this.limit = limit;
    }

    public String getMaxPri() {
        return maxPri;
    }

    public void setMaxPri(String maxPri) {
        this.maxPri = maxPri;
    }

    public String getMinPri() {
        return minPri;
    }

    public void setMinPri(String minPri) {
        this.minPri = minPri;
    }

    public Stock(String stockType, String stockName, String stockNum, String nowPri, String uppic, String limit, String formpri, String openpri, String traAmount, String traNumber, String markValue, String date, String minPri, String maxPri) {
        this.stockType = stockType;
        this.stockName = stockName;
        this.stockNum = stockNum;
        this.nowPri = nowPri;
        this.uppic = uppic;
        this.limit = limit;
        this.formpri = formpri;
        this.openpri = openpri;
        this.traAmount = traAmount;
        this.traNumber = traNumber;
        this.markValue = markValue;
        this.date = date;
        this.minPri=minPri;
        this.maxPri=maxPri;
    }

    protected Stock(Parcel in) {
        stockType = in.readString();
        stockName = in.readString();
        stockNum = in.readString();
        nowPri = in.readString();
        uppic = in.readString();
        limit = in.readString();
        traNumber = in.readString();
        traAmount = in.readString();
        formpri = in.readString();
        openpri = in.readString();
        markValue = in.readString();
        date = in.readString();
        minPri=in.readString();
        maxPri=in.readString();
    }

    public static final Creator<Stock> CREATOR = new Creator<Stock>() {
        @Override
        public Stock createFromParcel(Parcel in) {
            return new Stock(in);
        }

        @Override
        public Stock[] newArray(int size) {
            return new Stock[size];
        }
    };

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFormpri() {
        return formpri;
    }

    public void setFormpri(String formpri) {
        this.formpri = formpri;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getMarkValue() {
        return markValue;
    }

    public void setMarkValue(String markValue) {
        this.markValue = markValue;
    }

    public String getNowPri() {
        return nowPri;
    }

    public void setNowPri(String nowPri) {
        this.nowPri = nowPri;
    }

    public String getOpenpri() {
        return openpri;
    }

    public void setOpenpri(String openpri) {
        this.openpri = openpri;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getStockNum() {
        return stockNum;
    }

    public void setStockNum(String stockNum) {
        this.stockNum = stockNum;
    }

    public String getStockType() {
        return stockType;
    }

    public void setStockType(String stockType) {
        this.stockType = stockType;
    }

    public String getTraAmount() {
        return traAmount;
    }

    public void setTraAmount(String traAmount) {
        this.traAmount = traAmount;
    }

    public String getTraNumber() {
        return traNumber;
    }

    public void setTraNumber(String traNumber) {
        this.traNumber = traNumber;
    }

    public String getUppic() {
        return uppic;
    }

    public void setUppic(String uppic) {
        this.uppic = uppic;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "date='" + date + '\'' +
                ", stockType='" + stockType + '\'' +
                ", stockName='" + stockName + '\'' +
                ", stockNum='" + stockNum + '\'' +
                ", nowPri='" + nowPri + '\'' +
                ", uppic='" + uppic + '\'' +
                ", limit='" + limit + '\'' +
                ", traNumber='" + traNumber + '\'' +
                ", traAmount='" + traAmount + '\'' +
                ", formpri='" + formpri + '\'' +
                ", openpri='" + openpri + '\'' +
                ", markValue='" + markValue + '\'' +
                ", minPri='" + minPri + '\'' +
                ", maxPri='" + maxPri + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(stockType);
        dest.writeString(stockName);
        dest.writeString(stockNum);
        dest.writeString(nowPri);
        dest.writeString(uppic);
        dest.writeString(limit);
        dest.writeString(traNumber);
        dest.writeString(traAmount);
        dest.writeString(formpri);
        dest.writeString(openpri);
        dest.writeString(markValue);
        dest.writeString(date);
        dest.writeString(minPri);
        dest.writeString(maxPri);
    }
}
