package com.example.hopithopit.ui.ambulance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.hopithopit.R;

import java.util.List;

public class AmbulAdapter extends ArrayAdapter<Ambul> {

    private Context context;
    private List mList;
    private ListView mListView;

    class UserViewHolder {
        public TextView type;
        public TextView num;
        public TextView tel;
    }

    public AmbulAdapter(Context context, List<Ambul> list, ListView listview) {
        super(context, 0, list);

        this.context = context;
        this.mList = list;
        this.mListView = listview;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parentViewGroup) {

        View rowView = convertView;
        UserViewHolder viewHolder;
        String Status;

        if (rowView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            rowView = layoutInflater.inflate(R.layout.ambulance_list, parentViewGroup, false);

            viewHolder = new UserViewHolder();
            viewHolder.type = (TextView) rowView.findViewById(R.id.amb_type);
            viewHolder.num = (TextView) rowView.findViewById(R.id.amb_num);
            viewHolder.tel = (TextView) rowView.findViewById(R.id.amb_tel);

            rowView.setTag(viewHolder);

            Status = "created";
        } else {

            viewHolder = (UserViewHolder) rowView.getTag();

            Status = "reused";
        }
/*
        String Tag = rowView.getTag().toString();
        int idx = Tag.indexOf("@");
        String tag = Tag.substring(idx + 1);*/

        Ambul ambul = (Ambul) mList.get(position);

        viewHolder.type.setText(ambul.getType());
        viewHolder.num.setText(ambul.getNum());
        viewHolder.tel.setText(ambul.getTel());
        return rowView;
    }
}