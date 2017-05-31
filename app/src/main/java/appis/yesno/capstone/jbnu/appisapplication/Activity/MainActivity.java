package appis.yesno.capstone.jbnu.appisapplication.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import appis.yesno.capstone.jbnu.appisapplication.HttpService.ServerRequest;
import appis.yesno.capstone.jbnu.appisapplication.HttpService.Service;
import appis.yesno.capstone.jbnu.appisapplication.HttpService.ServiceInfo;
import appis.yesno.capstone.jbnu.appisapplication.JsonObject.SendJsonData;
import appis.yesno.capstone.jbnu.appisapplication.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Sangjin on 2017. 3. 29..
 */

public class MainActivity extends AppCompatActivity {
    private Button carInfo;
    private Button parkingMap;
    private double latitude = 0;
    private double longitude = 0;
    private Context context;
    private SendJsonData sendJsonData;
    private LocationListener locationListener;
    private LocationManager locationManager;

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this.getApplicationContext();
        carInfo = (Button) findViewById(R.id.info_button);
        parkingMap = (Button) findViewById(R.id.search_button);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        carInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** carInfo를 출력하는 버튼 코드
                 *
                 */

            }
        });

        parkingMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** 주차장 정보를 출력하는 버튼 코드 */
                /**    현재 위치정보 위도 경도를 가져오는 코드
                 */

                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (locationManager == null) {
                    Toast.makeText(getApplicationContext(), "위치정보를 가져올 수 없습니다.", Toast.LENGTH_LONG).show();
                }
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "위치 서비스를 켜주세요", Toast.LENGTH_LONG).show();
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                }
                boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                if (isNetworkEnabled) {
                    Log.e("GPS Enable", "true");

                    locationListener = new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            setLatitude(location.getLatitude());
                            setLongitude(location.getLongitude());
                            Log.e("위치", "[" + location.getProvider() + "] (" + location.getLatitude() + "," +
                                    location.getLongitude() + ")");
                            locationManager.removeUpdates(locationListener);
                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {
                            Log.e("onStatusChanged", "onStatusChanged");
                        }

                        @Override
                        public void onProviderEnabled(String provider) {
                            Log.e("onProviderEnabled", "onProviderEnabled");
                        }

                        @Override
                        public void onProviderDisabled(String provider) {
                            Log.e("onProviderDisabled", "onProviderDisabled");
                        }
                    };

                    // QQQ: 시간, 거리를 0 으로 설정하면 가급적 자주 위치 정보가 갱신되지만 베터리 소모가 많을 수 있다.
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 0, locationListener);
                }

                /**  모든 주차장 정보 */
                sendJsonData = new SendJsonData("reGeoInfo");
                Service service = ServiceInfo.createService(Service.class);
                Call<ServerRequest> convertedContent = service.request(sendJsonData.returnJson());
                convertedContent.enqueue(new Callback<ServerRequest>() {
                    @Override
                    public void onResponse(Call<ServerRequest> call, final Response<ServerRequest> response) {
                        Log.d("리지오 : ", String.valueOf(response.code()));
                        String res = response.body().toString();
                        Log.d("리지오", res);

                        // if parsing the JSON body failed, `response.body()` returns null
                        ServerRequest.USER_INFO = response.body();
                        // 주차장 전체의 위치 및 공석 데이터 저장된 리스트
//                        ServerRequest.getInstance().getGeoInfo();


                        Toast.makeText(getApplicationContext(), "위치정보 수신중 입니다. 잠시만 기다려 주세요.", Toast.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("위치", latitude + "");
                                Log.d("위치", longitude + "");
                                if (latitude != 0 && longitude != 0) {
                                    /** 위치요청 중지하는 코드 들어갈곳 */
                                }
                                if (String.valueOf(response.code()).equals("200")) {
                                    Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                                    intent.putExtra("list", (Serializable) ServerRequest.getInstance().getGeoInfo());
                                    intent.putExtra("latitude", latitude);
                                    intent.putExtra("longitude", longitude);
                                    startActivity(intent);
                                }
//                        ParkingLot a = (ParkingLot) tmp.get(0);

//                        if (ServerRequest.getInstance().getCarNum().equals("200")) {
//                            Toast.makeText(getApplicationContext(), "메일을 보냈습니다.", Toast.LENGTH_LONG).show();
//                        }
                            }
                        }, 5000);


                    }

                    @Override
                    public void onFailure(Call<ServerRequest> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "서버연결에 실패 하였습니다.", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

    }
}
