package com.ags.ayolelang.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ags.ayolelang.Models.Pekerjaan;
import com.ags.ayolelang.Models.TWPekerjaan;

import java.util.ArrayList;

import static com.ags.ayolelang.DBHelper.DBContract.TABLE_TWPEKERJAAN;
import static com.ags.ayolelang.DBHelper.DBContract.TWPEKERJAAN.TWPEKERJAAN_ANGGARANID;
import static com.ags.ayolelang.DBHelper.DBContract.TWPEKERJAAN.TWPEKERJAAN_ID;
import static com.ags.ayolelang.DBHelper.DBContract.TWPEKERJAAN.TWPEKERJAAN_PEKERJAANID;
import static com.ags.ayolelang.DBHelper.DBContract.TWPEKERJAAN.TWPEKERJAAN_TAWARANID;


public class TWPekerjaanHelper {

    private Context context;
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public TWPekerjaanHelper(Context context) {
        this.context = context;
    }

    public TWPekerjaanHelper open() throws SQLException {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public ArrayList<TWPekerjaan> getDataby(String tawaran_id, String pekerjaan_id) {
        db.beginTransaction();
        ArrayList<TWPekerjaan> twPekerjaans = new ArrayList<>();
        Cursor cursor = db.query(TABLE_TWPEKERJAAN, null, TWPEKERJAAN_TAWARANID + "=?", new String[]{tawaran_id}, null, null, null, null);
        cursor.moveToFirst();
        TWPekerjaan twPekerjaan;
        if (cursor.getCount() > 0) {
            do {
                twPekerjaan = new TWPekerjaan();
                twPekerjaan.setTwpekerjaan_id(cursor.getInt(cursor.getColumnIndexOrThrow(TWPEKERJAAN_ID)));
                twPekerjaan.setTwpekerjaan_tawaranid(cursor.getInt(cursor.getColumnIndexOrThrow(TWPEKERJAAN_TAWARANID)));
                twPekerjaan.setTwpekerjaan_pekerjaanid(cursor.getInt(cursor.getColumnIndexOrThrow(TWPEKERJAAN_PEKERJAANID)));
                twPekerjaan.setTwpekerjaan_anggaran(cursor.getLong(cursor.getColumnIndexOrThrow(TWPEKERJAAN_ANGGARANID)));
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        db.endTransaction();
        return twPekerjaans;
    }

    public TWPekerjaan getSingleTWPekerjaanby(String tawaran_id, String pekerjaan_id) {
        db.beginTransaction();
        Cursor cursor = db.query(TABLE_TWPEKERJAAN, null, TWPEKERJAAN_TAWARANID + "=? AND " + TWPEKERJAAN_PEKERJAANID + "=?", new String[]{tawaran_id, pekerjaan_id}, null, null, null, null);
        cursor.moveToFirst();
        TWPekerjaan twPekerjaan = null;
        if (cursor.getCount() > 0) {
            cursor.moveToPosition(0);
            twPekerjaan = new TWPekerjaan();
            twPekerjaan.setTwpekerjaan_id(cursor.getInt(cursor.getColumnIndexOrThrow(TWPEKERJAAN_ID)));
            twPekerjaan.setTwpekerjaan_tawaranid(cursor.getInt(cursor.getColumnIndexOrThrow(TWPEKERJAAN_TAWARANID)));
            twPekerjaan.setTwpekerjaan_pekerjaanid(cursor.getInt(cursor.getColumnIndexOrThrow(TWPEKERJAAN_PEKERJAANID)));
            twPekerjaan.setTwpekerjaan_anggaran(cursor.getLong(cursor.getColumnIndexOrThrow(TWPEKERJAAN_ANGGARANID)));
        }
        cursor.close();
        db.endTransaction();
        return twPekerjaan;
    }

    public boolean isEmpty() {
        Cursor cursor = db.query(TABLE_TWPEKERJAAN, null, null, null, null, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        if (count == 0) {
            return true;
        }
        return false;
    }

    public long insert(TWPekerjaan twPekerjaan) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(TWPEKERJAAN_ID, twPekerjaan.getTwpekerjaan_id());
        initialValues.put(TWPEKERJAAN_TAWARANID, twPekerjaan.getTwpekerjaan_tawaranid());
        initialValues.put(TWPEKERJAAN_PEKERJAANID, twPekerjaan.getTwpekerjaan_pekerjaanid());
        initialValues.put(TWPEKERJAAN_ANGGARANID, twPekerjaan.getTwpekerjaan_anggaran());
        return db.insert(TABLE_TWPEKERJAAN, null, initialValues);
    }

    public void bulk_insert(ArrayList<TWPekerjaan> list) {
        if (list != null && list.size() > 0) {
            db.beginTransaction();
            try {
                for (TWPekerjaan twPekerjaan : list) {
                    insert(twPekerjaan);
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
        db.execSQL("DELETE FROM " + TABLE_TWPEKERJAAN);
        db.execSQL("VACUUM");
    }
}
