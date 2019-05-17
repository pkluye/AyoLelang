package com.ags.ayolelang.API;

import com.ags.ayolelang.Models.KotaResponArray;
import com.ags.ayolelang.Models.ProvinsiResponArray;
import com.ags.ayolelang.Models.StringRespon;
import com.ags.ayolelang.Models.UserRespon;
import com.ags.ayolelang.Models.KategoriResponArray;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

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

    @Multipart
    @POST("c_upload/do_upload")
    Call<StringRespon> uploadfile(
            @Part("secret_key") RequestBody secret_key,
            @Part MultipartBody.Part userfile
    );

    @FormUrlEncoded
    @POST("c_indonesia/getkabupaten")
    Call<KotaResponArray> getkabupaten(
            @Field("secret_key") String secret_key,
            @Field("provinsi_id")int provinsi_id);

    @FormUrlEncoded
    @POST("c_indonesia/getprovinsi")
    Call<ProvinsiResponArray> getprovinsi(
            @Field("secret_key") String secret_key);
}
