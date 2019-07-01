package com.ags.ayolelang.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.ags.ayolelang.DBHelper.REQLelangHelper;
import com.ags.ayolelang.DBHelper.REQPekerjaanHelper;
import com.ags.ayolelang.DBHelper.SpecBarangHelper;
import com.ags.ayolelang.Models.Lelang;
import com.ags.ayolelang.Models.Pekerjaan;
import com.ags.ayolelang.R;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;

public class DetailSpesifikasi extends AppCompatActivity {

    private int kategori_id;
    private EditText bahan, harga, catatan;
    private MaterialEditText quantity;
    private Toolbar toolbar;
    private TextView txt_subtext_kategori_dipilih;
    private boolean edit;
    private int pekerjaan_id;
    private long lastharga;
    private Spinner ukuransp, bahansp;
    private SpecBarangHelper SBHelper;
    private RadioButton sisiradio1, sisiradio2;
    private Spinner laminasisp;
    //layout
    private LinearLayout layout_ukuran, layout_bahan, layout_sisi, layout_laminasi;
    private EditText lebar;
    private EditText panjang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_spesifikasi);
        panjang = findViewById(R.id.in_panjang);
        lebar = findViewById(R.id.in_lebar);
        bahan = findViewById(R.id.in_bahan);
        ukuransp = findViewById(R.id.ukuran_sp);
        bahansp = findViewById(R.id.bahan_sp);
        quantity = findViewById(R.id.in_quantity);
        laminasisp = findViewById(R.id.laminasi_sp);
        sisiradio1 = findViewById(R.id.radio_1);
        sisiradio2 = findViewById(R.id.radio_2);
        harga = findViewById(R.id.in_harga);
        catatan = findViewById(R.id.in_catatan);
        txt_subtext_kategori_dipilih = findViewById(R.id.txt_subtext_kategori_dipilih);

        //layout
        layout_ukuran = findViewById(R.id.layout_ukuran);
        layout_bahan = findViewById(R.id.layout_bahan);
        layout_sisi = findViewById(R.id.layout_sisi);
        layout_laminasi = findViewById(R.id.layout_laminasi);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SBHelper = new SpecBarangHelper(getApplicationContext());

        Intent intent = getIntent();
        kategori_id = intent.getIntExtra("kategori_id", 0);
        String kategori_nama = intent.getStringExtra("kategori_nama");
        edit = intent.getBooleanExtra("edit", false);
        txt_subtext_kategori_dipilih.setText(kategori_nama);
        if (edit) {
            pekerjaan_id = intent.getIntExtra("pekerjaan_id", 0);
            String ukuran_e = intent.getStringExtra("ukuran");
            //ukuran.setText(ukuran_e);
            String bahan_e = intent.getStringExtra("bahan");
            bahan.setText(bahan_e);
            int jumlah = intent.getIntExtra("jumlah", 0);
            quantity.setText(jumlah + "");
            long harga_e = intent.getLongExtra("harga", 0);
            harga.setText(harga_e + "");
            String catatan_e = intent.getStringExtra("catatan");
            catatan.setText(catatan_e);
            lastharga = intent.getLongExtra("harga", 0);
        }
        loadbahan();
        loadukuran();
        loadlaminasi();
        loadsisi();
        sisiradio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadharga();
            }
        });
        sisiradio2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadharga();
            }
        });
        quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() < 1) return;
                if (quantity.getText().toString().equalsIgnoreCase("0")) {
                    quantity.setError("jumlah pemesanan harus lebih dari 0");
                    quantity.requestFocus();
                    return;
                }

                if (quantity.getText().charAt(0)=='0') {
                    quantity.setError("jumlah pemesanan tidak valid");
                    quantity.requestFocus();
                    return;
                }

                loadharga();
            }
        });
        loadharga();
    }

    private void loadsisi() {
        SBHelper.open();
        ArrayList<String> listsisi = SBHelper.getSisi(kategori_id + "");
        SBHelper.close();
        if (listsisi != null) {
            if (!listsisi.contains("N/A")) {
                layout_sisi.setVisibility(View.VISIBLE);
            } else {
                layout_sisi.setVisibility(View.GONE);
            }
        }
    }

    private void loadlaminasi() {
        SBHelper.open();
        ArrayList<String> listlaminasi = SBHelper.getLaminasi(kategori_id + "");
        SBHelper.close();
        if (listlaminasi != null) {
            if (!listlaminasi.contains("N/A")) {
                layout_laminasi.setVisibility(View.VISIBLE);
            } else {
                layout_laminasi.setVisibility(View.GONE);
            }
            ArrayAdapter<String> spadapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, listlaminasi);
            spadapter.notifyDataSetChanged();
            laminasisp.setAdapter(spadapter);
        }
        laminasisp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadharga();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void loadharga() {
        SBHelper.open();
        int sisi = sisiradio1.isChecked() ? 1 : 2;
        long harga = 0;
        if (layout_sisi.getVisibility() == View.VISIBLE) {
            harga = SBHelper.getHargaSatuan(
                    kategori_id + "",
                    ukuransp.getSelectedItem().toString(),
                    bahansp.getSelectedItem().toString(),
                    sisi + "",
                    laminasisp.getSelectedItem().toString());
        } else {
            harga = SBHelper.getHargaSatuan(
                    kategori_id + "",
                    ukuransp.getSelectedItem().toString(),
                    bahansp.getSelectedItem().toString(),
                    "N/A",
                    laminasisp.getSelectedItem().toString());
        }
        if (harga > 0) {
            quantity.setHelperText("Rp. " + harga + "/" + SBHelper.getSatuan(kategori_id + ""));
        } else {
            quantity.setHelperText("harga satuan belum tersedia");
        }
        harga *= Long.parseLong(quantity.getText().toString());
        if (ukuransp.getSelectedItem().toString().equalsIgnoreCase("custom")) {
            harga *= Long.parseLong(panjang.getText().toString());
            harga *= Long.parseLong(lebar.getText().toString());

        }
        this.harga.setText(harga + "");
        if (harga < 1) {
            this.harga.setEnabled(true);
        } else {
            this.harga.setEnabled(false);
        }
    }

    public void next(View view) {
        String ukuran = "",
                ukuransp = this.ukuransp.getSelectedItem().toString().trim(),
                bahan = this.bahan.getText().toString().trim(),
                bahansp = this.bahansp.getSelectedItem().toString().trim(),
                quantity = this.quantity.getText().toString().trim(),
                harga = this.harga.getText().toString().trim(),
                catatan = this.catatan.getText().toString().trim();

        if (ukuransp.equalsIgnoreCase("custom")) {
            if (TextUtils.isEmpty(this.panjang.getText().toString())) {
                this.panjang.setError("harap lengkapi form ini");
                this.panjang.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(this.lebar.getText().toString())) {
                this.lebar.setError("harap lengkapi form ini");
                this.lebar.requestFocus();
                return;
            }

            if (this.panjang.getText().toString().equalsIgnoreCase("0")) {
                this.panjang.setError("masukan panjang yang benar");
                this.panjang.requestFocus();
                return;
            }

            if (this.lebar.getText().toString().equalsIgnoreCase("0")) {
                this.lebar.setError("lebar harus lebih dari 0");
                this.lebar.requestFocus();
                return;
            }

            ukuran = this.panjang.getText().toString() + " x " + this.lebar.getText().toString();
        } else {
            ukuran = ukuransp;
        }

        if (bahansp.equalsIgnoreCase("custom")) {
            if (bahan.isEmpty()) {
                this.bahan.setError("harap lengkapi form ini");
                this.bahan.requestFocus();
                return;
            }
        } else {
            bahan = bahansp;
        }

        if (quantity.isEmpty()) {
            this.quantity.setError("harap lengkapi form ini");
            this.quantity.requestFocus();
            return;
        }

        if (harga.isEmpty()) {
            this.harga.setError("harap lengkapi form ini");
            this.harga.requestFocus();
            return;
        }

        REQPekerjaanHelper reqPekerjaanHelper = new REQPekerjaanHelper(this);
        reqPekerjaanHelper.open();
        Intent intent;
        REQLelangHelper reqLelangHelper = new REQLelangHelper(this);
        reqLelangHelper.open();
        boolean lelang_isempty = reqLelangHelper.isempty();
        Lelang lelang = reqLelangHelper.getLelang();
        if (edit) {
            reqPekerjaanHelper.update(new Pekerjaan(ukuran, bahan, catatan, pekerjaan_id, Integer.parseInt(quantity), kategori_id, Long.parseLong(harga)));
            if (!lelang_isempty) {
                lelang.setLelang_anggaran(lelang.getLelang_anggaran() + Long.parseLong(harga) - lastharga);
                reqLelangHelper.update(lelang);
            }
        } else {
            if (!lelang_isempty) {
                lelang.setLelang_anggaran(lelang.getLelang_anggaran() + Long.parseLong(harga));
                reqLelangHelper.update(lelang);
                intent = new Intent(this, Preview.class);
                if (lelang.getLelang_status() != 0) {
                    intent.putExtra("edit", true);
                }
            } else {
                intent = new Intent(this, ListPekerjaan.class);
            }
            intent.putExtra("kategori_id", kategori_id);
            reqPekerjaanHelper.insert(new Pekerjaan(ukuran, bahan, catatan, Integer.parseInt(quantity), kategori_id, Long.parseLong(harga)));
            startActivity(intent);
        }
        reqLelangHelper.close();
        reqPekerjaanHelper.close();

        finish();
    }

    private void loadbahan() {
        SBHelper.open();
        ArrayList<String> listbahan = SBHelper.getBahan(kategori_id + "");
        SBHelper.close();
        if (listbahan != null) {
            if (!listbahan.contains("N/A")) {
                layout_bahan.setVisibility(View.VISIBLE);
            } else {
                layout_bahan.setVisibility(View.GONE);
            }
            ArrayAdapter<String> spadapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, listbahan);
            spadapter.notifyDataSetChanged();
            bahansp.setAdapter(spadapter);
        }
        bahansp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectitem = bahansp.getSelectedItem().toString();
                bahan.setVisibility(View.GONE);
                if (selectitem.equalsIgnoreCase("custom")) {
                    bahan.setVisibility(View.VISIBLE);
                    bahan.requestFocus();
                }
                loadharga();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void loadukuran() {
        SBHelper.open();
        ArrayList<String> listukuran = SBHelper.getUkuran(kategori_id + "");
        SBHelper.close();
        if (listukuran != null) {
            if (!listukuran.contains("N/A")) {
                layout_ukuran.setVisibility(View.VISIBLE);
            } else {
                layout_ukuran.setVisibility(View.GONE);
            }
            ArrayAdapter<String> spadapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, listukuran);
            spadapter.notifyDataSetChanged();
            ukuransp.setAdapter(spadapter);
        }
        ukuransp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectitem = ukuransp.getSelectedItem().toString();
                panjang.setVisibility(View.GONE);
                lebar.setVisibility(View.GONE);
                if (selectitem.equalsIgnoreCase("custom")) {
                    panjang.setVisibility(View.VISIBLE);
                    lebar.setVisibility(View.VISIBLE);
                    panjang.requestFocus();
                    panjang.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            if (s.length() < 1) return;
                            if (panjang.getText().toString().equalsIgnoreCase("0")) {
                                panjang.setError("panjang harus lebih dari 0");
                                panjang.requestFocus();
                                return;
                            }

                            if (panjang.getText().charAt(0)=='0') {
                                panjang.setError("panjang tidak valid");
                                panjang.requestFocus();
                                return;
                            }

                            loadharga();
                        }
                    });
                    lebar.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            if (s.length() < 1) {
                                return;
                            }

                            if (lebar.getText().toString().equalsIgnoreCase("0")) {
                                lebar.setError("lebar harus lebih dari 0");
                                lebar.requestFocus();
                                return;
                            }

                            if (lebar.getText().charAt(0)=='0') {
                                lebar.setError("lebar tidak valid");
                                lebar.requestFocus();
                                return;
                            }

                            loadharga();
                        }
                    });
                }
                loadharga();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void back(View view) {
        finish();
    }

    public void qty_kurang(View view) {
        int qty = 0;
        if (quantity.getText().length() != 0) {
            qty = Integer.parseInt(quantity.getText().toString());
        } else {
            qty = 1;
        }
        if (qty > 1) {
            qty--;
        }
        quantity.setText(qty + "");
    }

    public void qty_tambah(View view) {
        int qty = 0;
        if (quantity.getText().length() != 0) {
            qty = Integer.parseInt(quantity.getText().toString());
        }
        qty++;
        quantity.setText(qty + "");
    }
}
