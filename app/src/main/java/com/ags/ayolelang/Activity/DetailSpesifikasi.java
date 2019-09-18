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

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

public class DetailSpesifikasi extends AppCompatActivity {

    private int kategori_id;
    private EditText bahan, harga, catatan;
    private MaterialEditText quantity;
    private Toolbar toolbar;
    private TextView txt_subtext_kategori_dipilih;
    private boolean edit;
    private int pekerjaan_id;
    private Spinner ukuransp, bahansp;
    private SpecBarangHelper SBHelper;
    private RadioButton sisiradio1, sisiradio2;
    private String jmlsisi;
    private Spinner laminasisp;
    private LinearLayout layout_ukuran, layout_bahan, layout_sisi, layout_laminasi;
    private EditText lebar;
    private EditText panjang;
    private Pekerjaan lastPekerjaan;
    private LinearLayout in_custom;
    private Spinner in_satuan;

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
        in_satuan = findViewById(R.id.in_satuan);

        //layout
        layout_ukuran = findViewById(R.id.layout_ukuran);
        layout_bahan = findViewById(R.id.layout_bahan);
        layout_sisi = findViewById(R.id.layout_sisi);
        layout_laminasi = findViewById(R.id.layout_laminasi);
        in_custom = findViewById(R.id.in_custom);

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
            lastPekerjaan = (Pekerjaan) intent.getSerializableExtra("pekerjaan");
            Log.d("pekerjaan", lastPekerjaan.toString());
            quantity.setText(lastPekerjaan.getPekerjaan_jumlah() + "");
        }
        loadbahan();
        loadukuran();
        loadlaminasi();
        loadsisi();

        sisiradio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ukuransp.getAdapter() != null || bahansp.getAdapter() != null || laminasisp != null)
                    loadharga();
            }
        });
        sisiradio2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ukuransp.getAdapter() != null || bahansp.getAdapter() != null || laminasisp != null)
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

                if (quantity.getText().charAt(0) == '0') {
                    quantity.setError("jumlah pemesanan tidak valid");
                    quantity.requestFocus();
                    return;
                }

                if (ukuransp.getAdapter() != null || bahansp.getAdapter() != null || laminasisp != null)
                    loadharga();
            }
        });
        harga.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() < 1) return;
                if (harga.getText().toString().equalsIgnoreCase("0")) {
                    harga.setError("biaya pemesanan harus lebih dari 0");
                    harga.requestFocus();
                    return;
                }

                if (harga.getText().charAt(0) == '0') {
                    harga.setError("biaya pemesanan tidak valid");
                    harga.requestFocus();
                    return;
                }
                harga.removeTextChangedListener(this);
                harga.setText(currencyFormat(harga.getText().toString()));
                harga.setSelection(harga.getText().length());
                harga.addTextChangedListener(this);
            }
        });
        if (ukuransp.getAdapter() != null || bahansp.getAdapter() != null || laminasisp != null)
            loadharga();
    }

    private void loadsisi() {
        SBHelper.open();
        ArrayList<String> listsisi = SBHelper.getSisi(kategori_id + "");
        SBHelper.close();
        if (listsisi != null) {
            if (!listsisi.contains("N/A")) {
                layout_sisi.setVisibility(View.VISIBLE);
                if (edit) {
                    if (lastPekerjaan.getPekerjaan_jmlsisi().equalsIgnoreCase("1")) {
                        sisiradio1.setChecked(true);
                    } else {
                        sisiradio2.setChecked(true);
                    }
                }
            } else {
                jmlsisi = "N/A";
                layout_sisi.setVisibility(View.GONE);
            }
        }
    }

    private void loadlaminasi() {
        SBHelper.open();
        ArrayList<String> listlaminasi = SBHelper.getLaminasi(kategori_id + "");
        SBHelper.close();
        Log.d("laminasi", listlaminasi.size() + " " + listlaminasi.get(0));
        if (listlaminasi != null) {
            ArrayAdapter<String> spadapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, listlaminasi);
            spadapter.notifyDataSetChanged();
            laminasisp.setAdapter(spadapter);
            if (!listlaminasi.contains("N/A")) {
                layout_laminasi.setVisibility(View.VISIBLE);
                if (edit) {
                    if (listlaminasi.contains(lastPekerjaan.getPekerjaan_laminasi())) {
                        Log.d("p1", "0");
                        int potition = spadapter.getPosition(lastPekerjaan.getPekerjaan_laminasi());
                        laminasisp.setSelection(potition);
                    } else {
                        Log.d("p1", "1");
                        int potition = spadapter.getPosition("custom");
                        laminasisp.setSelection(potition);
                    }
                }
            } else {
                Log.d("p1", "2");
                layout_laminasi.setVisibility(View.GONE);
            }
        }
        laminasisp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (bahansp.getAdapter() != null || ukuransp.getAdapter() != null) loadharga();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void loadharga() {
        SBHelper.open();
        int sisi = sisiradio1.isChecked() ? 1 : 2;
        jmlsisi = sisi + "";
        long harga = 0;
        if (layout_sisi.getVisibility() == View.VISIBLE) {
            harga = SBHelper.getHargaSatuan(
                    kategori_id + "",
                    ukuransp.getSelectedItem().toString() + "",
                    bahansp.getSelectedItem().toString() + "",
                    sisi + "",
                    laminasisp.getSelectedItem().toString() + "");
        } else {
            harga = SBHelper.getHargaSatuan(
                    kategori_id + "",
                    ukuransp.getSelectedItem().toString() + "",
                    bahansp.getSelectedItem().toString() + "",
                    "N/A",
                    laminasisp.getSelectedItem().toString() + "");
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
        this.harga.setText(currencyFormat(harga + ""));
        if (harga < 1) {
            this.harga.setEnabled(true);
        } else {
            this.harga.setEnabled(false);
        }

    }

    private String currencyFormat(String harga) {
        Locale localeID = new Locale("in", "ID");
        harga = harga.replaceAll("[.,]", "");
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance(localeID);
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
        formatRp.setCurrencySymbol("");
        formatRp.setGroupingSeparator('.');
        formatRp.setMonetaryDecimalSeparator(',');
        kursIndonesia.setDecimalFormatSymbols(formatRp);
        return kursIndonesia.format(Double.parseDouble(harga));
    }

    private void loadbahan() {
        SBHelper.open();
        ArrayList<String> listbahan = SBHelper.getBahan(kategori_id + "");
        SBHelper.close();
        if (listbahan != null) {
            ArrayAdapter<String> spadapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, listbahan);
            spadapter.notifyDataSetChanged();
            bahansp.setAdapter(spadapter);

            if (!listbahan.contains("N/A")) {
                layout_bahan.setVisibility(View.VISIBLE);
                if (edit) {
                    if (listbahan.contains(lastPekerjaan.getPekerjaan_bahan())) {
                        int potition = spadapter.getPosition(lastPekerjaan.getPekerjaan_bahan());
                        bahansp.setSelection(potition);
                    } else {
                        int potition = spadapter.getPosition("custom");
                        bahansp.setSelection(potition);
                    }
                }
            } else {
                layout_bahan.setVisibility(View.GONE);
            }
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
                if (ukuransp.getAdapter() != null || laminasisp.getAdapter() != null) loadharga();
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
            ArrayAdapter<String> spadapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, listukuran);
            spadapter.notifyDataSetChanged();
            ukuransp.setAdapter(spadapter);

            if (!listukuran.contains("N/A")) {
                Log.d("ppp", "ppp");
                layout_ukuran.setVisibility(View.VISIBLE);
                if (edit) {
                    if (listukuran.contains(lastPekerjaan.getPekerjaan_ukuran())) {
                        int potition = spadapter.getPosition(lastPekerjaan.getPekerjaan_bahan());
                        ukuransp.setSelection(potition);
                    } else {
                        int potition = spadapter.getPosition("custom");
                        ukuransp.setSelection(potition);
                        String ukuran_et = lastPekerjaan.getPekerjaan_ukuran().replace(" ", "");
                        String[] split = ukuran_et.split("x");
                        panjang.setText(split[0]);
                        lebar.setText(split[1]);
                    }
                }
            } else {
                layout_ukuran.setVisibility(View.GONE);
            }
        }
        ukuransp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectitem = ukuransp.getSelectedItem().toString();
                in_custom.setVisibility(View.GONE);
                if (selectitem.equalsIgnoreCase("custom")) {
                    in_custom.setVisibility(View.VISIBLE);
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

                            if (panjang.getText().charAt(0) == '0') {
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

                            if (lebar.getText().charAt(0) == '0') {
                                lebar.setError("lebar tidak valid");
                                lebar.requestFocus();
                                return;
                            }

                            loadharga();
                        }
                    });
                }
                if (bahansp.getAdapter() != null || laminasisp.getAdapter() != null) loadharga();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void next(View view) {
        String ukuran = "",
                ukuransp = this.ukuransp.getSelectedItem().toString().trim(),
                bahan = this.bahan.getText().toString().trim(),
                bahansp = this.bahansp.getSelectedItem().toString().trim(),
                jmlsisi = this.jmlsisi,
                laminasi = this.laminasisp.getSelectedItem().toString(),
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

            ukuran = this.panjang.getText().toString() + " x " + this.lebar.getText().toString() + " " + in_satuan.getSelectedItem().toString();
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

        if (harga.charAt(0) == '0') {
            this.harga.setError("harga tidak valid");
            this.harga.requestFocus();
            return;
        }

        if (catatan.isEmpty()) {
            catatan = "-";
        }

        harga = harga.replaceAll("[.,]", "");

        REQPekerjaanHelper reqPekerjaanHelper = new REQPekerjaanHelper(this);
        reqPekerjaanHelper.open();
        Intent intent;
        REQLelangHelper reqLelangHelper = new REQLelangHelper(this);
        reqLelangHelper.open();
        boolean lelang_isempty = reqLelangHelper.isempty();
        Lelang lelang = reqLelangHelper.getLelang();
        if (edit) {
            reqPekerjaanHelper.update(new Pekerjaan(ukuran, bahan, catatan, pekerjaan_id, Integer.parseInt(quantity), kategori_id, Long.parseLong(harga), jmlsisi, laminasi));
            if (!lelang_isempty) {
                lelang.setLelang_anggaran(lelang.getLelang_anggaran() + Long.parseLong(harga) - lastPekerjaan.getPekerjaan_harga());
                reqLelangHelper.update(lelang);
            }
        } else {
            intent = new Intent(this, ListPekerjaan.class);
            intent.putExtra("kategori_id", kategori_id);
            reqPekerjaanHelper.insert(new Pekerjaan(ukuran, bahan, catatan, Integer.parseInt(quantity), kategori_id, Long.parseLong(harga), jmlsisi, laminasi));
            startActivity(intent);
        }
        reqLelangHelper.close();
        reqPekerjaanHelper.close();

        finish();
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
