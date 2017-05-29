package appis.yesno.capstone.jbnu.appisapplication.Activity;

import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.JsonParser;

import java.util.List;

import appis.yesno.capstone.jbnu.appisapplication.GPS.GpsInfo;
import appis.yesno.capstone.jbnu.appisapplication.HttpService.ParkingLot;
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
    private LocationManager locationManager;
    private Context context;
    private SendJsonData sendJsonData;

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
                /**    현재 위치정보 위도 경도  */
                // 과연 위치정보를 한방에 가져올 수 있나????
                GpsInfo gpsInfo = new GpsInfo(context);
                gpsInfo.startGeoInfoSearch();

                longitude = gpsInfo.getLatitue();
                latitude = gpsInfo.getLogitude();

                if (longitude != 0 && latitude != 0) {
                    gpsInfo.stopGeoInfo();
                    Log.d("위치", "끔");
                }
                /**  모든 주차장 정보 */
                sendJsonData = new SendJsonData("reGeoInfo");
                Service service = ServiceInfo.createService(Service.class);
                Call<ServerRequest> convertedContent = service.request(sendJsonData.returnJson());
                convertedContent.enqueue(new Callback<ServerRequest>() {
                    @Override
                    public void onResponse(Call<ServerRequest> call, Response<ServerRequest> response) {
                        Log.d("리지오 : ", String.valueOf(response.code()));
                        String res = response.body().toString();
                        Log.d("리지오", res);

//                        // if parsing the JSON body failed, `response.body()` returns null
                        ServerRequest.USER_INFO = response.body();
                        List tmp = ServerRequest.getInstance().getGeoInfo();
                        ParkingLot a = (ParkingLot) tmp.get(0);

                        Log.d("메세지", a.getLatitude());
//                        if (ServerRequest.getInstance().getCarNum().equals("200")) {
//                            Log.d("SendMail", "메일 보내기 성공");
//                            Toast.makeText(getApplicationContext(), "메일을 보냈습니다.", Toast.LENGTH_LONG).show();
//                        }

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
