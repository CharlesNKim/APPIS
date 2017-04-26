package appis.yesno.capstone.jbnu.appisapplication.JsonObject;

/**
 * Created by charlesn on 2017-04-17.
 */


import com.google.gson.JsonObject;

/**
 * Created by charlesn on 2017-04-17.
 */

public class SendJsonData {


    private JsonObject sendJson;
    public SendJsonData() {

    }
    // login const
    public SendJsonData(String identify, String carNum, String passwd) {
        sendJson = new JsonObject();
        sendJson.addProperty("identify",identify);
        sendJson.addProperty("carNum",carNum);
        sendJson.addProperty("passwd",passwd);
    }
    // joinsu const
    public SendJsonData(String identify, String carNum, String passwd, String email) {
        sendJson = new JsonObject();
        sendJson.addProperty("identify", identify);
        sendJson.addProperty("carNum", carNum);
        sendJson.addProperty("passwd", passwd);
        sendJson.addProperty("email", email);
    }
    // email certification const
    public SendJsonData(String identify, String email, int codeNum) {
        sendJson = new JsonObject();
        sendJson.addProperty("identify", identify);
        sendJson.addProperty("email", email);
        sendJson.addProperty("code", codeNum);
    }
    // map ??? 여기에 어떤 정보가 들어가야 할까???
    public SendJsonData(String indentify) {

    }
    // car info & send mail
    public SendJsonData(String identify, String carNum) {
        sendJson = new JsonObject();
        if(identify.equals("email")) {
            sendJson.addProperty("identify", "email");
            sendJson.addProperty("email", carNum);
        } else if(identify.equals("carInfo")) {
            sendJson.addProperty("identify", "carInfo");
            sendJson.addProperty("carNum", carNum);
        }
    }
    public JsonObject returnJson() {
        return sendJson;
    }
}