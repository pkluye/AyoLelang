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
import static com.ags.ayolelang.DBHelper.DBContract.LELANG.LELANG_FILEURL;
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
import static com.ags.ayolelang.DBHelper.DBContract.PEKERJAAN.PEKERJAAN_HARGA;
import static com.ags.ayolelang.DBHelper.DBContract.PEKERJAAN.PEKERJAAN_ID;
import static com.ags.ayolelang.DBHelper.DBContract.PEKERJAAN.PEKERJAAN_JUMLAH;
import static com.ags.ayolelang.DBHelper.DBContract.PEKERJAAN.PEKERJAAN_KATEGORIID;
import static com.ags.ayolelang.DBHelper.DBContract.PEKERJAAN.PEKERJAAN_LELANGID;
import static com.ags.ayolelang.DBHelper.DBContract.PEKERJAAN.PEKERJAAN_STATUS;
import static com.ags.ayolelang.DBHelper.DBContract.PEKERJAAN.PEKERJAAN_UKURAN;
import static com.ags.ayolelang.DBHelper.DBContract.PROVINSI.PROVINSI_ID;
import static com.ags.ayolelang.DBHelper.DBContract.PROVINSI.PROVINSI_NAMA;
import static com.ags.ayolelang.DBHelper.DBContract.SPECBARANG.SPECBARANG_BAHAN;
import static com.ags.ayolelang.DBHelper.DBContract.SPECBARANG.SPECBARANG_HARGASATUAN;
import static com.ags.ayolelang.DBHelper.DBContract.SPECBARANG.SPECBARANG_ID;
import static com.ags.ayolelang.DBHelper.DBContract.SPECBARANG.SPECBARANG_KATEGORIID;
import static com.ags.ayolelang.DBHelper.DBContract.SPECBARANG.SPECBARANG_SATUAN;
import static com.ags.ayolelang.DBHelper.DBContract.SPECBARANG.SPECBARANG_UKURAN;
import static com.ags.ayolelang.DBHelper.DBContract.SPECBARANG.SPECBARANG_jmlsisi;
import static com.ags.ayolelang.DBHelper.DBContract.SPECBARANG.SPECBARANG_laminasi;
import static com.ags.ayolelang.DBHelper.DBContract.TABLE_HISTORITAWARAN;
import static com.ags.ayolelang.DBHelper.DBContract.TABLE_KATEGORI;
import static com.ags.ayolelang.DBHelper.DBContract.TABLE_KOTA;
import static com.ags.ayolelang.DBHelper.DBContract.TABLE_LELANG;
import static com.ags.ayolelang.DBHelper.DBContract.TABLE_PEKERJAAN;
import static com.ags.ayolelang.DBHelper.DBContract.TABLE_PROVINSI;
import static com.ags.ayolelang.DBHelper.DBContract.TABLE_REQ_LELANG;
import static com.ags.ayolelang.DBHelper.DBContract.TABLE_REQ_PEKERJAAN;
import static com.ags.ayolelang.DBHelper.DBContract.TABLE_SPECBARANG;
import static com.ags.ayolelang.DBHelper.DBContract.TABLE_TAWARAN;
import static com.ags.ayolelang.DBHelper.DBContract.TABLE_USER;
import static com.ags.ayolelang.DBHelper.DBContract.TAWARAN.TAWARAN_ANGGARAN;
import static com.ags.ayolelang.DBHelper.DBContract.TAWARAN.TAWARAN_ID;
import static com.ags.ayolelang.DBHelper.DBContract.TAWARAN.TAWARAN_LELANGID;
import static com.ags.ayolelang.DBHelper.DBContract.TAWARAN.TAWARAN_USERID;
import static com.ags.ayolelang.DBHelper.DBContract.USER.USER_ALAMAT;
import static com.ags.ayolelang.DBHelper.DBContract.USER.USER_EMAIL;
import static com.ags.ayolelang.DBHelper.DBContract.USER.USER_ID;
import static com.ags.ayolelang.DBHelper.DBContract.USER.USER_IMGURL;
import static com.ags.ayolelang.DBHelper.DBContract.USER.USER_NAMA;
import static com.ags.ayolelang.DBHelper.DBContract.USER.USER_STATUS;
import static com.ags.ayolelang.DBHelper.DBContract.USER.USER_TELPON;

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
                    LELANG_FILEURL + " TEKS NOT NULL, " +
                    LELANG_TGLMULAI + " TEKS NOT NULL, " +
                    LELANG_TGLSELESAI + " TEKS NOT NULL, " +
                    LELANG_USERID + " TEXT NOT NULL)";

    public static String CREATE_TABLE_PEKERJAAN =
            "CREATE TABLE " + TABLE_PEKERJAAN + " (" +
                    PEKERJAAN_ID + " INTEGER PRIMARY KEY, " +
                    PEKERJAAN_BAHAN + " TEKS NOT NULL, " +
                    PEKERJAAN_CATATAN + " TEKS NOT NULL, " +
                    PEKERJAAN_HARGA + " INTEGER NOT NULL, " +
                    PEKERJAAN_JUMLAH + " INTEGER NOT NULL, " +
                    PEKERJAAN_KATEGORIID + " INTEGER NOT NULL, " +
                    PEKERJAAN_LELANGID + " INTEGER NOT NULL, " +
                    PEKERJAAN_STATUS + " INTEGER NOT NULL, " +
                    PEKERJAAN_UKURAN + " TEKS NOT NULL)";

    public static String CREATE_TABLE_REQ_LELANG =
            "CREATE TABLE " + TABLE_REQ_LELANG + " (" +
                    LELANG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    LELANG_ALAMAT + " TEXT NOT NULL, " +
                    LELANG_ANGGARAN + " BIGINT NOT NULL, " +
                    LELANG_DESKRIPSI + " TEXT NOT NULL, " +
                    LELANG_JUDUL + " TEXT NOT NULL, " +
                    LELANG_KOTA + " INTEGER NOT NULL, " +
                    LELANG_PEMBAYARAN + " INTEGER NOT NULL, " +
                    LELANG_FILEURL + " TEKS NOT NULL, " +
                    LELANG_TGLSELESAI + " TEKS NOT NULL, " +
                    LELANG_STATUS + " INTEGER, " +
                    LELANG_USERID + " TEXT NOT NULL)";

    public static String CREATE_TABLE_REQ_PEKERJAAN =
            "CREATE TABLE " + TABLE_REQ_PEKERJAAN + " (" +
                    PEKERJAAN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    PEKERJAAN_BAHAN + " TEKS NOT NULL, " +
                    PEKERJAAN_CATATAN + " TEKS NOT NULL, " +
                    PEKERJAAN_HARGA + " INTEGER NOT NULL, " +
                    PEKERJAAN_JUMLAH + " INTEGER NOT NULL, " +
                    PEKERJAAN_KATEGORIID + " INTEGER NOT NULL, " +
                    PEKERJAAN_UKURAN + " TEKS NOT NULL," +
                    PEKERJAAN_STATUS + " INTEGER)";

    public static String CREATE_TABLE_USER =
            "CREATE TABLE " + TABLE_USER + " (" +
                    USER_ID + " TEKS PRIMARY KEY, " +
                    USER_NAMA + " TEKS NOT NULL, " +
                    USER_EMAIL + " TEKS NOT NULL, " +
                    USER_TELPON + " TEKS, " +
                    USER_ALAMAT + " TEKS, " +
                    USER_IMGURL + " TEKS)";

    public static String CREATE_TABLE_TAWARAN =
            "CREATE TABLE " + TABLE_TAWARAN + " (" +
                    TAWARAN_ID + " INTEGER PRIMARY KEY, " +
                    TAWARAN_LELANGID + " INTEGER NOT NULL, " +
                    TAWARAN_USERID + " INTEGER NOT NULL, " +
                    TAWARAN_ANGGARAN + " INTEGER NOT NULL)";

    public static String CREATE_TABLE_HISTORITAWARAN =
            "CREATE TABLE " + TABLE_HISTORITAWARAN + " (" +
                    "tawaran_historiid INTEGER PRIMARY KEY, " +
                    TAWARAN_LELANGID + " INTEGER NOT NULL, " +
                    TAWARAN_USERID + " INTEGER NOT NULL, " +
                    TAWARAN_ANGGARAN + " INTEGER NOT NULL)";

    public static String CREATE_TABLE_SPECBARANG =
            "CREATE TABLE " + TABLE_SPECBARANG + " (" +
                    SPECBARANG_ID + " INTEGER PRIMARY KEY, " +
                    SPECBARANG_KATEGORIID + " INTEGER NOT NULL, " +
                    SPECBARANG_UKURAN + " STRING NOT NULL, " +
                    SPECBARANG_BAHAN + " STRING NOT NULL, " +
                    SPECBARANG_jmlsisi + " INTEGER NOT NULL, " +
                    SPECBARANG_laminasi + " STRING NOT NULL, " +
                    SPECBARANG_HARGASATUAN + " INTEGER NOT NULL, " +
                    SPECBARANG_SATUAN + " STRING NOT NULL)";

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
        db.execSQL(CREATE_TABLE_REQ_LELANG);
        db.execSQL(CREATE_TABLE_REQ_PEKERJAAN);
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_TAWARAN);
        db.execSQL(CREATE_TABLE_HISTORITAWARAN);
        db.execSQL(CREATE_TABLE_SPECBARANG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROVINSI);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KOTA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KATEGORI);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LELANG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PEKERJAAN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REQ_LELANG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REQ_PEKERJAAN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORITAWARAN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TAWARAN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SPECBARANG);
        onCreate(db);
    }

}
