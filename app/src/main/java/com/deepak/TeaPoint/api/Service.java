package com.deepak.TeaPoint.api;

import com.deepak.TeaPoint.Models.Hero;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by delaroy on 3/31/17.
 */
public interface Service {

    @GET("marvel/")
    Call<List<Hero>> getHeros();
}
