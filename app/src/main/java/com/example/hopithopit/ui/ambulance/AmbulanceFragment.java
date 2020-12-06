package com.example.hopithopit.ui.ambulance;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.hopithopit.ui.ambulance.Ambul;
import com.example.hopithopit.ui.ambulance.AmbulAdapter;
import com.example.hopithopit.ui.ambulance.AmbulanceViewModel;
import com.example.hopithopit.MainActivity;
import com.example.hopithopit.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class AmbulanceFragment extends Fragment {

    private AmbulanceViewModel ambulanceViewModel;

    private List<Ambul> ambulList = new ArrayList<>();
    ListView listView;

    private Spinner spinner1;
    private Spinner spinner2;
    //선택된 대구 경북 시구군
    private String select_dg_gb;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ambulanceViewModel =
                ViewModelProviders.of(this).get(AmbulanceViewModel.class);
        View root = inflater.inflate(R.layout.fragment_ambulance, container, false);

        spinner1=(Spinner)root.findViewById(R.id.spinner1);
        spinner2=(Spinner)root.findViewById(R.id.spinner2);

        final String[] dosi={"대구", "경북"};
        final String[] dg={"남구","달서구","동구","북구","서구","수성구","중구","달성군"};
        final String[] gb={"경산시","경주시","고령군","구미시","군위군","김천시", "문경시",
                "봉화군","상주시","성주군","안동시","영덕군","영양군","영주시","영천시",
                "예천군","울릉군","울진군","의성군","청도군","청송군","칠곡군","포항시"};

        //지역 조건 선택하는 spinner
        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,dosi);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapter1.getItem(i).equals("대구")){
                    final ArrayAdapter<String> adapter2=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, dg);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(adapter2);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            select_dg_gb= (String) adapterView.getItemAtPosition(i);
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }
                else if (adapter1.getItem(i).equals("경북")){
                    final ArrayAdapter<String> adapter2=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, gb);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(adapter2);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            select_dg_gb= (String) adapterView.getItemAtPosition(i);
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button button = (Button)root.findViewById(R.id.ambulance_search);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readData();
            }
        });
        listView = (ListView)root.findViewById(R.id.ambulance_list);
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
                //Log.d("AmbulanceFragment", "Just created : " + ambulList.get(ambulList.size() -1));
            }
        } catch(IOException e){
            Log.wtf("AmbulanceFragment", "Error" + line, e);
            e.printStackTrace();
        }
        //String[] array = ambulList.toArray(new String[ambulList.size()]);

        ArrayList<Ambul> result = new ArrayList<>();
        //result.addAll(ambulList);
        for(int i = 0; i<ambulList.size()/2; i++){
            // result.removeAll(result);
            if(select_dg_gb.equals("달서구") && ambulList.get(i).getDistrict().contains("달서구")){
                result.add(ambulList.get(i));
                Log.d("result", "result : " + result);
            }
            else if(select_dg_gb.equals("남구") && (ambulList.get(i).getCity().contains("대구") && ambulList.get(i).getDistrict().contains("남구"))){
                result.add(ambulList.get(i));
                Log.d("result", "result : " + result);
            }
            else if(select_dg_gb.equals("중구") && ambulList.get(i).getCity().contains("대구") && ambulList.get(i).getDistrict().contains("중구")){
                result.add(ambulList.get(i));
                Log.d("result", "result : " + result);
            }
            else if(select_dg_gb.equals("경산시") && ambulList.get(i).getDistrict().contains("경산시")){
                result.add(ambulList.get(i));
                Log.d("result", "result : " + result);
            }
            else if(select_dg_gb.equals("경주시") && ambulList.get(i).getDistrict().contains("경주시")){
                result.add(ambulList.get(i));
                Log.d("result", "result : " + i + result);
            }
            else if(select_dg_gb.equals("구미시") && ambulList.get(i).getDistrict().contains("구미시")){
                result.add(ambulList.get(i));
                Log.d("result", "result : " + result);
            }
            else if(select_dg_gb.equals("문경시") && ambulList.get(i).getDistrict().contains("문경시")){
                result.add(ambulList.get(i));
                Log.d("result", "result : " + result);
            }
            else if(select_dg_gb.equals("안동시") && ambulList.get(i).getDistrict().contains("안동시")){
                result.add(ambulList.get(i));
                Log.d("result", "result : " + result);
            }
            else if(select_dg_gb.equals("영덕군") && ambulList.get(i).getDistrict().contains("영덕군")){
                result.add(ambulList.get(i));
                Log.d("result", "result : " + result);
            }
            else if(select_dg_gb.equals("포항시") && ambulList.get(i).getDistrict().contains("포항시 남구")){
                result.add(ambulList.get(i));
                Log.d("result", "result : " + result);
            }
        }
        final AmbulAdapter adapter = new AmbulAdapter((MainActivity)getActivity(), result, listView);
        listView.setAdapter(adapter);

        //result.clear();
        //ambulList.remove(0);
        //result.removeall(result);
    }

}