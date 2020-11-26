package com.example.hopithopit.ui.acceptable;


import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

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
    TextView txt_er, txt_ward;
    TextView txt_ce_he, txt_ce_in, txt_mi, txt_ab_in, txt_massive_bu, txt_connect_li;
    TextView txt_em_en, txt_em_di, txt_pre_mom, txt_mental_pat, txt_baby;
    TextView hop_name;
    CheckBox er_check, ward_check;
    CheckBox ce_he_check, ce_in_check, mi_check, ab_in_check, massive_bu_check, connect_li_check, em_en_check, em_di_check, pre_mom_check, mental_pat_check, baby_check;
    Button room_button, dis_button;

    //대구 응급실 api
    String room_dal_api = "http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EB%8C%80%EA%B5%AC%EA%B4%91%EC%97%AD%EC%8B%9C&STAGE2=%EB%8B%AC%EC%84%9C%EA%B5%AC&pageNo=1&numOfRows=10";
    String room_buk_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EB%8C%80%EA%B5%AC%EA%B4%91%EC%97%AD%EC%8B%9C&STAGE2=%EB%B6%81%EA%B5%AC&pageNo=1&numOfRows=10";
    String room_dong_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EB%8C%80%EA%B5%AC%EA%B4%91%EC%97%AD%EC%8B%9C&STAGE2=%EB%8F%99%EA%B5%AC&pageNo=1&numOfRows=10";
    String room_nam_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EB%8C%80%EA%B5%AC%EA%B4%91%EC%97%AD%EC%8B%9C&STAGE2=%EB%82%A8%EA%B5%AC&pageNo=1&numOfRows=10";
    String room_su_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EB%8C%80%EA%B5%AC%EA%B4%91%EC%97%AD%EC%8B%9C&STAGE2=%EC%88%98%EC%84%B1%EA%B5%AC&pageNo=1&numOfRows=10";
    String room_seo_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EB%8C%80%EA%B5%AC%EA%B4%91%EC%97%AD%EC%8B%9C&STAGE2=%EC%84%9C%EA%B5%AC&pageNo=1&numOfRows=10";
    String room_jung_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EB%8C%80%EA%B5%AC%EA%B4%91%EC%97%AD%EC%8B%9C&STAGE2=%EC%A4%91%EA%B5%AC&pageNo=1&numOfRows=10";
    String room_gun_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EB%8C%80%EA%B5%AC%EA%B4%91%EC%97%AD%EC%8B%9C&STAGE2=%EB%8B%AC%EC%84%9C%EA%B5%B0&pageNo=1&numOfRows=10";

    //대구 중증 api
    String dis_dal_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getSrsillDissAceptncPosblInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EB%8C%80%EA%B5%AC%EA%B4%91%EC%97%AD%EC%8B%9C&STAGE2=%EB%8B%AC%EC%84%9C%EA%B5%AC&SM_TYPE=1&pageNo=1&numOfRows=10";
    String dis_buk_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getSrsillDissAceptncPosblInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EB%8C%80%EA%B5%AC%EA%B4%91%EC%97%AD%EC%8B%9C&STAGE2=%EB%B6%81%EA%B5%AC&SM_TYPE=1&pageNo=1&numOfRows=10";
    String dis_dong_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getSrsillDissAceptncPosblInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EB%8C%80%EA%B5%AC%EA%B4%91%EC%97%AD%EC%8B%9C&STAGE2=%EB%8F%99%EA%B5%AC&SM_TYPE=1&pageNo=1&numOfRows=10";
    String dis_nam_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getSrsillDissAceptncPosblInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EB%8C%80%EA%B5%AC%EA%B4%91%EC%97%AD%EC%8B%9C&STAGE2=%EB%82%A8%EA%B5%AC&SM_TYPE=1&pageNo=1&numOfRows=10";
    String dis_su_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getSrsillDissAceptncPosblInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EB%8C%80%EA%B5%AC%EA%B4%91%EC%97%AD%EC%8B%9C&STAGE2=%EC%88%98%EC%84%B1%EA%B5%AC&SM_TYPE=1&pageNo=1&numOfRows=10";
    String dis_seo_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getSrsillDissAceptncPosblInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EB%8C%80%EA%B5%AC%EA%B4%91%EC%97%AD%EC%8B%9C&STAGE2=%EC%84%9C%EA%B5%AC&SM_TYPE=1&pageNo=1&numOfRows=10";
    String dis_jung_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getSrsillDissAceptncPosblInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EB%8C%80%EA%B5%AC%EA%B4%91%EC%97%AD%EC%8B%9C&STAGE2=%EC%A4%91%EA%B5%AC&SM_TYPE=1&pageNo=1&numOfRows=10";
    String dis_gun_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getSrsillDissAceptncPosblInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EB%8C%80%EA%B5%AC%EA%B4%91%EC%97%AD%EC%8B%9C&STAGE2=%EB%8B%AC%EC%84%B1%EA%B5%B0&SM_TYPE=1&pageNo=1&numOfRows=10";

    //경북 응급실 api
    String room_gs_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&STAGE2=%EA%B2%BD%EC%82%B0%EC%8B%9C&pageNo=1&numOfRows=10";
    String room_gj_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&STAGE2=%EA%B2%BD%EC%A3%BC%EC%8B%9C&pageNo=1&numOfRows=10";
    String room_gr_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&STAGE2=%EA%B3%A0%EB%A0%B9%EA%B5%B0&pageNo=1&numOfRows=10";
    String room_gm_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&STAGE2=%EA%B5%AC%EB%AF%B8%EC%8B%9C&pageNo=1&numOfRows=10";
    String room_gw_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&STAGE2=%EA%B5%B0%EC%9C%84%EA%B5%B0&pageNo=1&numOfRows=10";
    String room_gc_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&STAGE2=%EA%B9%80%EC%B2%9C%EC%8B%9C&pageNo=1&numOfRows=10";
    String room_mg_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&STAGE2=%EB%AC%B8%EA%B2%BD%EC%8B%9C&pageNo=1&numOfRows=10";
    String room_bh_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&STAGE2=%EB%B4%89%ED%99%94%EA%B5%B0&pageNo=1&numOfRows=10";
    String room_sangj_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&STAGE2=%EC%83%81%EC%A3%BC%EC%8B%9C&pageNo=1&numOfRows=10";
    String room_seongj_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&STAGE2=%EC%84%B1%EC%A3%BC%EA%B5%B0&pageNo=1&numOfRows=10";
    String room_ad_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&STAGE2=%EC%95%88%EB%8F%99%EC%8B%9C&pageNo=1&numOfRows=10";
    String room_yd_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&STAGE2=%EC%98%81%EB%8D%95%EA%B5%B0&pageNo=1&numOfRows=10";
    String room_yy_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&STAGE2=%EC%98%81%EC%96%91%EA%B5%B0&pageNo=1&numOfRows=10";
    String room_yj_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&STAGE2=%EC%98%81%EC%A3%BC%EC%8B%9C&pageNo=1&numOfRows=10";
    String room_yeongc_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&STAGE2=%EC%98%81%EC%B2%9C%EC%8B%9C&pageNo=1&numOfRows=10";
    String room_yec_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&STAGE2=%EC%98%88%EC%B2%9C%EA%B5%B0&pageNo=1&numOfRows=10";
    String room_uldo_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&STAGE2=%EC%9A%B8%EB%A6%89%EA%B5%B0&pageNo=1&numOfRows=10";
    String room_ulgin_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&STAGE2=%EC%9A%B8%EC%A7%84%EA%B5%B0&pageNo=1&numOfRows=10";
    String room_us_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&STAGE2=%EC%9D%98%EC%84%B1%EA%B5%B0&pageNo=1&numOfRows=10";
    String room_cd_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&STAGE2=%EC%B2%AD%EB%8F%84%EA%B5%B0&pageNo=1&numOfRows=10";
    String room_cs_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&STAGE2=%EC%B2%AD%EC%86%A1%EA%B5%B0&pageNo=1&numOfRows=10";
    String room_cg_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&STAGE2=%EC%B9%A0%EA%B3%A1%EA%B5%B0&pageNo=1&numOfRows=10";
    String room_ph_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&STAGE2=%ED%8F%AC%ED%95%AD%EC%8B%9C&pageNo=1&numOfRows=10";

    //경북 중증 api
    String dis_gs_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getSrsillDissAceptncPosblInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&STAGE2=%EA%B2%BD%EC%82%B0%EC%8B%9C&SM_TYPE=1&pageNo=1&numOfRows=10";
    String dis_gj_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getSrsillDissAceptncPosblInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&STAGE2=%EA%B2%BD%EC%A3%BC%EC%8B%9C&SM_TYPE=1&pageNo=1&numOfRows=10";
    String dis_gr_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getSrsillDissAceptncPosblInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&STAGE2=%EA%B3%A0%EB%A0%B9%EA%B5%B0&SM_TYPE=1&pageNo=1&numOfRows=10";
    String dis_gm_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getSrsillDissAceptncPosblInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&STAGE2=%EA%B5%AC%EB%AF%B8%EC%8B%9C&SM_TYPE=1&pageNo=1&numOfRows=10";
    String dis_gw_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getSrsillDissAceptncPosblInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&STAGE2=%EA%B5%B0%EC%9C%84%EA%B5%B0&SM_TYPE=1&pageNo=1&numOfRows=10";
    String dis_gc_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getSrsillDissAceptncPosblInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&STAGE2=%EA%B9%80%EC%B2%9C%EC%8B%9C&SM_TYPE=1&pageNo=1&numOfRows=10";
    String dis_mg_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getSrsillDissAceptncPosblInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&STAGE2=%EB%AC%B8%EA%B2%BD%EC%8B%9C&SM_TYPE=1&pageNo=1&numOfRows=10";
    String dis_bh_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getSrsillDissAceptncPosblInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&STAGE2=%EB%B4%89%ED%99%94%EA%B5%B0&SM_TYPE=1&pageNo=1&numOfRows=10";
    String dis_sangj_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getSrsillDissAceptncPosblInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&STAGE2=%EC%83%81%EC%A3%BC%EC%8B%9C&SM_TYPE=1&pageNo=1&numOfRows=10";
    String dis_seongj_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getSrsillDissAceptncPosblInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&STAGE2=%EC%84%B1%EC%A3%BC%EA%B5%B0&SM_TYPE=1&pageNo=1&numOfRows=10";
    String dis_ad_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getSrsillDissAceptncPosblInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&STAGE2=%EC%95%88%EB%8F%99%EC%8B%9C&SM_TYPE=1&pageNo=1&numOfRows=10";
    String dis_yd_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getSrsillDissAceptncPosblInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&STAGE2=%EC%98%81%EB%8D%95%EA%B5%B0&SM_TYPE=1&pageNo=1&numOfRows=10";
    String dis_yy_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getSrsillDissAceptncPosblInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&STAGE2=%EC%98%81%EC%96%91%EA%B5%B0&SM_TYPE=1&pageNo=1&numOfRows=10";
    String dis_yj_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getSrsillDissAceptncPosblInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&STAGE2=%EC%98%81%EC%A3%BC%EC%8B%9C&SM_TYPE=1&pageNo=1&numOfRows=10";
    String dis_yeongc_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getSrsillDissAceptncPosblInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&STAGE2=%EC%98%81%EC%B2%9C%EC%8B%9C&SM_TYPE=1&pageNo=1&numOfRows=10";
    String dis_yec_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getSrsillDissAceptncPosblInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&STAGE2=%EC%98%88%EC%B2%9C%EA%B5%B0&SM_TYPE=1&pageNo=1&numOfRows=10";
    String dis_uldo_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getSrsillDissAceptncPosblInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&STAGE2=%EC%9A%B8%EB%A6%89%EA%B5%B0&SM_TYPE=1&pageNo=1&numOfRows=10";
    String dis_ulgin_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getSrsillDissAceptncPosblInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&STAGE2=%EC%9A%B8%EC%A7%84%EA%B5%B0&SM_TYPE=1&pageNo=1&numOfRows=10";
    String dis_us_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getSrsillDissAceptncPosblInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&STAGE2=%EC%9D%98%EC%84%B1%EA%B5%B0&SM_TYPE=1&pageNo=1&numOfRows=10";
    String dis_cd_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getSrsillDissAceptncPosblInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&STAGE2=%EC%B2%AD%EB%8F%84%EA%B5%B0&SM_TYPE=1&pageNo=1&numOfRows=10";
    String dis_cs_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getSrsillDissAceptncPosblInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&STAGE2=%EC%B2%AD%EC%86%A1%EA%B5%B0&SM_TYPE=1&pageNo=1&numOfRows=10";
    String dis_cg_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getSrsillDissAceptncPosblInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&STAGE2=%EC%B9%A0%EA%B3%A1%EA%B5%B0&SM_TYPE=1&pageNo=1&numOfRows=10";
    String dis_ph_api="http://apis.data.go.kr/B552657/ErmctInfoInqireService/getSrsillDissAceptncPosblInfoInqire?serviceKey=%2BlL1h1z6TorEtxdAYIedcpcsRa4Y66RE3JzmNnSfOuEoAdgj0%2BH5Lk%2BnhRT9itOfsAGmCnV%2FBhEzvqgHcW5zaA%3D%3D&STAGE1=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&STAGE2=%ED%8F%AC%ED%95%AD%EC%8B%9C&SM_TYPE=1&pageNo=1&numOfRows=10";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        acceptableViewModel =
                ViewModelProviders.of(this).get(AcceptableViewModel.class);
        View root = inflater.inflate(R.layout.fragment_acceptable, container, false);
        txt_er=(TextView)root.findViewById(R.id.txt_er);
        txt_ward=(TextView)root.findViewById(R.id.txt_ward);
        txt_ce_he=(TextView)root.findViewById(R.id.txt_ce_he);
        txt_ce_in=(TextView)root.findViewById(R.id.txt_ce_in);
        txt_mi=(TextView)root.findViewById(R.id.txt_mi);
        txt_ab_in=(TextView)root.findViewById(R.id.txt_ab_in);
        txt_massive_bu=(TextView)root.findViewById(R.id.txt_massive_bu);
        txt_connect_li=(TextView)root.findViewById(R.id.txt_connect_li);
        txt_em_en=(TextView)root.findViewById(R.id.txt_em_en);
        txt_em_di=(TextView)root.findViewById(R.id.txt_em_di);
        txt_pre_mom=(TextView)root.findViewById(R.id.txt_pre_mom);
        txt_mental_pat=(TextView)root.findViewById(R.id.txt_mental_pat);
        txt_baby=(TextView)root.findViewById(R.id.txt_baby);
        hop_name=(TextView)root.findViewById(R.id.hop_name);
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
                else if (adapter1.getItem(i).equals("경북")){
                    final ArrayAdapter<String> adapter2=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, gb);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(adapter2);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            select_gb= (String) adapterView.getItemAtPosition(i);
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
                //응급실이나 입원실 체크박스 선택
                if(er_check.isChecked() || ward_check.isChecked()){
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
                    else if(select_gb.equals("경산시")){
                        DownloadWebpageTask task=new DownloadWebpageTask();
                        task.execute(room_gs_api);
                    }
                    else if(select_gb.equals("경주시")){
                        DownloadWebpageTask task=new DownloadWebpageTask();
                        task.execute(room_gj_api);
                    }
                    else if(select_gb.equals("고령군")){
                        DownloadWebpageTask task=new DownloadWebpageTask();
                        task.execute(room_gr_api);
                    }
                    else if(select_gb.equals("구미시")){
                        DownloadWebpageTask task=new DownloadWebpageTask();
                        task.execute(room_gm_api);
                    }
                    else if(select_gb.equals("군위군")){
                        DownloadWebpageTask task=new DownloadWebpageTask();
                        task.execute(room_gw_api);
                    }
                    else if(select_gb.equals("김천시")){
                        DownloadWebpageTask task=new DownloadWebpageTask();
                        task.execute(room_gc_api);
                    }
                    else if(select_gb.equals("문경시")){
                        DownloadWebpageTask task=new DownloadWebpageTask();
                        task.execute(room_mg_api);
                    }
                    else if(select_gb.equals("봉화군")){
                        DownloadWebpageTask task=new DownloadWebpageTask();
                        task.execute(room_bh_api);
                    }
                    else if(select_gb.equals("상주시")){
                        DownloadWebpageTask task=new DownloadWebpageTask();
                        task.execute(room_sangj_api);
                    }else if(select_gb.equals("성주군")){
                        DownloadWebpageTask task=new DownloadWebpageTask();
                        task.execute(room_seongj_api);
                    }else if(select_gb.equals("안동시")){
                        DownloadWebpageTask task=new DownloadWebpageTask();
                        task.execute(room_ad_api);
                    }else if(select_gb.equals("영덕군")){
                        DownloadWebpageTask task=new DownloadWebpageTask();
                        task.execute(room_yd_api);
                    }else if(select_gb.equals("영양군")){
                        DownloadWebpageTask task=new DownloadWebpageTask();
                        task.execute(room_yy_api);
                    }else if(select_gb.equals("영주시")){
                        DownloadWebpageTask task=new DownloadWebpageTask();
                        task.execute(room_yj_api);
                    }else if(select_gb.equals("영천시")){
                        DownloadWebpageTask task=new DownloadWebpageTask();
                        task.execute(room_yeongc_api);
                    }else if(select_gb.equals("예천군")){
                        DownloadWebpageTask task=new DownloadWebpageTask();
                        task.execute(room_yec_api);
                    }else if(select_gb.equals("울릉군")){
                        DownloadWebpageTask task=new DownloadWebpageTask();
                        task.execute(room_uldo_api);
                    }else if(select_gb.equals("울진군")){
                        DownloadWebpageTask task=new DownloadWebpageTask();
                        task.execute(room_ulgin_api);
                    }else if(select_gb.equals("의성군")){
                        DownloadWebpageTask task=new DownloadWebpageTask();
                        task.execute(room_us_api);
                    }else if(select_gb.equals("청도군")){
                        DownloadWebpageTask task=new DownloadWebpageTask();
                        task.execute(room_cd_api);
                    }else if(select_gb.equals("청송군")){
                        DownloadWebpageTask task=new DownloadWebpageTask();
                        task.execute(room_cs_api);
                    }else if(select_gb.equals("칠곡군")){
                        DownloadWebpageTask task=new DownloadWebpageTask();
                        task.execute(room_cg_api);
                    }
                    else if(select_gb.equals("포항시")){
                        DownloadWebpageTask task=new DownloadWebpageTask();
                        task.execute(room_ph_api);
                    }
                    select_dg=" ";
                    select_gb=" ";
                }
                else if(!er_check.isChecked()){
                    txt_er.setText(" ");
                }
                else if(!ward_check.isChecked()){
                    txt_ward.setText(" ");
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
                //중증질환 체크박스 선택
                if( ce_he_check.isChecked()||  ce_in_check.isChecked() || mi_check.isChecked()|| ab_in_check.isChecked()|| massive_bu_check.isChecked()|| connect_li_check.isChecked()|| em_en_check.isChecked()|| em_di_check.isChecked()|| pre_mom_check.isChecked()|| mental_pat_check.isChecked()|| baby_check.isChecked()){
                    if (select_dg.equals("달서구")) {
                        DownloadWebpageTask2 task = new DownloadWebpageTask2();
                        task.execute(dis_dal_api);
                    }
                    else if(select_dg.equals("북구")){
                        DownloadWebpageTask2 task=new DownloadWebpageTask2();
                        task.execute(dis_buk_api);
                    }
                    else if(select_dg.equals("동구")){
                        DownloadWebpageTask2 task=new DownloadWebpageTask2();
                        task.execute(dis_dong_api);
                    }
                    else if(select_dg.equals("남구")){
                        DownloadWebpageTask2 task=new DownloadWebpageTask2();
                        task.execute(dis_nam_api);
                    }
                    else if(select_dg.equals("수성구")){
                        DownloadWebpageTask2 task=new DownloadWebpageTask2();
                        task.execute(dis_su_api);
                    }
                    else if(select_dg.equals("서구")){
                        DownloadWebpageTask2 task=new DownloadWebpageTask2();
                        task.execute(dis_seo_api);
                    }
                    else if(select_dg.equals("중구")){
                        DownloadWebpageTask2 task=new DownloadWebpageTask2();
                        task.execute(dis_jung_api);
                    }
                    else if(select_dg.equals("달서군")){
                        DownloadWebpageTask2 task=new DownloadWebpageTask2();
                        task.execute(dis_gun_api);
                    }
                    else if(select_gb.equals("경산시")){
                        DownloadWebpageTask2 task=new DownloadWebpageTask2();
                        task.execute(dis_gs_api);
                    }
                    else if(select_gb.equals("경주시")){
                        DownloadWebpageTask2 task=new DownloadWebpageTask2();
                        task.execute(dis_gj_api);
                    }
                    else if(select_gb.equals("고령군")){
                        DownloadWebpageTask2 task=new DownloadWebpageTask2();
                        task.execute(dis_gr_api);
                    }
                    else if(select_gb.equals("구미시")){
                        DownloadWebpageTask2 task=new DownloadWebpageTask2();
                        task.execute(dis_gm_api);
                    }
                    else if(select_gb.equals("군위군")){
                        DownloadWebpageTask2 task=new DownloadWebpageTask2();
                        task.execute(dis_gw_api);
                    }
                    else if(select_gb.equals("김천시")){
                        DownloadWebpageTask2 task=new DownloadWebpageTask2();
                        task.execute(dis_gc_api);
                    }
                    else if(select_gb.equals("문경시")){
                        DownloadWebpageTask2 task=new DownloadWebpageTask2();
                        task.execute(dis_mg_api);
                    }
                    else if(select_gb.equals("봉화군")){
                        DownloadWebpageTask2 task=new DownloadWebpageTask2();
                        task.execute(dis_bh_api);
                    }
                    else if(select_gb.equals("상주시")){
                        DownloadWebpageTask2 task=new DownloadWebpageTask2();
                        task.execute(dis_sangj_api);
                    }else if(select_gb.equals("성주군")){
                        DownloadWebpageTask2 task=new DownloadWebpageTask2();
                        task.execute(dis_seongj_api);
                    }else if(select_gb.equals("안동시")){
                        DownloadWebpageTask2 task=new DownloadWebpageTask2();
                        task.execute(dis_ad_api);
                    }else if(select_gb.equals("영덕군")){
                        DownloadWebpageTask2 task=new DownloadWebpageTask2();
                        task.execute(dis_yd_api);
                    }else if(select_gb.equals("영양군")){
                        DownloadWebpageTask2 task=new DownloadWebpageTask2();
                        task.execute(dis_yy_api);
                    }else if(select_gb.equals("영주시")){
                        DownloadWebpageTask2 task=new DownloadWebpageTask2();
                        task.execute(dis_yj_api);
                    }else if(select_gb.equals("영천시")){
                        DownloadWebpageTask2 task=new DownloadWebpageTask2();
                        task.execute(dis_yeongc_api);
                    }else if(select_gb.equals("예천군")){
                        DownloadWebpageTask2 task=new DownloadWebpageTask2();
                        task.execute(dis_yec_api);
                    }else if(select_gb.equals("울릉군")){
                        DownloadWebpageTask2 task=new DownloadWebpageTask2();
                        task.execute(dis_uldo_api);
                    }else if(select_gb.equals("울진군")){
                        DownloadWebpageTask2 task=new DownloadWebpageTask2();
                        task.execute(dis_ulgin_api);
                    }else if(select_gb.equals("의성군")){
                        DownloadWebpageTask2 task=new DownloadWebpageTask2();
                        task.execute(dis_us_api);
                    }else if(select_gb.equals("청도군")){
                        DownloadWebpageTask2 task=new DownloadWebpageTask2();
                        task.execute(dis_cd_api);
                    }else if(select_gb.equals("청송군")){
                        DownloadWebpageTask2 task=new DownloadWebpageTask2();
                        task.execute(dis_cs_api);
                    }else if(select_gb.equals("칠곡군")){
                        DownloadWebpageTask2 task=new DownloadWebpageTask2();
                        task.execute(dis_cg_api);
                    }
                    else if(select_gb.equals("포항시")){
                        DownloadWebpageTask2 task=new DownloadWebpageTask2();
                        task.execute(dis_ph_api);
                    }
                    select_dg=" ";
                    select_gb=" ";
                }

                else if(!ce_he_check.isChecked()){
                    txt_ce_he.setText(" ");
                }
                else if(!ce_in_check.isChecked()){
                    txt_ce_in.setText(" ");
                }
                else if(!mi_check.isChecked()){
                    txt_mi.setText(" ");
                }
                else if(!ab_in_check.isChecked()){
                    txt_ab_in.setText(" ");
                }
                else if(!massive_bu_check.isChecked()){
                    txt_massive_bu.setText(" ");
                }
                else if(!connect_li_check.isChecked()){
                    txt_connect_li.setText(" ");
                }
                else if(!em_en_check.isChecked()){
                    txt_em_en.setText(" ");
                }
                else if(!em_di_check.isChecked()){
                    txt_em_di.setText(" ");
                }
                else if(!pre_mom_check.isChecked()){
                    txt_pre_mom.setText(" ");
                }
                else if(!mental_pat_check.isChecked()){
                    txt_mental_pat.setText(" ");
                }
                else if(!baby_check.isChecked()){
                    txt_baby.setText(" ");
                }
            }
        });

        return root;
    }
