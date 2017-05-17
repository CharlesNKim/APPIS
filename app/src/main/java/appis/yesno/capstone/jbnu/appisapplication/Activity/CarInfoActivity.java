package appis.yesno.capstone.jbnu.appisapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.squareup.okhttp.internal.Internal;

import org.w3c.dom.Text;

import appis.yesno.capstone.jbnu.appisapplication.R;

/**
 * Created by pc on 2017-04-26.
 */

public class CarInfoActivity extends AppCompatActivity {

    private TextView carNumText;
    private TextView timeText;
    private TextView feeText;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);

        Intent intent = getIntent();
        String carNum = intent.getStringExtra("로그인 차량번호");

        Log.d("성공", carNum);










    }


}
