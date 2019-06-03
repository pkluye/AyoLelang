package com.ags.ayolelang.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ags.ayolelang.Models.Lelang;

import java.util.ArrayList;

import static com.ags.ayolelang.DBHelper.DBContract.LELANG.LELANG_ALAMAT;
import static com.ags.ayolelang.DBHelper.DBContract.LELANG.LELANG_ANGGARAN;
import static com.ags.ayolelang.DBHelper.DBContract.LELANG.LELANG_DESKRIPSI;
import static com.ags.ayolelang.DBHelper.DBContract.LELANG.LELANG_FILEURL;
import static com.ags.ayolelang.DBHelper.DBContract.LELANG.LELANG_ID;
import static com.ags.ayolelang.DBHelper.DBContract.LELANG.LELANG_JUDUL;
import static com.ags.ayolelang.DBHelper.DBContract.LELANG.LELANG_KOTA;
import static com.ags.ayolelang.DBHelper.DBContract.LELANG.LELANG_PEMBAYARAN;
import static com.ags.ayolelang.DBHelper.DBContract.LELANG.LELANG_TGLSELESAI;
import static com.ags.ayolelang.DBHelper.DBContract.LELANG.LELANG_USERID;
import static com.ags.ayolelang.DBHelper.DBContract.TABLE_REQ_LELANG;

public class REQLelangHelper {
    private Context context;
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public REQLelangHelper(Context context) {
        this.context = context;
    }

    public REQLelangHelper open() throws SQLException {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public Lelang getLelang() {
        db.beginTransaction();
        Cursor cursor = db.query(TABLE_REQ_LELANG, null, null, null, null, null, null, null);
        cursor.moveToFirst();
        Lelang lelang = new Lelang();
        if (cursor.getCount() > 0) {
            cursor.moveToPosition(0);
            lelang.setLelang_id(cursor.getInt(cursor.getColumnIndexOrThrow(LELANG_ID)));
            lelang.setLelang_judul(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_JUDUL)));
            lelang.setLelang_alamat(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_ALAMAT)));
            lelang.setLelang_anggaran(cursor.getLong(cursor.getColumnIndexOrThrow(LELANG_ANGGARAN)));
            lelang.setLelang_fileurl(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_FILEURL)));
            lelang.setLelang_kota(cursor.getInt(cursor.getColumnIndexOrThrow(LELANG_KOTA)));
            lelang.setLelang_deskripsi(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_DESKRIPSI)));
            lelang.setLelang_pembayaran(cursor.getInt(cursor.getColumnIndexOrThrow(LELANG_PEMBAYARAN)));
            lelang.setLelang_tglselesai(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_TGLSELESAI)));
            lelang.setLelang_userid(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_USERID)));
        }
        cursor.close();
        db.endTransaction();
        return lelang;
    }

    public long update(Lelang lelang){
        ContentValues contentValues=new ContentValues();
        contentValues.put(LELANG_JUDUL, lelang.getLelang_judul());
        contentValues.put(LELANG_ALAMAT, lelang.getLelang_alamat());
        contentValues.put(LELANG_ANGGARAN, lelang.getLelang_anggaran());
        contentValues.put(LELANG_FILEURL, lelang.getLelang_fileurl());
        contentValues.put(LELANG_DESKRIPSI, lelang.getLelang_deskripsi());
        contentValues.put(LELANG_KOTA, lelang.getLelang_kota());
        contentValues.put(LELANG_PEMBAYARAN, lelang.getLelang_pembayaran());
        contentValues.put(LELANG_TGLSELESAI, lelang.getLelang_tglselesai());
        contentValues.put(LELANG_USERID, lelang.getLelang_userid());
        Log.d("lelang log",lelang.toString());
        return db.update(TABLE_REQ_LELANG,contentValues,LELANG_ID+"='"+lelang.getLelang_id()+"'",null);
    }

    public long insert(Lelang lelang) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(LELANG_JUDUL, lelang.getLelang_judul());
        contentValues.put(LELANG_ALAMAT, lelang.getLelang_alamat());
        contentValues.put(LELANG_ANGGARAN, lelang.getLelang_anggaran());
        contentValues.put(LELANG_FILEURL, lelang.getLelang_fileurl());
        contentValues.put(LELANG_DESKRIPSI, lelang.getLelang_deskripsi());
        contentValues.put(LELANG_KOTA, lelang.getLelang_kota());
        contentValues.put(LELANG_PEMBAYARAN, lelang.getLelang_pembayaran());
        contentValues.put(LELANG_TGLSELESAI, lelang.getLelang_tglselesai());
        contentValues.put(LELANG_USERID, lelang.getLelang_userid());
        return db.insert(TABLE_REQ_LELANG, null, contentValues);
    }

    public void bulk_insert(ArrayList<Lelang> list) {
        if (list != null && list.size() > 0) {
            db.beginTransaction();
            try {
                for (Lelang lelang : list) {
                    insert(lelang);
                }
                db.setTransactionSuccessful();
            } catch (SQLException e) {
                Log.d("insert_error", e.getMessage());
            } finally {
                db.endTransaction();
            }
        }
    }

    public boolean isempty() {
        Cursor cursor = db.query(TABLE_REQ_LELANG, null, null, null, null, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        if (count == 0) {
            return true;
        }
        return false;
    }

    public void truncate() {
        db.execSQL("DELETE FROM " + TABLE_REQ_LELANG);
        db.execSQL("VACUUM");
    }
}
