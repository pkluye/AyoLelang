package com.ags.ayolelang.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ags.ayolelang.Models.Kota;

import java.util.ArrayList;

import static com.ags.ayolelang.DBHelper.DBContract.KOTA.KOTA_ID;
import static com.ags.ayolelang.DBHelper.DBContract.KOTA.KOTA_NAMA;
import static com.ags.ayolelang.DBHelper.DBContract.KOTA.KOTA_PROVINSIID;
import static com.ags.ayolelang.DBHelper.DBContract.TABLE_KOTA;

public class KotaHelper {
    private Context context;
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public KotaHelper(Context context) {
        this.context = context;
    }

    public KotaHelper open() throws SQLException {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public ArrayList<Kota> getKotabyProvId(int prov_id) {
        db.beginTransaction();
        ArrayList<Kota> kotas = new ArrayList<>();
        Cursor cursor = db.query(TABLE_KOTA, null, KOTA_PROVINSIID + "='" + prov_id + "'", null, null, null, KOTA_NAMA + " ASC", null);
        cursor.moveToFirst();
        Kota kota;
        if (cursor.getCount() > 0) {
            do {
                kota = new Kota();
                kota.setId(cursor.getInt(cursor.getColumnIndexOrThrow(KOTA_ID)));
                kota.setNama(cursor.getString(cursor.getColumnIndexOrThrow(KOTA_NAMA)));
                kota.setProvinsi_id(cursor.getInt(cursor.getColumnIndexOrThrow(KOTA_PROVINSIID)));
                kotas.add(kota);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        db.endTransaction();
        return kotas;
    }

    public Kota getKotabyidKota(int i) {
        Cursor cursor = db.query(TABLE_KOTA, null, KOTA_ID + "='" + i + "'", null, null, null, KOTA_NAMA + " ASC", null);
        cursor.moveToFirst();
        Kota kota = kota = new Kota();
        if (cursor.getCount() > 0) {
            cursor.moveToPosition(0);
            kota.setId(cursor.getInt(cursor.getColumnIndexOrThrow(KOTA_ID)));
            kota.setNama(cursor.getString(cursor.getColumnIndexOrThrow(KOTA_NAMA)));
            kota.setProvinsi_id(cursor.getInt(cursor.getColumnIndexOrThrow(KOTA_PROVINSIID)));
        }
        cursor.close();
        return kota;
    }

    public Kota getKotabyname(String s) {
        Cursor cursor = db.query(TABLE_KOTA, null, KOTA_NAMA + "=?", new String[]{s}, null, null, KOTA_NAMA + " ASC", null);
        cursor.moveToFirst();
        Kota kota = kota = new Kota();
        if (cursor.getCount() > 0) {
            cursor.moveToPosition(0);
            kota.setId(cursor.getInt(cursor.getColumnIndexOrThrow(KOTA_ID)));
            kota.setNama(cursor.getString(cursor.getColumnIndexOrThrow(KOTA_NAMA)));
            kota.setProvinsi_id(cursor.getInt(cursor.getColumnIndexOrThrow(KOTA_PROVINSIID)));
        }
        cursor.close();
        return kota;
    }

    public boolean isEmpty() {
        Cursor cursor = db.query(TABLE_KOTA, null, null, null, null, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        if (count == 0) {
            return true;
        }
        return false;
    }

    public long insert(Kota kota) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KOTA_ID, kota.getId());
        contentValues.put(KOTA_NAMA, kota.getNama());
        contentValues.put(KOTA_PROVINSIID, kota.getProvinsi_id());
        return db.insert(TABLE_KOTA, null, contentValues);
    }

    public void bulk_insert(ArrayList<Kota> list) {
        if (list != null && list.size() > 0) {
            db.beginTransaction();
            try {
                for (Kota kota : list) {
                    insert(kota);
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
        db.execSQL("DELETE FROM " + TABLE_KOTA);
        db.execSQL("VACUUM");
    }
}
