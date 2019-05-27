package com.ags.ayolelang.DBHelper;

import android.provider.BaseColumns;

public class DBContract {

    static String TABLE_PROVINSI="TABLE_PROVINSI";
    static String TABLE_KOTA="TABLE_KOTA";

    static final class PROVINSI implements BaseColumns{
        static String PROVINSI_ID="provinsi_id";
        static String PROVINSI_NAMA="provinsi_nama";
    }

    static final class KOTA implements BaseColumns{
        static String KOTA_ID="kota_id";
        static String KOTA_NAMA="kota_nama";
        static String KOTA_PROVINSIID="kota_provinsiid";
    }

}
