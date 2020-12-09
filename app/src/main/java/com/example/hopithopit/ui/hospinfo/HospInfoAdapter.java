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
import java.util.Collections;
import java.util.Comparator;

public class HospInfoAdapter extends RecyclerView.Adapter<HospInfoAdapter.ViewHolder>{

    ArrayList<HospInfoItem> mHospInfoItems = null;

    public HospInfoAdapter(ArrayList<HospInfoItem> mHospInfoItems) {
        Collections.sort(mHospInfoItems, new Comparator<HospInfoItem>() {
            @Override
            public int compare(HospInfoItem item, HospInfoItem t1) {
                return Float.compare(item.getDistance(), t1.getDistance());
            }
        });
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
        holder.hospAdr.setText(item.getHospAdr());
        holder.hospTel1.setText(item.getHospTel1());
        holder.hospTel3.setText(item.getHospTel3());
        holder.hospTime.setText(item.getHospTime());
        holder.hospDist.setText(String.format("%.2f", item.getDistance()/1000)+"km");
    }

    @Override
    public int getItemCount() {
        return mHospInfoItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView hospName, hospAdr, hospTel1, hospTel3, hospTime, hospDist;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            hospName = itemView.findViewById(R.id.hospName);
            hospAdr = itemView.findViewById(R.id.hospAddress);
            hospTel1 = itemView.findViewById(R.id.hospTel1);
            hospTel3 = itemView.findViewById(R.id.hospTel3);
            hospTime = itemView.findViewById(R.id.hospTime);
            hospDist = itemView.findViewById(R.id.hospDist);
        }
    }
}