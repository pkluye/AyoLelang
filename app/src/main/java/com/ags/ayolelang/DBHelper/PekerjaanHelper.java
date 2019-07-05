package com.ags.ayolelang.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ags.ayolelang.Models.Pekerjaan;

import java.util.ArrayList;

import static com.ags.ayolelang.DBHelper.DBContract.PEKERJAAN.PEKERJAAN_BAHAN;
import static com.ags.ayolelang.DBHelper.DBContract.PEKERJAAN.PEKERJAAN_CATATAN;
import static com.ags.ayolelang.DBHelper.DBContract.PEKERJAAN.PEKERJAAN_HARGA;
import static com.ags.ayolelang.DBHelper.DBContract.PEKERJAAN.PEKERJAAN_ID;
import static com.ags.ayolelang.DBHelper.DBContract.PEKERJAAN.PEKERJAAN_JMLSISI;
import static com.ags.ayolelang.DBHelper.DBContract.PEKERJAAN.PEKERJAAN_JUMLAH;
import static com.ags.ayolelang.DBHelper.DBContract.PEKERJAAN.PEKERJAAN_KATEGORIID;
import static com.ags.ayolelang.DBHelper.DBContract.PEKERJAAN.PEKERJAAN_LAMINASI;
import static com.ags.ayolelang.DBHelper.DBContract.PEKERJAAN.PEKERJAAN_LELANGID;
import static com.ags.ayolelang.DBHelper.DBContract.PEKERJAAN.PEKERJAAN_STATUS;
import static com.ags.ayolelang.DBHelper.DBContract.PEKERJAAN.PEKERJAAN_UKURAN;
import static com.ags.ayolelang.DBHelper.DBContract.TABLE_PEKERJAAN;

public class PekerjaanHelper {
    private Context context;
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public PekerjaanHelper(Context context) {
        this.context = context;
    }

    public PekerjaanHelper open() throws SQLException {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public ArrayList<Pekerjaan> getPekerjaan(int id){
        db.beginTransaction();
        ArrayList<Pekerjaan> pekerjaans=new ArrayList<>();
        Cursor cursor = db.query(TABLE_PEKERJAAN, null, PEKERJAAN_LELANGID+"='"+id+"'", null, null, null, null, null);
        cursor.moveToFirst();
        Pekerjaan pekerjaan;
        if (cursor.getCount()>0){
            do{
                pekerjaan=new Pekerjaan();
                pekerjaan.setPekerjaan_id(cursor.getInt(cursor.getColumnIndexOrThrow(PEKERJAAN_ID)));
                pekerjaan.setPekerjaan_kategoriid(cursor.getInt(cursor.getColumnIndexOrThrow(PEKERJAAN_KATEGORIID)));
                pekerjaan.setPekerjaan_lelangid(cursor.getInt(cursor.getColumnIndexOrThrow(PEKERJAAN_LELANGID)));
                pekerjaan.setPekerjaan_ukuran(cursor.getString(cursor.getColumnIndexOrThrow(PEKERJAAN_UKURAN)));
                pekerjaan.setPekerjaan_bahan(cursor.getString(cursor.getColumnIndexOrThrow(PEKERJAAN_BAHAN)));
                pekerjaan.setPekerjaan_jmlsisi(cursor.getString(cursor.getColumnIndexOrThrow(PEKERJAAN_JMLSISI)));
                pekerjaan.setPekerjaan_laminasi(cursor.getString(cursor.getColumnIndexOrThrow(PEKERJAAN_LAMINASI)));
                pekerjaan.setPekerjaan_harga(cursor.getLong(cursor.getColumnIndexOrThrow(PEKERJAAN_HARGA)));
                pekerjaan.setPekerjaan_jumlah(cursor.getInt(cursor.getColumnIndexOrThrow(PEKERJAAN_JUMLAH)));
                pekerjaan.setPekerjaan_catatan(cursor.getString(cursor.getColumnIndexOrThrow(PEKERJAAN_CATATAN)));
                pekerjaan.setPekerjaan_status(cursor.getInt(cursor.getColumnIndexOrThrow(PEKERJAAN_STATUS)));
                pekerjaans.add(pekerjaan);
                cursor.moveToNext();
            }while (!cursor.isAfterLast());
        }
        cursor.close();
        db.endTransaction();
        return pekerjaans;
    }

    public boolean isempty() {
        Cursor cursor = db.query(TABLE_PEKERJAAN, null, null, null, null, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        if (count == 0) {
            return true;
        }
        return false;
    }

    public void truncate(){
        db.execSQL("DELETE FROM "+TABLE_PEKERJAAN);
        db.execSQL("VACUUM");
    }

    public void bulk_insert(ArrayList<Pekerjaan> list) {
        if (list != null && list.size() > 0) {
            db.beginTransaction();
            try {
                for (Pekerjaan pekerjaan : list) {
                    insert(pekerjaan);
                }
                db.setTransactionSuccessful();
            } catch (SQLException e) {
                Log.d("insert_error", e.getMessage());
            } finally {
                db.endTransaction();
            }
        }
    }

    private long insert(Pekerjaan pekerjaan) {
        ContentValues contentValue= new ContentValues();
        contentValue.put(PEKERJAAN_ID,pekerjaan.getPekerjaan_id());
        contentValue.put(PEKERJAAN_KATEGORIID,pekerjaan.getPekerjaan_kategoriid());
        contentValue.put(PEKERJAAN_LELANGID,pekerjaan.getPekerjaan_lelangid());
        contentValue.put(PEKERJAAN_CATATAN,pekerjaan.getPekerjaan_catatan());
        contentValue.put(PEKERJAAN_JUMLAH,pekerjaan.getPekerjaan_jumlah());
        contentValue.put(PEKERJAAN_JMLSISI,pekerjaan.getPekerjaan_jmlsisi());
        contentValue.put(PEKERJAAN_LAMINASI,pekerjaan.getPekerjaan_laminasi());
        contentValue.put(PEKERJAAN_HARGA,pekerjaan.getPekerjaan_harga());
        contentValue.put(PEKERJAAN_UKURAN,pekerjaan.getPekerjaan_ukuran());
        contentValue.put(PEKERJAAN_BAHAN,pekerjaan.getPekerjaan_bahan());
        contentValue.put(PEKERJAAN_STATUS,pekerjaan.getPekerjaan_status());
        return db.insert(TABLE_PEKERJAAN,null,contentValue);
    }
}
