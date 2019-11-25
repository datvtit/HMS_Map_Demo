package com.wz.android.zzmapdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.huawei.hms.maps.CameraUpdate;
import com.huawei.hms.maps.CameraUpdateFactory;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.MapView;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.model.BitmapDescriptorFactory;
import com.huawei.hms.maps.model.CameraPosition;
import com.huawei.hms.maps.model.Circle;
import com.huawei.hms.maps.model.CircleOptions;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.Marker;
import com.huawei.hms.maps.model.MarkerOptions;
import com.huawei.hms.maps.util.LogM;



public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "MapViewDemoActivity";
    //Huawei map
    private static final int REQUEST_CODE = 100;
    private HuaweiMap hmap;
    private MapView mMapView;
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    private Marker mMarker;
    private static final LatLng LAT_LNG = new LatLng(45.4628327,9.1075207);
    private static final String[] RUNTIME_PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.INTERNET
    };
    private Circle mCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        LogM.d(TAG, "onCreate:hzj");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!hasPermissions(this, RUNTIME_PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, RUNTIME_PERMISSIONS, REQUEST_CODE);
        }



        mMapView = findViewById(R.id.mapView);
        Bundle mapViewBundle = null;
        if( savedInstanceState!= null){
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mMapView.onCreate(mapViewBundle);

        mMapView.getMapAsync(this);


    }

    @Override
    public void onMapReady(HuaweiMap huaweiMap) {
        Log.d(TAG, "onMapReady");
        hmap = huaweiMap;
        hmap.setMyLocationEnabled(true);

        CameraPosition build = new CameraPosition.Builder().target(LAT_LNG).zoom(5).build();

        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(build);
        hmap.animateCamera(cameraUpdate);

        // mark can be add by HuaweiMap
        mMarker = hmap.addMarker(new MarkerOptions().position(LAT_LNG)
                .alpha(0.5f)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.badge_ph))
                .infoWindowAnchor(0.5f, 0.5f)
                .clusterable(true)
               );

        mMarker.setAnchor(0, -1);
        mMarker.showInfoWindow();

        // circle can be add by HuaweiMap
        mCircle = hmap
                .addCircle(new CircleOptions().center(new LatLng(60, 60)).radius(500).fillColor(0XFF00FFFF).strokeWidth(10).strokeColor(
                        Color.RED));

    }
    @Override
    protected void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }


    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }


    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}
