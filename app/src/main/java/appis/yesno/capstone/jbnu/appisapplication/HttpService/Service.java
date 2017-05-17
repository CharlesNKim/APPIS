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
    Call<ServerRequest> request(@Body JsonObject body);
}
