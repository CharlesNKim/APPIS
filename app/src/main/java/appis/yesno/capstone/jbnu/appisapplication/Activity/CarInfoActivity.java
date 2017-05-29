package appis.yesno.capstone.jbnu.appisapplication.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

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
    private TextView feeText;
    private SendJsonData sendJsonData;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);
        carNumText = (TextView) findViewById(R.id.carNum);
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

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss");
                Date date = new Date();
                String today = df.format(date);

                String Time = ServerRequest.USER_INFO.getTime().replaceAll("T", "  ");
                String[] dateAndTime = Time.split("\\.");
                String[] NowDateAndTime = today.split("\\  ");


                Log.d("TT", Time);
                Log.d("TT", dateAndTime[0]);
                Log.d("TT", today);
                Log.d("TT", NowDateAndTime[0]);
                Log.d("TT", NowDateAndTime[1]);

                carNumText.setText(ServerRequest.USER_INFO.getCarNum());
                timeText.setText(dateAndTime[0]);
                feeText.setText(ServerRequest.USER_INFO.getFee());

            }

            @Override
            public void onFailure(Call<ServerRequest> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "서버연결에 실패 하였습니다.", Toast.LENGTH_LONG).show();
            }

        });
    }


}
