package com.example.apps1m.cumpleric;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface TonteriasApi {

    @FormUrlEncoded
    @POST("bday")
    Call<Cumpleanos> cumples(@Field("name") String name, @Field("image") String imagen, @Field("date") long date);

    @GET("bdays")
    Call<ArrayList<Cumpleanos>> getCumples();


    @GET("today.php")
    Call<String> hayCumple();
}
