package com.example.hopithopit.ui.acceptable;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.hopithopit.MainActivity;
import com.example.hopithopit.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AcceptableFragment extends Fragment {

    private AcceptableViewModel acceptableViewModel;
    private Spinner spinner1;
    private Spinner spinner2;
    //선택된 대구 경북 시구군
    private String select_dg;
    private String select_gb;
    //병상 출력, 중증 응급 질환 정보 출력
    TextView txt_room;
    TextView txt_dis;
    CheckBox er_check, ward_check;
    CheckBox ce_he_check, ce_in_check, mi_check, ab_in_check, massive_bu_check, connect_li_check, em_en_check, em_di_check, pre_mom_check, mental_pat_check, baby_check;
    Button room_button, dis_button;

    String room_dal_api = "http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EB%8C%80%EA%B5%AC%EA%B4%91%EC%97%AD%EC%8B%9C&STAGE2=%EB%8B%AC%EC%84%9C%EA%B5%AC&pageNo=1&numOfRows=10";
    String room_buk_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EB%8C%80%EA%B5%AC%EA%B4%91%EC%97%AD%EC%8B%9C&STAGE2=%EB%B6%81%EA%B5%AC&pageNo=1&numOfRows=10";
    String room_dong_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EB%8C%80%EA%B5%AC%EA%B4%91%EC%97%AD%EC%8B%9C&STAGE2=%EB%8F%99%EA%B5%AC&pageNo=1&numOfRows=10";
    String room_nam_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EB%8C%80%EA%B5%AC%EA%B4%91%EC%97%AD%EC%8B%9C&STAGE2=%EB%82%A8%EA%B5%AC&pageNo=1&numOfRows=10";
    String room_su_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EB%8C%80%EA%B5%AC%EA%B4%91%EC%97%AD%EC%8B%9C&STAGE2=%EC%88%98%EC%84%B1%EA%B5%AC&pageNo=1&numOfRows=10";
    String room_seo_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EB%8C%80%EA%B5%AC%EA%B4%91%EC%97%AD%EC%8B%9C&STAGE2=%EC%84%9C%EA%B5%AC&pageNo=1&numOfRows=10";
    String room_jung_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EB%8C%80%EA%B5%AC%EA%B4%91%EC%97%AD%EC%8B%9C&STAGE2=%EC%A4%91%EA%B5%AC&pageNo=1&numOfRows=10";
    String room_gun_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EB%8C%80%EA%B5%AC%EA%B4%91%EC%97%AD%EC%8B%9C&STAGE2=%EB%8B%AC%EC%84%9C%EA%B5%B0&pageNo=1&numOfRows=10";

    String dis_dal_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getSrsillDissAceptncPosblInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EB%8C%80%EA%B5%AC%EA%B4%91%EC%97%AD%EC%8B%9C&STAGE2=%EB%8B%AC%EC%84%9C%EA%B5%AC&SM_TYPE=1&pageNo=1&numOfRows=10";
    String dis_buk_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getSrsillDissAceptncPosblInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EB%8C%80%EA%B5%AC%EA%B4%91%EC%97%AD%EC%8B%9C&STAGE2=%EB%B6%81%EA%B5%AC&SM_TYPE=1&pageNo=1&numOfRows=10";
    String dis_dong_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getSrsillDissAceptncPosblInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EB%8C%80%EA%B5%AC%EA%B4%91%EC%97%AD%EC%8B%9C&STAGE2=%EB%8F%99%EA%B5%AC&SM_TYPE=1&pageNo=1&numOfRows=10";
    String dis_nam_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getSrsillDissAceptncPosblInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EB%8C%80%EA%B5%AC%EA%B4%91%EC%97%AD%EC%8B%9C&STAGE2=%EB%82%A8%EA%B5%AC&SM_TYPE=1&pageNo=1&numOfRows=10";
    String dis_su_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getSrsillDissAceptncPosblInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EB%8C%80%EA%B5%AC%EA%B4%91%EC%97%AD%EC%8B%9C&STAGE2=%EC%88%98%EC%84%B1%EA%B5%AC&SM_TYPE=1&pageNo=1&numOfRows=10";
    String dis_seo_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getSrsillDissAceptncPosblInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EB%8C%80%EA%B5%AC%EA%B4%91%EC%97%AD%EC%8B%9C&STAGE2=%EC%84%9C%EA%B5%AC&SM_TYPE=1&pageNo=1&numOfRows=10";
    String dis_jung_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getSrsillDissAceptncPosblInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EB%8C%80%EA%B5%AC%EA%B4%91%EC%97%AD%EC%8B%9C&STAGE2=%EC%A4%91%EA%B5%AC&SM_TYPE=1&pageNo=1&numOfRows=10";
    String dis_gun_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getSrsillDissAceptncPosblInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EB%8C%80%EA%B5%AC%EA%B4%91%EC%97%AD%EC%8B%9C&STAGE2=%EB%8B%AC%EC%84%B1%EA%B5%B0&SM_TYPE=1&pageNo=1&numOfRows=10";
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        acceptableViewModel =
                ViewModelProviders.of(this).get(AcceptableViewModel.class);
        View root = inflater.inflate(R.layout.fragment_acceptable, container, false);
        txt_room=(TextView)root.findViewById(R.id.text_room);
        txt_dis=(TextView)root.findViewById(R.id.text_dis);
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
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapter1.getItem(i).equals("대구")){
                    final ArrayAdapter<String> adapter2=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, dg);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(adapter2);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            select_dg= (String) adapterView.getItemAtPosition(i);
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }
                else{
                    final ArrayAdapter<String> adapter2=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, gb);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(adapter2);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            select_gb= (String) adapterView.getItemAtPosition(i);
                            //txt_room.setText(select_gb);
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

        er_check=(CheckBox)root.findViewById(R.id.er);
        ward_check=(CheckBox)root.findViewById(R.id.ward);
        room_button=(Button) root.findViewById(R.id.room_button);

        room_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //응급실 체크박스 선택
                if(er_check.isChecked()){
                    if (select_dg.equals("달서구")) {
                        DownloadWebpageTask task = new DownloadWebpageTask();
                        task.execute(room_dal_api);
                    }
                    else if(select_dg.equals("북구")){
                        txt_room.setText("북구선택");
                        DownloadWebpageTask task=new DownloadWebpageTask();
                        task.execute(room_buk_api);
                    }
                    else if(select_dg.equals("동구")){
                        DownloadWebpageTask task=new DownloadWebpageTask();
                        task.execute(room_dong_api);
                    }
                    else if(select_dg.equals("남구")){
                        DownloadWebpageTask task=new DownloadWebpageTask();
                        task.execute(room_nam_api);
                    }
                    else if(select_dg.equals("수성구")){
                        DownloadWebpageTask task=new DownloadWebpageTask();
                        task.execute(room_su_api);
                    }
                    else if(select_dg.equals("서구")){
                        DownloadWebpageTask task=new DownloadWebpageTask();
                        task.execute(room_seo_api);
                    }
                    else if(select_dg.equals("중구")){
                        DownloadWebpageTask task=new DownloadWebpageTask();
                        task.execute(room_jung_api);
                    }
                    else if(select_dg.equals("달서군")){
                        DownloadWebpageTask task=new DownloadWebpageTask();
                        task.execute(room_gun_api);
                    }

                }
                //입원실 체크박스 선택
                else if(ward_check.isChecked()){
                    if (select_dg.equals("달서구")) {
                        DownloadWebpageTask2 task = new DownloadWebpageTask2();
                        task.execute(room_dal_api);
                    }
                    else if(select_dg.equals("북구")){
                        DownloadWebpageTask2 task=new DownloadWebpageTask2();
                        task.execute(room_buk_api);
                    }
                    else if(select_dg.equals("동구")){
                        DownloadWebpageTask2 task=new DownloadWebpageTask2();
                        task.execute(room_dong_api);
                    }
                    else if(select_dg.equals("남구")){
                        DownloadWebpageTask2 task=new DownloadWebpageTask2();
                        task.execute(room_nam_api);
                    }
                    else if(select_dg.equals("수성구")){
                        DownloadWebpageTask2 task=new DownloadWebpageTask2();
                        task.execute(room_su_api);
                    }
                    else if(select_dg.equals("서구")){
                        DownloadWebpageTask2 task=new DownloadWebpageTask2();
                        task.execute(room_seo_api);
                    }
                    else if(select_dg.equals("중구")){
                        DownloadWebpageTask2 task=new DownloadWebpageTask2();
                        task.execute(room_jung_api);
                    }
                    else if(select_dg.equals("달서군")){
                        DownloadWebpageTask2 task=new DownloadWebpageTask2();
                        task.execute(room_gun_api);
                    }
                }
            }
        });

        ce_he_check=(CheckBox)root.findViewById(R.id.ce_he);
        ce_in_check=(CheckBox)root.findViewById(R.id.ce_in);
        mi_check=(CheckBox)root.findViewById(R.id.mi);
        ab_in_check=(CheckBox)root.findViewById(R.id.ab_in);
        massive_bu_check=(CheckBox)root.findViewById(R.id.massive_bu);
        connect_li_check=(CheckBox)root.findViewById(R.id.connect_li);
        em_en_check=(CheckBox)root.findViewById(R.id.em_en);
        em_di_check=(CheckBox)root.findViewById(R.id.em_di);
        pre_mom_check=(CheckBox)root.findViewById(R.id.pre_mom);
        mental_pat_check=(CheckBox)root.findViewById(R.id.mental_pat);
        baby_check=(CheckBox)root.findViewById(R.id.baby);
        dis_button=(Button)root.findViewById(R.id.dis_button);

        dis_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //뇌출혈 체크박스 선택
                if(ce_he_check.isChecked()){
                    if (select_dg.equals("달서구")) {
                        DownloadWebpageTask3 task = new DownloadWebpageTask3();
                        task.execute(dis_dal_api);
                    }
                    else if(select_dg.equals("북구")){
                        DownloadWebpageTask3 task=new DownloadWebpageTask3();
                        task.execute(dis_buk_api);
                    }
                    else if(select_dg.equals("동구")){
                        DownloadWebpageTask3 task=new DownloadWebpageTask3();
                        task.execute(dis_dong_api);
                    }
                    else if(select_dg.equals("남구")){
                        DownloadWebpageTask3 task=new DownloadWebpageTask3();
                        task.execute(dis_nam_api);
                    }
                    else if(select_dg.equals("수성구")){
                        DownloadWebpageTask3 task=new DownloadWebpageTask3();
                        task.execute(dis_su_api);
                    }
                    else if(select_dg.equals("서구")){
                        DownloadWebpageTask3 task=new DownloadWebpageTask3();
                        task.execute(dis_seo_api);
                    }
                    else if(select_dg.equals("중구")){
                        DownloadWebpageTask3 task=new DownloadWebpageTask3();
                        task.execute(dis_jung_api);
                    }
                    else if(select_dg.equals("달서군")){
                        DownloadWebpageTask3 task=new DownloadWebpageTask3();
                        task.execute(dis_gun_api);
                    }

                }
                //뇌경색 체크박스 선택
                else if(ce_in_check.isChecked()){
                    if (select_dg.equals("달서구")) {
                        DownloadWebpageTask4 task = new DownloadWebpageTask4();
                        task.execute(dis_dal_api);
                    }
                    else if(select_dg.equals("북구")){
                        DownloadWebpageTask4 task=new DownloadWebpageTask4();
                        task.execute(dis_buk_api);
                    }
                    else if(select_dg.equals("동구")){
                        DownloadWebpageTask4 task=new DownloadWebpageTask4();
                        task.execute(dis_dong_api);
                    }
                    else if(select_dg.equals("남구")){
                        DownloadWebpageTask4 task=new DownloadWebpageTask4();
                        task.execute(dis_nam_api);
                    }
                    else if(select_dg.equals("수성구")){
                        DownloadWebpageTask4 task=new DownloadWebpageTask4();
                        task.execute(dis_su_api);
                    }
                    else if(select_dg.equals("서구")){
                        DownloadWebpageTask4 task=new DownloadWebpageTask4();
                        task.execute(dis_seo_api);
                    }
                    else if(select_dg.equals("중구")){
                        DownloadWebpageTask4 task=new DownloadWebpageTask4();
                        task.execute(dis_jung_api);
                    }
                    else if(select_dg.equals("달서군")){
                        DownloadWebpageTask4 task=new DownloadWebpageTask4();
                        task.execute(dis_gun_api);
                    }
                } //심근경색
                else if(mi_check.isChecked()){
                    if (select_dg.equals("달서구")) {
                        DownloadWebpageTask5 task = new DownloadWebpageTask5();
                        task.execute(dis_dal_api);
                    }
                    else if(select_dg.equals("북구")){
                        DownloadWebpageTask5 task= new DownloadWebpageTask5();
                        task.execute(dis_buk_api);
                    }
                    else if(select_dg.equals("동구")){
                        DownloadWebpageTask5 task= new DownloadWebpageTask5();
                        task.execute(dis_dong_api);
                    }
                    else if(select_dg.equals("남구")){
                        DownloadWebpageTask5 task= new DownloadWebpageTask5();
                        task.execute(dis_nam_api);
                    }
                    else if(select_dg.equals("수성구")){
                        DownloadWebpageTask5 task = new DownloadWebpageTask5();
                        task.execute(dis_su_api);
                    }
                    else if(select_dg.equals("서구")){
                        DownloadWebpageTask5 task= new DownloadWebpageTask5();
                        task.execute(dis_seo_api);
                    }
                    else if(select_dg.equals("중구")){
                        DownloadWebpageTask5 task= new DownloadWebpageTask5();
                        task.execute(dis_jung_api);
                    }
                    else if(select_dg.equals("달서군")){
                        DownloadWebpageTask5 task= new DownloadWebpageTask5();
                        task.execute(dis_gun_api);
                    }
                }//복부손상
                else if(ab_in_check.isChecked()){
                    if (select_dg.equals("달서구")) {
                        DownloadWebpageTask6 task = new DownloadWebpageTask6();
                        task.execute(dis_dal_api);
                    }
                    else if(select_dg.equals("북구")){
                        DownloadWebpageTask6 task= new DownloadWebpageTask6();
                        task.execute(dis_buk_api);
                    }
                    else if(select_dg.equals("동구")){
                        DownloadWebpageTask6 task= new DownloadWebpageTask6();
                        task.execute(dis_dong_api);
                    }
                    else if(select_dg.equals("남구")){
                        DownloadWebpageTask6 task= new DownloadWebpageTask6();
                        task.execute(dis_nam_api);
                    }
                    else if(select_dg.equals("수성구")){
                        DownloadWebpageTask6 task= new DownloadWebpageTask6();
                        task.execute(dis_su_api);
                    }
                    else if(select_dg.equals("서구")){
                        DownloadWebpageTask6 task= new DownloadWebpageTask6();
                        task.execute(dis_seo_api);
                    }
                    else if(select_dg.equals("중구")){
                        DownloadWebpageTask6 task= new DownloadWebpageTask6();
                        task.execute(dis_jung_api);
                    }
                    else if(select_dg.equals("달서군")){
                        DownloadWebpageTask6 task= new DownloadWebpageTask6();
                        task.execute(dis_gun_api);
                    }
                }//중증화상
                else if(massive_bu_check.isChecked()){
                    if (select_dg.equals("달서구")) {
                        DownloadWebpageTask7 task = new DownloadWebpageTask7();
                        task.execute(dis_dal_api);
                    }
                    else if(select_dg.equals("북구")){
                        DownloadWebpageTask7 task= new DownloadWebpageTask7();
                        task.execute(dis_buk_api);
                    }
                    else if(select_dg.equals("동구")){
                        DownloadWebpageTask7 task= new DownloadWebpageTask7();
                        task.execute(dis_dong_api);
                    }
                    else if(select_dg.equals("남구")){
                        DownloadWebpageTask7 task= new DownloadWebpageTask7();
                        task.execute(dis_nam_api);
                    }
                    else if(select_dg.equals("수성구")){
                        DownloadWebpageTask7 task= new DownloadWebpageTask7();
                        task.execute(dis_su_api);
                    }
                    else if(select_dg.equals("서구")){
                        DownloadWebpageTask7 task= new DownloadWebpageTask7();
                        task.execute(dis_seo_api);
                    }
                    else if(select_dg.equals("중구")){
                        DownloadWebpageTask7 task= new DownloadWebpageTask7();
                        task.execute(dis_jung_api);
                    }
                    else if(select_dg.equals("달서군")){
                        DownloadWebpageTask7 task= new DownloadWebpageTask7();
                        task.execute(dis_gun_api);
                    }
                }//사지접합
                else if(connect_li_check.isChecked()){
                    if (select_dg.equals("달서구")) {
                        DownloadWebpageTask8 task = new DownloadWebpageTask8();
                        task.execute(dis_dal_api);
                    }
                    else if(select_dg.equals("북구")){
                        DownloadWebpageTask8 task= new DownloadWebpageTask8();
                        task.execute(dis_buk_api);
                    }
                    else if(select_dg.equals("동구")){
                        DownloadWebpageTask8 task= new DownloadWebpageTask8();
                        task.execute(dis_dong_api);
                    }
                    else if(select_dg.equals("남구")){
                        DownloadWebpageTask8 task= new DownloadWebpageTask8();
                        task.execute(dis_nam_api);
                    }
                    else if(select_dg.equals("수성구")){
                        DownloadWebpageTask8 task= new DownloadWebpageTask8();
                        task.execute(dis_su_api);
                    }
                    else if(select_dg.equals("서구")){
                        DownloadWebpageTask8 task= new DownloadWebpageTask8();
                        task.execute(dis_seo_api);
                    }
                    else if(select_dg.equals("중구")){
                        DownloadWebpageTask8 task= new DownloadWebpageTask8();
                        task.execute(dis_jung_api);
                    }
                    else if(select_dg.equals("달서군")){
                        DownloadWebpageTask8 task= new DownloadWebpageTask8();
                        task.execute(dis_gun_api);
                    }
                }//응급내시경
                else if(em_en_check.isChecked()){
                    if (select_dg.equals("달서구")) {
                        DownloadWebpageTask9 task = new DownloadWebpageTask9();
                        task.execute(dis_dal_api);
                    }
                    else if(select_dg.equals("북구")){
                        DownloadWebpageTask9 task= new DownloadWebpageTask9();
                        task.execute(dis_buk_api);
                    }
                    else if(select_dg.equals("동구")){
                        DownloadWebpageTask9 task= new DownloadWebpageTask9();
                        task.execute(dis_dong_api);
                    }
                    else if(select_dg.equals("남구")){
                        DownloadWebpageTask9 task= new DownloadWebpageTask9();
                        task.execute(dis_nam_api);
                    }
                    else if(select_dg.equals("수성구")){
                        DownloadWebpageTask9 task= new DownloadWebpageTask9();
                        task.execute(dis_su_api);
                    }
                    else if(select_dg.equals("서구")){
                        DownloadWebpageTask9 task= new DownloadWebpageTask9();
                        task.execute(dis_seo_api);
                    }
                    else if(select_dg.equals("중구")){
                        DownloadWebpageTask9 task= new DownloadWebpageTask9();
                        task.execute(dis_jung_api);
                    }
                    else if(select_dg.equals("달서군")){
                        DownloadWebpageTask9 task= new DownloadWebpageTask9();
                        task.execute(dis_gun_api);
                    }
                }//응급투석
                else if(em_di_check.isChecked()){
                    if (select_dg.equals("달서구")) {
                        DownloadWebpageTask10 task = new DownloadWebpageTask10();
                        task.execute(dis_dal_api);
                    }
                    else if(select_dg.equals("북구")){
                        DownloadWebpageTask10 task= new DownloadWebpageTask10();
                        task.execute(dis_buk_api);
                    }
                    else if(select_dg.equals("동구")){
                        DownloadWebpageTask10 task= new DownloadWebpageTask10();
                        task.execute(dis_dong_api);
                    }
                    else if(select_dg.equals("남구")){
                        DownloadWebpageTask10 task= new DownloadWebpageTask10();
                        task.execute(dis_nam_api);
                    }
                    else if(select_dg.equals("수성구")){
                        DownloadWebpageTask10 task= new DownloadWebpageTask10();
                        task.execute(dis_su_api);
                    }
                    else if(select_dg.equals("서구")){
                        DownloadWebpageTask10 task= new DownloadWebpageTask10();
                        task.execute(dis_seo_api);
                    }
                    else if(select_dg.equals("중구")){
                        DownloadWebpageTask10 task= new DownloadWebpageTask10();
                        task.execute(dis_jung_api);
                    }
                    else if(select_dg.equals("달서군")){
                        DownloadWebpageTask10 task= new DownloadWebpageTask10();
                        task.execute(dis_gun_api);
                    }
                }//조산산모
                else if(pre_mom_check.isChecked()){
                    if (select_dg.equals("달서구")) {
                        DownloadWebpageTask11 task = new DownloadWebpageTask11();
                        task.execute(dis_dal_api);
                    }
                    else if(select_dg.equals("북구")){
                        DownloadWebpageTask11 task= new DownloadWebpageTask11();
                        task.execute(dis_buk_api);
                    }
                    else if(select_dg.equals("동구")){
                        DownloadWebpageTask11 task= new DownloadWebpageTask11();
                        task.execute(dis_dong_api);
                    }
                    else if(select_dg.equals("남구")){
                        DownloadWebpageTask11 task= new DownloadWebpageTask11();
                        task.execute(dis_nam_api);
                    }
                    else if(select_dg.equals("수성구")){
                        DownloadWebpageTask11 task= new DownloadWebpageTask11();
                        task.execute(dis_su_api);
                    }
                    else if(select_dg.equals("서구")){
                        DownloadWebpageTask11 task= new DownloadWebpageTask11();
                        task.execute(dis_seo_api);
                    }
                    else if(select_dg.equals("중구")){
                        DownloadWebpageTask11 task= new DownloadWebpageTask11();
                        task.execute(dis_jung_api);
                    }
                    else if(select_dg.equals("달서군")){
                        DownloadWebpageTask11 task= new DownloadWebpageTask11();
                        task.execute(dis_gun_api);
                    }
                }//정신질환자
                else if(mental_pat_check.isChecked()){
                    if (select_dg.equals("달서구")) {
                        DownloadWebpageTask12 task = new DownloadWebpageTask12();
                        task.execute(dis_dal_api);
                    }
                    else if(select_dg.equals("북구")){
                        DownloadWebpageTask12 task= new DownloadWebpageTask12();
                        task.execute(dis_buk_api);
                    }
                    else if(select_dg.equals("동구")){
                        DownloadWebpageTask12 task= new DownloadWebpageTask12();
                        task.execute(dis_dong_api);
                    }
                    else if(select_dg.equals("남구")){
                        DownloadWebpageTask12 task= new DownloadWebpageTask12();
                        task.execute(dis_nam_api);
                    }
                    else if(select_dg.equals("수성구")){
                        DownloadWebpageTask12 task= new DownloadWebpageTask12();
                        task.execute(dis_su_api);
                    }
                    else if(select_dg.equals("서구")){
                        DownloadWebpageTask12 task= new DownloadWebpageTask12();
                        task.execute(dis_seo_api);
                    }
                    else if(select_dg.equals("중구")){
                        DownloadWebpageTask12 task= new DownloadWebpageTask12();
                        task.execute(dis_jung_api);
                    }
                    else if(select_dg.equals("달서군")){
                        DownloadWebpageTask12 task= new DownloadWebpageTask12();
                        task.execute(dis_gun_api);
                    }
                }//신생아
                else if(baby_check.isChecked()){
                    if (select_dg.equals("달서구")) {
                        DownloadWebpageTask13 task = new DownloadWebpageTask13();
                        task.execute(dis_dal_api);
                    }
                    else if(select_dg.equals("북구")){
                        DownloadWebpageTask13 task= new DownloadWebpageTask13();
                        task.execute(dis_buk_api);
                    }
                    else if(select_dg.equals("동구")){
                        DownloadWebpageTask13 task= new DownloadWebpageTask13();
                        task.execute(dis_dong_api);
                    }
                    else if(select_dg.equals("남구")){
                        DownloadWebpageTask13 task= new DownloadWebpageTask13();
                        task.execute(dis_nam_api);
                    }
                    else if(select_dg.equals("수성구")){
                        DownloadWebpageTask13 task= new DownloadWebpageTask13();
                        task.execute(dis_su_api);
                    }
                    else if(select_dg.equals("서구")){
                        DownloadWebpageTask13 task= new DownloadWebpageTask13();
                        task.execute(dis_seo_api);
                    }
                    else if(select_dg.equals("중구")){
                        DownloadWebpageTask13 task= new DownloadWebpageTask13();
                        task.execute(dis_jung_api);
                    }
                    else if(select_dg.equals("달서군")){
                        DownloadWebpageTask13 task= new DownloadWebpageTask13();
                        task.execute(dis_gun_api);
                    }
                }
            }
        });

        return root;
    }
