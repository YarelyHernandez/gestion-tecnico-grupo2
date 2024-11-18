package com.gestion.repository;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.google.gson.GsonBuilder;
import java.util.concurrent.TimeUnit;

public class DataBaseEquipos {
    
    private Retrofit retrofit;
    private HttpLoggingInterceptor interceptor = null;
    
    public DataBaseEquipos(String url, Long timeout) {
        
    	this.interceptor = new HttpLoggingInterceptor();
    	this.interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        
        OkHttpClient equipo = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(timeout, TimeUnit.MILLISECONDS)
                .readTimeout(timeout, TimeUnit.MILLISECONDS)
                .writeTimeout(timeout, TimeUnit.MILLISECONDS)
                .build();
        
        retrofit = new Retrofit.Builder()
                .client(equipo)
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(
                        new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create()
                ))
                .build();
    }
    
    public DataBaseRepository getDataBase() {
    	return retrofit.create(DataBaseRepository.class);
    }
    
}
