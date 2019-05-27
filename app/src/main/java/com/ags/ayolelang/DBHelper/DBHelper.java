package com.ags.ayolelang.DBHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.ags.ayolelang.DBHelper.DBContract.KOTA.KOTA_ID;
import static com.ags.ayolelang.DBHelper.DBContract.KOTA.KOTA_NAMA;
import static com.ags.ayolelang.DBHelper.DBContract.KOTA.KOTA_PROVINSIID;
import static com.ags.ayolelang.DBHelper.DBContract.PROVINSI.PROVINSI_ID;
import static com.ags.ayolelang.DBHelper.DBContract.PROVINSI.PROVINSI_NAMA;
import static com.ags.ayolelang.DBHelper.DBContract.TABLE_KOTA;
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

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PROVINSI);
        db.execSQL(CREATE_TABLE_KOTA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROVINSI);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_KOTA);
        onCreate(db);
    }
}
