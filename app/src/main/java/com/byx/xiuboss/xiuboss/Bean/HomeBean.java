package com.byx.xiuboss.xiuboss.Bean;

import java.io.Serializable;

/**
 * Created by wangwenjie001 on 2018/10/8.
 */

public class HomeBean {


    /**
     * code : 2000
     * message : 请求成功
     * data : {"fee":{"total_fee":"5,746.40","customer":"17"},"todaySum":55,"todayFee":"76,295.5","yesterSum":1,"yesterFee":"100","currentGrade":{"f2":"100000","f3":"0.0015","f4":"150","id":"21","fee":"100,000"},"signtime":"1539150181","nextGrade":{"f2":"150000","f3":"0.00153","f4":"229.5","id":"22","fee":"150,000"},"amount":{"amount":"143131.52","signtime":"1539150181","amountFee":"143,131.52"},"monthMoney":5498,"monthFee":"5,498","nextDifference":"6,868.48"}
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
         * fee : {"total_fee":"5,746.40","customer":"17"}
         * todaySum : 55
         * todayFee : 76,295.5
         * yesterSum : 1
         * yesterFee : 100
         * currentGrade : {"f2":"100000","f3":"0.0015","f4":"150","id":"21","fee":"100,000"}
         * signtime : 1539150181
         * nextGrade : {"f2":"150000","f3":"0.00153","f4":"229.5","id":"22","fee":"150,000"}
         * amount : {"amount":"143131.52","signtime":"1539150181","amountFee":"143,131.52"}
         * monthMoney : 5498
         * monthFee : 5,498
         * nextDifference : 6,868.48
         */

        private FeeBean fee;
        private int todaySum;
        private String todayFee;
        private int yesterSum;
        private String yesterFee;
        private CurrentGradeBean currentGrade;
        private String signtime;
        private NextGradeBean nextGrade;
        private AmountBean amount;
        private int monthMoney;
        private String monthFee;
        private String nextDifference;

        public FeeBean getFee() {
            return fee;
        }

        public void setFee(FeeBean fee) {
            this.fee = fee;
        }

        public int getTodaySum() {
            return todaySum;
        }

        public void setTodaySum(int todaySum) {
            this.todaySum = todaySum;
        }

        public String getTodayFee() {
            return todayFee;
        }

        public void setTodayFee(String todayFee) {
            this.todayFee = todayFee;
        }

        public int getYesterSum() {
            return yesterSum;
        }

        public void setYesterSum(int yesterSum) {
            this.yesterSum = yesterSum;
        }

        public String getYesterFee() {
            return yesterFee;
        }

        public void setYesterFee(String yesterFee) {
            this.yesterFee = yesterFee;
        }

        public CurrentGradeBean getCurrentGrade() {
            return currentGrade;
        }

        public void setCurrentGrade(CurrentGradeBean currentGrade) {
            this.currentGrade = currentGrade;
        }

        public String getSigntime() {
            return signtime;
        }

        public void setSigntime(String signtime) {
            this.signtime = signtime;
        }

        public NextGradeBean getNextGrade() {
            return nextGrade;
        }

        public void setNextGrade(NextGradeBean nextGrade) {
            this.nextGrade = nextGrade;
        }

        public AmountBean getAmount() {
            return amount;
        }

        public void setAmount(AmountBean amount) {
            this.amount = amount;
        }

        public int getMonthMoney() {
            return monthMoney;
        }

        public void setMonthMoney(int monthMoney) {
            this.monthMoney = monthMoney;
        }

        public String getMonthFee() {
            return monthFee;
        }

        public void setMonthFee(String monthFee) {
            this.monthFee = monthFee;
        }

        public String getNextDifference() {
            return nextDifference;
        }

        public void setNextDifference(String nextDifference) {
            this.nextDifference = nextDifference;
        }

        public static class FeeBean {
            /**
             * total_fee : 5,746.40
             * customer : 17
             */

            private String total_fee;
            private String customer;

            public String getTotal_fee() {
                return total_fee;
            }

            public void setTotal_fee(String total_fee) {
                this.total_fee = total_fee;
            }

            public String getCustomer() {
                return customer;
            }

            public void setCustomer(String customer) {
                this.customer = customer;
            }
        }

        public static class CurrentGradeBean {
            /**
             * f2 : 100000
             * f3 : 0.0015
             * f4 : 150
             * id : 21
             * fee : 100,000
             */

            private String f2;
            private String f3;
            private String f4;
            private String id;
            private String fee;

            public String getF2() {
                return f2;
            }

            public void setF2(String f2) {
                this.f2 = f2;
            }

            public String getF3() {
                return f3;
            }

            public void setF3(String f3) {
                this.f3 = f3;
            }

            public String getF4() {
                return f4;
            }

            public void setF4(String f4) {
                this.f4 = f4;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getFee() {
                return fee;
            }

            public void setFee(String fee) {
                this.fee = fee;
            }
        }

        public static class NextGradeBean {
            /**
             * f2 : 150000
             * f3 : 0.00153
             * f4 : 229.5
             * id : 22
             * fee : 150,000
             */

            private String f2;
            private String f3;
            private String f4;
            private String id;
            private String fee;

            public String getF2() {
                return f2;
            }

            public void setF2(String f2) {
                this.f2 = f2;
            }

            public String getF3() {
                return f3;
            }

            public void setF3(String f3) {
                this.f3 = f3;
            }

            public String getF4() {
                return f4;
            }

            public void setF4(String f4) {
                this.f4 = f4;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getFee() {
                return fee;
            }

            public void setFee(String fee) {
                this.fee = fee;
            }
        }

        public static class AmountBean {
            /**
             * amount : 143131.52
             * signtime : 1539150181
             * amountFee : 143,131.52
             */

            private String amount;
            private String signtime;
            private String amountFee;

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getSigntime() {
                return signtime;
            }

            public void setSigntime(String signtime) {
                this.signtime = signtime;
            }

            public String getAmountFee() {
                return amountFee;
            }

            public void setAmountFee(String amountFee) {
                this.amountFee = amountFee;
            }
        }
    }
}
