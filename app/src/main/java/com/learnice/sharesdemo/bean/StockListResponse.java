package com.learnice.sharesdemo.bean;

import java.util.List;

/**
 * Created by Xuebin He on 2017/5/24.
 * e-mail:learnice.he@gmail.com.
 */

public class StockListResponse<T> {

    /**
     * error_code : 0
     * reason : SUCCESSED!
     * result : {"totalCount":"1250","page":"1","num":"20","data":[]}
     */

    private int error_code;
    private String reason;
    private ResultBean<T> result;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean<T> {
        /**
         * totalCount : 1250
         * page : 1
         * num : 20
         * data : []
         */

        private String totalCount;
        private String page;
        private String num;
        private List<T> data;

        public String getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(String totalCount) {
            this.totalCount = totalCount;
        }

        public String getPage() {
            return page;
        }

        public void setPage(String page) {
            this.page = page;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public List<?> getData() {
            return data;
        }

        public void setData(List<T> data) {
            this.data = data;
        }
    }
}
