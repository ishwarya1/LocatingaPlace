package sg.edu.rp.soi.locatingaplace;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btn1, btn2, btn3;
    private GoogleMap map;
    LatLng poi_East, poi_RP, poi_Central, singapore;
    Spinner spinner;
    ArrayList<String> alSel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment)
                fm.findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback(){
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                singapore = new LatLng(1.3143394,103.7041601);
                poi_East = new LatLng(1.3508834,103.9656748);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom( singapore,
                        10));
                UiSettings ui = map.getUiSettings();
                ui.setCompassEnabled(true);
                ui.setZoomControlsEnabled(true);
                int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION);
                if (permissionCheck == PermissionChecker.PERMISSION_GRANTED) {
                    map.setMyLocationEnabled(true);
                } else {
                    Log.e("GMap - Permission", "GPS access has not been granted");
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                }
                Marker cp = map.addMarker(new
                        MarkerOptions()
                        .position(poi_East)
                        .title("East")
                        .snippet("Block 555, Tampines Ave 3, 287788 \n" +
                                "Operating hours: 9am-5pm\n" +
                                "Tel:66776677\"\n")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                poi_RP = new LatLng(1.44224, 103.785733);
                Marker rp = map.addMarker(new
                        MarkerOptions()
                        .position(poi_RP)
                        .title("HQ-North")
                        .snippet("Block 333, Admiralty Ave 3, 765654\n"+" Operating hours: 10am-5pm\n" +
                                "Tel:65433456\n")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                poi_Central = new LatLng(1.2871618,103.8356821);
                Marker C = map.addMarker(new
                        MarkerOptions()
                        .position(poi_Central)
                        .title("Central")
                        .snippet("Block 3A, Orchard Ave 3, 134542 \n" +
                                "Operating hours: 11am-8pm\n" +
                                "Tel:67788652\n")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        Toast.makeText(getApplicationContext(), "This is "+marker.getTitle(), Toast.LENGTH_LONG).show();
                        return false;
                    }
                });
            }
        });
        alSel = new ArrayList<>();
        alSel.add("Select Region");
        alSel.add("HQ-North");
        alSel.add("Central");
        alSel.add("East");
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, alSel);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(alSel.get(i)=="Select Branch"){
                    if (map != null){
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom( singapore,
                                10));
                    }
                }else if(alSel.get(i)=="HQ-North"){
                    if (map != null){
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_RP,
                                15));
                    }
                }else if(alSel.get(i)=="Central"){
                    if (map != null){
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_Central,
                                15));
                    }
                }else{
                    if (map != null){
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_East,
                                15));
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
}