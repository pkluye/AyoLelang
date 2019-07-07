package com.ags.ayolelang.API;

import com.ags.ayolelang.Models.FetchDBRespon;
import com.ags.ayolelang.Models.PesanRespon;
import com.ags.ayolelang.Models.RoomRespon;
import com.ags.ayolelang.Models.StringRespon;
import com.ags.ayolelang.Models.UserRespon;

import io.reactivex.Observable;
import io.reactivex.Single;
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

    @FormUrlEncoded
    @POST("c_fetchdata/fetchdata")
    Observable<FetchDBRespon> fetchDB(
            @Field("secret_key") String secret_key,
            @Field("token_kategori") String s,
            @Field("token_provinsi") String s1,
            @Field("token_kota") String s2,
            @Field("token_lelang") String s3,
            @Field("token_pekerjaan") String s4,
            @Field("token_user") String s5,
            @Field("token_tawaran") String s6,
            @Field("token_specbarang") String s7);

    @Headers("Content-Type: application/json")
    @POST("c_lelang/lelang_edit")
    Call<StringRespon> editLelang(@Body String toString);

    @FormUrlEncoded
    @POST("c_tawaran/tawaran_buat")
    Call<StringRespon> buatTawaran(
            @Field("secret_key") String secret_key,
            @Field("user_id") String userid,
            @Field("lelang_id") int lelang_id,
            @Field("anggaran") long penawaran);

    @Headers("Content-Type: application/json")
    @POST("c_tawaran/tawaran_buatv2")
    Single<StringRespon> tawaran_buatv2(
            @Body String body);

    @Headers("Content-Type: application/json")
    @POST("c_tawaran/tawaran_editv2")
    Single<StringRespon> tawaran_editv2(
            @Body String body);

    @FormUrlEncoded
    @POST("c_tawaran/tawaran_edit")
    Call<StringRespon> editTawaran(
            @Field("secret_key") String secret_key,
            @Field("user_id") String userid,
            @Field("tawaran_id") int tawaran_id,
            @Field("anggaran") long penawaran);

    @FormUrlEncoded
    @POST("c_tawaran/tawaran_delete")
    Call<StringRespon> deleteTawaran(
            @Field("secret_key") String secret_key,
            @Field("user_id") String userid,
            @Field("tawaran_id") int tawaran_id);

    @FormUrlEncoded
    @POST("c_pesan/pesan_getroom")
    Observable<RoomRespon> getRoom(
            @Field("secret_key") String secret_key,
            @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("c_pesan/pesan_getpesan")
    Observable<PesanRespon> getPesan(
            @Field("secret_key") String secret_key,
            @Field("room_id") int room_id
    );

    @FormUrlEncoded
    @POST("c_pesan/pesan_buat")
    Single<StringRespon> sentPesan(
            @Field("secret_key") String secret_key,
            @Field("user_id") String user_id,
            @Field("penerima") String user_id1,
            @Field("isi") String toString);
}
