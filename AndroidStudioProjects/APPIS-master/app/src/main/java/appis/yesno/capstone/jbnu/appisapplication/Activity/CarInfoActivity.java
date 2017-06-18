package appis.yesno.capstone.jbnu.appisapplication.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import appis.yesno.capstone.jbnu.appisapplication.HttpService.ServerRequest;
import appis.yesno.capstone.jbnu.appisapplication.HttpService.Service;
import appis.yesno.capstone.jbnu.appisapplication.HttpService.ServiceInfo;
import appis.yesno.capstone.jbnu.appisapplication.JsonObject.SendJsonData;
import appis.yesno.capstone.jbnu.appisapplication.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

/**
 * Created by pc on 2017-04-26.
 */

public class CarInfoActivity extends AppCompatActivity {

    private TextView carNumText;
    private TextView timeText;
    private TextView parkingText;
    private TextView feeText;
    private SendJsonData sendJsonData;
    private Date nowDate;
    private Date timeDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);
        parkingText = (TextView) findViewById(R.id.parking_place);
        carNumText = (TextView) findViewById(R.id.CarNum);
        timeText = (TextView) findViewById(R.id.time);
        feeText = (TextView) findViewById(R.id.fee);


        SharedPreferences test = getSharedPreferences("carNum", MODE_PRIVATE);
        final String carNum = test.getString("loginCarNum", NULL);


        sendJsonData = new SendJsonData("carInfo", carNum);
        Service service = ServiceInfo.createService(Service.class);
        Call<ServerRequest> convertedContent = service.request(sendJsonData.returnJson());
        convertedContent.enqueue(new Callback<ServerRequest>() {
            @Override
            public void onResponse(Call<ServerRequest> call, Response<ServerRequest> response) {
                Log.d("Response status code: ", String.valueOf(response.code()));

                // if parsing the JSON body failed, `response.body()` returns null
                ServerRequest.USER_INFO = response.body();

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date date = new Date();
                String today = df.format(date);
                try {
                    nowDate = df.parse(today);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String Time = ServerRequest.USER_INFO.getTime().replaceAll("T", "  ");
                try {
                    timeDate = df.parse(Time);
                    Log.d("TT", timeDate.getTime() + "");
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String[] dateAndTime = Time.split("\\.");

                long diff = nowDate.getTime() - timeDate.getTime();
                long calculateTime = diff / (1800 * 1000);  // 30분당 시간계산
                long calculateFee = calculateTime * Long.parseLong(ServerRequest.USER_INFO.getFee()); // 요금 계산

                parkingText.setText(ServerRequest.USER_INFO.getParkingLotID());
                carNumText.setText(ServerRequest.USER_INFO.getCarNum());
                timeText.setText(dateAndTime[0]);
                feeText.setText(Long.toString(calculateFee) + "원");

            }

            @Override
            public void onFailure(Call<ServerRequest> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "서버연결에 실패 하였습니다.", Toast.LENGTH_LONG).show();
            }

        });
    }


}
