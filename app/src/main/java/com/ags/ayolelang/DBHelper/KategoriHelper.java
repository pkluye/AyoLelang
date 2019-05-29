package com.ags.ayolelang.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ags.ayolelang.Models.Kategori;

import java.util.ArrayList;

import static com.ags.ayolelang.DBHelper.DBContract.KATEGORI.KATEGORI_ID;
import static com.ags.ayolelang.DBHelper.DBContract.KATEGORI.KATEGORI_NAMA;
import static com.ags.ayolelang.DBHelper.DBContract.KATEGORI.KATEGORI_PARENTID;
import static com.ags.ayolelang.DBHelper.DBContract.KATEGORI.KATEGORI_SUBPARENTID;
import static com.ags.ayolelang.DBHelper.DBContract.TABLE_KATEGORI;

public class KategoriHelper {
    private Context context;
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public KategoriHelper(Context context) {
        this.context = context;
    }

    public KategoriHelper open() throws SQLException {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public ArrayList<Kategori> getKategori(int id) {
        db.beginTransaction();
        ArrayList<Kategori> kategoris = new ArrayList<>();
        Cursor cursor = db.query(TABLE_KATEGORI, null, KATEGORI_SUBPARENTID + "='" + id + "'", null, null, null, KATEGORI_NAMA + " ASC", null);
        cursor.moveToFirst();
        Kategori kategori;
        if (cursor.getCount()>0){
            do{
                kategori=new Kategori();
                kategori.setKategori_id(cursor.getInt(cursor.getColumnIndexOrThrow(KATEGORI_ID)));
                kategori.setKategori_nama(cursor.getString(cursor.getColumnIndexOrThrow(KATEGORI_NAMA)));
                kategori.setKategori_parentid(cursor.getInt(cursor.getColumnIndexOrThrow(KATEGORI_PARENTID)));
                kategori.setKategori_subparentid(cursor.getInt(cursor.getColumnIndexOrThrow(KATEGORI_SUBPARENTID)));
                kategoris.add(kategori);
                cursor.moveToNext();
            }while (!cursor.isAfterLast());
        }
        cursor.close();
        db.endTransaction();
        return kategoris;
    }

    public ArrayList<Kategori> getKategori_SubParent(int id) {
        db.beginTransaction();
        ArrayList<Kategori> kategoris = new ArrayList<>();
        Cursor cursor = db.query(TABLE_KATEGORI, null, KATEGORI_PARENTID + "='" + id + "' AND "+KATEGORI_SUBPARENTID+" = 0", null, null, null, KATEGORI_NAMA + " ASC", null);
        cursor.moveToFirst();
        Kategori kategori;
        if (cursor.getCount()>0){
            do{
                kategori=new Kategori();
                kategori.setKategori_id(cursor.getInt(cursor.getColumnIndexOrThrow(KATEGORI_ID)));
                kategori.setKategori_nama(cursor.getString(cursor.getColumnIndexOrThrow(KATEGORI_NAMA)));
                kategori.setKategori_parentid(cursor.getInt(cursor.getColumnIndexOrThrow(KATEGORI_PARENTID)));
                kategori.setKategori_subparentid(cursor.getInt(cursor.getColumnIndexOrThrow(KATEGORI_SUBPARENTID)));
                kategoris.add(kategori);
                cursor.moveToNext();
            }while (!cursor.isAfterLast());
        }
        cursor.close();
        db.endTransaction();
        return kategoris;
    }



    public long insert(Kategori kategori){
        ContentValues contentValues=new ContentValues();
        contentValues.put(KATEGORI_ID,kategori.getKategori_id());
        contentValues.put(KATEGORI_NAMA,kategori.getKategori_nama());
        contentValues.put(KATEGORI_PARENTID,kategori.getKategori_parentid());
        contentValues.put(KATEGORI_SUBPARENTID,kategori.getKategori_subparentid());
        return db.insert(TABLE_KATEGORI,null,contentValues);
    }

    public void bulk_insert(ArrayList<Kategori> list) {
        if (list != null && list.size() > 0) {
            db.beginTransaction();
            try {
                for (Kategori kategori : list) {
                    insert(kategori);
                }
                db.setTransactionSuccessful();
            } catch (SQLException e) {
                Log.d("insert_error", e.getMessage());
            } finally {
                db.endTransaction();
            }
        }
    }

    public boolean isEmpty() {
        Cursor cursor = db.query(TABLE_KATEGORI, null, null, null, null, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        if (count == 0) {
            return true;
        }
        return false;
    }

    public void truncate(){
        db.execSQL("DELETE FROM "+TABLE_KATEGORI);
        db.execSQL("VACUUM");
    }
}
