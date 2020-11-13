package com.example.hopithopit.ui.acceptable;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;

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
    CheckBox er_check;
    CheckBox ward_check;

    String room_dal_api = "http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EB%8C%80%EA%B5%AC%EA%B4%91%EC%97%AD%EC%8B%9C&STAGE2=%EB%8B%AC%EC%84%9C%EA%B5%AC&pageNo=1&numOfRows=10";
    String room_buk_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EB%8C%80%EA%B5%AC%EA%B4%91%EC%97%AD%EC%8B%9C&STAGE2=%EB%B6%81%EA%B5%AC&pageNo=1&numOfRows=10";
    String room_dong_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EB%8C%80%EA%B5%AC%EA%B4%91%EC%97%AD%EC%8B%9C&STAGE2=%EB%8F%99%EA%B5%AC&pageNo=1&numOfRows=10";
    String room_nam_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EB%8C%80%EA%B5%AC%EA%B4%91%EC%97%AD%EC%8B%9C&STAGE2=%EB%82%A8%EA%B5%AC&pageNo=1&numOfRows=10";
    String room_su_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EB%8C%80%EA%B5%AC%EA%B4%91%EC%97%AD%EC%8B%9C&STAGE2=%EC%88%98%EC%84%B1%EA%B5%AC&pageNo=1&numOfRows=10";
    String room_seo_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EB%8C%80%EA%B5%AC%EA%B4%91%EC%97%AD%EC%8B%9C&STAGE2=%EC%84%9C%EA%B5%AC&pageNo=1&numOfRows=10";
    String room_jung_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EB%8C%80%EA%B5%AC%EA%B4%91%EC%97%AD%EC%8B%9C&STAGE2=%EC%A4%91%EA%B5%AC&pageNo=1&numOfRows=10";
    String room_gun_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EB%8C%80%EA%B5%AC%EA%B4%91%EC%97%AD%EC%8B%9C&STAGE2=%EB%8B%AC%EC%84%9C%EA%B5%B0&pageNo=1&numOfRows=10";

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
                            //txt_room.setText(select_dg);
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
//응급실 버튼 눌렀을 때

        er_check=(CheckBox)root.findViewById(R.id.er);
        er_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                String dal_api, buk_api, dong_api, nam_api, su_api, seo_api, jung_api,gun_api;
                if(b) {
                    if (select_dg.equals("달서구")) {
                        DownloadWebpageTask task = new DownloadWebpageTask();
                        task.execute(room_dal_api);
                    }
                    else if(select_dg.equals("북구")){
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
                }else{txt_room.setText("");}
            }
        });

//입원실 버튼 눌렀을 때
        ward_check=(CheckBox)root.findViewById(R.id.ward);
        ward_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                String dal_api, buk_api, dong_api, nam_api, su_api, seo_api, jung_api,gun_api;
                if(b) {
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
                }else{txt_room.setText("");}
            }
        });
        return root;
    }
//응급실
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
            txt_room.setText("");
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
            txt_room.setText("");
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

}