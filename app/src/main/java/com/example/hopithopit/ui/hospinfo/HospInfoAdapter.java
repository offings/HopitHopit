package com.example.hopithopit.ui.hospinfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hopithopit.R;

import java.util.ArrayList;

public class HospInfoAdapter extends RecyclerView.Adapter<HospInfoAdapter.ViewHolder>{

    ArrayList<HospInfoItem> mHospInfoItems = null;

    public HospInfoAdapter(ArrayList<HospInfoItem> mHospInfoItems) {
        this.mHospInfoItems = mHospInfoItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.hospinfo_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HospInfoItem item = mHospInfoItems.get(position);

        holder.hospName.setText(item.getHospName());
        holder.hospTel.setText(item.getHospTel());
        holder.hospAdr.setText(item.getHospAdr());
        holder.hospDgid.setText(item.getHospDgid());
        holder.hospSpecialist.setText(item.getHospSpecialist());
        holder.hospTime.setText(item.getHospTime());
    }

    @Override
    public int getItemCount() {
        return mHospInfoItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView hospName, hospTel, hospAdr, hospDgid, hospSpecialist, hospTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            hospName = itemView.findViewById(R.id.hospName);
            hospTel = itemView.findViewById(R.id.hospTel);
            hospAdr = itemView.findViewById(R.id.hospAddress);
            hospDgid = itemView.findViewById(R.id.hospDgid);
            hospSpecialist = itemView.findViewById(R.id.hospSpecialist);
            hospTime = itemView.findViewById(R.id.hospTime);
        }
    }
}