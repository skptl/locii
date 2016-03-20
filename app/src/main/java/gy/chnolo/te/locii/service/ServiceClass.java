package gy.chnolo.te.locii.service;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Created by Shilpan Patel on 3/9/16.
 */
public class ServiceClass extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {


    protected Location mLocation;
    protected IBinder iBinder = new BinderService();
    protected GoogleApiClient mGoogleApiClient;
    protected double latitude, longitude;
    protected LocationRequest locationRequest = LocationRequest.create();


    public synchronized void buildGoogleApiClient() {


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }


    @Override
    public void onCreate() {
        Log.i("create", "Service_created");
        super.onCreate();
        buildGoogleApiClient();
        mGoogleApiClient.connect();
        setLocationRequests();
    }

    public void setLocationRequests() {

        locationRequest.setFastestInterval(1000);
        locationRequest.setInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        Log.i("bind", "activityBind");
        return iBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {

        Log.i("unBind","Activity_unbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onConnected(Bundle bundle) {


        Log.i("onconnect", "connect_api");

                mLocation=LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLocation==null) {
        Log.i("Location","returning Null");
        }
                longitude=mLocation.getLongitude();
                latitude=mLocation.getLatitude();
                Log.i("get_Location",mLocation.toString());
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);
                Log.i("requesting", "request");
                Log.i("null", "location_null");

            }



    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        mGoogleApiClient.disconnect();

        //nothing to do here
    }

    @Override
    public void onLocationChanged(Location location) {

        Log.i("change",location.toString());
    }


    public class BinderService extends Binder {

        ServiceClass locationService() {

            return ServiceClass.this;
        }
    }

    @Override
    public void onDestroy() {
        Log.i("destroy","Service_destroy");
        super.onDestroy();
    }
}

