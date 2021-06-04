package com.iug.pushnotification.Api;

import com.iug.pushnotification.Api.Model.GlobalModel;

import java.util.Map;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiInterface {
    @POST("login")
    Single<GlobalModel> login(@Body Map<Object, Object> params);

    @POST("add_new_user")
    Single<GlobalModel> addNewUser(@Body Map<Object, Object> params);

    @PUT("add_reg_token")
    Single<GlobalModel> addRegToken(@Body Map<Object, Object> params);
}
