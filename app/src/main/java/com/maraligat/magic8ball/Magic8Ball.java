package com.maraligat.magic8ball;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;

public class Magic8Ball implements SensorEventListener{

    private Sensor accelerometer;
    private SensorManager sm;
    private long curTime, lastUpdate;
    private float x, y, z, last_x, last_y, last_z;

    private final static long UPDATEPERIOD = 300;
    private static final int SHAKE_THRESHOLD = 500;

    private final String [] predictions = {"Yes, definitely", "No way", "Let me think about that", "Maybe"};

    Context thisContext;

    public Magic8Ball(Context thisContext) {
        this.thisContext = thisContext;
        sm = (SensorManager)thisContext.getSystemService(thisContext.SENSOR_SERVICE);
        accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        curTime = lastUpdate = (long)0.0;
        x = y = z = last_x = last_y = last_z;
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        long curTime = System.currentTimeMillis();

        if ((curTime - lastUpdate) > UPDATEPERIOD){
            long diffTime = (curTime - lastUpdate);
            lastUpdate = curTime;

            x = sensorEvent.values[0];
            y = sensorEvent.values[1];
            z = sensorEvent.values[2];

            float speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000;

            if (speed > SHAKE_THRESHOLD){
                //Toast.makeText(thisContext, "Shake detected w/ speed: " + speed, Toast.LENGTH_SHORT).show();
                this.makePrediction();
            }

            last_x = x;
            last_y = y;
            last_z = z;
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public void makePrediction() {
            
    }

}
