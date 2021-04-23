package com.dongwanghan.mapscov;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dongwanghan.mapscov.map.MapsActivity;
import com.dongwanghan.mapscov.view.ArcMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener {
    private Context context;
    private ArcMenu mArcMenu;
    private GridView mGridView;
    private SimpleAdapter mSimpleAdapter;
    private List<Map<String,Object>> dataList;
    private int[] image = {R.drawable.ic_launcher_foreground };
    private String[] text = {"地图定位"};
    private final int SDK_PERMISSION_REQUEST = 127;
    private String permissionInfo;

    /**
     * Handler 消息传递机制
     *
     */
    Handler handler = new Handler(){
        /**
         * Handler中的处理消息方法
         */
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                /**
                 * 地图                 *
                 */
                case 1:
                    Intent intent_to_map = new Intent(context, MapsActivity.class);
                    startActivity(intent_to_map);

                    break;
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = this;
        initGredView();
        //判断联网
        inNetworkConnectting();
        //判断权限
        getPersimmions();
    }
    @TargetApi(23)
    private void getPersimmions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<String>();
            /***
             * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
             */
            // 定位精确位置
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            /*
             * 读写权限和电话状态权限非必要权限(建议授予)只会申请一次，用户同意或者禁止，只会弹一次
             */
            // 读写权限
            if (addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissionInfo += "Manifest.permission.WRITE_EXTERNAL_STORAGE Deny \n";
            }
            if (permissions.size() > 0) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
            }
        }
    }

    @TargetApi(23)
    private boolean addPermission(ArrayList<String> permissionsList, String permission) {
        // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(permission)) {
                return true;
            } else {
                permissionsList.add(permission);
                return false;
            }
        } else {
            return true;
        }
    }
    /**
     *
     * 手势
     */
//    private void initGesture(){
//        final GestureLibrary gestureLibrary = GestureLibraries.fromRawResource(context,R.raw.gestures);
//    }
    private void inNetworkConnectting(){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        //判断联网
        if (networkInfo != null){
            //不做任何操作
        }
        // 未联网，弹出对话框进行提醒；
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("网络状态");
            builder.setMessage("您当前未联网，请连接网络。");

            builder.setPositiveButton("开启WIFI", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    WifiManager wifiManager = (WifiManager) context
                            .getSystemService(WIFI_SERVICE);
                    wifiManager.setWifiEnabled(true);
                    Toast.makeText(context, "已经为您开启WIFI", Toast.LENGTH_SHORT).show();
                }
                }
            );
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

    }
    //判断gps权限开启,动态权限申请
//    private void inGPSTest() {
//        // 要申请的权限 数组 可以同时申请多个权限
//        String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION};
//
//        if (Build.VERSION.SDK_INT >= 23) {
//            //如果超过6.0才需要动态权限，否则不需要动态权限
//            //如果同时申请多个权限，可以for循环遍历
//            int check = ContextCompat.checkSelfPermission(this, permissions[0]);
//            if (check == PackageManager.PERMISSION_GRANTED) {
//                //写入你需要权限才能使用的方法
//
//            } else {
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setMessage("您当前未打开GPS，请打开GPS权限");
//                builder.setPositiveButton("打开GPS", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                })
//                //手动去请求用户打开权限(可以在数组中添加多个权限) 1 为请求码 一般设置为final静态变量
//                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
//            }
//        }
//    }
    /**
     * 网格布局java配置
     *
     */
    private void initGredView(){
        mGridView = (GridView) findViewById(R.id.id_gridview);
        mGridView.setOnItemClickListener(this);
        dataList = new ArrayList<Map<String, Object>>();
        mSimpleAdapter = new SimpleAdapter(context,getData(),
                R.layout.gridview_item, new String[] { "image", "text" },
                new int[] {R.id.id_pic, R.id.id_text });
        mGridView.setAdapter(mSimpleAdapter);

    }
    /**
     * Hashmap 进行网格布局的匹配
     * datalist存储
     */
    private List<Map<String,Object>> getData(){
        for (int i = 0; i<image.length;i++){
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("image",image[i]);
            map.put("text",text[i]);
            dataList.add(map);
        }
        return dataList;
    }
//    private void initEvent(){
//        mArcMenu.serOnMenuItemClickListener(new ArcMenu.OnMenuItemClickListener() {
//            @Override
//            public void onClick(View view, int pos) {
//                Toast.makeText(MainActivity.this, pos + ":" + view.getTag(), Toast.LENGTH_SHORT).show();
//                final Message message = new Message();
//                switch (pos){
//                    case 1:
//                        new Thread(){
//                            @Override
//                            public void run() {
//                                try {
//                                    Thread.sleep(1000);
//                                    message.what = 1;
//                                    handler.sendMessage(message);
//                                }catch (InterruptedException e){
//                                    e.printStackTrace();
//                                }
//
//                            }
//                        }.start();
//
//                        break;
//                }
//            }
//        });
//    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        switch (position){
            case 0:
                Intent intent_map = new Intent(context,MapsActivity.class);
                startActivity(intent_map);
                //overridePendingTransition(R);

                break;
        }
    }
}
