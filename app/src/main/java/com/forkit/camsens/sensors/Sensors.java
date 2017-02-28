package com.forkit.camsens.sensors;

/**
 * Created by naman on 28/2/17.
 */

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

public class Sensors {
    private LocationManager locationManager;
    private LocationListener locationListener;
    private SensorManager accSensorManager, laccSensorManager, comSensorManager, gyrSensorManager, lightSensorManager;
    private SensorEventListener accSensorEventListener, laccSensorEventListener, comSensorEventListener, gyrSensorEventListener, lightSensorEventListener;
    private Sensor accSensor, laccSensor, comSensor, gyrSensor, lightSensor;
    private boolean lightStarted = false, gpsStarted = false, accStarted = false, laccStarted = false, comStarted = false, gyrStarted = false;
    PowerManager powerManager;
    PowerManager.WakeLock wakeLock;
    Context context;

    public Sensors(Context x) {
        this.context = x;
        powerManager = (PowerManager) x.getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "LoggerWakeLock");
        locationManager = (LocationManager) x.getSystemService(Context.LOCATION_SERVICE);
        gpsRecord();
        accRecord();
        laccRecord();
        compassRecord();
        gyrRecord();
    }

    private void gpsRecord() {
        locationManager = (LocationManager) this.context.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double altitude = location.getAltitude();
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                double speed = location.getSpeed();
                Log.v("GPS", "Lat " + latitude + " Long " + longitude + " alt " + altitude + " speed " + speed);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                //Toast.makeText(this,"GPS is disabled",Toast.LENGTH_SHORT);
            }
        };
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
        gpsStarted=true;
    }

    private void accRecord(){
        accSensorManager=(SensorManager) this.context.getSystemService(Context.SENSOR_SERVICE);
        accSensor=accSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        accSensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x=event.values[0];
                float y=event.values[1];
                float z=event.values[2];
                Log.v("Acc","x: "+x+" y: "+y+" z: "+z);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        accSensorManager.registerListener(accSensorEventListener, accSensor, SensorManager.SENSOR_DELAY_NORMAL);
        accStarted=true;
    }

    private void laccRecord(){
        laccSensorManager=(SensorManager) this.context.getSystemService(Context.SENSOR_SERVICE);
        laccSensor=laccSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        laccSensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x=event.values[0];
                float y=event.values[1];
                float z=event.values[2];
                Log.v("Lacc","x: "+x+" y: "+y+" z: "+z);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        laccSensorManager.registerListener(laccSensorEventListener, laccSensor, SensorManager.SENSOR_DELAY_NORMAL);
        laccStarted=true;
    }

    private void compassRecord(){
        comSensorManager=(SensorManager) this.context.getSystemService(Context.SENSOR_SERVICE);
        comSensor=comSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        comSensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x=event.values[0];
                float y=event.values[1];
                float z=event.values[2];
                Log.v("Compass","x: "+x+" y: "+y+" z: "+z);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        comSensorManager.registerListener(comSensorEventListener, comSensor, SensorManager.SENSOR_DELAY_NORMAL);
        comStarted=true;
    }

    private void gyrRecord(){
        gyrSensorManager=(SensorManager) this.context.getSystemService(Context.SENSOR_SERVICE);
        gyrSensor=gyrSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        gyrSensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x=event.values[0];
                float y=event.values[1];
                float z=event.values[2];
                Log.v("Gyr","x: "+x+" y: "+y+" z: "+z);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        gyrSensorManager.registerListener(gyrSensorEventListener, gyrSensor, SensorManager.SENSOR_DELAY_NORMAL);
        gyrStarted=true;
    }

    public void stopAll(){
        if(gpsStarted){
            locationManager.removeUpdates(locationListener);
        }
        if(accStarted){
            accSensorManager.unregisterListener(accSensorEventListener);
        }
        if(laccStarted){
            laccSensorManager.unregisterListener(laccSensorEventListener);
        }
        if(comStarted){
            comSensorManager.unregisterListener(comSensorEventListener);
        }
        if(gyrStarted){
            gyrSensorManager.unregisterListener(gyrSensorEventListener);
        }

    }

}
