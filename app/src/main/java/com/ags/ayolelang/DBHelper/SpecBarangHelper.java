package com.ags.ayolelang.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ags.ayolelang.Models.SpecBarang;

import java.util.ArrayList;

import static com.ags.ayolelang.DBHelper.DBContract.SPECBARANG.SPECBARANG_BAHAN;
import static com.ags.ayolelang.DBHelper.DBContract.SPECBARANG.SPECBARANG_HARGASATUAN;
import static com.ags.ayolelang.DBHelper.DBContract.SPECBARANG.SPECBARANG_ID;
import static com.ags.ayolelang.DBHelper.DBContract.SPECBARANG.SPECBARANG_KATEGORIID;
import static com.ags.ayolelang.DBHelper.DBContract.SPECBARANG.SPECBARANG_SATUAN;
import static com.ags.ayolelang.DBHelper.DBContract.SPECBARANG.SPECBARANG_UKURAN;
import static com.ags.ayolelang.DBHelper.DBContract.SPECBARANG.SPECBARANG_JMLSISI;
import static com.ags.ayolelang.DBHelper.DBContract.SPECBARANG.SPECBARANG_LAMINASI;
import static com.ags.ayolelang.DBHelper.DBContract.TABLE_SPECBARANG;

public class SpecBarangHelper {
    private Context context;
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public SpecBarangHelper(Context context) {
        this.context = context;
    }

    public SpecBarangHelper open() throws SQLException {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public ArrayList<String> getUkuran(String id) {
        db.beginTransaction();
        ArrayList<String> strings = new ArrayList<>();
        Cursor cursor = db.query(true,TABLE_SPECBARANG,new String[]{SPECBARANG_UKURAN},SPECBARANG_KATEGORIID+"=?",new String[]{id},null,null,null,null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            do {
                strings.add(cursor.getString(cursor.getColumnIndexOrThrow(SPECBARANG_UKURAN)));
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        db.endTransaction();
        return strings;
    }

    public ArrayList<String> getBahan(String id) {
        db.beginTransaction();
        ArrayList<String> strings = new ArrayList<>();
        Cursor cursor = db.query(true,TABLE_SPECBARANG,new String[]{SPECBARANG_BAHAN},SPECBARANG_KATEGORIID+"=?",new String[]{id},null,null,null,null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            do {
                strings.add(cursor.getString(cursor.getColumnIndexOrThrow(SPECBARANG_BAHAN)));
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        db.endTransaction();
        return strings;
    }

    public ArrayList<String> getSisi(String id) {
        db.beginTransaction();
        ArrayList<String> strings = new ArrayList<>();
        Cursor cursor = db.query(true,TABLE_SPECBARANG,new String[]{SPECBARANG_JMLSISI},SPECBARANG_KATEGORIID+"=?",new String[]{id},null,null,null,null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            do {
                strings.add(cursor.getString(cursor.getColumnIndexOrThrow(SPECBARANG_JMLSISI)));
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        db.endTransaction();
        return strings;
    }

    public ArrayList<String> getLaminasi(String id) {
        db.beginTransaction();
        ArrayList<String> strings = new ArrayList<>();
        Cursor cursor = db.query(true,TABLE_SPECBARANG,new String[]{SPECBARANG_LAMINASI},SPECBARANG_KATEGORIID+"=?",new String[]{id},null,null,null,null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            do {
                strings.add(cursor.getString(cursor.getColumnIndexOrThrow(SPECBARANG_LAMINASI)));
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        db.endTransaction();
        return strings;
    }

    public String getSatuan(String id) {
        db.beginTransaction();
        String satuan="";
        Cursor cursor = db.query(true,TABLE_SPECBARANG,new String[]{SPECBARANG_SATUAN},SPECBARANG_KATEGORIID+"=?",new String[]{id},null,null,null,null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            cursor.moveToPosition(0);
            satuan=cursor.getString(cursor.getColumnIndexOrThrow(SPECBARANG_SATUAN));
        }
        cursor.close();
        db.endTransaction();
        return satuan;
    }

    public long getHargaSatuan(String id, String Ukuran, String Bahan,String sisi,String Laminasi){
        db.beginTransaction();
        long harga=0;
        Cursor cursor = db.query(TABLE_SPECBARANG, null, SPECBARANG_KATEGORIID+ " = ? AND "+SPECBARANG_UKURAN+"= ? AND "+SPECBARANG_BAHAN+" = ? AND "+ SPECBARANG_JMLSISI +" = ? AND "+ SPECBARANG_LAMINASI +" = ?", new String[]{id,Ukuran,Bahan,sisi,Laminasi}, null, null, null, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            cursor.moveToPosition(0);
            harga=cursor.getLong(cursor.getColumnIndexOrThrow(SPECBARANG_HARGASATUAN));
        }
        cursor.close();
        db.endTransaction();
        return harga;
    }

    public long insert(SpecBarang specBarang) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SPECBARANG_ID, specBarang.getSpecbarang_id());
        contentValues.put(SPECBARANG_KATEGORIID,specBarang.getSpecbarang_kategoriid());
        contentValues.put(SPECBARANG_UKURAN, specBarang.getSpecbarang_ukuran());
        contentValues.put(SPECBARANG_BAHAN,specBarang.getSpecbarang_bahan());
        contentValues.put(SPECBARANG_JMLSISI,specBarang.getSpecbarang_jmlsisi());
        contentValues.put(SPECBARANG_LAMINASI,specBarang.getSpecbarang_laminasi());
        contentValues.put(SPECBARANG_HARGASATUAN,specBarang.getSpecbarang_hargasatuan());
        contentValues.put(SPECBARANG_SATUAN,specBarang.getSpecbarang_satuan());
        return db.insert(TABLE_SPECBARANG, null, contentValues);
    }

    public void bulk_insert(ArrayList<SpecBarang> list) {
        if (list != null && list.size() > 0) {
            db.beginTransaction();
            try {
                for (SpecBarang specBarang : list) {
                    insert(specBarang);
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
        db.execSQL("DELETE FROM " + TABLE_SPECBARANG);
        db.execSQL("VACUUM");
    }

    public boolean isempty() {
        Cursor cursor = db.query(TABLE_SPECBARANG, null, null, null, null, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        if (count == 0) {
            return true;
        }
        return false;
    }

}
