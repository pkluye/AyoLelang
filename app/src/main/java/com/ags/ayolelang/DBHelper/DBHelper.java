package com.ags.ayolelang.DBHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.ags.ayolelang.DBHelper.DBContract.KATEGORI.KATEGORI_ID;
import static com.ags.ayolelang.DBHelper.DBContract.KATEGORI.KATEGORI_NAMA;
import static com.ags.ayolelang.DBHelper.DBContract.KATEGORI.KATEGORI_PARENTID;
import static com.ags.ayolelang.DBHelper.DBContract.KATEGORI.KATEGORI_SUBPARENTID;
import static com.ags.ayolelang.DBHelper.DBContract.KOTA.KOTA_ID;
import static com.ags.ayolelang.DBHelper.DBContract.KOTA.KOTA_NAMA;
import static com.ags.ayolelang.DBHelper.DBContract.KOTA.KOTA_PROVINSIID;
import static com.ags.ayolelang.DBHelper.DBContract.LELANG.LELANG_ALAMAT;
import static com.ags.ayolelang.DBHelper.DBContract.LELANG.LELANG_ANGGARAN;
import static com.ags.ayolelang.DBHelper.DBContract.LELANG.LELANG_DESKRIPSI;
import static com.ags.ayolelang.DBHelper.DBContract.LELANG.LELANG_ID;
import static com.ags.ayolelang.DBHelper.DBContract.LELANG.LELANG_JUDUL;
import static com.ags.ayolelang.DBHelper.DBContract.LELANG.LELANG_KOTA;
import static com.ags.ayolelang.DBHelper.DBContract.LELANG.LELANG_PEMBAYARAN;
import static com.ags.ayolelang.DBHelper.DBContract.LELANG.LELANG_STATUS;
import static com.ags.ayolelang.DBHelper.DBContract.LELANG.LELANG_TGLMULAI;
import static com.ags.ayolelang.DBHelper.DBContract.LELANG.LELANG_TGLSELESAI;
import static com.ags.ayolelang.DBHelper.DBContract.LELANG.LELANG_USERID;
import static com.ags.ayolelang.DBHelper.DBContract.PEKERJAAN.PEKERJAAN_BAHAN;
import static com.ags.ayolelang.DBHelper.DBContract.PEKERJAAN.PEKERJAAN_CATATAN;
import static com.ags.ayolelang.DBHelper.DBContract.PEKERJAAN.PEKERJAAN_FILEURL;
import static com.ags.ayolelang.DBHelper.DBContract.PEKERJAAN.PEKERJAAN_HARGA;
import static com.ags.ayolelang.DBHelper.DBContract.PEKERJAAN.PEKERJAAN_ID;
import static com.ags.ayolelang.DBHelper.DBContract.PEKERJAAN.PEKERJAAN_JUMLAH;
import static com.ags.ayolelang.DBHelper.DBContract.PEKERJAAN.PEKERJAAN_KATEGORIID;
import static com.ags.ayolelang.DBHelper.DBContract.PEKERJAAN.PEKERJAAN_LELANGID;
import static com.ags.ayolelang.DBHelper.DBContract.PEKERJAAN.PEKERJAAN_STATUS;
import static com.ags.ayolelang.DBHelper.DBContract.PEKERJAAN.PEKERJAAN_UKURAN;
import static com.ags.ayolelang.DBHelper.DBContract.PROVINSI.PROVINSI_ID;
import static com.ags.ayolelang.DBHelper.DBContract.PROVINSI.PROVINSI_NAMA;
import static com.ags.ayolelang.DBHelper.DBContract.TABLE_KATEGORI;
import static com.ags.ayolelang.DBHelper.DBContract.TABLE_KOTA;
import static com.ags.ayolelang.DBHelper.DBContract.TABLE_LELANG;
import static com.ags.ayolelang.DBHelper.DBContract.TABLE_PEKERJAAN;
import static com.ags.ayolelang.DBHelper.DBContract.TABLE_PROVINSI;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static String DATABASE_NAME = "dbayolelang";

    public static String CREATE_TABLE_PROVINSI =
            "CREATE TABLE " + TABLE_PROVINSI + " (" +
                    PROVINSI_ID + " INTEGER PRIMARY KEY, " +
                    PROVINSI_NAMA + " TEXT NOT NULL)";
    public static String CREATE_TABLE_KOTA =
            "CREATE TABLE " + TABLE_KOTA + " (" +
                    KOTA_ID + " INTEGER PRIMARY KEY, " +
                    KOTA_NAMA + " TEXT NOT NULL, " +
                    KOTA_PROVINSIID + " TEXT NOT NULL)";
    public static String CREATE_TABLE_KATEGORI =
            "CREATE TABLE " + TABLE_KATEGORI + " (" +
                    KATEGORI_ID + " INTEGER PRIMARY KEY, " +
                    KATEGORI_PARENTID + " INTEGER NOT NULL, " +
                    KATEGORI_SUBPARENTID + " INTEGER NOT NULL, " +
                    KATEGORI_NAMA + " TEXT NOT NULL)";
    public static String CREATE_TABLE_LELANG =
            "CREATE TABLE " + TABLE_LELANG + " (" +
                    LELANG_ID + " INTEGER PRIMARY KEY, " +
                    LELANG_ALAMAT + " TEXT NOT NULL, " +
                    LELANG_ANGGARAN + " BIGINT NOT NULL, " +
                    LELANG_DESKRIPSI + " TEXT NOT NULL, " +
                    LELANG_JUDUL + " TEXT NOT NULL, " +
                    LELANG_KOTA + " INTEGER NOT NULL, " +
                    LELANG_PEMBAYARAN + " INTEGER NOT NULL, " +
                    LELANG_STATUS + " INTEGER NOT NULL, " +
                    LELANG_TGLMULAI + " DATETIME, " +
                    LELANG_TGLSELESAI + " DATETIME, " +
                    LELANG_USERID + " TEXT NOT NULL)";
    public static String CREATE_TABLE_PEKERJAAN =
            "CREATE TABLE " + TABLE_PEKERJAAN + " (" +
                    PEKERJAAN_ID + " INTEGER PRIMARY KEY, " +
                    PEKERJAAN_BAHAN + " TEKS NOT NULL, " +
                    PEKERJAAN_CATATAN + " TEKS NOT NULL, " +
                    PEKERJAAN_FILEURL + " TEKS NOT NULL, " +
                    PEKERJAAN_HARGA + " INTEGER NOT NULL, " +
                    PEKERJAAN_JUMLAH + " INTEGER NOT NULL, " +
                    PEKERJAAN_KATEGORIID + " INTEGER NOT NULL, " +
                    PEKERJAAN_LELANGID + " INTEGER NOT NULL, " +
                    PEKERJAAN_STATUS + " INTEGER NOT NULL, " +
                    PEKERJAAN_UKURAN + " TEKS NOT NULL)";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PROVINSI);
        db.execSQL(CREATE_TABLE_KOTA);
        db.execSQL(CREATE_TABLE_KATEGORI);
        db.execSQL(CREATE_TABLE_LELANG);
        db.execSQL(CREATE_TABLE_PEKERJAAN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROVINSI);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KOTA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KATEGORI);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LELANG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PEKERJAAN);
        onCreate(db);
    }

}
