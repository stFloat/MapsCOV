package com.dongwanghan.mapscov;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.dongwanghan.mapscov.Listener.MyOrientationListener;

import static android.content.ContentValues.TAG;


public class MapsActivity extends Activity {
    private MapView mMapView; // 显示地图
    private BaiduMap mBaiduMap; // 百度地图

    private Context context;

    //定位相关
    private LocationClient mLocationClient;
    private MyLocationListener mMyLocationListener;
    private boolean isFristIn = true;//首次定位
    private double mLatitude;
    private double mLongitude;

    //图标
    private BitmapDescriptor mIconLocation;
    private MyOrientationListener myOrientationListener;
    private float mCurrentX;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_map);//加载地图界面
        this.context = this;
//        mBaiduMap = mMapView.getMap();
        //地图显示方法调用
        initView();
        //定位初始化方法调用
        initLocation();
    }
    //地图显示
    private void initView(){
        //获取地图
        mMapView = (MapView) findViewById(R.id.bmapView);

        mBaiduMap = mMapView.getMap();//将定位图层带入mBaiduMap
//        MapStatusUpdate after = MapStatusUpdateFactory.zoomTo(15.0f);
//        mBaiduMap.setMapStatus(after);
    }

    private void  initLocation(){
        //mLocationMode = MyLocationConfiguration.LocationMode.NORMAL;

        //定位初始化
        mLocationClient = new LocationClient(getApplicationContext());
        //注册 LocationListener监听器
        mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);


        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//高精度模式
        option.setOpenGps(true);//打开gps
        option.setCoorType("bd0911");//设置坐标类型
        option.setScanSpan(1000);//定位一秒请求一次
        //设置locationClientOption
        mLocationClient.setLocOption(option);

        //初始化图标
      //  mIconLocation = BitmapDescriptorFactory.fromResource(R.drawable.)

    }
    //定位到我的位置
    private void CenterToMyLocation(){
        LatLng latLng = new LatLng(mLatitude, mLongitude);
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(latLng);
        mBaiduMap.animateMapStatus(mapStatusUpdate);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //开启地图定位图层
        mBaiduMap.setMyLocationEnabled(true);
        if(!mLocationClient.isStarted()){
            mLocationClient.start();
        }
        CenterToMyLocation();
        //myOrientationListener.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //停止定位
        mLocationClient.stop();
        mBaiduMap.setMyLocationEnabled(false);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        mBaiduMap = null;


    }
    /**
     * 实现定位监听器BDLocationListener，重写onReceiveLocation方法；
     *
     */
    public class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //mapView 销毁后不在处理新接收的位置
//            if (location == null || mMapView == null){
//                return;
//            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection()).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);

            //更新经纬度
            mLatitude = location.getLatitude();
            mLongitude = location.getLongitude();

            String coorType = location.getCoorType();
            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准

            int errorCode = location.getLocType();
            //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明

            //如果是第一次创建，就获取位置信息并且将地图移到当前位置
            //为防止地图被反复移动，所以就只在第一次创建时执行
            if(isFristIn){
                //LatLng对象主要用来存放经纬度
                //zoomTo是用来设置百度地图的缩放级别，范围为3~19，数值越大越精确
                LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
                MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(latLng);
                mBaiduMap.animateMapStatus(mapStatusUpdate);
                isFristIn = false;
                Toast.makeText(context, location.getAddrStr(),Toast.LENGTH_SHORT).show();

                Log.i(TAG, location.getAddrStr() + "\n" + location.getAltitude()
                        + "" + "\n" + location.getCity() + "\n"
                        + location.getCityCode() + "\n"
                        + location.getCoorType() + "\n"
                        + location.getDirection() + "\n"
                        + location.getDistrict() + "\n"
                        + location.getFloor() + "\n"
                        + location.getLatitude() + "\n"
                        + location.getLongitude() + "\n"
                        + location.getNetworkLocationType() + "\n"
                        + location.getProvince() + "\n"
                        + location.getSatelliteNumber() + "\n"
                        + location.getStreet() + "\n"
                        + location.getStreetNumber() + "\n"
                        + location.getTime() + "\n");

                //弹出对话框显示定位信息
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("为您获得的定位信息：");
                builder.setMessage("当前位置：" + location.getAddrStr() + "\n"
                        + "城市编号：" + location.getCityCode() + "\n" + "定位时间："
                        + location.getTime() + "\n" + "当前纬度："
                        + location.getLatitude() + "\n" + "当前经度："
                        + location.getLongitude());
                builder.setPositiveButton("确定", null);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        }

    }
}
