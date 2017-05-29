package appis.yesno.capstone.jbnu.appisapplication.HttpService;

import com.google.gson.annotations.SerializedName;

/**
 * Created by charlesn on 2017-04-17.
 */

public class ServerRequest {

    public volatile static ServerRequest USER_INFO;

    public ServerRequest() {

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
    @SerializedName("time")
    private String time;
    @SerializedName("fee")
    private String fee;



    private static ServerRequest user = null;



    public String getIDENTIFY() {
        return identify;
    }
    public void setIDENTIFY(String identify) {
        this.identify = identify;
    }
    public String getCarNum() {
        return carNum;
    }
    public void setCarNum(String userMail) {
        carNum = carNum;
    }
    public String getUserPassword() {
        return passwd;
    }
    public void setUserPassword(String userPassword) {
        passwd = userPassword;
    }

    public String getFee(){return fee;}
    public void  setFee(String fee){this.fee = fee;}
    public String getTime(){return time;}
    public void  setTime(String time){this.time = time;}


    public static ServerRequest getUser() {
        return user;
    }

    public static void setUser(ServerRequest user) {
        ServerRequest.user = user;
    }

}
