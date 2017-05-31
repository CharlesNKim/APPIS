package appis.yesno.capstone.jbnu.appisapplication.Activity;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Arrays;
import java.util.List;

import appis.yesno.capstone.jbnu.appisapplication.HttpService.ParkingLot;
import appis.yesno.capstone.jbnu.appisapplication.R;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {
    private double latitude;
    private double longitude;
    private LatLng[] parkingLotMarker;
    private int closeParkingLotIndex;
    private int parkingLotNum;
    private LatLng now;
    private ParkingLot[] parkingLot;
//    private double closeParkingLotLongitude;
//    private double closeParkingLotLatitude;


    private double getDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(Math.abs(x2 - x1), 2) + Math.pow(Math.abs(y2 - y1), 2));
    }

    private int getMinIndexInDouble(double[] arrayDouble) {
        double[] array = new double[arrayDouble.length];
        System.arraycopy(arrayDouble, 0, array, 0, arrayDouble.length);
        Arrays.sort(array);
        for (int i = 0; i < array.length; i++) {
            if (array[0] == arrayDouble[i]) {
                return i;
            }
        }
        return 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // 인텐트로 넘겨받아 리스트에 저장한후 리스트 크기만큼 현재위치와 비교해야한다.
        List tmp = (List) getIntent().getSerializableExtra("list");
        latitude = (double) getIntent().getSerializableExtra("latitude");
        longitude = (double) getIntent().getSerializableExtra("longitude");
        Log.e("테스트", latitude + " " + longitude);
        parkingLot = new ParkingLot[tmp.size()];
        parkingLotMarker = new LatLng[tmp.size()];
        parkingLotNum = tmp.size();
        double[] distance = new double[tmp.size()];

        for (int i = 0; i < tmp.size(); i++) {
            parkingLot[i] = (ParkingLot) tmp.get(i);
            distance[i] = getDistance(latitude, longitude, Double.parseDouble(parkingLot[i].getLatitude()), Double.parseDouble(parkingLot[i].getLongitude()));
        }
//        closeParkingLotLatitude = Double.parseDouble(parkingLot[getMinIndexInDouble(distance)].getLatitude());
//        closeParkingLotLongitude = Double.parseDouble(parkingLot[getMinIndexInDouble(distance)].getLongitude());

        closeParkingLotIndex = getMinIndexInDouble(distance);


        /** 나머지 주차장에 대한 처리 필요 */
        for (int i = 0; i < tmp.size(); i++) {
            parkingLotMarker[i] = new LatLng(Double.parseDouble(parkingLot[i].getLatitude()), Double.parseDouble(parkingLot[i].getLongitude()));
        }


        now = new LatLng(latitude, longitude);
        // map 연동 코드
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        GoogleMap mMap = googleMap;
        for (int i = 0; i < parkingLotNum; i++) {
            mMap.addMarker(new MarkerOptions().position(parkingLotMarker[i]).title("남은자리 : " + parkingLot[i].getEmptyLot()));
        }


        mMap.addMarker(new MarkerOptions().position(now).title("현재위치").icon(BitmapDescriptorFactory.fromResource(R.drawable.maker2)));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);
        mMap.moveCamera(zoom);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(parkingLotMarker[closeParkingLotIndex]));
    }
}
