package com.example.hopithopit.ui.ambulance;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.hopithopit.R;
import com.example.hopithopit.ui.search.SearchViewModel;

import java.io.IOException;
import java.io.InputStream;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class AmbulanceFragment extends Fragment {

    private AmbulanceViewModel ambulanceViewModel;
    private Spinner spinner1;
    private Spinner spinner2;

    //선택된 대구 경북 시구군
    private String select_dg;
    private String select_gb;

    ListView listView;
    ArrayAdapter<String> arrayAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ambulanceViewModel =
                ViewModelProviders.of(this).get(AmbulanceViewModel.class);
        View root = inflater.inflate(R.layout.fragment_ambulance, container, false);
        /*final TextView textView = root.findViewById(R.id.text_ambulance);
        ambulanceViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        listView = (ListView)root.findViewById(R.id.ambulance_list);
        arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);
        readExcel();
        return root;
    }
    public void readExcel() {
        Workbook workbook = null;
        try {
            InputStream is = getActivity().getResources().getAssets().open("dg_ambulance.xls");
            Workbook wb = Workbook.getWorkbook(is);

            if(wb != null) {
                Sheet sheet = wb.getSheet(0);   // 시트 불러오기
                if(sheet != null) {
                    int colTotal = sheet.getColumns();    // 전체 컬럼
                    int rowIndexStart = 1;                  // row 인덱스 시작
                    int rowTotal = sheet.getColumn(colTotal-1).length;

                    StringBuilder sb;
                    for(int row=rowIndexStart;row<rowTotal;row++) {
                        sb = new StringBuilder();
                        for(int col=0;col<colTotal;col++) {
                            String contents = sheet.getCell(col, row).getContents();
                            sb.append("col"+col+" : "+contents+" , ");
                            arrayAdapter.add(contents);
                        }
                        Log.i("test", sb.toString());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }finally {
            listView.setAdapter(arrayAdapter);
            //workbook.close();
        }
    }
}