package com.wu.yuanhao.db.util;

/**
 * Created by Yuanhao on 2018/6/3.
 */

public class MyQueryResult {
    public int status;
    public String msg;
    public QueryResultData data;
    public static class QueryResultData {
        public String name;
        public String sex;
    }

}
