package com.rekrux.OpenDataReggioCalabria;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.rekrux.mysql_test_volley.R;

import java.util.ArrayList;

public class PopolatedMap extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, GoogleMap.OnInfoWindowClickListener{


    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    private GoogleMap mMap;
    private UiSettings mUiSettings;
    TextView textView;

    private Marker position0;
    private Marker position1;
    private Marker position2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popolated_maps);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        textView = (TextView) findViewById(R.id.jsonData);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        mMap.setOnInfoWindowClickListener(PopolatedMap.this);


        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

        mUiSettings = googleMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(true);
        mUiSettings.setCompassEnabled(true);

        //////
        Intent intent = getIntent();
        ArrayList<Double> arrayListLat = (ArrayList<Double>) getIntent().getSerializableExtra("LAT_LIST");
        ArrayList<Double> arrayListLon = (ArrayList<Double>) getIntent().getSerializableExtra("LON_LIST");

        double latutudine0 = arrayListLat.get(0);
        double longitudine0 = arrayListLon.get(0);
        String nome0 = String.valueOf(intent.getStringArrayListExtra("NOME_LIST").get(0));


        double latutudine1 = arrayListLat.get(1);
        double longitudine1 = arrayListLon.get(1);
        String nome1 = String.valueOf(intent.getStringArrayListExtra("NOME_LIST").get(1));

        double latutudine2 = arrayListLat.get(2);
        double longitudine2 = arrayListLon.get(2);
        String nome2 = String.valueOf(intent.getStringArrayListExtra("NOME_LIST").get(2));

        Polyline line = googleMap.addPolyline(new PolylineOptions()
        .add(new LatLng(latutudine0, longitudine0),
                new LatLng(latutudine1, longitudine1),
                new LatLng(latutudine2, longitudine2))
                        .width(5)
                        .color(Color.RED)
                        .geodesic(false)
           );


        textView.setText("- " + nome0 + " " + latutudine0 + " " + longitudine0 + "\n\n" +
                "- " + nome1 + " " + latutudine1 + " " + longitudine1 + "\n\n" +
                "- " + nome2 + " " + latutudine2 + " " + longitudine2 + "\n\n");


        //move map camera
        LatLng np = new LatLng(38.12, 15.70);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(np));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

        googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(11), 2000, null);

    }


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    /////// MY POSITION //////
    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        Intent intent = getIntent();
        ArrayList<Double> arrayListLat = (ArrayList<Double>) getIntent().getSerializableExtra("LAT_LIST");
        ArrayList<Double> arrayListLon = (ArrayList<Double>) getIntent().getSerializableExtra("LON_LIST");

        // the three palinas
        double latutudine0 = arrayListLat.get(0);
        double longitudine0 = arrayListLon.get(0);
        LatLng latLng0 = new LatLng(latutudine0, longitudine0);
        final String nome0 = String.valueOf(intent.getStringArrayListExtra("NOME_LIST").get(0));

        double latutudine1 = arrayListLat.get(1);
        double longitudine1 = arrayListLon.get(1);
        LatLng latLng1 = new LatLng(latutudine1, longitudine1);
        String nome1 = String.valueOf(intent.getStringArrayListExtra("NOME_LIST").get(1));

        double latutudine2 = arrayListLat.get(2);
        double longitudine2 = arrayListLon.get(2);
        LatLng latLng2 = new LatLng(latutudine2, longitudine2);
        String nome2 = String.valueOf(intent.getStringArrayListExtra("NOME_LIST").get(2));

        //my position
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        if (distanceTo(latLng, latLng0) < distanceTo(latLng, latLng1) &&
                distanceTo(latLng, latLng0) < distanceTo(latLng, latLng2)) {
          position0 =  mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(latutudine0, longitudine0))
                    .title(nome0)
                    .snippet("PIU' VICINA")
                    .icon(BitmapDescriptorFactory.defaultMarker
                            ((BitmapDescriptorFactory.HUE_GREEN))));

            position1 = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(latutudine1, longitudine1))
                    .title(nome1)
                    .icon(BitmapDescriptorFactory.defaultMarker
                            ((BitmapDescriptorFactory.HUE_RED))));

            position2 =mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(latutudine2, longitudine2))
                    .title(nome2)
                    .icon(BitmapDescriptorFactory.defaultMarker
                            ((BitmapDescriptorFactory.HUE_RED))));

            Polyline near_you0 = mMap.addPolyline(new PolylineOptions()
                    .add(new LatLng(location.getLatitude(), location.getLongitude()),
                            new LatLng(latutudine0, longitudine0))
                    .width(5)
                    .color(Color.GREEN)
                    .geodesic(false)
            );

        } else if (distanceTo(latLng, latLng1) < distanceTo(latLng, latLng0) &&
                distanceTo(latLng, latLng1) < distanceTo(latLng, latLng2)) {
            position1 = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(latutudine1, longitudine1))
                    .title(nome1)
                    .snippet("PIU' VICINA")
                    .icon(BitmapDescriptorFactory.defaultMarker
                            ((BitmapDescriptorFactory.HUE_GREEN))));

            position0 =  mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(latutudine0, longitudine0))
                    .title(nome0)
                    .icon(BitmapDescriptorFactory.defaultMarker
                            ((BitmapDescriptorFactory.HUE_RED))));

            position2 = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(latutudine2, longitudine2))
                    .title(nome2)
                    .icon(BitmapDescriptorFactory.defaultMarker
                            ((BitmapDescriptorFactory.HUE_RED))));

            Polyline near_you1 = mMap.addPolyline(new PolylineOptions()
                    .add(new LatLng(location.getLatitude(), location.getLongitude()),
                            new LatLng(latutudine1, longitudine1))
                    .width(5)
                    .color(Color.GREEN)
                    .geodesic(false)
            );

        } else if (distanceTo(latLng, latLng2) < distanceTo(latLng, latLng0) &&
                distanceTo(latLng, latLng2) < distanceTo(latLng, latLng1)) {
            position2 = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(latutudine2, longitudine2))
                    .title(nome2)
                    .snippet("PIU' VICINA")
                    .icon(BitmapDescriptorFactory.defaultMarker
                            ((BitmapDescriptorFactory.HUE_GREEN))));

            position0 = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(latutudine0, longitudine0))
                    .title(nome0)
                    .icon(BitmapDescriptorFactory.defaultMarker
                            ((BitmapDescriptorFactory.HUE_RED))));

            position1 = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(latutudine1, longitudine1))
                    .title(nome1)
                    .icon(BitmapDescriptorFactory.defaultMarker
                            ((BitmapDescriptorFactory.HUE_RED))));

            Polyline near_you = mMap.addPolyline(new PolylineOptions()
                    .add(new LatLng(location.getLatitude(), location.getLongitude()),
                            new LatLng(latutudine2, longitudine2))
                    .width(5)
                    .color(Color.GREEN)
                    .geodesic(false)
            );

        }


        //Place current location marker
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("MY POSITION");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        //mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

    }

    /** Called when the user clicks a marker. */
    public void  onInfoWindowClick(final Marker marker) {

        Intent intent = new Intent(getBaseContext(), MarkerActivity.class);
        intent.putExtra("INFOMARKER", marker.getTitle());
        startActivity(intent);

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }

    public static double distanceTo(LatLng from, LatLng to) {
        Location fuck_location = new Location("");
        fuck_location.setLatitude(from.latitude);
        fuck_location.setLongitude(from.longitude);
        Location no_fuck_location = new Location("");
        no_fuck_location.setLatitude(to.latitude);
        no_fuck_location.setLongitude(to.longitude);

        return fuck_location.distanceTo(no_fuck_location) / 1000;
    }

    public void go_back(View v){
        Intent intent = new Intent(PopolatedMap.this, MainActivity.class);
        startActivity(intent);}


}

