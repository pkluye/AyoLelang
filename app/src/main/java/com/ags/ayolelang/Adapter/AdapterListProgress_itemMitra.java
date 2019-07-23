package com.ags.ayolelang.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ags.ayolelang.Activity.MainActivity;
import com.ags.ayolelang.DBHelper.UserHelper;
import com.ags.ayolelang.Fragment.FragmentMitra;
import com.ags.ayolelang.Models.Tawaran;
import com.ags.ayolelang.Models.User;
import com.ags.ayolelang.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterListProgress_itemMitra extends RecyclerView.Adapter<AdapterListProgress_itemMitra.CustomHolderView> {
    private final int lelang_id;
    ArrayList<Tawaran> tawarans;
    Context context;
    private LayoutInflater mInflater;

    public AdapterListProgress_itemMitra(Context context, int lelang_id) {
        this.context = context;
        this.lelang_id=lelang_id;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public CustomHolderView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.item_penawar, viewGroup, false);
        AdapterListProgress_itemMitra.CustomHolderView vh = new AdapterListProgress_itemMitra.CustomHolderView(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolderView customHolderView, int i) {
        Tawaran tawaran = tawarans.get(i);
        Log.d("tawaran",tawaran.toString());
        UserHelper userHelper = new UserHelper(context);
        userHelper.open();
        final User user = userHelper.getSingleUser(tawaran.getTawaran_userid());
        userHelper.close();

        customHolderView.txt_namaPenawar.setText(user.getUser_nama());
        customHolderView.txt_hargaPenawar.setText("Rp. " + currencyFormat(tawaran.getTawaran_anggaran()+""));
        customHolderView.btn_nextArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentMitra fragment= new FragmentMitra();
                Bundle bundle=new Bundle();
                bundle.putSerializable("user",user);
                bundle.putInt("lelang_id",lelang_id);
                fragment.setArguments(bundle);
                ((MainActivity)context)._loadFragment(fragment);
            }
        });
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

    @Override
    public int getItemCount() {
        return tawarans.size();
    }

    public void addItem(ArrayList<Tawaran> tawarans) {
        this.tawarans = tawarans;
        notifyDataSetChanged();
    }

    public class CustomHolderView extends RecyclerView.ViewHolder {

        ImageButton btn_nextArrow;
        TextView txt_lokasiPenawar;
        CircleImageView image_user;
        TextView txt_hargaPenawar;
        TextView txt_namaPenawar;

        public CustomHolderView(@NonNull View v) {
            super(v);
            image_user=v.findViewById(R.id.image_user);
            txt_namaPenawar = v.findViewById(R.id.txt_namaPenawar);
            txt_hargaPenawar = v.findViewById(R.id.txt_hargaPenawar);
            txt_lokasiPenawar=v.findViewById(R.id.txt_lokasiPenawar);
            btn_nextArrow=v.findViewById(R.id.btn_nextArrow);
        }
    }
}
