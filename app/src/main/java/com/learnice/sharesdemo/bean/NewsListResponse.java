package com.learnice.sharesdemo.bean;

import java.util.List;

/**
 * Created by Xuebin He on 2017/5/16.
 * e-mail:learnice.he@gmail.com.
 */

public class NewsListResponse<T> {

    /**
     * reason : 成功的返回
     * result : {"stat":"1","data":[]}
     * error_code : 0
     */

    private String reason;
    private ResultBean<T> result;
    private int error_code;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean<T> getResult() {
        return result;
    }

    public void setResult(ResultBean<T> result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static class ResultBean<T> {
        /**
         * stat : 1
         * data : []
         */

        private String stat;
        private List<T> data;

        public String getStat() {
            return stat;
        }

        public void setStat(String stat) {
            this.stat = stat;
        }

        public List<T> getData() {
            return data;
        }

        public void setData(List<T> data) {
            this.data = data;
        }
    }
}
