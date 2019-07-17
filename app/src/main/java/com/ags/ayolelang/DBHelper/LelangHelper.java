package com.ags.ayolelang.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.AbstractCursor;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ags.ayolelang.Models.Lelang;

import java.util.ArrayList;

import static com.ags.ayolelang.DBHelper.DBContract.KATEGORI.KATEGORI_ID;
import static com.ags.ayolelang.DBHelper.DBContract.KATEGORI.KATEGORI_PARENTID;
import static com.ags.ayolelang.DBHelper.DBContract.LELANG.LELANG_ALAMAT;
import static com.ags.ayolelang.DBHelper.DBContract.LELANG.LELANG_ANGGARAN;
import static com.ags.ayolelang.DBHelper.DBContract.LELANG.LELANG_DESKRIPSI;
import static com.ags.ayolelang.DBHelper.DBContract.LELANG.LELANG_FILEURL;
import static com.ags.ayolelang.DBHelper.DBContract.LELANG.LELANG_ID;
import static com.ags.ayolelang.DBHelper.DBContract.LELANG.LELANG_JUDUL;
import static com.ags.ayolelang.DBHelper.DBContract.LELANG.LELANG_KOTA;
import static com.ags.ayolelang.DBHelper.DBContract.LELANG.LELANG_MITRAID;
import static com.ags.ayolelang.DBHelper.DBContract.LELANG.LELANG_PEMBAYARAN;
import static com.ags.ayolelang.DBHelper.DBContract.LELANG.LELANG_STATUS;
import static com.ags.ayolelang.DBHelper.DBContract.LELANG.LELANG_TGLMULAI;
import static com.ags.ayolelang.DBHelper.DBContract.LELANG.LELANG_TGLSELESAI;
import static com.ags.ayolelang.DBHelper.DBContract.LELANG.LELANG_USERID;
import static com.ags.ayolelang.DBHelper.DBContract.PEKERJAAN.PEKERJAAN_ID;
import static com.ags.ayolelang.DBHelper.DBContract.PEKERJAAN.PEKERJAAN_KATEGORIID;
import static com.ags.ayolelang.DBHelper.DBContract.PEKERJAAN.PEKERJAAN_LELANGID;
import static com.ags.ayolelang.DBHelper.DBContract.TABLE_KATEGORI;
import static com.ags.ayolelang.DBHelper.DBContract.TABLE_LELANG;
import static com.ags.ayolelang.DBHelper.DBContract.TABLE_PEKERJAAN;
import static com.ags.ayolelang.DBHelper.DBContract.TABLE_TAWARAN;

public class LelangHelper {
    private Context context;
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public LelangHelper(Context context) {
        this.context = context;
    }

