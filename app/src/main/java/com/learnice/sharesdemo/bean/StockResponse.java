package com.learnice.sharesdemo.bean;

import java.util.List;

/**
 * Created by Xuebin He on 2017/5/28.
 * e-mail:learnice.he@gmail.com.
 */

public class StockResponse<T> {

    /**
     * resultcode : 200
     * reason : SUCCESSED!
     * result : [{"data":{},"dapandata":{},"gopicture":{}}]
     * error_code : 0
     */

    private String resultcode;
    private String reason;
    private int error_code;
    private List<ResultBean<T>> result;

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public List<ResultBean<T>> getResult() {
        return result;
    }

    public void setResult(List<ResultBean<T>> result) {
        this.result = result;
    }

    public static class ResultBean<T> {
        /**
         * data : {}
         * dapandata : {}
         * gopicture : {}
         */

        private T data;
        private DapandataBean dapandata;
        private GopictureBean gopicture;

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public DapandataBean getDapandata() {
            return dapandata;
        }

        public void setDapandata(DapandataBean dapandata) {
            this.dapandata = dapandata;
        }

        public GopictureBean getGopicture() {
            return gopicture;
        }

        public void setGopicture(GopictureBean gopicture) {
            this.gopicture = gopicture;
        }

        public static class DapandataBean {
        }

        public static class GopictureBean {
        }
    }
}
