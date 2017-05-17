package appis.yesno.capstone.jbnu.appisapplication.GPS;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

/**
 * Created by charlesn on 2017-04-26.
 */

public class GpsInfo {
    private Context context;
    private LocationManager locationManager;
    private static double longitude = 0;
    private static double latitude = 0;

    public LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Log.d("위치 변환", "됨");
            longitude = location.getLongitude(); //경도
            latitude = location.getLatitude();   //위도
            Log.d(""+longitude, latitude+"");
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    public double getLatitue() {
        Log.d("위치 주자", latitude+"");
        return latitude;
    }

    public double getLogitude() {
        Log.d("위치 주자", longitude+"");
        return longitude;
    }

    public GpsInfo(Context mapActivityContext) {
        locationManager = (LocationManager) mapActivityContext.getSystemService(Context.LOCATION_SERVICE);
        context = mapActivityContext;
    }

    public void stopGeoInfo() {
        locationManager.removeUpdates(mLocationListener);
    }

    public void startGeoInfoSearch() {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, // 등록할 위치제공자
                100, // 통지사이의 최소 시간간격 (miliSecond)
                1, // 통지사이의 최소 변경거리 (m)
                mLocationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, // 등록할 위치제공자
                100, // 통지사이의 최소 시간간격 (miliSecond)
                1, // 통지사이의 최소 변경거리 (m)
                mLocationListener);

    }


}
