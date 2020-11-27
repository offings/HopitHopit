package com.example.hopithopit.ui.hospinfo;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hopithopit.R;

import java.util.ArrayList;

public class HospinfoListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hospinfo_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            ArrayList<HospInfoItem> hospInfoItems = new ArrayList<HospInfoItem>();
            hospInfoItems.add(new HospInfoItem("병원이름", "전화번호", "주소", "진료과목", "전문의", "진료시간"));

            recyclerView.setAdapter(new HospInfoAdapter(hospInfoItems));
        }
        return view;
    }

}