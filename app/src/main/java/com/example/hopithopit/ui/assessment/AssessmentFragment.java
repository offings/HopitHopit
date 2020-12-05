package com.example.hopithopit.ui.assessment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.hopithopit.MainActivity;
import com.example.hopithopit.R;
import com.example.hopithopit.ui.search.SearchViewModel;

import java.util.ArrayList;

public class AssessmentFragment extends Fragment {

    private AssessmentViewModel assessmentViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_assessment, container, false);

        final ListView listView = root.findViewById(R.id.ass_listView);
        ListViewAdapter adapter = new ListViewAdapter();
        listView.setAdapter(adapter);

        return root;
    }

    class ListViewAdapter extends BaseAdapter {

        Context context = null;
        String[] diseaseText = {"폐렴", "관상동맥우회술", "급성기뇌졸중", "급성심근경색증", "간암", "폐암", "대장암", "위암", "유방암"};

        @Override
        public int getCount() {
            return diseaseText.length;
        }

        @Override
        public Object getItem(int i) {
            return diseaseText[i];
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
                view = layoutInflater.inflate(R.layout.fragment_assessment_item, viewGroup, false);
            }

            TextView TextView = view.findViewById(R.id.disease_name);
            TextView.setText(diseaseText[i]);

            final String assinfo = diseaseText[i];
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, assinfo + " 클릭", Toast.LENGTH_SHORT).show();
                    ((MainActivity)getActivity()).replaceFragment(R.id.assessmentListFragment);
                }
            });

            return view;
        }
    }
}