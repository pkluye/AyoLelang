package com.ags.ayolelang.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ags.ayolelang.Models.User;

import java.util.ArrayList;

import static com.ags.ayolelang.DBHelper.DBContract.TABLE_USER;
import static com.ags.ayolelang.DBHelper.DBContract.USER.USER_ALAMAT;
import static com.ags.ayolelang.DBHelper.DBContract.USER.USER_EMAIL;
import static com.ags.ayolelang.DBHelper.DBContract.USER.USER_ID;
import static com.ags.ayolelang.DBHelper.DBContract.USER.USER_IMGURL;
import static com.ags.ayolelang.DBHelper.DBContract.USER.USER_NAMA;
import static com.ags.ayolelang.DBHelper.DBContract.USER.USER_TELPON;

public class UserHelper {
    private Context context;
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public UserHelper(Context context) {
        this.context = context;
    }

    public UserHelper open() throws SQLException {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public User getSingleUser(String id) {
        db.beginTransaction();
        Cursor cursor = db.query(TABLE_USER, null, USER_ID + "='" + id + "'", null, null, null, null, null);
        cursor.moveToFirst();
        User user=new User();
        if (cursor.getCount() > 0) {
            cursor.moveToPosition(0);
            user.setUser_id(cursor.getString(cursor.getColumnIndexOrThrow(USER_ID)));
            user.setUser_nama(cursor.getString(cursor.getColumnIndexOrThrow(USER_NAMA)));
            user.setUser_email(cursor.getString(cursor.getColumnIndexOrThrow(USER_EMAIL)));
            user.setUser_telpon(cursor.getString(cursor.getColumnIndexOrThrow(USER_TELPON)));
            user.setUser_alamat(cursor.getString(cursor.getColumnIndexOrThrow(USER_ALAMAT)));
            user.setUser_imgurl(cursor.getString(cursor.getColumnIndexOrThrow(USER_IMGURL)));
        }
        //Log.d("useer",user.toString());
        cursor.close();
        db.endTransaction();
        return user;
    }

    public long insert(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_ID, user.getUser_id());
        contentValues.put(USER_NAMA, user.getUser_nama());
        contentValues.put(USER_EMAIL, user.getUser_email());
        contentValues.put(USER_TELPON, user.getUser_telpon());
        contentValues.put(USER_IMGURL,user.getUser_imgurl());
        contentValues.put(USER_ALAMAT,user.getUser_alamat());
        return db.insert(TABLE_USER, null, contentValues);
    }

    public void bulk_insert(ArrayList<User> list) {
        if (list != null && list.size() > 0) {
            db.beginTransaction();
            try {
                for (User user : list) {
                    insert(user);
                }
                db.setTransactionSuccessful();
            } catch (SQLException e) {
                Log.e("insert_error", e.getMessage());
            } finally {
                db.endTransaction();
            }
        }
    }

    public boolean isEmpty() {
        Cursor cursor = db.query(TABLE_USER, null, null, null, null, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        if (count == 0) {
            return true;
        }
        return false;
    }

    public void truncate() {
        db.execSQL("DELETE FROM " + TABLE_USER);
        db.execSQL("VACUUM");
    }

    public ArrayList<User> getUserbyName(String s) {
        db.beginTransaction();
        ArrayList<User> users=new ArrayList<>();
        Cursor cursor = db.query(TABLE_USER, null, USER_NAMA + " LIKE '%" + s + "%'", null, null, null, USER_NAMA+" ASC", null);
        cursor.moveToFirst();
        User user;
        if (cursor.getCount() > 0) {
            do {
                user=new User();
                user.setUser_id(cursor.getString(cursor.getColumnIndexOrThrow(USER_ID)));
                user.setUser_nama(cursor.getString(cursor.getColumnIndexOrThrow(USER_NAMA)));
                user.setUser_email(cursor.getString(cursor.getColumnIndexOrThrow(USER_EMAIL)));
                user.setUser_telpon(cursor.getString(cursor.getColumnIndexOrThrow(USER_TELPON)));
                user.setUser_alamat(cursor.getString(cursor.getColumnIndexOrThrow(USER_ALAMAT)));
                user.setUser_imgurl(cursor.getString(cursor.getColumnIndexOrThrow(USER_IMGURL)));
                users.add(user);
                cursor.moveToNext();
            }while (!cursor.isAfterLast());
        }
        cursor.close();
        db.endTransaction();
        return users;
    }

    public ArrayList<User> getUser() {
        db.beginTransaction();
        ArrayList<User> users=new ArrayList<>();
        Cursor cursor = db.query(TABLE_USER, null, null, null, null, null, USER_NAMA+" ASC", null);
        cursor.moveToFirst();
        User user;
        if (cursor.getCount() > 0) {
            do {
                user=new User();
                user.setUser_id(cursor.getString(cursor.getColumnIndexOrThrow(USER_ID)));
                user.setUser_nama(cursor.getString(cursor.getColumnIndexOrThrow(USER_NAMA)));
                user.setUser_email(cursor.getString(cursor.getColumnIndexOrThrow(USER_EMAIL)));
                user.setUser_telpon(cursor.getString(cursor.getColumnIndexOrThrow(USER_TELPON)));
                user.setUser_alamat(cursor.getString(cursor.getColumnIndexOrThrow(USER_ALAMAT)));
                user.setUser_imgurl(cursor.getString(cursor.getColumnIndexOrThrow(USER_IMGURL)));
                users.add(user);
                cursor.moveToNext();
            }while (!cursor.isAfterLast());
        }
        cursor.close();
        db.endTransaction();
        return users;
    }
}
