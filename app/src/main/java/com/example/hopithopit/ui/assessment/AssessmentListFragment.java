package com.example.hopithopit.ui.assessment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.hopithopit.MainActivity;
import com.example.hopithopit.R;
import com.example.hopithopit.ui.ambulance.AmbulAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class AssessmentListFragment extends Fragment {
    ListView listView;
    private List<AssementSample> AssessmentSamples = new ArrayList<>();
    private AssessmentViewModel assessmentViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_assessment_list, container, false);

        listView = root.findViewById(R.id.disease_list);
        readAssessmentData();
        readAssessmentData_cancer();
        return root;
    }

    private void readAssessmentData() {
        InputStream is = getResources().openRawResource(R.raw.acute_test);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("x-windows-949"))
        );

        String line = "";

        try{
            reader.readLine();
            while((line = reader.readLine()) != null){
                String[] tokens = line.split(",");

                if(AssessmentFragment.disease_info.equals(tokens[1])) {
                    AssementSample sample = new AssementSample();
                    sample.setName(tokens[0]);
                    sample.setDisease(tokens[1]);
                    sample.setLevel(tokens[2]);
                    sample.setLocation(tokens[3]);
                    AssessmentSamples.add(sample);
                    Log.d("My Activity", "Just created " + sample);
                }
            }
        } catch(IOException e){
            e.printStackTrace();
        }

        final AssessmentAdapter adapter = new AssessmentAdapter((MainActivity)getActivity(), AssessmentSamples, listView);
        listView.setAdapter(adapter);
    }

    private void readAssessmentData_cancer() {
        InputStream is = getResources().openRawResource(R.raw.cancer_test);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("x-windows-949"))
        );

        String line = "";

        try{
            reader.readLine();
            while((line = reader.readLine()) != null){
                String[] tokens = line.split(",");

                if(AssessmentFragment.disease_info.equals(tokens[1])) {
                    AssementSample sample = new AssementSample();
                    sample.setName(tokens[0]);
                    sample.setDisease(tokens[1]);
                    sample.setLevel(tokens[2]);
                    sample.setLocation(tokens[3]);
                    AssessmentSamples.add(sample);
                    Log.d("My Activity", "Just created " + sample);
                }
            }
        } catch(IOException e){
            e.printStackTrace();
        }

        final AssessmentAdapter adapter = new AssessmentAdapter((MainActivity)getActivity(), AssessmentSamples, listView);
        listView.setAdapter(adapter);
    }
}