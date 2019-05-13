package com.ags.ayolelang.API;

import com.ags.ayolelang.Models.DefaultResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("c_auth/auth_login")
    Call<DefaultResponse> userLogin(
            @Field("secret_key") String secret_key,
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("c_auth/auth_register")
    Call<DefaultResponse> userRegister(
            @Field("secret_key") String secret_key,
            @Field("nama") String nama,
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("c_auth/auth_verif")
    Call<DefaultResponse> auth_verif(
            @Field("secret_key") String secret_key,
            @Field("user_id") String user_id,
            @Field("code") String code);

//    @FormUrlEncoded
//    @POST("auth/auth_verif")
//    Call<DefaultResponse> userVerification(
//            @Field("id_verification") int id_verification,
//            @Field("username") String username
//    );
//
//
//    @FormUrlEncoded
//    @POST("auth/reqNewVerifCode")
//    Call<DefaultResponse> reqNewVerifCode(
//            @Field("id_verification") String id_verification,
//            @Field("username") String username
//    );
//
//    @FormUrlEncoded
//    @GET("category/getwithmessage")
//    Call<KategoriRespon> getwithmessage(
//            @Field("id_category") String id_verification
//    );
//
//    @GET("category/getwithmessage")
//    Call<KategoriRespon> getwithmessage();

}
