package com.example.hopithopit.ui.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hopithopit.R;
import com.example.hopithopit.ui.hospinfo.HospInfoAdapter;
import com.example.hopithopit.ui.hospinfo.HospInfoItem;
import com.example.hopithopit.ui.search.SearchAdapter;
import com.example.hopithopit.ui.search.SearchListFragment;
import com.example.hopithopit.ui.search.SearchAdapter;
import com.example.hopithopit.ui.search.SearchItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>{

    ArrayList<SearchItem> mSearchItems = null;

    public SearchAdapter(ArrayList<SearchItem> mSearchItems) {
        Collections.sort(mSearchItems, new Comparator<SearchItem>() {
            @Override
            public int compare(SearchItem item, SearchItem t1) {
                return Float.compare(item.getDistance(), t1.getDistance());
            }
        });
        this.mSearchItems = mSearchItems;
    }

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.hospinfo_list_item, parent, false);
        return new SearchAdapter.ViewHolder(view);
    }


    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder holder, int position) {
        SearchItem item = mSearchItems.get(position);

        holder.hospName.setText(item.getHospName());
        holder.hospAdr.setText(item.getHospAdr());
        holder.hospTel1.setText(item.getHospTel1());
        holder.hospTel3.setText(item.getHospTel3());
        holder.hospTime.setText(item.getHospTime());
        holder.hospDist.setText(String.format("%.2f", item.getDistance()/1000)+"km");
    }

    @Override
    public int getItemCount() {
        return mSearchItems.size();
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