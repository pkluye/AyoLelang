package com.ags.ayolelang.DBHelper;

import android.provider.BaseColumns;

public class DBContract {

    static String TABLE_PROVINSI = "TABLE_PROVINSI";
    static String TABLE_KOTA = "TABLE_KOTA";
    static String TABLE_KATEGORI = "TABLE_KATEGORI";
    static String TABLE_LELANG = "TABLE_LELANG";
    static String TABLE_PEKERJAAN = "TABLE_PEKERJAAN";
    static String TABLE_REQ_LELANG = "TABLE_REQ_LELANG";
    static String TABLE_REQ_PEKERJAAN = "TABLE_REQ_PEKERJAAN";
    static String TABLE_USER = "TABLE_USER";
    static String TABLE_TAWARAN = "TABLE_TAWARAN";
    static String TABLE_HISTORITAWARAN = "TABLE_HISTORITAWARAN";
    static String TABLE_SPECBARANG="TABLE_SPECBARANG";

    static final class PROVINSI implements BaseColumns {
        static String PROVINSI_ID = "provinsi_id";
        static String PROVINSI_NAMA = "provinsi_nama";
    }

    static final class KOTA implements BaseColumns {
        static String KOTA_ID = "kota_id";
        static String KOTA_NAMA = "kota_nama";
        static String KOTA_PROVINSIID = "kota_provinsiid";
    }

    static final class KATEGORI implements BaseColumns {
        static String KATEGORI_ID = "kategori_id";
        static String KATEGORI_PARENTID = "kategori_parentid";
        static String KATEGORI_SUBPARENTID = "kategori_subparentid";
        static String KATEGORI_NAMA = "kategori_nama";
    }

    static final class LELANG implements BaseColumns {
        static String LELANG_ID = "lelang_id";
        static String LELANG_ANGGARAN = "lelang_anggaran";
        static String LELANG_DESKRIPSI = "lelang_deskripsi";
        static String LELANG_TGLMULAI = "lelang_tglmulai";
        static String LELANG_TGLSELESAI = "lelang_tglselesai";
        static String LELANG_JUDUL = "lelang_judul";
        static String LELANG_USERID = "lelang_userid";
        static String LELANG_PEMBAYARAN = "lelang_pembayaran";
        static String LELANG_KOTA = "lelang_kota";
        static String LELANG_ALAMAT = "lelang_alamat";
        static String LELANG_STATUS = "lelang_status";
        static String LELANG_FILEURL = "lelang_fileurl";
    }

    static final class PEKERJAAN implements BaseColumns {
        static String PEKERJAAN_ID = "pekerjaan_id";
        static String PEKERJAAN_LELANGID = "pekerjaan_lelangid";
        static String PEKERJAAN_UKURAN = "pekerjaan_ukuran";
        static String PEKERJAAN_BAHAN = "pekerjaan_bahan";
        static String PEKERJAAN_JUMLAH = "pekerjaan_jumlah";
        static String PEKERJAAN_HARGA = "pekerjaan_harga";
        static String PEKERJAAN_CATATAN = "pekerjaan_catatan";
        static String PEKERJAAN_KATEGORIID = "pekerjaan_kategoriid";
        static String PEKERJAAN_STATUS = "pekerjaan_status";
    }

    static final class USER implements BaseColumns {
        static String USER_ID = "user_id";
        static String USER_NAMA = "user_nama";
        static String USER_EMAIL = "user_email";
        static String USER_TELPON = "user_telpon";
        static String USER_STATUS = "user_status";
        static String USER_ALAMAT = "user_alamat";
        static String USER_IMGURL = "user_imgurl";
    }

    static final class TAWARAN implements BaseColumns {
        static String TAWARAN_ID = "tawaran_id";
        static String TAWARAN_LELANGID = "tawaran_lelangid";
        static String TAWARAN_USERID = "tawaran_userid";
        static String TAWARAN_ANGGARAN = "tawaran_anggaran";
    }

    static final class SPECBARANG implements BaseColumns{
        static String SPECBARANG_ID="specbarang_id";
        static String SPECBARANG_KATEGORIID="specbarang_kategoriid";
        static String SPECBARANG_UKURAN="specbarang_ukuran";
        static String SPECBARANG_BAHAN="specbarang_bahan";
        static String SPECBARANG_JMLSISI ="specbarang_jmlsisi";
        static String SPECBARANG_LAMINASI ="specbarang_laminasi";
        static String SPECBARANG_HARGASATUAN="specbarang_hargasatuan";
        static String SPECBARANG_SATUAN="specbarang_satuan";
    }

}
