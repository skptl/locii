package gy.chnolo.te.locii.service;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import gy.chnolo.te.locii.R;

public class LocationClass extends Activity {

    protected TextView lat_text;
    protected TextView long_text;

     protected Button button;
    protected Intent i;
    protected double latitude,longitude;
    ServiceClass locationService = new ServiceClass();

    //protected Location location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        lat_text = (TextView) findViewById(R.id.latitude);
        long_text = (TextView) findViewById(R.id.longitude);
        button=(Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCoordinates();
                updateLocationUI(longitude, latitude);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent i = new Intent(this,ServiceClass.class);
        bindService(i, mServiceConnection, Context.BIND_AUTO_CREATE);
        startService(i);
    }

    protected ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            Log.i("connected","service_connected");
            ServiceClass.BinderService binderService = (ServiceClass.BinderService) service;
            locationService = binderService.locationService();


        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(getApplicationContext(),"Service Disconnected",Toast.LENGTH_LONG).show();

        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(mServiceConnection);
    }

    public void getCoordinates() {

        latitude = locationService.latitude;
        Log.i("lat",String.valueOf(latitude));
        longitude = locationService.longitude;

    }

    public void updateLocationUI (double a,double b) {

        lat_text.setText("Latitude :" +b);
        long_text.setText("Longitude :" +a);

    }



}