package com.example.hopithopit.ui.hospinfo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import java.util.ArrayList;

public class HospinfoListFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<HospInfoItem> hospInfoItems;
    Location mlocation = null;

    // 종합병원, 내과, 정형외과, 공공의료원, 치과, 안과, 소아과, 이비인후과,
    // 피부과, 한의원, 산부인과, 비뇨기과, 정신의학과, 성형외과, 요양병원, 기타
    //대구
    String dg_general_api = "http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncListInfoInqire?serviceKey=yNTJ6AknFLjMpn1Bme5Rk6Rr1Piz57T4zyDXLp7MfYFXgsOnojoMBpujFVSTkODNcAk1O3dWCtOiZIW%2F%2BKVFPg%3D%3D&Q0=%EB%8C%80%EA%B5%AC%EA%B4%91%EC%97%AD%EC%8B%9C&QZ=A&pageNo=1&numOfRows=100";
    String dg_internal_api = "http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncListInfoInqire?serviceKey=yNTJ6AknFLjMpn1Bme5Rk6Rr1Piz57T4zyDXLp7MfYFXgsOnojoMBpujFVSTkODNcAk1O3dWCtOiZIW%2F%2BKVFPg%3D%3D&Q0=%EB%8C%80%EA%B5%AC%EA%B4%91%EC%97%AD%EC%8B%9C&QD=D001&pageNo=1&numOfRows=100";
    String dg_orthopedic_api = "http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncListInfoInqire?serviceKey=yNTJ6AknFLjMpn1Bme5Rk6Rr1Piz57T4zyDXLp7MfYFXgsOnojoMBpujFVSTkODNcAk1O3dWCtOiZIW%2F%2BKVFPg%3D%3D&Q0=%EB%8C%80%EA%B5%AC%EA%B4%91%EC%97%AD%EC%8B%9C&QD=D008&pageNo=1&numOfRows=100";
    String dg_public_api = "http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncListInfoInqire?serviceKey=yNTJ6AknFLjMpn1Bme5Rk6Rr1Piz57T4zyDXLp7MfYFXgsOnojoMBpujFVSTkODNcAk1O3dWCtOiZIW%2F%2BKVFPg%3D%3D&Q0=%EB%8C%80%EA%B5%AC%EA%B4%91%EC%97%AD%EC%8B%9C&QZ=R&pageNo=1&numOfRows=100";
    String dg_dentistry_api = "http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncListInfoInqire?serviceKey=yNTJ6AknFLjMpn1Bme5Rk6Rr1Piz57T4zyDXLp7MfYFXgsOnojoMBpujFVSTkODNcAk1O3dWCtOiZIW%2F%2BKVFPg%3D%3D&Q0=%EB%8C%80%EA%B5%AC%EA%B4%91%EC%97%AD%EC%8B%9C&QD=D026&pageNo=1&numOfRows=100";
    String dg_eye_api = "http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncListInfoInqire?serviceKey=yNTJ6AknFLjMpn1Bme5Rk6Rr1Piz57T4zyDXLp7MfYFXgsOnojoMBpujFVSTkODNcAk1O3dWCtOiZIW%2F%2BKVFPg%3D%3D&Q0=%EB%8C%80%EA%B5%AC%EA%B4%91%EC%97%AD%EC%8B%9C&QD=D012&pageNo=1&numOfRows=100";
    String dg_pediatrics_api = "http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncListInfoInqire?serviceKey=yNTJ6AknFLjMpn1Bme5Rk6Rr1Piz57T4zyDXLp7MfYFXgsOnojoMBpujFVSTkODNcAk1O3dWCtOiZIW%2F%2BKVFPg%3D%3D&Q0=%EB%8C%80%EA%B5%AC%EA%B4%91%EC%97%AD%EC%8B%9C&QD=D002&pageNo=1&numOfRows=100";
    String dg_ear_api = "http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncListInfoInqire?serviceKey=yNTJ6AknFLjMpn1Bme5Rk6Rr1Piz57T4zyDXLp7MfYFXgsOnojoMBpujFVSTkODNcAk1O3dWCtOiZIW%2F%2BKVFPg%3D%3D&Q0=%EB%8C%80%EA%B5%AC%EA%B4%91%EC%97%AD%EC%8B%9C&QD=D013&pageNo=1&numOfRows=100";
    String dg_dermatology_api = "http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncListInfoInqire?serviceKey=yNTJ6AknFLjMpn1Bme5Rk6Rr1Piz57T4zyDXLp7MfYFXgsOnojoMBpujFVSTkODNcAk1O3dWCtOiZIW%2F%2BKVFPg%3D%3D&Q0=%EB%8C%80%EA%B5%AC%EA%B4%91%EC%97%AD%EC%8B%9C&QD=D005&pageNo=1&numOfRows=100";
    String dg_oriental_api = "http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncListInfoInqire?serviceKey=yNTJ6AknFLjMpn1Bme5Rk6Rr1Piz57T4zyDXLp7MfYFXgsOnojoMBpujFVSTkODNcAk1O3dWCtOiZIW%2F%2BKVFPg%3D%3D&Q0=%EB%8C%80%EA%B5%AC%EA%B4%91%EC%97%AD%EC%8B%9C&QZ=G&pageNo=1&numOfRows=100";
    String dg_obstetrics_api = "http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncListInfoInqire?serviceKey=yNTJ6AknFLjMpn1Bme5Rk6Rr1Piz57T4zyDXLp7MfYFXgsOnojoMBpujFVSTkODNcAk1O3dWCtOiZIW%2F%2BKVFPg%3D%3D&Q0=%EB%8C%80%EA%B5%AC%EA%B4%91%EC%97%AD%EC%8B%9C&QD=D011&pageNo=1&numOfRows=100";
    String dg_urology_api = "http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncListInfoInqire?serviceKey=yNTJ6AknFLjMpn1Bme5Rk6Rr1Piz57T4zyDXLp7MfYFXgsOnojoMBpujFVSTkODNcAk1O3dWCtOiZIW%2F%2BKVFPg%3D%3D&Q0=%EB%8C%80%EA%B5%AC%EA%B4%91%EC%97%AD%EC%8B%9C&QD=D014&pageNo=1&numOfRows=100";
    String dg_psychiatry_api = "http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncListInfoInqire?serviceKey=yNTJ6AknFLjMpn1Bme5Rk6Rr1Piz57T4zyDXLp7MfYFXgsOnojoMBpujFVSTkODNcAk1O3dWCtOiZIW%2F%2BKVFPg%3D%3D&Q0=%EB%8C%80%EA%B5%AC%EA%B4%91%EC%97%AD%EC%8B%9C&QD=D004&pageNo=1&numOfRows=100";
    String dg_plastic_api = "http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncListInfoInqire?serviceKey=yNTJ6AknFLjMpn1Bme5Rk6Rr1Piz57T4zyDXLp7MfYFXgsOnojoMBpujFVSTkODNcAk1O3dWCtOiZIW%2F%2BKVFPg%3D%3D&Q0=%EB%8C%80%EA%B5%AC%EA%B4%91%EC%97%AD%EC%8B%9C&QD=D010&pageNo=1&numOfRows=100";
    String dg_nursing_api = "http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncListInfoInqire?serviceKey=yNTJ6AknFLjMpn1Bme5Rk6Rr1Piz57T4zyDXLp7MfYFXgsOnojoMBpujFVSTkODNcAk1O3dWCtOiZIW%2F%2BKVFPg%3D%3D&Q0=%EB%8C%80%EA%B5%AC%EA%B4%91%EC%97%AD%EC%8B%9C&QZ=D&pageNo=1&numOfRows=100";
    String dg_more_api = "http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncListInfoInqire?serviceKey=yNTJ6AknFLjMpn1Bme5Rk6Rr1Piz57T4zyDXLp7MfYFXgsOnojoMBpujFVSTkODNcAk1O3dWCtOiZIW%2F%2BKVFPg%3D%3D&Q0=%EB%8C%80%EA%B5%AC%EA%B4%91%EC%97%AD%EC%8B%9C&QZ=B&QD=D003&pageNo=1&numOfRows=100";

    //경북
    String gb_general_api = "http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncListInfoInqire?serviceKey=yNTJ6AknFLjMpn1Bme5Rk6Rr1Piz57T4zyDXLp7MfYFXgsOnojoMBpujFVSTkODNcAk1O3dWCtOiZIW%2F%2BKVFPg%3D%3D&Q0=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&QZ=A&pageNo=1&numOfRows=100";
    String gb_internal_api = "http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncListInfoInqire?serviceKey=yNTJ6AknFLjMpn1Bme5Rk6Rr1Piz57T4zyDXLp7MfYFXgsOnojoMBpujFVSTkODNcAk1O3dWCtOiZIW%2F%2BKVFPg%3D%3D&Q0=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&QD=D001&pageNo=1&numOfRows=100";
    String gb_orthopedic_api = "http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncListInfoInqire?serviceKey=yNTJ6AknFLjMpn1Bme5Rk6Rr1Piz57T4zyDXLp7MfYFXgsOnojoMBpujFVSTkODNcAk1O3dWCtOiZIW%2F%2BKVFPg%3D%3D&Q0=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&QD=D008&pageNo=1&numOfRows=100";
    String gb_public_api = "http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncListInfoInqire?serviceKey=yNTJ6AknFLjMpn1Bme5Rk6Rr1Piz57T4zyDXLp7MfYFXgsOnojoMBpujFVSTkODNcAk1O3dWCtOiZIW%2F%2BKVFPg%3D%3D&Q0=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&QZ=R&pageNo=1&numOfRows=100";
    String gb_dentistry_api = "http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncListInfoInqire?serviceKey=yNTJ6AknFLjMpn1Bme5Rk6Rr1Piz57T4zyDXLp7MfYFXgsOnojoMBpujFVSTkODNcAk1O3dWCtOiZIW%2F%2BKVFPg%3D%3D&Q0=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&QD=D026&pageNo=1&numOfRows=100";
    String gb_eye_api = "http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncListInfoInqire?serviceKey=yNTJ6AknFLjMpn1Bme5Rk6Rr1Piz57T4zyDXLp7MfYFXgsOnojoMBpujFVSTkODNcAk1O3dWCtOiZIW%2F%2BKVFPg%3D%3D&Q0=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&QD=D012&pageNo=1&numOfRows=100";
    String gb_pediatrics_api = "http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncListInfoInqire?serviceKey=yNTJ6AknFLjMpn1Bme5Rk6Rr1Piz57T4zyDXLp7MfYFXgsOnojoMBpujFVSTkODNcAk1O3dWCtOiZIW%2F%2BKVFPg%3D%3D&Q0=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&QD=D002&pageNo=1&numOfRows=100";
    String gb_ear_api = "http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncListInfoInqire?serviceKey=yNTJ6AknFLjMpn1Bme5Rk6Rr1Piz57T4zyDXLp7MfYFXgsOnojoMBpujFVSTkODNcAk1O3dWCtOiZIW%2F%2BKVFPg%3D%3D&Q0=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&QD=D013&pageNo=1&numOfRows=100";
    String gb_dermatology_api = "http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncListInfoInqire?serviceKey=yNTJ6AknFLjMpn1Bme5Rk6Rr1Piz57T4zyDXLp7MfYFXgsOnojoMBpujFVSTkODNcAk1O3dWCtOiZIW%2F%2BKVFPg%3D%3D&Q0=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&QD=D005&pageNo=1&numOfRows=100";
    String gb_oriental_api = "http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncListInfoInqire?serviceKey=yNTJ6AknFLjMpn1Bme5Rk6Rr1Piz57T4zyDXLp7MfYFXgsOnojoMBpujFVSTkODNcAk1O3dWCtOiZIW%2F%2BKVFPg%3D%3D&Q0=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&QZ=G&pageNo=1&numOfRows=100";
    String gb_obstetrics_api = "http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncListInfoInqire?serviceKey=yNTJ6AknFLjMpn1Bme5Rk6Rr1Piz57T4zyDXLp7MfYFXgsOnojoMBpujFVSTkODNcAk1O3dWCtOiZIW%2F%2BKVFPg%3D%3D&Q0=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&QD=D011&pageNo=1&numOfRows=100";
    String gb_urology_api = "http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncListInfoInqire?serviceKey=yNTJ6AknFLjMpn1Bme5Rk6Rr1Piz57T4zyDXLp7MfYFXgsOnojoMBpujFVSTkODNcAk1O3dWCtOiZIW%2F%2BKVFPg%3D%3D&Q0=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&QD=D014&pageNo=1&numOfRows=100";
    String gb_psychiatry_api = "http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncListInfoInqire?serviceKey=yNTJ6AknFLjMpn1Bme5Rk6Rr1Piz57T4zyDXLp7MfYFXgsOnojoMBpujFVSTkODNcAk1O3dWCtOiZIW%2F%2BKVFPg%3D%3D&Q0=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&QD=D004&pageNo=1&numOfRows=100";
    String gb_plastic_api = "http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncListInfoInqire?serviceKey=yNTJ6AknFLjMpn1Bme5Rk6Rr1Piz57T4zyDXLp7MfYFXgsOnojoMBpujFVSTkODNcAk1O3dWCtOiZIW%2F%2BKVFPg%3D%3D&Q0=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&QD=D010&pageNo=1&numOfRows=100";
    String gb_nursing_api = "http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncListInfoInqire?serviceKey=yNTJ6AknFLjMpn1Bme5Rk6Rr1Piz57T4zyDXLp7MfYFXgsOnojoMBpujFVSTkODNcAk1O3dWCtOiZIW%2F%2BKVFPg%3D%3D&Q0=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&QZ=D&pageNo=1&numOfRows=100";
    String gb_more_api = "http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncListInfoInqire?serviceKey=yNTJ6AknFLjMpn1Bme5Rk6Rr1Piz57T4zyDXLp7MfYFXgsOnojoMBpujFVSTkODNcAk1O3dWCtOiZIW%2F%2BKVFPg%3D%3D&Q0=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&QZ=B&QD=D003&pageNo=1&numOfRows=100";

    String[] more_QD = {"D003", "D006", "D007", "D016", "D017", "D018", "D019", "D020", "D021", "D022", "D023", "D024", "D034"};
