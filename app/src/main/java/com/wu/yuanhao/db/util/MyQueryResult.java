package com.wu.yuanhao.db.util;

/**
 * Created by Yuanhao on 2018/6/3.
 */

public class MyQueryResult {
    public String status;
    public String msg;
    public QueryResultData data;
    public static class QueryResultData {
        public String name;
        public String sex;
    }

    public String show(){
        String res = "Status:" + this.status + "\n" + "Message:" + this.msg + "\n"
                + "Data:" + this.data.name + this.data.sex;
        return res;
    }
}
