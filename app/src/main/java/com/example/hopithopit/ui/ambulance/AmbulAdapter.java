package com.example.hopithopit.ui.ambulance;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.hopithopit.R;
import com.example.hopithopit.ui.ambulance.Ambul;

import java.util.ArrayList;
import java.util.List;

public class AmbulAdapter extends ArrayAdapter<Ambul> {

    private Context context;
    private List mList;
    private ListView mListView;

    class UserViewHolder {
        public TextView own;
        public TextView type;
        public TextView num;
        public TextView tel;
        public ImageView call;
    }

    public AmbulAdapter(Context context, List<Ambul> list, ListView listview) {
        super(context, 0, list);

        this.context = context;
        this.mList = list;
        this.mListView = listview;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parentViewGroup) {

        View rowView = convertView;
        final UserViewHolder viewHolder;

        if (rowView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            rowView = layoutInflater.inflate(R.layout.ambulance_list, parentViewGroup, false);

            viewHolder = new UserViewHolder();
            viewHolder.call = (ImageView)rowView.findViewById(R.id.amb_call);
            viewHolder.own = (TextView) rowView.findViewById(R.id.amb_own);
            viewHolder.type = (TextView) rowView.findViewById(R.id.amb_type);
            viewHolder.num = (TextView) rowView.findViewById(R.id.amb_num);
            viewHolder.tel = (TextView) rowView.findViewById(R.id.amb_tel);

            rowView.setTag(viewHolder);

        } else {

            viewHolder = (UserViewHolder) rowView.getTag();

        }

        final Ambul ambul = (Ambul) mList.get(position);
        viewHolder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" +((Ambul) mList.get(position)).getTel()));
                v.getContext().startActivity(intent);
            }
        });

        viewHolder.own.setText(ambul.getOwn());
        viewHolder.type.setText(ambul.getType());
        viewHolder.num.setText(ambul.getNum());
        viewHolder.tel.setText(ambul.getTel());
        return  rowView;
    }
}