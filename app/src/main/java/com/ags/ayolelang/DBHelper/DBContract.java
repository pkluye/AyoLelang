package com.ags.ayolelang.DBHelper;

import android.provider.BaseColumns;

public class DBContract {

    static String TABLE_PROVINSI="TABLE_PROVINSI";
    static String TABLE_KOTA="TABLE_KOTA";
    static String TABLE_KATEGORI="TABLE_KATEGORI";

    static final class PROVINSI implements BaseColumns{
        static String PROVINSI_ID="provinsi_id";
        static String PROVINSI_NAMA="provinsi_nama";
    }

    static final class KOTA implements BaseColumns{
        static String KOTA_ID="kota_id";
        static String KOTA_NAMA="kota_nama";
        static String KOTA_PROVINSIID="kota_provinsiid";
    }

    static final class KATEGORI implements BaseColumns{
        static String KATEGORI_ID="kategori_id";
        static String KATEGORI_PARENTID="kategori_parentid";
        static String KATEGORI_SUBPARENTID="kategori_subparentid";
        static String KATEGORI_NAMA="kategori_nama";
    }

}
