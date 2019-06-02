package com.ags.ayolelang.API;

import com.ags.ayolelang.Models.FetchDBRespon;
import com.ags.ayolelang.Models.LelangRespon;
import com.ags.ayolelang.Models.StringRespon;
import com.ags.ayolelang.Models.UserRespon;

import org.json.JSONObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
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

    @Headers("Content-Type: application/json")
    @POST("c_lelang/lelang_buat")
    Call<StringRespon> buatLelang(
            @Body String body);

    @Multipart
    @POST("c_upload/do_upload")
    Call<StringRespon> uploadfile(
            @Part("secret_key") RequestBody secret_key,
            @Part MultipartBody.Part userfile
    );

//    @FormUrlEncoded
//    @POST("c_indonesia/getkabupaten")
//    Call<KotaResponArray> getkabupaten(
//            @Field("secret_key") String secret_key,
//            @Field("provinsi_id") int provinsi_id);
//
//    @FormUrlEncoded
//    @POST("c_indonesia/getprovinsi")
//    Call<ProvinsiResponArray> getprovinsi(
//            @Field("secret_key") String secret_key);

//    @FormUrlEncoded
//    @POST("c_lelang/lelang_buat_plus_pekerjaan")
//    Call<StringRespon> lelang_buat_plus_pekerjaan(
//            @Field("secret_key") String secret_key,
//            @Field("lelang_userid") String user_id,
//            @Field("lelang_deskripsi") String deskripsi,
//            @Field("lelang_tglselesai") String deadline,
//            @Field("lelang_judul") String judul,
//            @Field("lelang_pembayaran") int pembayaran,
//            @Field("lelang_alamat") String alamat,
//            @Field("lelang_kota") int kotaid,
//            @Field("pekerjaan_ukuran") String pekerjaan_ukuran,
//            @Field("pekerjaan_bahan") String pekerjaan_bahan,
//            @Field("pekerjaan_jumlah") int pekerjaan_jumlah,
//            @Field("pekerjaan_harga") long pekerjaan_harga,
//            @Field("pekerjaan_kategoriid") int pekerjaan_kategoriid,
//            @Field("pekerjaan_fileurl") String pekerjaan_fileurl,
//            @Field("pekerjaan_catatan") String pekerjaan_catatan);

//    @FormUrlEncoded
//    @POST("c_lelang/lelang_edit")
//    Call<StringRespon> lelang_edit(
//            @Field("secret_key") String secret_key,
//            @Field("lelang_id") int lelang_id,
//            @Field("lelang_userid") String user_id,
//            @Field("lelang_deskripsi") String deskripsi,
//            @Field("lelang_tglselesai") String deadline,
//            @Field("lelang_judul") String judul,
//            @Field("lelang_pembayaran") int pembayaran,
//            @Field("lelang_alamat") String alamat,
//            @Field("lelang_kota") int kotaid,
//            @Field("lelang_fileurl") String fileurl);

//    @FormUrlEncoded
//    @POST("c_lelang/lelang_posting")
//    Call<StringRespon> lelang_posting(
//            @Field("secret_key") String secret_key,
//            @Field("lelang_id") int lelang_id,
//            @Field("lelang_userid") String user_id);

//    @FormUrlEncoded
//    @POST("c_lelang/lelang_getlelang")
//    Call<LelangRespon> lelang_getLelang(
//            @Field("secret_key") String secret_key,
//            @Field("lelang_id") int lelang_id);

//    @FormUrlEncoded
//    @POST("c_pekerjaan/pekerjaan_edit")
//    Call<StringRespon> pekerjaan_edit(
//            @Field("secret_key") String secret_key,
//            @Field("pekerjaan_id") int pekerjaan_id,
//            @Field("pekerjaan_ukuran") String pekerjaan_ukuran,
//            @Field("pekerjaan_bahan") String pekerjaan_bahan,
//            @Field("pekerjaan_jumlah") int pekerjaan_jumlah,
//            @Field("pekerjaan_harga") long pekerjaan_harga,
//            @Field("pekerjaan_fileurl") String pekerjaan_fileurl,
//            @Field("pekerjaan_catatan") String pekerjaan_catatan,
//            @Field("user_id") String user_id);

//    @FormUrlEncoded
//    @POST("c_pekerjaan/pekerjaan_delete")
//    Call<StringRespon> pekerjaan_delete(
//            @Field("secret_key") String secret_key,
//            @Field("pekerjaan_id") int pekerjaan_id,
//            @Field("user_id") String user_id);

//    @FormUrlEncoded
//    @POST("c_pekerjaan/pekerjaan_buat")
//    Call<StringRespon> pekerjaan_buat(
//            @Field("secret_key") String secret_key,
//            @Field("pekerjaan_lelangid") int pekerjaan_id,
//            @Field("pekerjaan_ukuran") String pekerjaan_ukuran,
//            @Field("pekerjaan_bahan") String pekerjaan_bahan,
//            @Field("pekerjaan_jumlah") int pekerjaan_jumlah,
//            @Field("pekerjaan_harga") long pekerjaan_harga,
//            @Field("pekerjaan_fileurl") String pekerjaan_fileurl,
//            @Field("pekerjaan_catatan") String pekerjaan_catatan,
//            @Field("pekerjaan_kategoriid") int pekerjaan_kategoriid,
//            @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("c_fetchdata/fetchdata")
    Call<FetchDBRespon> fetchDB(
            @Field("secret_key") String secret_key,
            @Field("token_kategori") String s,
            @Field("token_provinsi") String s1,
            @Field("token_kota") String s2,
            @Field("token_lelang") String s3,
            @Field("token_pekerjaan") String s4);
}
