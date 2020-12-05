package com.example.hopithopit.ui.assessment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.hopithopit.R;
import com.example.hopithopit.ui.ambulance.Ambul;

import java.util.List;

public class AssessmentAdapter extends ArrayAdapter<AssementSample> {

    private Context context;
    private List mList;
    private ListView mListView;

    class UserViewHolder {
        public TextView name;
        public TextView level;
        public TextView location;
    }

    public AssessmentAdapter(Context context, List<AssementSample> list, ListView listview) {
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

        if (rowView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            rowView = layoutInflater.inflate(R.layout.fragment_assessment_listitem, parentViewGroup, false);

            viewHolder = new UserViewHolder();
            viewHolder.name = (TextView) rowView.findViewById(R.id.hospName);
            viewHolder.level = (TextView) rowView.findViewById(R.id.hospLevel);
            viewHolder.location = (TextView) rowView.findViewById(R.id.hospLocation);

            rowView.setTag(viewHolder);
        } else {
            viewHolder = (UserViewHolder) rowView.getTag();
        }

        AssementSample assementSample = (AssementSample) mList.get(position);

        viewHolder.name.setText(assementSample.getName());
        viewHolder.level.setText(assementSample.getLevel());
        viewHolder.location.setText(assementSample.getLocation());
        return rowView;
    }
}