package com.forkit.camsens;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.forkit.camsens.sensors.Sensors;

public class MainActivity extends AppCompatActivity {
    Sensors s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        s = new Sensors(this.getApplicationContext());
    }
    @Override
    protected  void onDestroy(){
        s.stopAll();
        super.onDestroy();
        getDelegate().onDestroy();
    }

}
