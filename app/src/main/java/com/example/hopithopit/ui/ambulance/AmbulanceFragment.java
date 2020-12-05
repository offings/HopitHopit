package com.example.hopithopit.ui.ambulance;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.hopithopit.MainActivity;
import com.example.hopithopit.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class AmbulanceFragment extends Fragment {

    private AmbulanceViewModel ambulanceViewModel;

    private ArrayList<Ambul> ambulList = new ArrayList<>();
    ListView listView;
    TextView type; TextView num; TextView tel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ambulanceViewModel =
                ViewModelProviders.of(this).get(AmbulanceViewModel.class);
        View root = inflater.inflate(R.layout.fragment_ambulance, container, false);
        listView = (ListView)root.findViewById(R.id.ambulance_list);
        readData();
        return root;
    }

    private void readData() {
        InputStream is = getResources().openRawResource(R.raw.ambulance);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );

        String line = " ";

        try {
            while ((line = reader.readLine()) != null) {
                //split by ","
                String[] tokens = line.split(",");

                //read the data
                Ambul ambul = new Ambul();
                //ambul.setOwn(tokens[0]);
                ambul.setType(tokens[1]);
                ambul.setNum(tokens[2]);
                ambul.setTel(tokens[3]);
                ambul.setCity(tokens[4]);
                ambul.setDistrict(tokens[5]);
                ambulList.add(ambul);
                Log.d("AmbulanceFragment", "Just created : " + ambulList.get(ambulList.size() -1));
            }
            } catch(IOException e){
                Log.wtf("AmbulanceFragment", "Error" + line, e);
                e.printStackTrace();
        }
        //adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, ambulList);
        final AmbulAdapter adapter = new AmbulAdapter((MainActivity)getActivity(), ambulList, listView);
        listView.setAdapter(adapter);
    }

}