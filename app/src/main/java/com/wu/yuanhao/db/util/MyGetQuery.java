package com.wu.yuanhao.db.util;

import retrofit2.Call;
import retrofit2.http.HTTP;
import retrofit2.http.Path;

public interface MyGetQuery {
    @HTTP(method = "GET", path = "querydb", hasBody = false)
    Call<MyQueryResult> getCall();
}
