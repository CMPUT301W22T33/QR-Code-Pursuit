package com.team33.qrcodepursuit.activities.Map;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;




import com.team33.qrcodepursuit.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment {

    private MapView map = null;

    private final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;

    public MapFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        Context context = this.getContext();
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));



        //these lines should make it follow the player, but i dont know how to make class for it (not important)
        //https://stackoverflow.com/questions/53651627/how-to-add-my-current-location-with-osmdroid
        //https://osmdroid.github.io/osmdroid/javadocs/osmdroid-android/debug/index.html?org/osmdroid/views/overlay/mylocation/MyLocationNewOverlay.html
        //this.mLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(context),map);
        //this.mLocationOverlay.enableMyLocation();
        //map.getOverlays().add(this.mLocationOverlay);


        /*
        //https://www.tabnine.com/code/java/methods/org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay/getMyLocation
        GeoPoint currentPoint = mMyLocationOverlay.getMyLocation(); //why dis error?
        //https://osmdroid.github.io/osmdroid/javadocAll/org/osmdroid/util/GeoPoint.html
        //1 latitude is about 111km
        //1 longitude is about 111km
        //set upper and lower bounds
        double latUpper=currentPoint.getLatitude()+0.9; //currentlat+ 10km
        double latLower=currentPoint.getLatitude()-0.9; //currentlat- 10km
        double lonUpper=currentPoint.getLongitude()+0.9;//currentlon+10km
        double lonLower=currentPoint.getLongitude()+0.9;//currentlon-10km




        //another way of making array of map markers, but my way is easier if it works because this is fucky with images
        //http://android-er.blogspot.com/2012/05/create-multi-marker-openstreetmap-for.html
        //OverlayItemArray = new ArrayList<OverlayItem>(); //create array of markers
        //for (int i=0; i<nearbyScans; i++){
        //	OverlayItemArray.add(new OverlayItem("0, 0", "0, 0",  point));
        //}


        //get all the nearby scans
        //https://firebase.google.com/docs/firestore/query-data/queries#java_1
        //https://cloud.google.com/firestore/docs/samples/firestore-query-filter-range-valid
        // Create a reference to the cities collection
        CollectionReference QRs = db.collection("GameQRs");
        // Create a query against the collection.
        //https://developer.android.com/reference/android/location/Location
        Query QRquery =QRs.where("location.getLatitude()<latUpper").where("getLatitude()>latLower").where("location.getLongitude()<lonUpper").where("location.getLongitude()>lonLower");

        // retrieve  query results asynchronously using query.get()
        ApiFuture<QuerySnapshot> querySnapshot = QRquery.get();

        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            nearbyScans.add(document);
        }



        //for each nearby scan, add a marker (this does not do images yet!)
        for (int i=0; i<(nearbyScans.size());i++) {
            //GeoPoint point = new GeoPoint(45.845557, 26.170010);
            GeoPoint point = new GeoPoint(nearbyScans.get(i).location.getLattitude, nearbyScans.get(i).location.getLongitude);//hope this works?
            Marker QRMarker = new Marker(map);
            QRMarker.setPosition(point);
            QRMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
            map.getOverlays().add(QRMarker);
        }
        //map.getController().setCenter(point);
    }
    */

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        // Inflate the layout for this fragment
        map = rootView.findViewById(R.id.mapview);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.getController().setZoom(18.0);

        requestPermissionsIfNecessary(new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.INTERNET
        });
        map.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.ALWAYS);
        map.setMultiTouchControls(true);


        CompassOverlay compassOverlay = new CompassOverlay(this.getContext(), map);
        compassOverlay.enableCompass();
        map.getOverlays().add(compassOverlay);
        return rootView;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            permissionsToRequest.add(permissions[i]);
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(this.getActivity(), permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    private void requestPermissionsIfNecessary(String[] permissions) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this.getActivity(), permission)
                    != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(permission);
            }
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this.getActivity(), permissionsToRequest.toArray(new String[0]), REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }
}