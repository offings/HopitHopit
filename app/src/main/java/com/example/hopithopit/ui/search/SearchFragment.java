package com.example.hopithopit.ui.search;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.hopithopit.MainActivity;
import com.example.hopithopit.R;
import com.example.hopithopit.ui.search.SearchViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SearchFragment extends Fragment implements OnMapReadyCallback {

    private SearchViewModel searchViewModel;
    private MapView mapView = null;
    private GoogleMap mMap;
    private FragmentActivity mContext;
    private Marker currentMarker = null;
    public static String search="";
    public String qn = "";

    private Location mCurrentLocatiion;

    String dg_search_api = "http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncListInfoInqire?serviceKey=yNTJ6AknFLjMpn1Bme5Rk6Rr1Piz57T4zyDXLp7MfYFXgsOnojoMBpujFVSTkODNcAk1O3dWCtOiZIW%2F%2BKVFPg%3D%3D&Q0=%EB%8C%80%EA%B5%AC%EA%B4%91%EC%97%AD%EC%8B%9C&QN=";
    String gb_search_api = "http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncListInfoInqire?serviceKey=yNTJ6AknFLjMpn1Bme5Rk6Rr1Piz57T4zyDXLp7MfYFXgsOnojoMBpujFVSTkODNcAk1O3dWCtOiZIW%2F%2BKVFPg%3D%3D&Q0=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84&QN=";

    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";

    public void onAttach(Activity activity) {
        mContext = (FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        searchViewModel =
                ViewModelProviders.of(this).get(SearchViewModel.class);
        if (savedInstanceState != null) {
            mCurrentLocatiion = savedInstanceState.getParcelable(KEY_LOCATION);
            CameraPosition mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }

        View root = inflater.inflate(R.layout.fragment_search, container, false);

        EditText input_search = (EditText)root.findViewById(R.id.search);
        qn = input_search.getText().toString();

        ImageView btn_search = (ImageView)root.findViewById(R.id.btn_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    search_api(dg_search_api, qn);
                    search_api(gb_search_api, qn);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        mapView = (MapView) root.findViewById(R.id.mapFragment);
        if (mapView != null) {
            mapView.onCreate(savedInstanceState);
        }

        mapView.getMapAsync(this);


        Context context = null;
        context=container.getContext();
        /*try {
            search_what= URLEncoder.encode(search_info.getText().toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        final String finalSearch_what = search_what;
        */final Context finalContext = context;
        final Context finalContext1 = context;
        /*root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText search_info=(EditText)root.findViewById(R.id.search);
                String search_what=null;
                try {
                    search_what= URLEncoder.encode(search_info.getText().toString(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                ((MainActivity)getActivity()).replaceFragment(R.id.searchListFragment);
                Toast.makeText(finalContext1, search_what, Toast.LENGTH_LONG).show();
                search=search_what;
            }
        });*/
        return root;
    }

    public void search_api(String api, String searchQN) throws IOException {
        StringBuilder urlBuilder = new StringBuilder(api);
        urlBuilder.append(URLEncoder.encode(searchQN, "UTF-8"));
        urlBuilder.append("&pageNo=1&numOfRows=10");
//        Toast.makeText(getContext(), searchQN, Toast.LENGTH_SHORT).show();

        DownloadWebpageTask downloadWebpageTask = new DownloadWebpageTask();
        downloadWebpageTask.execute(urlBuilder.toString());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng SEOUL = new LatLng(37.56, 126.97);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(SEOUL);
        markerOptions.title("서울");
        markerOptions.snippet("수도");
        googleMap.addMarker(markerOptions);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(13));

    }

    @Override
    public void onResume() { // 유저에게 Fragment가 보여지고, 유저와 상호작용이 가능하게 되는 부분
        super.onResume();
        mapView.onResume();
        /*if (mLocationPermissionGranted) {
            Log.d("Search", "onResume : requestLocationUpdates");
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mFusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
            if (mMap!=null)
                mMap.setMyLocationEnabled(true);
        }*/
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
                String txt = (String) downloadUrl((String) urls[0]);
                Log.d("doinbackground", urls[0]);
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

            boolean indutyAddr = false; boolean indutyName = false; boolean indutyTel1 = false; boolean indutyTel3 = false;
            boolean inwgs84Lon = false; boolean inwgs84Lat = false;
            String dutyAddr = ""; String dutyName = ""; String dutyTel1 = ""; String dutyTel3 = "";
            double wgs84Lon = 0.0d; double wgs84Lat = 0.0d;

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
                        } else if (inwgs84Lon){
                            wgs84Lon = Double.parseDouble(xpp.getText());
                            inwgs84Lon = false;
                        } else if (inwgs84Lat){
                            wgs84Lat = Double.parseDouble(xpp.getText());
                            inwgs84Lat = false;
                        }
                    } else if (eventType == XmlPullParser.END_TAG) {
                        if (xpp.getName().equals("item")) {
                            LatLng hlatlng = new LatLng(wgs84Lat, wgs84Lon);
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(hlatlng);
                            markerOptions.title(dutyName);
                            markerOptions.snippet(dutyAddr);
                            mMap.addMarker(markerOptions);
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(hlatlng));
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
                        }
                    }
                    eventType = xpp.next();
                }
            } catch (Exception e) {

            }
        }
    }

}