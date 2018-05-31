package com.wu.yuanhao.db.util;

import android.content.Context;

import com.wu.yuanhao.db.R;

import java.sql.Connection;

/**
 * Created by Yuanhao on 2018/5/31.
 */

public class MyDbHandler {
    private static Connection mDbCon = null;
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS test(" +
            "id INT NOT NULL AUTO_INCREMENT," +
            "name VARCHAR(100) NOT NULL," +
            "sex VARCHAR(10) NOT NULL," +
            "PRIMARY KEY (id))ENGINE=InnoDB DEFAULT CHARSET=utf8";

    public MyDbHandler(Context context, String url, String username, String password) {
        String driver = context.getString(R.string.driver);

    }

    public void onCreate()
}
