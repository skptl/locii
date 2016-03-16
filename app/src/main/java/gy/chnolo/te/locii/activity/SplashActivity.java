package gy.chnolo.te.locii.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import gy.chnolo.te.locii.R;
import gy.chnolo.te.locii.service.LocationClass;

/**
 * Created by Shilpan Patel on 3/16/16.
 */
public class SplashActivity extends Activity{

    protected static int MAX_TIMEOUT = 5000 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this,LocationClass.class);
                startActivity(i);
                finish();
            }
        },MAX_TIMEOUT);



    }
}
