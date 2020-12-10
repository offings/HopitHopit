package com.example.hopithopit.ui.search;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.hopithopit.MainActivity;
import com.example.hopithopit.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class SearchFragment extends Fragment implements OnMapReadyCallback {

    private SearchViewModel searchViewModel;
    private MapView mapView = null;
    private GoogleMap mMap;
    private FragmentActivity mContext;
    private Marker currentMarker = null;
    public static String search="";

    public static Location mlocation = null;

    String cur_search_api = "http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncLcinfoInqire?serviceKey=yNTJ6AknFLjMpn1Bme5Rk6Rr1Piz57T4zyDXLp7MfYFXgsOnojoMBpujFVSTkODNcAk1O3dWCtOiZIW%2F%2BKVFPg%3D%3D&WGS84_LON=127.085156592737&WGS84_LAT=37.4881325624879&pageNo=1&numOfRows=100";

    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";

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
            //Toast.makeText(getContext(), "" + permission + " is already granted", Toast.LENGTH_SHORT).show();
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
            mlocation = savedInstanceState.getParcelable(KEY_LOCATION);
            CameraPosition mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }

        final View root = inflater.inflate(R.layout.fragment_search, container, false);

        askForPermission(Manifest.permission.ACCESS_FINE_LOCATION, APP_PERMISSION);
        LocationManager lm = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new LocationListener(){

            @Override
            public void onLocationChanged(@NonNull Location location) {
                mlocation = location;
                DownloadWebpageTask downloadWebpageTask = new DownloadWebpageTask();
                downloadWebpageTask.execute(cur_search_api.replace("WGS84_LON=127.085156592737", "WGS84_LON="+mlocation.getLongitude()).replace("WGS84_LAT=37.4881325624879", "WGS84_LAT="+mlocation.getLatitude()));

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {

            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {

            }
        };

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
            return null;

        if (lm.getAllProviders().contains(LocationManager.NETWORK_PROVIDER))
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 100, locationListener);

        if (lm.getAllProviders().contains(LocationManager.GPS_PROVIDER))
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 100, locationListener);


        ImageView btn_search = (ImageView)root.findViewById(R.id.btn_search);

        mapView = (MapView) root.findViewById(R.id.mapFragment);
        if (mapView != null) {
            mapView.onCreate(savedInstanceState);
        }

        mapView.getMapAsync(this);


        Context context = null;
        context=container.getContext();
        final Context finalContext = context;
        final Context finalContext1 = context;
        root.setOnClickListener(new View.OnClickListener() {
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
                //Toast.makeText(finalContext1, search_what, Toast.LENGTH_LONG).show();
                search=search_what;
            }
        });
        return root;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng curPos = new LatLng(mlocation.getLatitude(), mlocation.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(curPos);
        markerOptions.title("현재위치");
        googleMap.addMarker(markerOptions);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(curPos));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(13));

        DownloadWebpageTask downloadWebpageTask = new DownloadWebpageTask();
        downloadWebpageTask.execute(cur_search_api.replace("WGS84_LON=127.085156592737", "WGS84_LON="+curPos.longitude).replace("WGS84_LAT=37.4881325624879", "WGS84_LAT="+curPos.latitude));

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

            boolean indutyAddr = false; boolean indutyName = false; boolean indutyTel1 = false;
            boolean indistance = false; boolean inlongitude = false; boolean inlatitude = false;
            String dutyAddr = ""; String dutyName = ""; String dutyTel1 = "";
            double distance = 0.0d; double longitude = 0.0d; double latitude = 0.0d;

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
                        } else if (xpp.getName().equals("distance")){
                            indistance = true;
                        } else if (xpp.getName().equals("longitude")){
                            inlongitude = true;
                        } else if (xpp.getName().equals("latitude")){
                            inlatitude = true;
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
                        } else if (indistance){
                            distance = Double.parseDouble(xpp.getText());
                            indistance = false;
                        } else if (inlongitude){
                            longitude = Double.parseDouble(xpp.getText());
                            inlongitude = false;
                        } else if (inlatitude){
                            latitude = Double.parseDouble(xpp.getText());
                            inlatitude = false;
                        }
                    } else if (eventType == XmlPullParser.END_TAG) {
                        if (xpp.getName().equals("item")) {
                            LatLng hlatlng = new LatLng(latitude, longitude);
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(hlatlng);
                            markerOptions.title(dutyName);
                            markerOptions.snippet(dutyAddr);
                            mMap.addMarker(markerOptions);
                        }
                    }
                    eventType = xpp.next();
                }
                LatLng curPos = new LatLng(mlocation.getLatitude(), mlocation.getLongitude());
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(curPos);
                markerOptions.title("현재위치");
                mMap.addMarker(markerOptions);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(curPos));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(13));
            } catch (Exception e) {

            }
        }
    }

}