////응급실
    private class DownloadWebpageTask extends AsyncTask<String, Void, String>{
        protected String doInBackground(String... urls){
            try{
                String txt=(String)downloadUrl((String)urls[0]);
                return txt;
            }
            catch (IOException e){
                return "다운로드 실패";
            }
        }
        protected void onPostExecute(String result){
            boolean indutyName=false, inhvec=false, inhvgc=false, initem=false;
            String dutyName=null; String hvec=null; String hvgc=null;
            super.onPostExecute(result);
            txt_room.setText(" ");
            try {// XML Pull Parser 객체생성
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                // XML namespace 지원여부설정. 디폴트는false
                XmlPullParser xpp= factory.newPullParser();
                // 파싱할문서설정
                xpp.setInput(new StringReader(result));
                // 현재이벤트유형반환(START_DOCUMENT, START_TAG, TEXT, END_TAG, END_DOCUMENT)
                int eventType=xpp.getEventType();
                //여기까지 파싱 시작
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    switch (eventType){
                        case XmlPullParser.START_TAG:
                            if(xpp.getName().equals("dutyName")){
                                indutyName=true;
                            }
                            if(xpp.getName().equals("hvec")){
                                inhvec=true;
                            }
                            break;
                        case XmlPullParser.TEXT:
                            if(indutyName){
                                dutyName=xpp.getText();
                                indutyName=false;
                            }
                            if(inhvec){
                                hvec=xpp.getText();
                                inhvec=false;
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            if(xpp.getName().equals("item")){
                                txt_room.append("병원 이름: "+
                                        dutyName+"\n 응급실 잔여병상: "+
                                        hvec+"\n\n");
                                initem=false;
                            }
                            break;
                    }
                    eventType=xpp.next();
                }
            }catch (Exception e){
                txt_room.setText("에러ㅜ");
            }
        }
        private String downloadUrl(String api)throws IOException{
            HttpURLConnection conn=null;

            try{
                URL url=new URL(api);
                conn=(HttpURLConnection)url.openConnection();
                BufferedInputStream buf=new BufferedInputStream(conn.getInputStream());
                BufferedReader bufreader=new BufferedReader(new InputStreamReader(buf, "utf-8"));

                String line=null;
                String page="";
                while((line=bufreader.readLine())!=null){
                    page+=line;
                }
                return page;
            }finally {
                conn.disconnect();
            }

        }
    }

