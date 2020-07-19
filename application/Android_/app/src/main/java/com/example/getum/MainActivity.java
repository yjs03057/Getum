package com.example.getum;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import android.content.res.AssetManager;
import com.example.getum.SQLite.SQLiteDBHelper;
import com.example.getum.SQLite.StorageContract;
import com.example.getum.SQLite.UmbrellaContract;
import com.example.getum.SQLite.UserContract;
import com.google.android.gms.common.internal.Objects;
import com.google.zxing.integration.android.IntentIntegrator;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.widget.Toast.*;


public class MainActivity extends AppCompatActivity
        implements OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback, GoogleMap.OnMarkerClickListener {

    private DrawerLayout mDrawerLayout;
    public static Context context;
    private View mLayout;

    private GoogleMap mMap;
    private Marker currentMarker = null;

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int UPDATE_INTERVAL_MS = 1000;
    private static final int FASTEST_UPDATE_INTERVAL_MS = 500;

    //permission 구분
    private static final int PERMISSION_REQUEST_CODE = 100;
    boolean needRequest = false;

    String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    Location mCurrentLocation;
    LatLng currentPosition;

    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest locationRequest;
    private Location location;
    private Button scanQRBtn;
    private SQLiteDBHelper dbHelper;

    private View storage_info;
    private int storage_info_flag;
    private TextView storage_name;
    private TextView storage_address;
    private TextView um_cnt;

    private static int login_flag = 0;  //로그인, 로그아웃 구분

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        storage_info_flag = 0;

        scanQRBtn = (Button) findViewById(R.id.QRcode);
        dbHelper = new SQLiteDBHelper(this);
        try {
            dbHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTimeStamp = dateFormat.format(new Date());
        dbHelper.insertRentalLogRecord("rental", 1, "00001", 1, currentTimeStamp);


        scanQRBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ScanQR.class);
                startActivity(intent);
            }
        });

        locationRequest = new LocationRequest()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL_MS)
                .setFastestInterval(FASTEST_UPDATE_INTERVAL_MS);

        LocationSettingsRequest.Builder builder =
                new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ////////////////////////////////////////////////////////

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false); // 기존 title 지우기
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼 만들기
        actionBar.setHomeAsUpIndicator(R.drawable.menu); //뒤로가기 버튼 이미지 지정

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mLayout = findViewById(R.id.drawer_layout);

        storage_name = (TextView)findViewById(R.id.storage_name);
        storage_address = (TextView)findViewById(R.id.storage_address);
        um_cnt = (TextView)findViewById(R.id.um_cnt);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();

                int id = menuItem.getItemId();
                String title = menuItem.getTitle().toString();

                if (id == R.id.account) {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                } else if (id == R.id.info) {
                    Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
                    startActivity(intent);
                } else if (id == R.id.logout) {
                    Intent intent = new Intent(getApplicationContext(), CustomcenterActivity.class);
                    startActivity(intent);
                }

                return true;
            }
        });

        navigationView.getHeaderView(0).findViewById(R.id.btn_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //사용자 정보

                if(login_flag == 0){
                    Toast toast = makeText(getApplicationContext(), "로그인을 진행해주세요.", LENGTH_LONG);
                    toast.show();
                }
                else{
                    Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                    //intent.putextra
                    startActivity(intent);
                }
            }
        });


        storage_info = findViewById(R.id.storage_info_banner);

        //AFter Login
        if(login_flag == 1){
            Intent user_intent = getIntent();
            Log.d("user",user_intent.getExtras().getString("name"));
        }
    }

    private Drawable resize(Drawable image) {
        Bitmap b = ((BitmapDrawable) image).getBitmap();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, 15, 15, false);
        return new BitmapDrawable(getResources(), bitmapResized);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: { // 왼쪽 상단 버튼 눌렀을 때
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        mMap = googleMap;
        setDefaultLocation();

        int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);

        //permission있는지 체크
        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

            //permission있으면

            startLocationUpdates(); //위치 업데이트

        } else {   //permission 요청 필요

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])) {
                Snackbar.make(mLayout, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.",
                        Snackbar.LENGTH_INDEFINITE).setAction("확인", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //permission 요청, 요청 결과는 onRequestPermissionResult에서 수신됨.

                        ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS, PERMISSION_REQUEST_CODE);

                    }
                }).show();
            } else {
                //사용자가 permission 거부를 한 적이 없는 경우에 요청을 바로 함.
                //요청 결과는 onRequestPermissionResult에서 수신됨.

                ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, PERMISSION_REQUEST_CODE);

            }
        }

        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                storage_info.setVisibility(View.GONE);
                storage_info_flag = 0;
            }
        });


        storage_marker();   //storage Marker
    }


    LocationCallback locationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);

            List<Location> locationList = locationResult.getLocations();

            if (locationList.size() > 0) {
                location = locationList.get(locationList.size() - 1);

                currentPosition = new LatLng(location.getLatitude(), location.getLongitude());

                String markerTitle = getCurrentAddress(currentPosition);
                String markerSnippet = "위도: " + String.valueOf(location.getLatitude())
                        + "경도 : " + String.valueOf(location.getLongitude());

                //현재 위치에 마커 생성하고 이동
                setCurrentLocation(location, markerTitle, markerSnippet);
                mCurrentLocation = location;
            }

        }
    };

    private void startLocationUpdates() {

        if (!checkLocationServicesStatus()) {
            showDialogForLocationServiceSetting();
        } else {
            int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION);
            int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION);

            if (hasFineLocationPermission != PackageManager.PERMISSION_GRANTED ||
                    hasCoarseLocationPermission != PackageManager.PERMISSION_GRANTED) {

                return;
            }

            mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
            if (checkPermission()) {
                mMap.setMyLocationEnabled(true);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (checkPermission()) {

            mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);

            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }


    public String getCurrentAddress(LatLng latlng) {

        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses;

        try {
            addresses = geocoder.getFromLocation(
                    latlng.latitude,
                    latlng.longitude,
                    1
            );

        } catch (IOException ioException) {
            makeText(this, "지오코더 서비스 사용불가", LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";

        } catch (IllegalArgumentException illegalArgumentException) {
            makeText(this, "잘못된 GPS 좌표", LENGTH_LONG).show();
            return "잘못된 GPS 좌표";
        }


        if (addresses == null || addresses.size() == 0) {
            makeText(this, "주소 미발견", LENGTH_LONG).show();
            return "주소 미발견";
        } else {
            Address address = addresses.get(0);
            return address.getAddressLine(0).toString();
        }

    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public void setCurrentLocation(Location location, String markerTitle, String markerSnippet) {

        if (currentMarker != null) {
            currentMarker.remove();
        }
        LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());

        /*
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(currentLatLng);
        markerOptions.title(markerTitle);
        markerOptions.snippet(markerSnippet);
        markerOptions.draggable(true);

        currentMarker = mMap.addMarker(markerOptions);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(currentLatLng);
        mMap.moveCamera(cameraUpdate);
         */
    }

    public void setDefaultLocation() {

        LatLng DEFAULT_LOCATION = new LatLng(37.56, 126.97);
        String markerTitle = "위치정보 가져올 수 없음";
        String markerSnippet = "위치 퍼미션과 GPS 활성 여부 확인하세요";

        if (currentMarker != null) {
            currentMarker.remove();
        }

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(DEFAULT_LOCATION);
        markerOptions.title(markerTitle);
        markerOptions.snippet(markerSnippet);
        markerOptions.draggable(true);
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_um1_25));
        currentMarker = mMap.addMarker(markerOptions);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, 15);
        mMap.moveCamera(cameraUpdate);

    }

    //런타임 퍼미션 처리를 위한 메소드들
    private boolean checkPermission() {

        int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);

        //permission있는지 체크
        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

            return true;
        }

        return false;
    }


    /*
     * ActivityCompat.requestPermissions를 사용한 퍼미션 요청의 결과를 리턴받는 메소드입니다.
     */

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if (requestCode == PERMISSION_REQUEST_CODE &&
                grantResults.length == REQUIRED_PERMISSIONS.length) {

            boolean check_result = true;

            //모든 퍼미션을 허용했는지 체크합니다.
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }

            if (check_result) {
                //permission을 허용했다면 위치 업데이트 시작

                startLocationUpdates();

            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {

                    Snackbar.make(mLayout, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.",
                            Snackbar.LENGTH_INDEFINITE).setAction("확인", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finish();
                        }
                    }).show();
                } else {

                    Snackbar.make(mLayout, "퍼미션이 거부되었습니다. 설정(앱정보)에서 퍼미션을 허용해야 합니다.",
                            Snackbar.LENGTH_INDEFINITE).setAction("확인", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finish();
                        }
                    }).show();
                }
            }

        }
    }

    //GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting () {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n" + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent callGPSSettingIntent
                        = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);

                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        builder.create().show();
    }

    @Override
    protected void onActivityResult ( int requestCode, int resultCode,
                                      @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {
                        needRequest = true;
                        return;
                    }
                }

                break;
        }
    }

    public void storage_marker(){

        Cursor cursor = dbHelper.readStorageRecord();

        while(cursor.moveToNext()){

            double latitude = cursor.getDouble(cursor.getColumnIndexOrThrow(StorageContract.Storage.COLUMN_LATITUDE));
            double longitude = cursor.getDouble(cursor.getColumnIndexOrThrow(StorageContract.Storage.COLUMN_LONGITUDE));
            Log.d("marker", String.valueOf(latitude));
            Log.d("marker", String.valueOf(longitude));

            LatLng marker_loc = new LatLng(latitude, longitude);

            String id = cursor.getString(cursor.getColumnIndexOrThrow(StorageContract.Storage.COLUMN_ID));
            String location = cursor.getString(cursor.getColumnIndexOrThrow(StorageContract.Storage.COLUMN_LOCATION));
            String address = getCurrentAddress(marker_loc);
            Log.d("marker", address);

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(marker_loc);
            markerOptions.snippet(location + "\n"+address+"\n"+id); //상세정보
            markerOptions.draggable(false);
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_um1_50));
            mMap.addMarker(markerOptions);
        }

        cursor.close();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        String info = marker.getSnippet();
        Log.d("marker",info);
        String[] detail = info.split("\n");
        String location = detail[0];
        String address = detail[1];
        String storageId = detail[2];

        //umbrella 개수
        int cnt_um = dbHelper.UmbrellaInStorage(storageId);

        storage_name.setText(location);
        storage_address.setText(address);
        um_cnt.setText(String.valueOf(cnt_um));

        if(storage_info_flag == 0){
            storage_info.setVisibility(View.VISIBLE);
            storage_info_flag = 1;
        }
        else{
            storage_info.setVisibility(View.GONE);
            storage_info_flag = 0;
        }
        return true;
    }

    public void setLogin_flag(int i){
        login_flag = i;
    }

    public int getLogin_flag(){
        return login_flag;
    }

}
