package appis.yesno.capstone.jbnu.appisapplication.HttpService;
import com.google.gson.annotations.SerializedName;

/**
 * Created by charlesn on 2017-05-29.
 */

public class ParkingLot {

    @SerializedName("latitude")
    private String latitude;
    @SerializedName("longitude")
    private String longitude;
    @SerializedName("emptyLot")
    private String emptyLot;


    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getEmptyLot() {
        return emptyLot;
    }
}