    public LelangHelper open() throws SQLException {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public ArrayList<Lelang> getAllLelang() {
        db.beginTransaction();
        ArrayList<Lelang> lelangs = new ArrayList<>();
        Cursor cursor = db.query(TABLE_LELANG, null, LELANG_STATUS + " = 3", null, null, null, null, null);
        //Cursor cursor = db.query(TABLE_LELANG, null,null, null, null, null, null, String.valueOf(limit));
        cursor.moveToFirst();
        Lelang lelang;
        if (cursor.getCount() > 0) {
            do {
                lelang = new Lelang();
                lelang.setLelang_id(cursor.getInt(cursor.getColumnIndexOrThrow(LELANG_ID)));
                lelang.setLelang_judul(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_JUDUL)));
                lelang.setLelang_alamat(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_ALAMAT)));
                lelang.setLelang_anggaran(cursor.getLong(cursor.getColumnIndexOrThrow(LELANG_ANGGARAN)));
                lelang.setLelang_status(cursor.getInt(cursor.getColumnIndexOrThrow(LELANG_STATUS)));
                lelang.setLelang_fileurl(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_FILEURL)));
                lelang.setLelang_kota(cursor.getInt(cursor.getColumnIndexOrThrow(LELANG_KOTA)));
                lelang.setLelang_deskripsi(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_DESKRIPSI)));
                lelang.setLelang_pembayaran(cursor.getInt(cursor.getColumnIndexOrThrow(LELANG_PEMBAYARAN)));
                lelang.setLelang_tglmulai(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_TGLMULAI)));
                lelang.setLelang_tglselesai(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_TGLSELESAI)));
                lelang.setLelang_userid(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_USERID)));
                lelang.setLelang_mitraid(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_MITRAID)));
                lelangs.add(lelang);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        db.endTransaction();
        return lelangs;
    }

    public ArrayList<Lelang> getbyKategoriParent(String parent) {
        db.beginTransaction();
        ArrayList<Lelang> lelangs = new ArrayList<>();
        String sql = "SELECT DISTINCT " + LELANG_ID + " as number,l.* FROM " + TABLE_LELANG + " l JOIN " + TABLE_PEKERJAAN + " p ON l." + LELANG_ID + "=p." + PEKERJAAN_LELANGID + " WHERE p." + PEKERJAAN_KATEGORIID + " IN (SELECT " + KATEGORI_ID + " FROM " + TABLE_KATEGORI + " WHERE " + KATEGORI_PARENTID + "=? AND " + LELANG_STATUS + "=3)";
        Cursor cursor = db.rawQuery(sql, new String[]{parent});
        //Cursor cursor = db.query(TABLE_LELANG, null,null, null, null, null, null, String.valueOf(limit));
        cursor.moveToFirst();
        Lelang lelang;
        if (cursor.getCount() > 0) {
            do {
                lelang = new Lelang();
                lelang.setLelang_id(cursor.getInt(cursor.getColumnIndexOrThrow(LELANG_ID)));
                lelang.setLelang_judul(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_JUDUL)));
                lelang.setLelang_alamat(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_ALAMAT)));
                lelang.setLelang_anggaran(cursor.getLong(cursor.getColumnIndexOrThrow(LELANG_ANGGARAN)));
                lelang.setLelang_status(cursor.getInt(cursor.getColumnIndexOrThrow(LELANG_STATUS)));
                lelang.setLelang_fileurl(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_FILEURL)));
                lelang.setLelang_kota(cursor.getInt(cursor.getColumnIndexOrThrow(LELANG_KOTA)));
                lelang.setLelang_deskripsi(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_DESKRIPSI)));
                lelang.setLelang_pembayaran(cursor.getInt(cursor.getColumnIndexOrThrow(LELANG_PEMBAYARAN)));
                lelang.setLelang_tglmulai(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_TGLMULAI)));
                lelang.setLelang_tglselesai(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_TGLSELESAI)));
                lelang.setLelang_userid(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_USERID)));
                lelang.setLelang_mitraid(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_MITRAID)));
                lelangs.add(lelang);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        db.endTransaction();
        return lelangs;
    }

    public ArrayList<Lelang> getbyKategoriParentAndSearch(String parent, String search) {
        db.beginTransaction();
        ArrayList<Lelang> lelangs = new ArrayList<>();
        String sql = "SELECT DISTINCT " + LELANG_ID + " as number,l.* FROM " + TABLE_LELANG + " l JOIN " + TABLE_PEKERJAAN + " p ON l." + LELANG_ID + "=p." + PEKERJAAN_ID + " WHERE p." + PEKERJAAN_KATEGORIID + " IN (SELECT " + KATEGORI_ID + " FROM " + TABLE_KATEGORI + " WHERE " + KATEGORI_PARENTID + "=? AND " + LELANG_STATUS + "=3 AND " + LELANG_JUDUL + " LIKE '%" + search + "%')";
        Cursor cursor = db.rawQuery(sql, new String[]{parent});
        //Cursor cursor = db.query(TABLE_LELANG, null,null, null, null, null, null, String.valueOf(limit));
        cursor.moveToFirst();
        Lelang lelang;
        if (cursor.getCount() > 0) {
            do {
                lelang = new Lelang();
                lelang.setLelang_id(cursor.getInt(cursor.getColumnIndexOrThrow(LELANG_ID)));
                lelang.setLelang_judul(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_JUDUL)));
                lelang.setLelang_alamat(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_ALAMAT)));
                lelang.setLelang_anggaran(cursor.getLong(cursor.getColumnIndexOrThrow(LELANG_ANGGARAN)));
                lelang.setLelang_status(cursor.getInt(cursor.getColumnIndexOrThrow(LELANG_STATUS)));
                lelang.setLelang_fileurl(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_FILEURL)));
                lelang.setLelang_kota(cursor.getInt(cursor.getColumnIndexOrThrow(LELANG_KOTA)));
                lelang.setLelang_deskripsi(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_DESKRIPSI)));
                lelang.setLelang_pembayaran(cursor.getInt(cursor.getColumnIndexOrThrow(LELANG_PEMBAYARAN)));
                lelang.setLelang_tglmulai(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_TGLMULAI)));
                lelang.setLelang_tglselesai(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_TGLSELESAI)));
                lelang.setLelang_userid(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_USERID)));
                lelang.setLelang_mitraid(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_MITRAID)));
                lelangs.add(lelang);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        db.endTransaction();
        return lelangs;
    }

    public ArrayList<Lelang> getLelangbyUser(String user_id,int status) {
        db.beginTransaction();
        ArrayList<Lelang> lelangs = new ArrayList<>();
        Cursor cursor = db.query(TABLE_LELANG, null, LELANG_USERID + " = '" + user_id + "' AND "+LELANG_STATUS+" = "+status, null, null, null, null, null);
        //Cursor cursor = db.query(TABLE_LELANG, null,null, null, null, null, null, String.valueOf(limit));
        cursor.moveToFirst();
        Lelang lelang;
        if (cursor.getCount() > 0) {
            do {
                lelang = new Lelang();
                lelang.setLelang_id(cursor.getInt(cursor.getColumnIndexOrThrow(LELANG_ID)));
                lelang.setLelang_judul(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_JUDUL)));
                lelang.setLelang_alamat(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_ALAMAT)));
                lelang.setLelang_anggaran(cursor.getLong(cursor.getColumnIndexOrThrow(LELANG_ANGGARAN)));
                lelang.setLelang_status(cursor.getInt(cursor.getColumnIndexOrThrow(LELANG_STATUS)));
                lelang.setLelang_fileurl(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_FILEURL)));
                lelang.setLelang_kota(cursor.getInt(cursor.getColumnIndexOrThrow(LELANG_KOTA)));
                lelang.setLelang_deskripsi(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_DESKRIPSI)));
                lelang.setLelang_pembayaran(cursor.getInt(cursor.getColumnIndexOrThrow(LELANG_PEMBAYARAN)));
                lelang.setLelang_tglmulai(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_TGLMULAI)));
                lelang.setLelang_tglselesai(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_TGLSELESAI)));
                lelang.setLelang_userid(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_USERID)));
                lelang.setLelang_mitraid(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_MITRAID)));
                lelangs.add(lelang);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        db.endTransaction();
        return lelangs;
    }

    public ArrayList<Lelang> getLelangbyMitra(String user_id,int status) {
        db.beginTransaction();
        ArrayList<Lelang> lelangs = new ArrayList<>();
        Cursor cursor=null;
        if (status==3){
            String sql="SELECT * FROM "+TABLE_LELANG+" l JOIN "+TABLE_TAWARAN+" t ON l.lelang_id=t.tawaran_lelangid WHERE t.tawaran_userid='"+user_id+"' AND l.lelang_status=3";
            cursor = db.rawQuery(sql,null);
        }else{
            cursor = db.query(TABLE_LELANG, null, LELANG_MITRAID + " = '" + user_id + "' AND "+LELANG_STATUS+" = "+status, null, null, null, null, null);
        }
        //Cursor cursor = db.query(TABLE_LELANG, null,null, null, null, null, null, String.valueOf(limit));
        cursor.moveToFirst();
        Lelang lelang;
        if (cursor.getCount() > 0) {
            do {
                lelang = new Lelang();
                lelang.setLelang_id(cursor.getInt(cursor.getColumnIndexOrThrow(LELANG_ID)));
                lelang.setLelang_judul(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_JUDUL)));
                lelang.setLelang_alamat(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_ALAMAT)));
                lelang.setLelang_anggaran(cursor.getLong(cursor.getColumnIndexOrThrow(LELANG_ANGGARAN)));
                lelang.setLelang_status(cursor.getInt(cursor.getColumnIndexOrThrow(LELANG_STATUS)));
                lelang.setLelang_fileurl(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_FILEURL)));
                lelang.setLelang_kota(cursor.getInt(cursor.getColumnIndexOrThrow(LELANG_KOTA)));
                lelang.setLelang_deskripsi(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_DESKRIPSI)));
                lelang.setLelang_pembayaran(cursor.getInt(cursor.getColumnIndexOrThrow(LELANG_PEMBAYARAN)));
                lelang.setLelang_tglmulai(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_TGLMULAI)));
                lelang.setLelang_tglselesai(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_TGLSELESAI)));
                lelang.setLelang_userid(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_USERID)));
                lelang.setLelang_mitraid(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_MITRAID)));
                lelangs.add(lelang);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        db.endTransaction();
        return lelangs;
    }

    public Lelang getLelang(int id) {
        db.beginTransaction();
        Cursor cursor = db.query(TABLE_LELANG, null, LELANG_ID + "='" + id + "'", null, null, null, null, null);
        //Cursor cursor = db.query(TABLE_LELANG, null,null, null, null, null, null, String.valueOf(limit));
        cursor.moveToFirst();
        Lelang lelang = new Lelang();
        if (cursor.getCount() > 0) {
            cursor.moveToPosition(0);
            lelang.setLelang_id(cursor.getInt(cursor.getColumnIndexOrThrow(LELANG_ID)));
            lelang.setLelang_judul(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_JUDUL)));
            lelang.setLelang_alamat(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_ALAMAT)));
            lelang.setLelang_anggaran(cursor.getLong(cursor.getColumnIndexOrThrow(LELANG_ANGGARAN)));
            lelang.setLelang_status(cursor.getInt(cursor.getColumnIndexOrThrow(LELANG_STATUS)));
            lelang.setLelang_fileurl(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_FILEURL)));
            lelang.setLelang_kota(cursor.getInt(cursor.getColumnIndexOrThrow(LELANG_KOTA)));
            lelang.setLelang_deskripsi(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_DESKRIPSI)));
            lelang.setLelang_pembayaran(cursor.getInt(cursor.getColumnIndexOrThrow(LELANG_PEMBAYARAN)));
            lelang.setLelang_tglmulai(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_TGLMULAI)));
            lelang.setLelang_tglselesai(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_TGLSELESAI)));
            lelang.setLelang_userid(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_USERID)));
            lelang.setLelang_mitraid(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_MITRAID)));
        }
        cursor.close();
        db.endTransaction();
        return lelang;
    }

    public long insert(Lelang lelang) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(LELANG_ID, lelang.getLelang_id());
        contentValues.put(LELANG_JUDUL, lelang.getLelang_judul());
        contentValues.put(LELANG_ALAMAT, lelang.getLelang_alamat());
        contentValues.put(LELANG_ANGGARAN, lelang.getLelang_anggaran());
        contentValues.put(LELANG_FILEURL, lelang.getLelang_fileurl());
        contentValues.put(LELANG_DESKRIPSI, lelang.getLelang_deskripsi());
        contentValues.put(LELANG_KOTA, lelang.getLelang_kota());
        contentValues.put(LELANG_PEMBAYARAN, lelang.getLelang_pembayaran());
        contentValues.put(LELANG_TGLMULAI, lelang.getLelang_tglmulai());
        contentValues.put(LELANG_TGLSELESAI, lelang.getLelang_tglselesai());
        contentValues.put(LELANG_STATUS, lelang.getLelang_status());
        contentValues.put(LELANG_USERID, lelang.getLelang_userid());
        contentValues.put(LELANG_MITRAID, lelang.getLelang_mitraid());
        return db.insert(TABLE_LELANG, null, contentValues);
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
        Cursor cursor = db.query(TABLE_LELANG, null, null, null, null, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        if (count == 0) {
            return true;
        }
        return false;
    }

    public void truncate() {
        db.execSQL("DELETE FROM " + TABLE_LELANG);
        db.execSQL("VACUUM");
    }

    public ArrayList<Lelang> getlelangbyjudul(String s) {
        db.beginTransaction();
        ArrayList<Lelang> lelangs = new ArrayList<>();
        Cursor cursor = db.query(TABLE_LELANG, null, LELANG_JUDUL + " LIKE '%" + s + "%'", null, null, null, null, null);
        //Cursor cursor = db.query(TABLE_LELANG, null,null, null, null, null, null, String.valueOf(limit));
        cursor.moveToFirst();
        Lelang lelang;
        if (cursor.getCount() > 0) {
            do {
                lelang = new Lelang();
                lelang.setLelang_id(cursor.getInt(cursor.getColumnIndexOrThrow(LELANG_ID)));
                lelang.setLelang_judul(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_JUDUL)));
                lelang.setLelang_alamat(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_ALAMAT)));
                lelang.setLelang_anggaran(cursor.getLong(cursor.getColumnIndexOrThrow(LELANG_ANGGARAN)));
                lelang.setLelang_status(cursor.getInt(cursor.getColumnIndexOrThrow(LELANG_STATUS)));
                lelang.setLelang_fileurl(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_FILEURL)));
                lelang.setLelang_mitraid(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_MITRAID)));
                lelang.setLelang_kota(cursor.getInt(cursor.getColumnIndexOrThrow(LELANG_KOTA)));
                lelang.setLelang_deskripsi(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_DESKRIPSI)));
                lelang.setLelang_pembayaran(cursor.getInt(cursor.getColumnIndexOrThrow(LELANG_PEMBAYARAN)));
                lelang.setLelang_tglmulai(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_TGLMULAI)));
                lelang.setLelang_tglselesai(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_TGLSELESAI)));
                lelang.setLelang_userid(cursor.getString(cursor.getColumnIndexOrThrow(LELANG_USERID)));
                lelangs.add(lelang);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        db.endTransaction();
        return lelangs;
    }
}
