package com.ags.ayolelang.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ags.ayolelang.Models.Tawaran;

import java.util.ArrayList;

import static com.ags.ayolelang.DBHelper.DBContract.TABLE_TAWARAN;
import static com.ags.ayolelang.DBHelper.DBContract.TAWARAN.TAWARAN_ANGGARAN;
import static com.ags.ayolelang.DBHelper.DBContract.TAWARAN.TAWARAN_ID;
import static com.ags.ayolelang.DBHelper.DBContract.TAWARAN.TAWARAN_LELANGID;
import static com.ags.ayolelang.DBHelper.DBContract.TAWARAN.TAWARAN_USERID;

public class TawaranHelper {
    private Context context;
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public TawaranHelper(Context context) {
        this.context = context;
    }

    public TawaranHelper open() throws SQLException {
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
        Cursor cursor = db.query(TABLE_TAWARAN, null, TAWARAN_LELANGID + "='" + id + "'", null, null, null, TAWARAN_ANGGARAN+" ASC", null);
        cursor.moveToFirst();
        Tawaran tawaran;
        if (cursor.getCount() > 0) {
            do{
                tawaran=new Tawaran();
                tawaran.setTawaran_id(cursor.getInt(cursor.getColumnIndexOrThrow(TAWARAN_ID)));
                tawaran.setTawaran_lelangid(cursor.getInt(cursor.getColumnIndexOrThrow(TAWARAN_LELANGID)));
                tawaran.setTawaran_userid(cursor.getString(cursor.getColumnIndexOrThrow(TAWARAN_USERID)));
                tawaran.setTawaran_anggaran(cursor.getLong(cursor.getColumnIndexOrThrow(TAWARAN_ANGGARAN)));
                tawarans.add(tawaran);
                cursor.moveToNext();
            }while (!cursor.isAfterLast());
        }
        cursor.close();
        db.endTransaction();
        return tawarans;
    }

    public long insert(Tawaran tawaran) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TAWARAN_ID, tawaran.getTawaran_id());
        contentValues.put(TAWARAN_LELANGID, tawaran.getTawaran_lelangid());
        contentValues.put(TAWARAN_USERID,tawaran.getTawaran_userid());
        contentValues.put(TAWARAN_ANGGARAN,tawaran.getTawaran_anggaran());
        return db.insert(TABLE_TAWARAN, null, contentValues);
    }

    public long delete(int id) {
        return db.delete(TABLE_TAWARAN, TAWARAN_ID+"='"+id+"'",null);
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

    public boolean isAlreadyBid(String userid,int lelang_id) {
        Cursor cursor = db.query(TABLE_TAWARAN, null, TAWARAN_USERID+"=? AND "+TAWARAN_LELANGID+"=?", new String[]{userid,lelang_id+""}, null, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        if (count > 0) {
            return true;
        }
        return false;
    }

    public Tawaran SingleTawaran(String userid,int lelang_id) {
        Cursor cursor = db.query(TABLE_TAWARAN, null, TAWARAN_USERID+"=? AND "+TAWARAN_LELANGID+"=?", new String[]{userid,lelang_id+""}, null, null, null, null);
        Tawaran tawaran=new Tawaran();
        cursor.moveToFirst();
        if (cursor.getCount()>0){
            cursor.moveToPosition(0);
            tawaran.setTawaran_id(cursor.getInt(cursor.getColumnIndexOrThrow(TAWARAN_ID)));
            tawaran.setTawaran_lelangid(cursor.getInt(cursor.getColumnIndexOrThrow(TAWARAN_LELANGID)));
            tawaran.setTawaran_userid(cursor.getString(cursor.getColumnIndexOrThrow(TAWARAN_USERID)));
            tawaran.setTawaran_anggaran(cursor.getLong(cursor.getColumnIndexOrThrow(TAWARAN_ANGGARAN)));
        }
        cursor.close();
        return tawaran;
    }

    public Tawaran getTawaran1st(int lelangid){
        Cursor cursor = db.query(TABLE_TAWARAN, null, TAWARAN_LELANGID+"=?", new String[]{lelangid+""}, null, null, TAWARAN_ANGGARAN+" ASC", null);
        Tawaran tawaran=new Tawaran();
        cursor.moveToFirst();
        if (cursor.getCount()>0){
            cursor.moveToPosition(0);
            tawaran.setTawaran_id(cursor.getInt(cursor.getColumnIndexOrThrow(TAWARAN_ID)));
            tawaran.setTawaran_lelangid(cursor.getInt(cursor.getColumnIndexOrThrow(TAWARAN_LELANGID)));
            tawaran.setTawaran_userid(cursor.getString(cursor.getColumnIndexOrThrow(TAWARAN_USERID)));
            tawaran.setTawaran_anggaran(cursor.getLong(cursor.getColumnIndexOrThrow(TAWARAN_ANGGARAN)));
        }
        cursor.close();
        return tawaran;
    }

    public void truncate() {
        db.execSQL("DELETE FROM " + TABLE_TAWARAN);
        db.execSQL("VACUUM");
    }
}