//입원실
    private class DownloadWebpageTask2 extends AsyncTask<String, Void, String>{
        protected String doInBackground(String... urls){
            try{
                String txt=(String)downloadUrl((String)urls[0]);
                return txt;
            }
            catch (IOException e){
                return "다운로드 실패";
            }
        }
        protected void onPostExecute(String result){
            boolean indutyName=false, inhvec=false, inhvgc=false, initem=false;
            String dutyName=null; String hvec=null; String hvgc=null;
            super.onPostExecute(result);
            txt_room.setText(" ");
            try {// XML Pull Parser 객체생성
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                // XML namespace 지원여부설정. 디폴트는false
                XmlPullParser xpp= factory.newPullParser();
                // 파싱할문서설정
                xpp.setInput(new StringReader(result));
                // 현재이벤트유형반환(START_DOCUMENT, START_TAG, TEXT, END_TAG, END_DOCUMENT)
                int eventType=xpp.getEventType();
                //여기까지 파싱 시작
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    switch (eventType){
                        case XmlPullParser.START_TAG:
                            if(xpp.getName().equals("dutyName")){
                                indutyName=true;
                            }
                            if(xpp.getName().equals("hvgc")){
                                inhvgc=true;
                            }
                            break;
                        case XmlPullParser.TEXT:
                            if(indutyName){
                                dutyName=xpp.getText();
                                indutyName=false;
                            }
                            if(inhvgc){
                                hvgc=xpp.getText();
                                inhvgc=false;
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            if(xpp.getName().equals("item")){
                                txt_room.append("병원 이름: "+
                                        dutyName+"\n 입원실 잔여 병상: "+
                                        hvgc+"\n\n");
                                initem=false;
                            }
                            break;
                    }
                    eventType=xpp.next();
                }
            }catch (Exception e){
                txt_room.setText("에러ㅜ");
            }
        }
        private String downloadUrl(String api)throws IOException{
            HttpURLConnection conn=null;

            try{
                URL url=new URL(api);
                conn=(HttpURLConnection)url.openConnection();
                BufferedInputStream buf=new BufferedInputStream(conn.getInputStream());
                BufferedReader bufreader=new BufferedReader(new InputStreamReader(buf, "utf-8"));

                String line=null;
                String page="";
                while((line=bufreader.readLine())!=null){
                    page+=line;
                }
                return page;
            }finally {
                conn.disconnect();
            }

        }
    }
    //뇌출혈
    private class DownloadWebpageTask3 extends AsyncTask<String, Void, String>{
        protected String doInBackground(String... urls){
            try{
                String txt=(String)downloadUrl((String)urls[0]);
                return txt;
            }
            catch (IOException e){
                return "다운로드 실패";
            }
        }
        protected void onPostExecute(String result){
            boolean indutyName=false, inMKioskTy1=false, initem=false;
            String dutyName=null; String MKioskTy1=null;
            super.onPostExecute(result);
            txt_dis.setText(" ");
            try {// XML Pull Parser 객체생성
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                // XML namespace 지원여부설정. 디폴트는false
                XmlPullParser xpp= factory.newPullParser();
                // 파싱할문서설정
                xpp.setInput(new StringReader(result));
                // 현재이벤트유형반환(START_DOCUMENT, START_TAG, TEXT, END_TAG, END_DOCUMENT)
                int eventType=xpp.getEventType();
                //여기까지 파싱 시작
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    switch (eventType){
                        case XmlPullParser.START_TAG:
                            if(xpp.getName().equals("dutyName")){
                                indutyName=true;
                            }
                            if(xpp.getName().equals("MKioskTy1")){
                                inMKioskTy1=true;
                            }
                            break;
                        case XmlPullParser.TEXT:
                            if(indutyName){
                                dutyName=xpp.getText();
                                indutyName=false;
                            }
                            if(inMKioskTy1){
                                MKioskTy1=xpp.getText();
                                inMKioskTy1=false;
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            if(xpp.getName().equals("item")){
                                txt_dis.append("병원 이름: "+
                                        dutyName+"\n 수용 가능 여부: "+
                                        MKioskTy1+"\n\n");
                                initem=false;
                            }
                            break;
                    }
                    eventType=xpp.next();
                }
            }catch (Exception e){
                txt_dis.setText("에러ㅜ");
            }
        }
        private String downloadUrl(String api)throws IOException{
            HttpURLConnection conn=null;

            try{
                URL url=new URL(api);
                conn=(HttpURLConnection)url.openConnection();
                BufferedInputStream buf=new BufferedInputStream(conn.getInputStream());
                BufferedReader bufreader=new BufferedReader(new InputStreamReader(buf, "utf-8"));

                String line=null;
                String page="";
                while((line=bufreader.readLine())!=null){
                    page+=line;
                }
                return page;
            }finally {
                conn.disconnect();
            }

        }
    }
    //뇌경색
    private class DownloadWebpageTask4 extends AsyncTask<String, Void, String>{
        protected String doInBackground(String... urls){
            try{
                String txt=(String)downloadUrl((String)urls[0]);
                return txt;
            }
            catch (IOException e){
                return "다운로드 실패";
            }
        }
        protected void onPostExecute(String result){
            boolean indutyName=false, inMKioskTy2=false, initem=false;
            String dutyName=null; String MKioskTy2=null;
            super.onPostExecute(result);
            txt_dis.setText(" ");
            try {// XML Pull Parser 객체생성
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                // XML namespace 지원여부설정. 디폴트는false
                XmlPullParser xpp= factory.newPullParser();
                // 파싱할문서설정
                xpp.setInput(new StringReader(result));
                // 현재이벤트유형반환(START_DOCUMENT, START_TAG, TEXT, END_TAG, END_DOCUMENT)
                int eventType=xpp.getEventType();
                //여기까지 파싱 시작
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    switch (eventType){
                        case XmlPullParser.START_TAG:
                            if(xpp.getName().equals("dutyName")){
                                indutyName=true;
                            }
                            if(xpp.getName().equals("MKioskTy2")){
                                inMKioskTy2=true;
                            }
                            break;
                        case XmlPullParser.TEXT:
                            if(indutyName){
                                dutyName=xpp.getText();
                                indutyName=false;
                            }
                            if(inMKioskTy2){
                                MKioskTy2=xpp.getText();
                                inMKioskTy2=false;
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            if(xpp.getName().equals("item")){
                                txt_dis.append("병원 이름: "+
                                        dutyName+"\n 수용 가능 여부: "+
                                        MKioskTy2+"\n\n");
                                initem=false;
                            }
                            break;
                    }
                    eventType=xpp.next();
                }
            }catch (Exception e){
                txt_dis.setText("에러ㅜ");
            }
        }
        private String downloadUrl(String api)throws IOException{
            HttpURLConnection conn=null;

            try{
                URL url=new URL(api);
                conn=(HttpURLConnection)url.openConnection();
                BufferedInputStream buf=new BufferedInputStream(conn.getInputStream());
                BufferedReader bufreader=new BufferedReader(new InputStreamReader(buf, "utf-8"));

                String line=null;
                String page="";
                while((line=bufreader.readLine())!=null){
                    page+=line;
                }
                return page;
            }finally {
                conn.disconnect();
            }

        }
    }
    //심근경색
    private class DownloadWebpageTask5 extends AsyncTask<String, Void, String>{
        protected String doInBackground(String... urls){
            try{
                String txt=(String)downloadUrl((String)urls[0]);
                return txt;
            }
            catch (IOException e){
                return "다운로드 실패";
            }
        }
        protected void onPostExecute(String result){
            boolean indutyName=false, inMKioskTy3=false, initem=false;
            String dutyName=null; String MKioskTy3=null;
            super.onPostExecute(result);
            txt_dis.setText(" ");
            try {// XML Pull Parser 객체생성
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                // XML namespace 지원여부설정. 디폴트는false
                XmlPullParser xpp= factory.newPullParser();
                // 파싱할문서설정
                xpp.setInput(new StringReader(result));
                // 현재이벤트유형반환(START_DOCUMENT, START_TAG, TEXT, END_TAG, END_DOCUMENT)
                int eventType=xpp.getEventType();
                //여기까지 파싱 시작
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    switch (eventType){
                        case XmlPullParser.START_TAG:
                            if(xpp.getName().equals("dutyName")){
                                indutyName=true;
                            }
                            if(xpp.getName().equals("MKioskTy3")){
                                inMKioskTy3=true;
                            }
                            break;
                        case XmlPullParser.TEXT:
                            if(indutyName){
                                dutyName=xpp.getText();
                                indutyName=false;
                            }
                            if(inMKioskTy3){
                                MKioskTy3=xpp.getText();
                                inMKioskTy3=false;
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            if(xpp.getName().equals("item")){
                                txt_dis.append("병원 이름: "+
                                        dutyName+"\n 수용 가능 여부: "+
                                        MKioskTy3+"\n\n");
                                initem=false;
                            }
                            break;
                    }
                    eventType=xpp.next();
                }
            }catch (Exception e){
                txt_dis.setText("에러ㅜ");
            }
        }
        private String downloadUrl(String api)throws IOException{
            HttpURLConnection conn=null;

            try{
                URL url=new URL(api);
                conn=(HttpURLConnection)url.openConnection();
                BufferedInputStream buf=new BufferedInputStream(conn.getInputStream());
                BufferedReader bufreader=new BufferedReader(new InputStreamReader(buf, "utf-8"));

                String line=null;
                String page="";
                while((line=bufreader.readLine())!=null){
                    page+=line;
                }
                return page;
            }finally {
                conn.disconnect();
            }

        }
    }
    //복부손상
    private class DownloadWebpageTask6 extends AsyncTask<String, Void, String>{
        protected String doInBackground(String... urls){
            try{
                String txt=(String)downloadUrl((String)urls[0]);
                return txt;
            }
            catch (IOException e){
                return "다운로드 실패";
            }
        }
        protected void onPostExecute(String result){
            boolean indutyName=false, inMKioskTy4=false, initem=false;
            String dutyName=null; String MKioskTy4=null;
            super.onPostExecute(result);
            txt_dis.setText(" ");
            try {// XML Pull Parser 객체생성
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                // XML namespace 지원여부설정. 디폴트는false
                XmlPullParser xpp= factory.newPullParser();
                // 파싱할문서설정
                xpp.setInput(new StringReader(result));
                // 현재이벤트유형반환(START_DOCUMENT, START_TAG, TEXT, END_TAG, END_DOCUMENT)
                int eventType=xpp.getEventType();
                //여기까지 파싱 시작
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    switch (eventType){
                        case XmlPullParser.START_TAG:
                            if(xpp.getName().equals("dutyName")){
                                indutyName=true;
                            }
                            if(xpp.getName().equals("MKioskTy4")){
                                inMKioskTy4=true;
                            }
                            break;
                        case XmlPullParser.TEXT:
                            if(indutyName){
                                dutyName=xpp.getText();
                                indutyName=false;
                            }
                            if(inMKioskTy4){
                                MKioskTy4=xpp.getText();
                                inMKioskTy4=false;
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            if(xpp.getName().equals("item")){
                                txt_dis.append("병원 이름: "+
                                        dutyName+"\n 수용 가능 여부: "+
                                        MKioskTy4+"\n\n");
                                initem=false;
                            }
                            break;
                    }
                    eventType=xpp.next();
                }
            }catch (Exception e){
                txt_dis.setText("에러ㅜ");
            }
        }
        private String downloadUrl(String api)throws IOException{
            HttpURLConnection conn=null;

            try{
                URL url=new URL(api);
                conn=(HttpURLConnection)url.openConnection();
                BufferedInputStream buf=new BufferedInputStream(conn.getInputStream());
                BufferedReader bufreader=new BufferedReader(new InputStreamReader(buf, "utf-8"));

                String line=null;
                String page="";
                while((line=bufreader.readLine())!=null){
                    page+=line;
                }
                return page;
            }finally {
                conn.disconnect();
            }

        }
    }
    //중증화상
    private class DownloadWebpageTask7 extends AsyncTask<String, Void, String>{
        protected String doInBackground(String... urls){
            try{
                String txt=(String)downloadUrl((String)urls[0]);
                return txt;
            }
            catch (IOException e){
                return "다운로드 실패";
            }
        }
        protected void onPostExecute(String result){
            boolean indutyName=false, inMKioskTy5=false, initem=false;
            String dutyName=null; String MKioskTy5=null;
            super.onPostExecute(result);
            txt_dis.setText(" ");
            try {// XML Pull Parser 객체생성
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                // XML namespace 지원여부설정. 디폴트는false
                XmlPullParser xpp= factory.newPullParser();
                // 파싱할문서설정
                xpp.setInput(new StringReader(result));
                // 현재이벤트유형반환(START_DOCUMENT, START_TAG, TEXT, END_TAG, END_DOCUMENT)
                int eventType=xpp.getEventType();
                //여기까지 파싱 시작
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    switch (eventType){
                        case XmlPullParser.START_TAG:
                            if(xpp.getName().equals("dutyName")){
                                indutyName=true;
                            }
                            if(xpp.getName().equals("MKioskTy5")){
                                inMKioskTy5=true;
                            }
                            break;
                        case XmlPullParser.TEXT:
                            if(indutyName){
                                dutyName=xpp.getText();
                                indutyName=false;
                            }
                            if(inMKioskTy5){
                                MKioskTy5=xpp.getText();
                                inMKioskTy5=false;
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            if(xpp.getName().equals("item")){
                                txt_dis.append("병원 이름: "+
                                        dutyName+"\n 수용 가능 여부: "+
                                        MKioskTy5+"\n\n");
                                initem=false;
                            }
                            break;
                    }
                    eventType=xpp.next();
                }
            }catch (Exception e){
                txt_dis.setText("에러ㅜ");
            }
        }
        private String downloadUrl(String api)throws IOException{
            HttpURLConnection conn=null;

            try{
                URL url=new URL(api);
                conn=(HttpURLConnection)url.openConnection();
                BufferedInputStream buf=new BufferedInputStream(conn.getInputStream());
                BufferedReader bufreader=new BufferedReader(new InputStreamReader(buf, "utf-8"));

                String line=null;
                String page="";
                while((line=bufreader.readLine())!=null){
                    page+=line;
                }
                return page;
            }finally {
                conn.disconnect();
            }

        }
    }
    //사지접합
    private class DownloadWebpageTask8 extends AsyncTask<String, Void, String>{
        protected String doInBackground(String... urls){
            try{
                String txt=(String)downloadUrl((String)urls[0]);
                return txt;
            }
            catch (IOException e){
                return "다운로드 실패";
            }
        }
        protected void onPostExecute(String result){
            boolean indutyName=false, inMKioskTy6=false, initem=false;
            String dutyName=null; String MKioskTy6=null;
            super.onPostExecute(result);
            txt_dis.setText(" ");
            try {// XML Pull Parser 객체생성
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                // XML namespace 지원여부설정. 디폴트는false
                XmlPullParser xpp= factory.newPullParser();
                // 파싱할문서설정
                xpp.setInput(new StringReader(result));
                // 현재이벤트유형반환(START_DOCUMENT, START_TAG, TEXT, END_TAG, END_DOCUMENT)
                int eventType=xpp.getEventType();
                //여기까지 파싱 시작
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    switch (eventType){
                        case XmlPullParser.START_TAG:
                            if(xpp.getName().equals("dutyName")){
                                indutyName=true;
                            }
                            if(xpp.getName().equals("MKioskTy6")){
                                inMKioskTy6=true;
                            }
                            break;
                        case XmlPullParser.TEXT:
                            if(indutyName){
                                dutyName=xpp.getText();
                                indutyName=false;
                            }
                            if(inMKioskTy6){
                                MKioskTy6=xpp.getText();
                                inMKioskTy6=false;
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            if(xpp.getName().equals("item")){
                                txt_dis.append("병원 이름: "+
                                        dutyName+"\n 수용 가능 여부: "+
                                        MKioskTy6+"\n\n");
                                initem=false;
                            }
                            break;
                    }
                    eventType=xpp.next();
                }
            }catch (Exception e){
                txt_dis.setText("에러ㅜ");
            }
        }
        private String downloadUrl(String api)throws IOException{
            HttpURLConnection conn=null;

            try{
                URL url=new URL(api);
                conn=(HttpURLConnection)url.openConnection();
                BufferedInputStream buf=new BufferedInputStream(conn.getInputStream());
                BufferedReader bufreader=new BufferedReader(new InputStreamReader(buf, "utf-8"));

                String line=null;
                String page="";
                while((line=bufreader.readLine())!=null){
                    page+=line;
                }
                return page;
            }finally {
                conn.disconnect();
            }

        }
    }
    //응급내시경
    private class DownloadWebpageTask9 extends AsyncTask<String, Void, String>{
        protected String doInBackground(String... urls){
            try{
                String txt=(String)downloadUrl((String)urls[0]);
                return txt;
            }
            catch (IOException e){
                return "다운로드 실패";
            }
        }
        protected void onPostExecute(String result){
            boolean indutyName=false, inMKioskTy7=false, initem=false;
            String dutyName=null; String MKioskTy7=null;
            super.onPostExecute(result);
            txt_dis.setText(" ");
            try {// XML Pull Parser 객체생성
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                // XML namespace 지원여부설정. 디폴트는false
                XmlPullParser xpp= factory.newPullParser();
                // 파싱할문서설정
                xpp.setInput(new StringReader(result));
                // 현재이벤트유형반환(START_DOCUMENT, START_TAG, TEXT, END_TAG, END_DOCUMENT)
                int eventType=xpp.getEventType();
                //여기까지 파싱 시작
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    switch (eventType){
                        case XmlPullParser.START_TAG:
                            if(xpp.getName().equals("dutyName")){
                                indutyName=true;
                            }
                            if(xpp.getName().equals("MKioskTy7")){
                                inMKioskTy7=true;
                            }
                            break;
                        case XmlPullParser.TEXT:
                            if(indutyName){
                                dutyName=xpp.getText();
                                indutyName=false;
                            }
                            if(inMKioskTy7){
                                MKioskTy7=xpp.getText();
                                inMKioskTy7=false;
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            if(xpp.getName().equals("item")){
                                txt_dis.append("병원 이름: "+
                                        dutyName+"\n 수용 가능 여부: "+
                                        MKioskTy7+"\n\n");
                                initem=false;
                            }
                            break;
                    }
                    eventType=xpp.next();
                }
            }catch (Exception e){
                txt_dis.setText("에러ㅜ");
            }
        }
        private String downloadUrl(String api)throws IOException{
            HttpURLConnection conn=null;

            try{
                URL url=new URL(api);
                conn=(HttpURLConnection)url.openConnection();
                BufferedInputStream buf=new BufferedInputStream(conn.getInputStream());
                BufferedReader bufreader=new BufferedReader(new InputStreamReader(buf, "utf-8"));

                String line=null;
                String page="";
                while((line=bufreader.readLine())!=null){
                    page+=line;
                }
                return page;
            }finally {
                conn.disconnect();
            }

        }
    }
    //응급투석
    private class DownloadWebpageTask10 extends AsyncTask<String, Void, String>{
        protected String doInBackground(String... urls){
            try{
                String txt=(String)downloadUrl((String)urls[0]);
                return txt;
            }
            catch (IOException e){
                return "다운로드 실패";
            }
        }
        protected void onPostExecute(String result){
            boolean indutyName=false, inMKioskTy8=false, initem=false;
            String dutyName=null; String MKioskTy8=null;
            super.onPostExecute(result);
            txt_dis.setText(" ");
            try {// XML Pull Parser 객체생성
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                // XML namespace 지원여부설정. 디폴트는false
                XmlPullParser xpp= factory.newPullParser();
                // 파싱할문서설정
                xpp.setInput(new StringReader(result));
                // 현재이벤트유형반환(START_DOCUMENT, START_TAG, TEXT, END_TAG, END_DOCUMENT)
                int eventType=xpp.getEventType();
                //여기까지 파싱 시작
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    switch (eventType){
                        case XmlPullParser.START_TAG:
                            if(xpp.getName().equals("dutyName")){
                                indutyName=true;
                            }
                            if(xpp.getName().equals("MKioskTy8")){
                                inMKioskTy8=true;
                            }
                            break;
                        case XmlPullParser.TEXT:
                            if(indutyName){
                                dutyName=xpp.getText();
                                indutyName=false;
                            }
                            if(inMKioskTy8){
                                MKioskTy8=xpp.getText();
                                inMKioskTy8=false;
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            if(xpp.getName().equals("item")){
                                txt_dis.append("병원 이름: "+
                                        dutyName+"\n 수용 가능 여부: "+
                                        MKioskTy8+"\n\n");
                                initem=false;
                            }
                            break;
                    }
                    eventType=xpp.next();
                }
            }catch (Exception e){
                txt_dis.setText("에러ㅜ");
            }
        }
        private String downloadUrl(String api)throws IOException{
            HttpURLConnection conn=null;

            try{
                URL url=new URL(api);
                conn=(HttpURLConnection)url.openConnection();
                BufferedInputStream buf=new BufferedInputStream(conn.getInputStream());
                BufferedReader bufreader=new BufferedReader(new InputStreamReader(buf, "utf-8"));

                String line=null;
                String page="";
                while((line=bufreader.readLine())!=null){
                    page+=line;
                }
                return page;
            }finally {
                conn.disconnect();
            }

        }
    }
    //조산산모
    private class DownloadWebpageTask11 extends AsyncTask<String, Void, String>{
        protected String doInBackground(String... urls){
            try{
                String txt=(String)downloadUrl((String)urls[0]);
                return txt;
            }
            catch (IOException e){
                return "다운로드 실패";
            }
        }
        protected void onPostExecute(String result){
            boolean indutyName=false, inMKioskTy9=false, initem=false;
            String dutyName=null; String MKioskTy9=null;
            super.onPostExecute(result);
            txt_dis.setText(" ");
            try {// XML Pull Parser 객체생성
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                // XML namespace 지원여부설정. 디폴트는false
                XmlPullParser xpp= factory.newPullParser();
                // 파싱할문서설정
                xpp.setInput(new StringReader(result));
                // 현재이벤트유형반환(START_DOCUMENT, START_TAG, TEXT, END_TAG, END_DOCUMENT)
                int eventType=xpp.getEventType();
                //여기까지 파싱 시작
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    switch (eventType){
                        case XmlPullParser.START_TAG:
                            if(xpp.getName().equals("dutyName")){
                                indutyName=true;
                            }
                            if(xpp.getName().equals("MKioskTy9")){
                                inMKioskTy9=true;
                            }
                            break;
                        case XmlPullParser.TEXT:
                            if(indutyName){
                                dutyName=xpp.getText();
                                indutyName=false;
                            }
                            if(inMKioskTy9){
                                MKioskTy9=xpp.getText();
                                inMKioskTy9=false;
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            if(xpp.getName().equals("item")){
                                txt_dis.append("병원 이름: "+
                                        dutyName+"\n 수용 가능 여부: "+
                                        MKioskTy9+"\n\n");
                                initem=false;
                            }
                            break;
                    }
                    eventType=xpp.next();
                }
            }catch (Exception e){
                txt_dis.setText("에러ㅜ");
            }
        }
        private String downloadUrl(String api)throws IOException{
            HttpURLConnection conn=null;

            try{
                URL url=new URL(api);
                conn=(HttpURLConnection)url.openConnection();
                BufferedInputStream buf=new BufferedInputStream(conn.getInputStream());
                BufferedReader bufreader=new BufferedReader(new InputStreamReader(buf, "utf-8"));

                String line=null;
                String page="";
                while((line=bufreader.readLine())!=null){
                    page+=line;
                }
                return page;
            }finally {
                conn.disconnect();
            }

        }
    }
    //정신질환자
    private class DownloadWebpageTask12 extends AsyncTask<String, Void, String>{
        protected String doInBackground(String... urls){
            try{
                String txt=(String)downloadUrl((String)urls[0]);
                return txt;
            }
            catch (IOException e){
                return "다운로드 실패";
            }
        }
        protected void onPostExecute(String result){
            boolean indutyName=false, inMKioskTy10=false, initem=false;
            String dutyName=null; String MKioskTy10=null;
            super.onPostExecute(result);
            txt_dis.setText(" ");
            try {// XML Pull Parser 객체생성
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                // XML namespace 지원여부설정. 디폴트는false
                XmlPullParser xpp= factory.newPullParser();
                // 파싱할문서설정
                xpp.setInput(new StringReader(result));
                // 현재이벤트유형반환(START_DOCUMENT, START_TAG, TEXT, END_TAG, END_DOCUMENT)
                int eventType=xpp.getEventType();
                //여기까지 파싱 시작
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    switch (eventType){
                        case XmlPullParser.START_TAG:
                            if(xpp.getName().equals("dutyName")){
                                indutyName=true;
                            }
                            if(xpp.getName().equals("MKioskTy10")){
                                inMKioskTy10=true;
                            }
                            break;
                        case XmlPullParser.TEXT:
                            if(indutyName){
                                dutyName=xpp.getText();
                                indutyName=false;
                            }
                            if(inMKioskTy10){
                                MKioskTy10=xpp.getText();
                                inMKioskTy10=false;
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            if(xpp.getName().equals("item")){
                                txt_dis.append("병원 이름: "+
                                        dutyName+"\n 수용 가능 여부: "+
                                        MKioskTy10+"\n\n");
                                initem=false;
                            }
                            break;
                    }
                    eventType=xpp.next();
                }
            }catch (Exception e){
                txt_dis.setText("에러ㅜ");
            }
        }
        private String downloadUrl(String api)throws IOException{
            HttpURLConnection conn=null;

            try{
                URL url=new URL(api);
                conn=(HttpURLConnection)url.openConnection();
                BufferedInputStream buf=new BufferedInputStream(conn.getInputStream());
                BufferedReader bufreader=new BufferedReader(new InputStreamReader(buf, "utf-8"));

                String line=null;
                String page="";
                while((line=bufreader.readLine())!=null){
                    page+=line;
                }
                return page;
            }finally {
                conn.disconnect();
            }

        }
    }
    //신생아
    private class DownloadWebpageTask13 extends AsyncTask<String, Void, String>{
        protected String doInBackground(String... urls){
            try{
                String txt=(String)downloadUrl((String)urls[0]);
                return txt;
            }
            catch (IOException e){
                return "다운로드 실패";
            }
        }
        protected void onPostExecute(String result){
            boolean indutyName=false, inMKioskTy11=false, initem=false;
            String dutyName=null; String MKioskTy11=null;
            super.onPostExecute(result);
            txt_dis.setText(" ");
            try {// XML Pull Parser 객체생성
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                // XML namespace 지원여부설정. 디폴트는false
                XmlPullParser xpp= factory.newPullParser();
                // 파싱할문서설정
                xpp.setInput(new StringReader(result));
                // 현재이벤트유형반환(START_DOCUMENT, START_TAG, TEXT, END_TAG, END_DOCUMENT)
                int eventType=xpp.getEventType();
                //여기까지 파싱 시작
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    switch (eventType){
                        case XmlPullParser.START_TAG:
                            if(xpp.getName().equals("dutyName")){
                                indutyName=true;
                            }
                            if(xpp.getName().equals("MKioskTy11")){
                                inMKioskTy11=true;
                            }
                            break;
                        case XmlPullParser.TEXT:
                            if(indutyName){
                                dutyName=xpp.getText();
                                indutyName=false;
                            }
                            if(inMKioskTy11){
                                MKioskTy11=xpp.getText();
                                inMKioskTy11=false;
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            if(xpp.getName().equals("item")){
                                txt_dis.append("병원 이름: "+
                                        dutyName+"\n 수용 가능 여부: "+
                                        MKioskTy11+"\n\n");
                                initem=false;
                            }
                            break;
                    }
                    eventType=xpp.next();
                }
            }catch (Exception e){
                txt_dis.setText("에러ㅜ");
            }
        }
        private String downloadUrl(String api)throws IOException{
            HttpURLConnection conn=null;

            try{
                URL url=new URL(api);
                conn=(HttpURLConnection)url.openConnection();
                BufferedInputStream buf=new BufferedInputStream(conn.getInputStream());
                BufferedReader bufreader=new BufferedReader(new InputStreamReader(buf, "utf-8"));

                String line=null;
                String page="";
                while((line=bufreader.readLine())!=null){
                    page+=line;
                }
                return page;
            }finally {
                conn.disconnect();
            }

        }
    }
}