package com.example.hopithopit.ui.acceptable;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.hopithopit.R;

public class AcceptableFragment extends Fragment {

    private AcceptableViewModel acceptableViewModel;
    private Spinner spinner1;
    private Spinner spinner2;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        acceptableViewModel =
                ViewModelProviders.of(this).get(AcceptableViewModel.class);
        View root = inflater.inflate(R.layout.fragment_acceptable, container, false);
        /*final TextView textView = root.findViewById(R.id.any);
        acceptableViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;*/

        spinner1=(Spinner)root.findViewById(R.id.spinner1);
        final String[] dosi={"대구", "경북"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,dosi);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
/*        @SuppressLint("ResourceType")

        final String[] dg={"달서구","북구","서구","중구","동구","수성구","남구","달성군"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_2,dg);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        final String[] gb={"포항시","경주시","김천시","안동시","구미시","영주시","영천시","" +
                "상주시","문경시","경산시","군위군","의성군","청송군","영양군","영덕군",
            "청도군","고령군","성주군","칠곡군","예천군","봉화군","울진군","울릉군"};
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getActivity(),android.R.layout.activity_list_item,gb);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter3);*/
        return root;
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


}