package com.dongwanghan.mapscov;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.baidu.mapapi.map.MapView;

public class MapsActivity extends Activity {
    private MapView mMapView = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);//加载地图界面
        initView();
    }
    public void initView(){
        mMapView = (MapView) findViewById(R.id.bmapView);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
