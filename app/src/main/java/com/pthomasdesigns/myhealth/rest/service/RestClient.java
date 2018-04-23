package com.pthomasdesigns.myhealth.rest.service;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public  class RestClient
{
        public static String BASE_URL = "http://34.201.36.168:8080/demo/";
        private static RestClient sInstance = null;
        private static ApiService sApiService = null;

        private RestClient()
        {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
            sApiService = retrofit.create(ApiService.class);
        }

        public static RestClient getInstance() {
            if (sInstance == null) {
                sInstance = new RestClient();
            }
            return  sInstance;
        }

        public static ApiService getApiService()
        {
              return sApiService;
        }

        public void setBaseUrl(String url) {
            BASE_URL = url;
        }
}