//응급실 or 입원실
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
            txt_er.setText(" ");
            txt_ward.setText(" ");
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
                            if(xpp.getName().equals("hvgc")){
                                inhvgc=true;
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
                            if(inhvgc){
                                hvgc=xpp.getText();
                                inhvgc=false;
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            if(xpp.getName().equals("item") && er_check.isChecked()){
                                txt_er.append(dutyName+"\n"+"[응급실]"+hvec+"\n\n");
                                initem=false;
                            }
                            if(xpp.getName().equals("item") && ward_check.isChecked()){
                                txt_ward.append(dutyName+"\n"+"[입원실]"+hvgc+"\n\n");
                                initem=false;
                            }
                            break;
                    }
                    eventType=xpp.next();
                }
            }catch (Exception e){
                txt_er.setText("error");
                txt_ward.setText("error");
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

    //중증 응급 질환
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
            boolean indutyName=false, inMKioskTy1=false, initem=false;
            String dutyName=null; String MKioskTy1=null;
            boolean inMKioskTy2=false, inMKioskTy3=false, inMKioskTy4=false,
                    inMKioskTy5=false, inMKioskTy6=false, inMKioskTy7=false,
                    inMKioskTy8=false, inMKioskTy9=false, inMKioskTy10=false,
                    inMKioskTy11=false, intotalCount=false;
            String MKioskTy2=null; String MKioskTy3=null; String MKioskTy4=null;
            String MKioskTy5=null; String MKioskTy6=null; String MKioskTy7=null;
            String MKioskTy8=null; String MKioskTy9=null; String MKioskTy10=null;
            String MKioskTy11=null; Integer totalCount=0;
            super.onPostExecute(result);
            txt_ce_he.setText(" "); txt_ce_in.setText(" "); txt_mi.setText(" "); txt_ab_in.setText(" ");
            txt_massive_bu.setText(" "); txt_connect_li.setText(" "); txt_em_en.setText(" "); txt_em_di.setText(" ");
            txt_pre_mom.setText(" "); txt_mental_pat.setText(" "); txt_baby.setText(" ");
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
                                indutyName=true; }
                            if(xpp.getName().equals("MKioskTy1")){
                                inMKioskTy1=true; }
                            if(xpp.getName().equals("MKioskTy2")){
                                inMKioskTy2=true; }
                            if(xpp.getName().equals("MKioskTy3")){
                                inMKioskTy3=true;
                            }if(xpp.getName().equals("MKioskTy4")){
                                inMKioskTy4=true;
                            }if(xpp.getName().equals("MKioskTy5")){
                                inMKioskTy5=true;
                            }if(xpp.getName().equals("MKioskTy6")){
                                inMKioskTy6=true;
                            }if(xpp.getName().equals("MKioskTy7")){
                                inMKioskTy7=true;
                            }if(xpp.getName().equals("MKioskTy8")){
                                inMKioskTy8=true;
                            }if(xpp.getName().equals("MKioskTy9")){
                                inMKioskTy9=true;
                            }if(xpp.getName().equals("MKioskTy10")){
                                inMKioskTy10=true;
                            }if(xpp.getName().equals("MKioskTy11")){
                                inMKioskTy11=true;
                            }if(xpp.getName().equals("totalCount")){
                                intotalCount=true;
                            }
                            break;
                        case XmlPullParser.TEXT:
                            if(indutyName){
                                dutyName=xpp.getText();
                                indutyName=false; }
                            if(inMKioskTy1){
                                MKioskTy1=xpp.getText();
                                inMKioskTy1=false; }
                            if(inMKioskTy2){
                                MKioskTy2=xpp.getText();
                                inMKioskTy2=false; }
                            if(inMKioskTy3){
                              MKioskTy3=xpp.getText();
                              inMKioskTy3=false; }
                            if(inMKioskTy4){
                               MKioskTy4=xpp.getText();
                               inMKioskTy4=false; }
                            if(inMKioskTy5){
                                MKioskTy5=xpp.getText();
                                inMKioskTy5=false; }
                            if(inMKioskTy6){
                              MKioskTy6=xpp.getText();
                              inMKioskTy6=false; }
                            if(inMKioskTy7){
                               MKioskTy7=xpp.getText();
                              inMKioskTy7=false; }
                            if(inMKioskTy8){
                                MKioskTy8=xpp.getText();
                                inMKioskTy8=false; }
                            if(inMKioskTy9){
                               MKioskTy9=xpp.getText();
                              inMKioskTy9=false; }
                            if(inMKioskTy10){
                                MKioskTy10=xpp.getText();
                                inMKioskTy10=false; }
                            if(inMKioskTy11){
                                MKioskTy11=xpp.getText();
                                inMKioskTy11=false; }
                            if(intotalCount){
                                totalCount=Integer.parseInt(xpp.getText());
                                intotalCount=false;
                            }
                            break;

                        case XmlPullParser.END_TAG:
                            if(xpp.getName().equals("item") && ce_he_check.isChecked()){
                                hop_name.setText(dutyName);
                                txt_ce_he.append("[뇌출혈] "+MKioskTy1+"\n");
                                initem=false;
                            }
                            if(xpp.getName().equals("item") && ce_in_check.isChecked()){
                                hop_name.setText(dutyName);
                                txt_ce_in.append("[뇌경색] "+MKioskTy2+"\n");
                                initem=false;
                            }
                            if(xpp.getName().equals("item") && mi_check.isChecked()){
                                hop_name.setText(dutyName);
                                txt_mi.append("[심근경색] "+MKioskTy3+"\n");
                                initem=false;
                            }
                            if(xpp.getName().equals("item") && ab_in_check.isChecked()){
                                hop_name.setText(dutyName);
                                txt_ab_in.append("[복부손상] "+MKioskTy4+"\n");
                                initem=false;
                            }
                            if(xpp.getName().equals("item") && massive_bu_check.isChecked()){
                                hop_name.setText(dutyName);
                                txt_massive_bu.append("[중증화상] "+MKioskTy5+"\n");
                                initem=false;
                            }
                            if(xpp.getName().equals("item") && connect_li_check.isChecked()){
                                hop_name.setText(dutyName);
                                txt_connect_li.append("[사지접합] "+MKioskTy6+"\n");
                                initem=false;
                            }
                            if(xpp.getName().equals("item") && em_en_check.isChecked()){
                                hop_name.setText(dutyName);
                                txt_em_en.append("[응급내시경] "+MKioskTy7+"\n");
                                initem=false;
                            }
                            if(xpp.getName().equals("item") && em_di_check.isChecked()){
                                hop_name.setText(dutyName);
                                txt_em_di.append("[응급투석] "+MKioskTy8+"\n");
                                initem=false;
                            }
                            if(xpp.getName().equals("item") && pre_mom_check.isChecked()){
                                hop_name.setText(dutyName);
                                txt_pre_mom.append("[조산산모] "+MKioskTy9+"\n");
                                initem=false;
                            }
                            if(xpp.getName().equals("item") && mental_pat_check.isChecked()){
                                hop_name.setText(dutyName);
                                txt_mental_pat.append("[정신질환자] "+MKioskTy10+"\n");
                                initem=false;
                            }
                            if(xpp.getName().equals("item") && baby_check.isChecked()){
                                hop_name.setText(dutyName);
                                txt_baby.append("[신생아] "+MKioskTy11+"\n");
                                initem=false;
                            }
                            if(xpp.getName().equals("totalCount")){
                                if(totalCount==0){
                                    hop_name.setText("없음");
                                }
                            }
                            break;
                    }
                    eventType=xpp.next();
                }
            }catch (Exception e){
                hop_name.setText("error");
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