package com.ags.ayolelang.DBHelper;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ags.ayolelang.Models.Tawaran;

import java.util.ArrayList;

import static com.ags.ayolelang.DBHelper.DBContract.TABLE_HISTORITAWARAN;
import static com.ags.ayolelang.DBHelper.DBContract.TABLE_TAWARAN;
import static com.ags.ayolelang.DBHelper.DBContract.TAWARAN.TAWARAN_ANGGARAN;
import static com.ags.ayolelang.DBHelper.DBContract.TAWARAN.TAWARAN_LELANGID;
import static com.ags.ayolelang.DBHelper.DBContract.TAWARAN.TAWARAN_USERID;

public class HistoriTawaranHelper {
    private Context context;
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public HistoriTawaranHelper(Context context) {
        this.context = context;
    }

    public HistoriTawaranHelper open() throws SQLException {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public ArrayList<Tawaran> getlisttawaran(int id) {
        ArrayList<Tawaran> tawarans=new ArrayList<>();
        db.beginTransaction();
        Cursor cursor = db.query(TABLE_TAWARAN, null, TAWARAN_LELANGID + "='" + id + "'", null, null, null, null, null);
        cursor.moveToFirst();
        Tawaran tawaran;
        if (cursor.getCount() > 0) {
            tawaran=new Tawaran();
            tawaran.setTawaran_historiid(cursor.getInt(cursor.getColumnIndexOrThrow("tawaran_historiid")));
            tawaran.setTawaran_lelangid(cursor.getInt(cursor.getColumnIndexOrThrow(TAWARAN_LELANGID)));
            tawaran.setTawaran_userid(cursor.getString(cursor.getColumnIndexOrThrow(TAWARAN_USERID)));
            tawaran.setTawaran_anggaran(cursor.getLong(cursor.getColumnIndexOrThrow(TAWARAN_ANGGARAN)));
            tawarans.add(tawaran);
            cursor.moveToNext();
        }
        cursor.close();
        db.endTransaction();
        return tawarans;
    }

    public long insert(Tawaran tawaran) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("tawaran_historiid", tawaran.getTawaran_historiid());
        contentValues.put(TAWARAN_LELANGID, tawaran.getTawaran_lelangid());
        contentValues.put(TAWARAN_USERID,tawaran.getTawaran_userid());
        contentValues.put(TAWARAN_ANGGARAN,tawaran.getTawaran_anggaran());
        return db.insert(TABLE_HISTORITAWARAN, null, contentValues);
    }

    public void bulk_insert(ArrayList<Tawaran> list) {
        if (list != null && list.size() > 0) {
            db.beginTransaction();
            try {
                for (Tawaran tawaran : list) {
                    insert(tawaran);
                }
                db.setTransactionSuccessful();
            } catch (SQLException e) {
                Log.d("insert_error", e.getMessage());
            } finally {
                db.endTransaction();
            }
        }
    }

    public void truncate() {
        db.execSQL("DELETE FROM " + TABLE_HISTORITAWARAN);
        db.execSQL("VACUUM");
    }
}

