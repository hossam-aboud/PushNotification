package com.iug.pushnotification.Api;

import androidx.annotation.NonNull;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class ApiClient {

    private static String TAG = "ApiClient";


    static Retrofit getClient(boolean authorization) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        if (BuildConfig.DEBUG) {
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//        } else {
//            logging.setLevel(HttpLoggingInterceptor.Level.NONE);
//        }
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS);

        httpClient.addInterceptor(logging);

        if (authorization) {
//            System.out.println("get AccessToken "+UserAuth.getInstance().getUser().getAccessToken());
            httpClient.addInterceptor(new Interceptor() {
                                          @NotNull
                                          @Override
                                          public Response intercept(@NonNull Chain chain) throws IOException {
                                              Request original = chain.request();
                                              Request request = original.newBuilder()
                                                      .header("Content-Type", "application/x-www-form-urlencoded")
                                                      .header("Accept", "application/json")
                                                      .header("type", "android")
//                                                      .header("Accept-Language", UserAuth.getInstance().getLang() != null ? UserAuth.getInstance().getLang() : "ar")
//                                                      .header("Authorization", "Bearer "+UserAuth.getInstance().getUser().getAccessToken())
//                                                      .header("Authorization", "Bearer 53|KvhQ0YyBCGOGQ4qT791rd8ZJx46t6yHjnJYS0pum")
                                                      .method(original.method(), original.body())
                                                      .build();
                                              return chain.proceed(request);
                                          }
                                      }
            );
        } else {
            httpClient.addInterceptor(new Interceptor() {
                                          @NotNull
                                          @Override
                                          public Response intercept(@NonNull Chain chain) throws IOException {

                                              Request original = chain.request();
                                              Request request = original.newBuilder()
                                                      .header("Content-Type", "application/x-www-form-urlencoded")
                                                      .header("Accept", "application/json")
                                                      .header("type", "android")
//                                                      .header("Accept-Language", UserAuth.getInstance().getLang() != null ? UserAuth.getInstance().getLang() : "ar")
                                                      .method(original.method(), original.body())
                                                      .build();

                                              return chain.proceed(request);
                                          }
                                      }
            );
        }


        return new Retrofit.Builder()
                .baseUrl("https://mcc-users-api.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build())
                .build();

    }


}