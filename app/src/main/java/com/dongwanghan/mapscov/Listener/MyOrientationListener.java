package com.dongwanghan.mapscov.Listener;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * @Description: TODO 方向传感器
 * @author: youyu
 * @date: 2021年04月19日 16:46
 */
public class MyOrientationListener implements SensorEventListener {
    private SensorManager mSensorManager;//传感器
    private Context mContext;
    private Sensor mSensor; //传感器

    private float lasrX;

    public MyOrientationListener(Context context){
        this.mContext=context;
    }
    public void start(){
       mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
       if(mSensorManager!=null) {
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
       }
       if (mSensor !=null){
           mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_UI);
       }
    }

    public void stop(){
        mSensorManager.unregisterListener(this);
    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float x = sensorEvent.values[0];
        if (Math.abs(x-lasrX) > 1.0){
            if (mOnOrientationListener !=null){
                mOnOrientationListener.onOrientationChanged(x);
            }
        }
    }
    private OnOrientationListener mOnOrientationListener;
    public void setOnOrientationListener(
            OnOrientationListener mOnOrientationListener) {
        this.mOnOrientationListener = mOnOrientationListener;
    }

    public interface OnOrientationListener{
        void onOrientationChanged(float x);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
