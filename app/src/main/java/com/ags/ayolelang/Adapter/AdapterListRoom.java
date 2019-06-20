package com.ags.ayolelang.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ags.ayolelang.Activity.MessageActivity;
import com.ags.ayolelang.DBHelper.UserHelper;
import com.ags.ayolelang.Models.RoomPesan;
import com.ags.ayolelang.Models.User;
import com.ags.ayolelang.R;
import com.ags.ayolelang.Storage.SharedPrefManager;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AdapterListRoom extends RecyclerView.Adapter<AdapterListRoom.CustomHolderView> {
    private LayoutInflater mInflater;
    Context context;
    ArrayList<RoomPesan> roompesan;

    public AdapterListRoom(Context context) {
        this.context = context;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public CustomHolderView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.item_inbox, viewGroup, false);
        AdapterListRoom.CustomHolderView vh = new AdapterListRoom.CustomHolderView(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolderView cvh, int i) {
        final RoomPesan roomPesan = roompesan.get(i);
        String userid = SharedPrefManager.getInstance(context).getUser().getUser_id();
        UserHelper userHelper = new UserHelper(context);
        userHelper.open();
        final User user = userHelper.getSingleUser(roomPesan.getRoom_user1() != userid ? roomPesan.getRoom_user1() : roomPesan.getRoom_user2());
        userHelper.close();
        cvh.txt_nama.setText(user.getUser_nama());
        cvh.txt_tanggal.setText(roomPesan.getRoom_tanggalpesan().substring(0,10));
        cvh.txt_preview.setText(roomPesan.getRoom_toppesan());
        cvh.layout_r_pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, MessageActivity.class);
                intent.putExtra("room_id",roomPesan.getRoom_id());
                intent.putExtra("userid2",user.getUser_id());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return roompesan.size();
    }

    public void addItem(ArrayList<RoomPesan> roomPesan) {
        this.roompesan = roomPesan;
        notifyDataSetChanged();
    }

    public class CustomHolderView extends RecyclerView.ViewHolder {
        ImageView img_akun;
        TextView txt_nama, txt_tanggal, txt_preview;
        LinearLayout layout_r_pesan;

        public CustomHolderView(@NonNull View itemView) {
            super(itemView);
            img_akun = itemView.findViewById(R.id.img_akun);
            txt_nama = itemView.findViewById(R.id.txt_nama);
            txt_tanggal = itemView.findViewById(R.id.txt_tanggal);
            txt_preview = itemView.findViewById(R.id.txt_preview);
            layout_r_pesan=itemView.findViewById(R.id.layout_r_pesan);
        }
    }
}
