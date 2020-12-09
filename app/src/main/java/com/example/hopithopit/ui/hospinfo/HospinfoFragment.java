package com.example.hopithopit.ui.hospinfo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.hopithopit.MainActivity;
import com.example.hopithopit.R;

import java.time.Instant;

public class HospinfoFragment extends Fragment {

    private HospinfoViewModel hospinfoViewModel;
    public static String hospinfo = "";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_hospinfo, container, false);

        final GridView gridView = root.findViewById(R.id.hospinfoGridView);
        GridViewAdapter gridViewAdapter = new GridViewAdapter();
        gridView.setAdapter(gridViewAdapter);


        return root;
    }

    class GridViewAdapter extends BaseAdapter{

        Context context = null;

        String[] hosptext = {"종합병원", "내과", "정형외과", "공공의료원",
                "치과", "안과", "소아과", "이비인후과",
                "피부과", "한의원", "산부인과", "비뇨기과",
                "정신의학과", "성형외과", "요양병원", "기타"};
        Integer[] hospimg = {R.drawable.general_hospital, R.drawable.internal_medicine_department, R.drawable.orthopedic_surgery, R.drawable.public_medical_center,
                R.drawable.dentistry, R.drawable.eye, R.drawable.pediatrics, R.drawable.ear_nose_throat,
                R.drawable.dermatology, R.drawable.oriental_medical, R.drawable.obstetrics_gynecology, R.drawable.urology,
                R.drawable.psychiatry, R.drawable.plastic_surgery, R.drawable.nursing_hospital, R.drawable.more};

        @Override
        public int getCount() {
            return hosptext.length;
        }

        @Override
        public Object getItem(int i) {
            return hosptext[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            if (view == null){
                context = viewGroup.getContext();
                LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = layoutInflater.inflate(R.layout.hospinfo_gridview_item, viewGroup, false);
            }

            ImageView hospImageView = view.findViewById(R.id.hosinfo_img);
            TextView hospTextView = view.findViewById(R.id.hosinfo_text);

            hospImageView.setImageResource(hospimg[i]);
            hospTextView.setText(hosptext[i]);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity)getActivity()).replaceFragment(R.id.hospinfoListFragment);
                    hospinfo = hosptext[i];
                }
            });

            return view;
        }
    }
}