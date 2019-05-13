package com.ags.ayolelang.API;

import feri.com.lpse.Models.DefaultResponse;
import feri.com.lpse.Models.KategoriRespon;
import feri.com.lpse.Models.LoginResponse;
import feri.com.lpse.Models.VerifCodeRespon;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("auth/userlogin")
    Call<LoginResponse> userLogin(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("auth/userregister")
    Call<DefaultResponse> userRegister(
            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("auth/userverif")
    Call<DefaultResponse> userVerification(
            @Field("id_verification") int id_verification,
            @Field("username") String username
    );

    @FormUrlEncoded
    @POST("auth/getVerifCode")
    Call<VerifCodeRespon> getVerifCode(
            @Field("username") String username,
            @Field("type") String type
    );

    @FormUrlEncoded
    @POST("auth/reqNewVerifCode")
    Call<VerifCodeRespon> reqNewVerifCode(
            @Field("id_verification") String id_verification,
            @Field("username") String username
    );

    @FormUrlEncoded
    @GET("category/getwithmessage")
    Call<KategoriRespon> getwithmessage(
            @Field("id_category") String id_verification
    );

    @GET("category/getwithmessage")
    Call<KategoriRespon> getwithmessage();

}
