package com.example.myapplication.Network;

import com.example.myapplication.MainActivity;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {
    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    private static Retrofit retrofit = null;

    public static Retrofit getRetrofitInstance(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                System.out.println("HttpLogging : " + message);
            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .client( new OkHttpClient.Builder()
                            .retryOnConnectionFailure(true)
                            .protocols(Arrays.asList(Protocol.HTTP_1_1))
                            .addInterceptor(interceptor)
                            .connectTimeout(3, TimeUnit.MINUTES)
                            .writeTimeout(3, TimeUnit.MINUTES)
                            .readTimeout(3, TimeUnit.MINUTES)
                            .build())
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
