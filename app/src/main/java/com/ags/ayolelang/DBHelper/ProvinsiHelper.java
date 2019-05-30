package com.ags.ayolelang.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ags.ayolelang.Models.Provinsi;

import java.util.ArrayList;

import static com.ags.ayolelang.DBHelper.DBContract.PROVINSI.PROVINSI_ID;
import static com.ags.ayolelang.DBHelper.DBContract.PROVINSI.PROVINSI_NAMA;
import static com.ags.ayolelang.DBHelper.DBContract.TABLE_PROVINSI;

public class ProvinsiHelper {

    private Context context;
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public ProvinsiHelper(Context context) {
        this.context = context;
    }

    public ProvinsiHelper open() throws SQLException {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public ArrayList<Provinsi> getAllData() {
        db.beginTransaction();
        ArrayList<Provinsi> provinsis = new ArrayList<>();
        Cursor cursor = db.query(TABLE_PROVINSI, null, null, null, null, null, PROVINSI_NAMA + " ASC", null);
        cursor.moveToFirst();
        Provinsi provinsi;
        if (cursor.getCount() > 0) {
            do {
                provinsi = new Provinsi();
                provinsi.setId(cursor.getInt(cursor.getColumnIndexOrThrow(PROVINSI_ID)));
                provinsi.setNama(cursor.getString(cursor.getColumnIndexOrThrow(PROVINSI_NAMA)));
                provinsis.add(provinsi);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        db.endTransaction();
        return provinsis;
    }

    public boolean isEmpty() {
        Cursor cursor = db.query(TABLE_PROVINSI, null, null, null, null, null, PROVINSI_NAMA + " ASC", null);
        int count = cursor.getCount();
        cursor.close();
        if (count == 0) {
            return true;
        }
        return false;
    }

    public long insert(Provinsi provinsi) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(PROVINSI_ID, provinsi.getId());
        initialValues.put(PROVINSI_NAMA, provinsi.getNama());
        return db.insert(TABLE_PROVINSI, null, initialValues);
    }

    public void bulk_insert(ArrayList<Provinsi> list) {
        if (list != null && list.size() > 0) {
            db.beginTransaction();
            try {
                for (Provinsi provinsi : list) {
                    insert(provinsi);
                }
                db.setTransactionSuccessful();
            } catch (SQLException e) {
                Log.d("insert_error", e.getMessage());
            } finally {
                db.endTransaction();
            }
        }
    }

    public Provinsi getProvinsibyname(String s) {
        Cursor cursor = db.query(TABLE_PROVINSI, null, PROVINSI_NAMA + "=?", new String[]{s}, null, null, PROVINSI_NAMA + " ASC", null);
        cursor.moveToFirst();
        Provinsi provinsi = new Provinsi();
        if (cursor.getCount() > 0) {
            cursor.moveToPosition(0);
            provinsi.setId(cursor.getInt(cursor.getColumnIndexOrThrow(PROVINSI_ID)));
            provinsi.setNama(cursor.getString(cursor.getColumnIndexOrThrow(PROVINSI_NAMA)));
        }
        cursor.close();
        return provinsi;
    }

    public Provinsi getProvinsibyProvid(int i) {
        Cursor cursor = db.query(TABLE_PROVINSI, null, PROVINSI_ID + "='" + i + "'", null, null, null, PROVINSI_NAMA + " ASC", null);
        cursor.moveToFirst();
        Provinsi provinsi = new Provinsi();
        if (cursor.getCount() > 0) {
            cursor.moveToPosition(0);
            provinsi.setId(cursor.getInt(cursor.getColumnIndexOrThrow(PROVINSI_ID)));
            provinsi.setNama(cursor.getString(cursor.getColumnIndexOrThrow(PROVINSI_NAMA)));
        }
        cursor.close();
        return provinsi;
    }

    public void truncate() {
        db.execSQL("DELETE FROM " + TABLE_PROVINSI);
        db.execSQL("VACUUM");
    }
}