/*
    static final Integer APP_PERMISSION = 1;

    private void askForPermission(String permission, Integer requestCode){
        if (ContextCompat.checkSelfPermission(getActivity(), permission)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission)) {
                ActivityCompat.requestPermissions(getActivity(), new String[] {permission }, requestCode);
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[] {permission }, requestCode);
            }
        } else {
            Toast.makeText(getContext(), "" + permission + " is already granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(ActivityCompat.checkSelfPermission(getActivity(), permissions[0]) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getContext(), "Permission granted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }
*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hospinfo_list, container, false);
/*
        askForPermission(Manifest.permission.ACCESS_FINE_LOCATION, APP_PERMISSION);
        LocationManager lm = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new LocationListener(){
            @Override
            public void onLocationChanged(@NonNull Location location) {
                mlocation.set(location);
            }
        };

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
            return null;

        if (lm.getAllProviders().contains(LocationManager.NETWORK_PROVIDER))
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

        if (lm.getAllProviders().contains(LocationManager.GPS_PROVIDER))
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0, locationListener);
*/
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            hospInfoItems = new ArrayList<HospInfoItem>();
        }


        if (HospinfoFragment.hospinfo.equals("종합병원")) {
            downloadWebpageTask_pageNo(dg_general_api, 1);
            downloadWebpageTask_pageNo(gb_general_api, 1);
        } else if (HospinfoFragment.hospinfo.equals("내과")) {
            downloadWebpageTask_pageNo(dg_internal_api, 12);
            downloadWebpageTask_pageNo(gb_internal_api, 11);
        } else if (HospinfoFragment.hospinfo.equals("정형외과")) {
            downloadWebpageTask_pageNo(dg_orthopedic_api, 6);
            downloadWebpageTask_pageNo(gb_orthopedic_api, 5);
        } else if (HospinfoFragment.hospinfo.equals("공공의료원")) {
            downloadWebpageTask_pageNo(dg_public_api, 3);
            downloadWebpageTask_pageNo(gb_public_api, 6);
        } else if (HospinfoFragment.hospinfo.equals("치과")){
            downloadWebpageTask_pageNo(dg_dentistry_api, 7);
            downloadWebpageTask_pageNo(gb_dentistry_api, 5);
        } else if (HospinfoFragment.hospinfo.equals("안과")) {
            downloadWebpageTask_pageNo(dg_eye_api, 3);
            downloadWebpageTask_pageNo(gb_eye_api, 3);
        } else if (HospinfoFragment.hospinfo.equals("소아과")) {
            downloadWebpageTask_pageNo(dg_pediatrics_api, 8);
            downloadWebpageTask_pageNo(gb_pediatrics_api, 7);
        } else if (HospinfoFragment.hospinfo.equals("이비인후과")) {
            downloadWebpageTask_pageNo(dg_ear_api, 6);
            downloadWebpageTask_pageNo(gb_ear_api, 5);
        } else if (HospinfoFragment.hospinfo.equals("피부과")) {
            downloadWebpageTask_pageNo(dg_dermatology_api, 7);
            downloadWebpageTask_pageNo(gb_dermatology_api, 6);
        } else if (HospinfoFragment.hospinfo.equals("한의원")) {
            downloadWebpageTask_pageNo(dg_oriental_api, 9);
            downloadWebpageTask_pageNo(gb_oriental_api, 7);
        } else if (HospinfoFragment.hospinfo.equals("산부인과")) {
            downloadWebpageTask_pageNo(dg_obstetrics_api, 2);
            downloadWebpageTask_pageNo(gb_obstetrics_api, 2);
        } else if (HospinfoFragment.hospinfo.equals("비뇨기과")) {
            downloadWebpageTask_pageNo(dg_urology_api, 4);
            downloadWebpageTask_pageNo(gb_urology_api, 4);
        } else if (HospinfoFragment.hospinfo.equals("정신의학과")) {
            downloadWebpageTask_pageNo(dg_psychiatry_api, 2);
            downloadWebpageTask_pageNo(gb_psychiatry_api, 2);
        } else if (HospinfoFragment.hospinfo.equals("성형외과")) {
            downloadWebpageTask_pageNo(dg_plastic_api, 3);
            downloadWebpageTask_pageNo(gb_plastic_api, 1);
        } else if (HospinfoFragment.hospinfo.equals("요양병원")) {
            downloadWebpageTask_pageNo(dg_nursing_api, 1);
            downloadWebpageTask_pageNo(gb_nursing_api, 2);
        } else if (HospinfoFragment.hospinfo.equals("기타")) {
            for (int i=0; i<more_QD.length; i++) {
                downloadWebpageTask_pageNo(dg_more_api.replace("QD=D003", "QD="+more_QD[i]), 1);
            }
            for (int i=0; i<more_QD.length; i++) {
                downloadWebpageTask_pageNo(gb_more_api.replace("QD=D003", "QD="+more_QD[i]), 1);
            }
        }

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
            boolean[] indutyTimec = new boolean[8];
            boolean[] indutyTimes = new boolean[8];
            String dutyAddr = ""; String dutyName = ""; String dutyTel1 = ""; String dutyTel3 = "";
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
                        }
                    } else if (eventType == XmlPullParser.END_TAG) {
                        if (xpp.getName().equals("item")) {
                            String dutyTime = "";
                            boolean weekday = true;
                            for (int i = 0; i < 4; i++) {
                                if (dutyTimes[i] != dutyTimes[i+1] || dutyTimec[i] != dutyTimec[i+1]){
                                    weekday = false;
                                    break;
                                }
                            }
                            if (weekday){
                                dutyTime = "평일 " + dutyTimes[0].substring(0, 2)+":"+dutyTimes[0].substring(2, 4)
                                        + " - " + dutyTimec[0].substring(0, 2)+":"+dutyTimec[0].substring(2, 4) + "\n";
                            } else {
                                for (int i = 0; i < 5; i++) {
                                    if (dutyTimes[i].equals("휴무")) {
                                        dutyTime += dutyDay[i] + " " + dutyTimes[i] + "\n";
                                    } else {
                                        dutyTime += dutyDay[i] + " " + dutyTimes[i].substring(0, 2)+":"+dutyTimes[i].substring(2, 4)
                                                + " - " + dutyTimec[i].substring(0, 2)+":"+dutyTimec[i].substring(2, 4) + "\n";
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
                                    dutyTime += dutyDay[i] + " " + dutyTimes[i].substring(0, 2)+":"+dutyTimes[i].substring(2, 4)
                                            + " - " + dutyTimec[i].substring(0, 2)+":"+dutyTimec[i].substring(2, 4);
                                }
                                if (i<7){
                                    dutyTime += "\n";
                                }
                                dutyTimes[i] = "휴무";
                            }

                            hospInfoItems.add(new HospInfoItem(dutyName, dutyAddr, "[대표전화] "+dutyTel1, "[응급실전화] "+dutyTel3, dutyTime));

                        }
                    }
                    eventType = xpp.next();
                }
            } catch (Exception e) {

            }

            recyclerView.setAdapter(new HospInfoAdapter(hospInfoItems));

        }
    }

}