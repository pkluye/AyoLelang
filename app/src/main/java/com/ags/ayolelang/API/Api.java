package com.ags.ayolelang.API;

import com.ags.ayolelang.Models.UserRespon;
import com.ags.ayolelang.Models.KategoriResponArray;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("c_auth/auth_login")
    Call<UserRespon> auth_login(
            @Field("secret_key") String secret_key,
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("c_auth/auth_register")
    Call<UserRespon> auth_register(
            @Field("secret_key") String secret_key,
            @Field("nama") String nama,
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("c_auth/auth_verif")
    Call<UserRespon> auth_verif(
            @Field("secret_key") String secret_key,
            @Field("user_id") String user_id,
            @Field("code") String code);

    @FormUrlEncoded
    @POST("c_kategori/kategori_getDataKategori")
    Call<KategoriResponArray> kategori_getDataKategori(
            @Field("secret_key") String secret_key,
            @Field("id")int kategori_id);

    @FormUrlEncoded
    @POST("c_kategori/kategori_getDataSubParentKategori")
    Call<KategoriResponArray> kategori_getDataSubParentKategori(
            @Field("secret_key") String secret_key,
            @Field("id")int kategori_id);


//    @FormUrlEncoded
//    @GET("category/getwithmessage")
//    Call<KategoriRespon> getwithmessage(
//            @Field("id_category") String id_verification
//    );
//
//    @GET("category/getwithmessage")
//    Call<KategoriRespon> getwithmessage();

}
