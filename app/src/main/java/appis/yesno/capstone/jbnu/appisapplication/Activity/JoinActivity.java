package appis.yesno.capstone.jbnu.appisapplication.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import appis.yesno.capstone.jbnu.appisapplication.Encript.Encrypt;
import appis.yesno.capstone.jbnu.appisapplication.HttpService.ServerRequest;
import appis.yesno.capstone.jbnu.appisapplication.HttpService.Service;
import appis.yesno.capstone.jbnu.appisapplication.HttpService.ServiceInfo;
import appis.yesno.capstone.jbnu.appisapplication.JsonObject.SendJsonData;
import appis.yesno.capstone.jbnu.appisapplication.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 2017-03-27.
 */

public class JoinActivity extends AppCompatActivity {
    private EditText carNumber;
    private EditText mPasswordView;
    private EditText mPasswordCheckView;
    private Button sendMail;
    private Button joinUs;
    private Button certificationEmail;
    private SendJsonData sendJsonData;
    private EditText emailText;
    private EditText emailCode;
    private boolean checkEmailCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        carNumber = (EditText) findViewById(R.id.edit_car_number);
        mPasswordView = (EditText) findViewById(R.id.edit_password);
        mPasswordCheckView = (EditText) findViewById(R.id.edit_check_password);
        emailText = (EditText) findViewById(R.id.edit_email);
        sendMail = (Button) findViewById(R.id.send_button);
        joinUs = (Button) findViewById(R.id.join_end);
        certificationEmail = (Button) findViewById(R.id.Authentication);
        emailCode = (EditText) findViewById(R.id.edit_check_number);
        checkEmailCode = false;

        /** send mail button add click listener
         */
        sendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** 메일보내는 코드 입력
                 */
                sendJsonData = new SendJsonData("email", emailText.getText().toString());
                Service service = ServiceInfo.createService(Service.class);
                Call<ServerRequest> convertedContent = service.request_sendMail(sendJsonData.returnJson());
                convertedContent.enqueue(new Callback<ServerRequest>() {
                    @Override
                    public void onResponse(Call<ServerRequest> call, Response<ServerRequest> response) {
                        Log.d("Response status code: ", String.valueOf(response.code()));

                        // if parsing the JSON body failed, `response.body()` returns null
                        ServerRequest.USER_INFO = response.body();
//                        Log.d("메세지", ServerRequest.getInstance().getCarNum());
                        if (ServerRequest.getInstance().getCarNum().equals("200")) {
                            Log.d("SendMail", "메일 보내기 성공");
                            Toast.makeText(getApplicationContext(), "메일을 보냈습니다.", Toast.LENGTH_LONG).show();
                        } else {
                            Log.d("SendMail", "메일 보내기 실패");
                            Toast.makeText(getApplicationContext(), "잠시후에 다시 시도해 주시기 바랍니다.", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ServerRequest> call, Throwable t) {
                        Log.d("실패", t.getMessage().toString());
                    }
                });

            }
        });

        certificationEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** 메일 인증번호를 확인하는 코드
                 *
                 */
                sendJsonData = new SendJsonData("codeCheck", emailText.getText().toString(), Integer.parseInt(emailCode.getText().toString()));
                Service service = ServiceInfo.createService(Service.class);
                Call<ServerRequest> convertedContent = service.request_mailCodeCheck(sendJsonData.returnJson());
                convertedContent.enqueue(new Callback<ServerRequest>() {
                    @Override
                    public void onResponse(Call<ServerRequest> call, Response<ServerRequest> response) {
                        Log.d("Response status code: ", String.valueOf(response.code()));

                        // if parsing the JSON body failed, `response.body()` returns null
                        ServerRequest.USER_INFO = response.body();
                        Log.d("메세지", ServerRequest.getInstance().getCarNum());
                        if (ServerRequest.getInstance().getCarNum().equals("200")) {
                            Log.d("CodeCheck", "인증번호 성공");
                            Toast.makeText(getApplicationContext(), "메일 인증 성공.", Toast.LENGTH_LONG).show();
                            checkEmailCode = true;
                        } else {
                            Log.d("CodeCheck", "인증번호 실패");
                            Toast.makeText(getApplicationContext(), "인증번호가 틀렸습니다.", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ServerRequest> call, Throwable t) {
                        Log.d("실패", t.getMessage().toString());
                    }
                });
            }
        });

        joinUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** 회원가입 버튼 클릭 코드 입력
                 *
                 */
                if (mPasswordView.getText().toString().equals(mPasswordCheckView.getText().toString())) {
                    if (checkEmailCode) {
                        sendJsonData = new SendJsonData("joinUs", carNumber.getText().toString(),
                                Encrypt.encrypt_SHA1(mPasswordView.getText().toString()),
                                emailText.getText().toString());
                        Service service = ServiceInfo.createService(Service.class);
                        Call<ServerRequest> convertedContent = service.request_mailCodeCheck(sendJsonData.returnJson());
                        convertedContent.enqueue(new Callback<ServerRequest>() {
                            @Override
                            public void onResponse(Call<ServerRequest> call, Response<ServerRequest> response) {
                                Log.d("Response status code: ", String.valueOf(response.code()));

                                // if parsing the JSON body failed, `response.body()` returns null
                                ServerRequest.USER_INFO = response.body();
                                Log.d("메세지", ServerRequest.getInstance().getCarNum());
                                if (ServerRequest.getInstance().getCarNum().equals("200")) {
                                    Log.d("JoinUs", "회원가입 성공");
                                    Toast.makeText(getApplicationContext(), "회원가입 성공.", Toast.LENGTH_LONG).show();
                                    checkEmailCode = false;
                                    finish();
                                } else {
                                    Log.d("JoinUs", "회원가입 실패");
                                    Toast.makeText(getApplicationContext(), "회원가입 실패", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ServerRequest> call, Throwable t) {
                                Log.d("실패", t.getMessage().toString());
                            }
                        });
                    } else {
                        Toast.makeText(getApplicationContext(), "메일인증을 해주세요", Toast.LENGTH_LONG).show();
                    }

                } else  {
                    Toast.makeText(getApplicationContext(), "비밀번호가 서로 다릅니다.", Toast.LENGTH_LONG).show();
                }

            }
        });


    }
}
