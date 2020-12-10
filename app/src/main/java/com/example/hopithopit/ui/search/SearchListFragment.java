package com.example.hopithopit.ui.search;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.ArrayList;

public class SearchListFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<SearchItem> SearchItems;
    Location mlocation = null;

    String dg_api="http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncListInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&Q0=%EB%8C%80%EA%B5%AC%EA%B4%91%EC%97%AD%EC%8B%9C&QN=%EB%8F%99%EC%82%B0&pageNo=1&numOfRows=10";
    String gb_api="http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncListInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&Q0=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&QN=%EB%8F%99%EC%82%B0&pageNo=1&numOfRows=10";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            SearchItems = new ArrayList<SearchItem>();
        }

        mlocation = SearchFragment.mlocation;


        downloadWebpageTask_pageNo(dg_api.replace("QN=%EB%8F%99%EC%82%B0", "QN="+SearchFragment.search), 1);
        downloadWebpageTask_pageNo(gb_api.replace("QN=%EB%8F%99%EC%82%B0", "QN="+SearchFragment.search), 1);

        return view;
    }

    private void downloadWebpageTask_pageNo(String api, int pageNoSize){
        for (int pageNo = 1; pageNo <= pageNoSize; pageNo++) {
            DownloadWebpageTask downloadWebpageTask = new DownloadWebpageTask();
            downloadWebpageTask.execute(api.replace("pageNo=1", "pageNo=" + pageNo));
        }
    }

    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
                String txt = (String)downloadUrl((String)urls[0]);
                return txt;
            } catch (IOException e) {
                Toast.makeText(getContext(), "다운로드 실패", Toast.LENGTH_SHORT);
                return "다운로드 실패";
            }
        }

        private String downloadUrl(String api) throws IOException {
            HttpURLConnection conn = null;

            try {
                URL url = new URL(api);
                conn = (HttpURLConnection) url.openConnection();
                BufferedInputStream buf = new BufferedInputStream(conn.getInputStream());
                BufferedReader bufreader = new BufferedReader(new InputStreamReader(buf, "utf-8"));

                String line = null;
                String page = "";
                while ((line = bufreader.readLine()) != null) {
                    page += line;
                }

                return page;
            } finally {
                conn.disconnect();
            }

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            String[] dutyDay = {"월요일", "화요일", "수요일", "목요일", "금요일", "토요일", "일요일", "공휴일"};
            boolean indutyAddr = false; boolean indutyName = false; boolean indutyTel1 = false; boolean indutyTel3 = false;
            boolean inwgs84Lon = false; boolean inwgs84Lat = false;
            boolean[] indutyTimec = new boolean[8];
            boolean[] indutyTimes = new boolean[8];
            String dutyAddr = ""; String dutyName = ""; String dutyTel1 = ""; String dutyTel3 = "";
            double wgs84Lon = 0.0d; double wgs84Lat = 0.0d;
            String[] dutyTimec = new String[8];
            String[] dutyTimes = new String[8];
            for (int i=0; i<8; i++){
                indutyTimec[i] = false;
                indutyTimes[i] = false;
                dutyTimec[i] = "";
                dutyTimes[i] = "휴무";
            }

            try {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();

                xpp.setInput(new StringReader(result));

                int eventType = xpp.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_DOCUMENT) {
                        ;
                    } else if (eventType == XmlPullParser.START_TAG) {
                        if (xpp.getName().equals("dutyAddr")){
                            indutyAddr = true;
                        } else if (xpp.getName().equals("dutyName")){
                            indutyName = true;
                        } else if (xpp.getName().equals("dutyTel1")){
                            indutyTel1 = true;
                        } else if (xpp.getName().equals("dutyTel3")){
                            indutyTel3 = true;
                        } else if (xpp.getName().equals("dutyTime1c")){
                            indutyTimec[0] = true;
                        } else if (xpp.getName().equals("dutyTime2c")){
                            indutyTimec[1] = true;
                        } else if (xpp.getName().equals("dutyTime3c")){
                            indutyTimec[2] = true;
                        } else if (xpp.getName().equals("dutyTime4c")){
                            indutyTimec[3] = true;
                        } else if (xpp.getName().equals("dutyTime5c")){
                            indutyTimec[4] = true;
                        } else if (xpp.getName().equals("dutyTime6c")){
                            indutyTimec[5] = true;
                        } else if (xpp.getName().equals("dutyTime7c")){
                            indutyTimec[6] = true;
                        } else if (xpp.getName().equals("dutyTime8c")){
                            indutyTimec[7] = true;
                        } else if (xpp.getName().equals("dutyTime1s")){
                            indutyTimes[0] = true;
                        } else if (xpp.getName().equals("dutyTime2s")){
                            indutyTimes[1] = true;
                        } else if (xpp.getName().equals("dutyTime3s")){
                            indutyTimes[2] = true;
                        } else if (xpp.getName().equals("dutyTime4s")){
                            indutyTimes[3] = true;
                        } else if (xpp.getName().equals("dutyTime5s")){
                            indutyTimes[4] = true;
                        } else if (xpp.getName().equals("dutyTime6s")){
                            indutyTimes[5] = true;
                        } else if (xpp.getName().equals("dutyTime7s")){
                            indutyTimes[6] = true;
                        } else if (xpp.getName().equals("dutyTime8s")){
                            indutyTimes[7] = true;
                        } else if (xpp.getName().equals("wgs84Lon")){
                            inwgs84Lon = true;
                        } else if (xpp.getName().equals("wgs84Lat")){
                            inwgs84Lat = true;
                        }
                    } else if (eventType == XmlPullParser.TEXT) {
                        if (indutyAddr){
                            dutyAddr = xpp.getText();
                            indutyAddr = false;
                        } else if (indutyName){
                            dutyName = xpp.getText();
                            indutyName = false;
                        } else if (indutyTel1){
                            dutyTel1 = xpp.getText();
                            indutyTel1 = false;
                        } else if (indutyTel3){
                            dutyTel3 = xpp.getText();
                            indutyTel3 = false;
                        } else if (indutyTimec[0]){
                            dutyTimec[0] = xpp.getText();
                            indutyTimec[0] = false;
                        } else if (indutyTimec[1]){
                            dutyTimec[1] = xpp.getText();
                            indutyTimec[1] = false;
                        } else if (indutyTimec[2]){
                            dutyTimec[2] = xpp.getText();
                            indutyTimec[2] = false;
                        } else if (indutyTimec[3]){
                            dutyTimec[3] = xpp.getText();
                            indutyTimec[3] = false;
                        } else if (indutyTimec[4]){
                            dutyTimec[4] = xpp.getText();
                            indutyTimec[4] = false;
                        } else if (indutyTimec[5]){
                            dutyTimec[5] = xpp.getText();
                            indutyTimec[5] = false;
                        } else if (indutyTimec[6]){
                            dutyTimec[6] = xpp.getText();
                            indutyTimec[6] = false;
                        } else if (indutyTimec[7]){
                            dutyTimec[7] = xpp.getText();
                            indutyTimec[7] = false;
                        } else if (indutyTimes[0]){
                            dutyTimes[0] = xpp.getText();
                            indutyTimes[0] = false;
                        } else if (indutyTimes[1]){
                            dutyTimes[1] = xpp.getText();
                            indutyTimes[1] = false;
                        } else if (indutyTimes[2]){
                            dutyTimes[2] = xpp.getText();
                            indutyTimes[2] = false;
                        } else if (indutyTimes[3]){
                            dutyTimes[3] = xpp.getText();
                            indutyTimes[3] = false;
                        } else if (indutyTimes[4]){
                            dutyTimes[4] = xpp.getText();
                            indutyTimes[4] = false;
                        } else if (indutyTimes[5]){
                            dutyTimes[5] = xpp.getText();
                            indutyTimes[5] = false;
                        } else if (indutyTimes[6]){
                            dutyTimes[6] = xpp.getText();
                            indutyTimes[6] = false;
                        } else if (indutyTimes[7]){
                            dutyTimes[7] = xpp.getText();
                            indutyTimes[7] = false;
                        } else if (inwgs84Lon){
                            wgs84Lon = Double.parseDouble(xpp.getText());
                            inwgs84Lon = false;
                        } else if (inwgs84Lat){
                            wgs84Lat = Double.parseDouble(xpp.getText());
                            inwgs84Lat = false;
                        }
                    } else if (eventType == XmlPullParser.END_TAG) {
                        if (xpp.getName().equals("item")) {
                            Location hlocation = new Location("");
                            hlocation.setLatitude(wgs84Lat);
                            hlocation.setLongitude(wgs84Lon);
                            float resultd = mlocation.distanceTo(hlocation);

                            if (resultd < 4000) {
                                Log.d("distance", ""+resultd);
                                String dutyTime = "";
                                boolean weekday = true;
                                for (int i = 0; i < 4; i++) {
                                    if (dutyTimes[i] != dutyTimes[i + 1] || dutyTimec[i] != dutyTimec[i + 1]) {
                                        weekday = false;
                                        break;
                                    }
                                }
                                if (weekday) {
                                    dutyTime = "평일 " + dutyTimes[0].substring(0, 2) + ":" + dutyTimes[0].substring(2, 4)
                                            + " - " + dutyTimec[0].substring(0, 2) + ":" + dutyTimec[0].substring(2, 4) + "\n";
                                } else {
                                    for (int i = 0; i < 5; i++) {
                                        if (dutyTimes[i].equals("휴무")) {
                                            dutyTime += dutyDay[i] + " " + dutyTimes[i] + "\n";
                                        } else {
                                            dutyTime += dutyDay[i] + " " + dutyTimes[i].substring(0, 2) + ":" + dutyTimes[i].substring(2, 4)
                                                    + " - " + dutyTimec[i].substring(0, 2) + ":" + dutyTimec[i].substring(2, 4) + "\n";
                                        }
                                    }
                                }
                                for (int i = 0; i < 5; i++) {
                                    dutyTimes[i] = "휴무";
                                }
                                for (int i = 5; i < 8; i++) {
                                    if (dutyTimes[i].equals("휴무")) {
                                        dutyTime += dutyDay[i] + " " + dutyTimes[i];
                                    } else {
                                        dutyTime += dutyDay[i] + " " + dutyTimes[i].substring(0, 2) + ":" + dutyTimes[i].substring(2, 4)
                                                + " - " + dutyTimec[i].substring(0, 2) + ":" + dutyTimec[i].substring(2, 4);
                                    }
                                    if (i < 7) {
                                        dutyTime += "\n";
                                    }
                                    dutyTimes[i] = "휴무";
                                }

                                SearchItems.add(new SearchItem(dutyName, dutyAddr, "[대표전화] " + dutyTel1, "[응급실전화] " + dutyTel3, dutyTime, resultd));
                            }
                        }
                    }
                    eventType = xpp.next();
                }
            } catch (Exception e) {

            }

            recyclerView.setAdapter(new SearchAdapter(SearchItems));

        }
    }

}