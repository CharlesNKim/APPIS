package appis.yesno.capstone.jbnu.appisapplication.HttpService;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by charlesn on 2017-04-17.
 */

public interface Service {
    @POST("/")
    Call<ServerRequest> request_login(@Body JsonObject body);
    @POST("/")
    Call<ServerRequest> request_joinUs(@Body JsonObject body);
    @POST("/")
    Call<ServerRequest> request_map(@Body JsonObject body);
    @POST("/")
    Call<ServerRequest> request_carInfo(@Body JsonObject body);
    @POST("/")
    Call<ServerRequest> request_mailCodeCheck(@Body JsonObject body);
    @POST("/")
    Call<ServerRequest> request_sendMail(@Body JsonObject body);
}
