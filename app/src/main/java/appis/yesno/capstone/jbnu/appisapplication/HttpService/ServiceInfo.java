package appis.yesno.capstone.jbnu.appisapplication.HttpService;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by charlesn on 2017-04-17.
 */

public class ServiceInfo {
    public static final String API_BASE_URL = "http://13.124.64.215:8080/";
    //    public static final String API_BASE_URL = "http://211.33.2.37:8000/";
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }

}
