package com.pthomasdesigns.myhealth.rest.service;

import com.pthomasdesigns.myhealth.rest.model.MedicalVisit;
import com.pthomasdesigns.myhealth.rest.model.Message;
import com.pthomasdesigns.myhealth.rest.model.UserInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by tthomas on 4/15/2018.
 */

public interface ApiService {
    @GET("userinfo/{id}")
    public Call<UserInfo> getUserInfo(@Path("id") String id);

    @GET("medicalvisits/all")
    public Call<List<MedicalVisit>> getMedicalVisits();

    @GET("messages/all")
    public Call<List<Message>> getMessages();

}

