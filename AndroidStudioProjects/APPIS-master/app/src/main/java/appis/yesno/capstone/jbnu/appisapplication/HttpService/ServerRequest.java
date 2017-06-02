package appis.yesno.capstone.jbnu.appisapplication.HttpService;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by charlesn on 2017-04-17.
 */

public class ServerRequest {

    public volatile static ServerRequest USER_INFO;

    private ServerRequest() {

    }

    public static ServerRequest getInstance() {
        if (USER_INFO == null) { //있는지 체크 없으면
            USER_INFO = new ServerRequest(); //생성한뒤
        }
        return USER_INFO; //성성자를 넘긴다.
    }


    @SerializedName("identify")
    private String identify;
    @SerializedName("carNum")
    private String carNum;
    @SerializedName("passwd")
    private String passwd;
    @SerializedName("email")
    private String email;
    @SerializedName("codeNum")
    private String codeNum;
    @SerializedName("time")
    private String time;
    @SerializedName("fee")
    private String fee;

    @SerializedName("geoInfo")
    private List<ParkingLot> geoInfo = null;

    public String getIdentify() {
        return identify;
    }


    public List<ParkingLot> getGeoInfo() {
        return geoInfo;
    }

    public String getPasswd() {
        return passwd;
    }

    public String getEmail() {
        return email;
    }

    public String getCodeNum() {
        return codeNum;
    }

    public String getTime() {
        return time;
    }

    public String getFee() {
        return fee;
    }

    private static ServerRequest user = null;


    public String getCarNum() {
        return carNum;
    }

    public String getUserPassword() {
        return passwd;
    }

    public static ServerRequest getUser() {
        return user;
    }


}
