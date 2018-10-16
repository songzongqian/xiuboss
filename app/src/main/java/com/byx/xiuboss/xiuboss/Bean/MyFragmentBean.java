package com.byx.xiuboss.xiuboss.Bean;

/**
 * Created by wangwenjie001 on 2018/10/3.
 */

public class MyFragmentBean {

    /**
     * code : 2000
     * message : 请求成功
     * data : {"username":"王文杰","mobile":"18301194611","role":"店员","title":"醉清风","logo":"https://img.ourdaidai.com/images/2/2018/05/oz0agabSvwY4zb00Zv5u5v0MtUk5a6.jpg","amount":"115950.86","broadcast":"0"}
     */

    private int code;
    private String message;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * username : 王文杰
         * mobile : 18301194611
         * role : 店员
         * title : 醉清风
         * logo : https://img.ourdaidai.com/images/2/2018/05/oz0agabSvwY4zb00Zv5u5v0MtUk5a6.jpg
         * amount : 115950.86
         * broadcast : 0
         */

        private String username;
        private String mobile;
        private String role;
        private String title;
        private String logo;
        private String amount;
        private String broadcast;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getBroadcast() {
            return broadcast;
        }

        public void setBroadcast(String broadcast) {
            this.broadcast = broadcast;
        }
    }
}
