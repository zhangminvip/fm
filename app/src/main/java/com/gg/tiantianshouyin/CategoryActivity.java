package com.gg.tiantianshouyin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;


import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gg.tiantianshouyin.appupdate.CheckUpdateUtils;
import com.gg.tiantianshouyin.appupdate.DownLoadApk;
import com.gg.tiantianshouyin.appupdate.bean.JsonsRootBean;

import java.util.List;

/**
 * Created by tom on 2017/7/4.
 */

public class CategoryActivity extends Activity implements View.OnClickListener{

    private AlertDialog.Builder mDialog;

    private String VersionCode = "3";

    private String AppName = "故事与英语";


    private String TAG = "CategoryActivity";


    private CardView btn_category_children;

    private CardView btn_category_english;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_category);
        initView();
        DetemineNetworkstatus();
        initEvent();
//        Intent intent = getIntent();


//        String music_name = intent.getStringExtra("music_name");
//        String music_type = intent.getStringExtra("music_type");
//        if(music_type.equals("1")){
//            Intent intent_seach = new Intent(this,SearchActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putString("music_name",music_name);
//            bundle.putString("music_type",music_type);
//            Log.d("音乐的类行",music_name+"   "+music_type);
//            intent_seach.putExtras(bundle);
//            startActivity(intent_seach);
//        }




            //网络检查版本是否需要更新
        CheckUpdateUtils.checkUpdate(AppName, VersionCode, new CheckUpdateUtils.CheckCallBack() {
            @Override
            public void onSuccess(JsonsRootBean updateInfo) {
                String isForce = updateInfo.getData().getForceupdate();//是否需要强制更新
                String downUrl = "http://192.168.124.26:8080/znsb" + updateInfo.getData().getFilepath();//apk下载地址
                String updateinfo = updateInfo.getData().getUpgradeinfo();//apk更新详情

                if (isForce.equals("yes")) {                              //强制更新     && !TextUtils.isEmpty(updateinfo)
                    Log.d(TAG, "强制升级:：：：" + isForce);
                    Log.d(TAG, "下载地址：：：" + downUrl);
                    Log.d(TAG, "跟新信息：：：" + updateinfo);
                    forceUpdate(CategoryActivity.this, AppName, downUrl, updateinfo);
                } else {//非强制更新
                    //正常升级
                    Log.d(TAG, "正常升级");
                    normalUpdate(CategoryActivity.this, AppName, downUrl, updateinfo);
                }
            }

            @Override
            public void onError() {
                Log.d(TAG,"onError");
                noneUpdate(CategoryActivity.this);
            }
        });



    }


    public void initView(){
//        btn_category_music = (Button)findViewById(R.id.btn_category_music);
        btn_category_children = (CardView) findViewById(R.id.btn_category_children);
//        btn_category_humanity = (Button)findViewById(R.id.btn_category_humanity);
        btn_category_english = (CardView) findViewById(R.id.btn_category_english);
    }

    public void initEvent(){
//        btn_category_music.setOnClickListener(this);
        btn_category_children.setOnClickListener(this);
//        btn_category_humanity.setOnClickListener(this);
        btn_category_english.setOnClickListener(this);
    }


    @Override
    public void onClick(View v){
        switch (v.getId()){
//            case R.id.btn_category_music:
//                String data_music = "music";
//                Intent intent_music = new Intent(CategoryActivity.this,TagActivity.class);
//                intent_music.putExtra("category",data_music);
//                startActivity(intent_music);
//                Log.d(TAG,""+data_music);
//                break;
            case R.id.btn_category_children:
                String data_children = "children";
                Intent intent_children = new Intent(CategoryActivity.this,TagActivity.class);
                intent_children.putExtra("category",data_children);
                startActivity(intent_children);
                break;
//            case R.id.btn_category_humanity:
//                String data_humanity = "humanity";
//                Intent intent_humanity = new Intent(CategoryActivity.this,TagActivity.class);
//                intent_humanity.putExtra("category",data_humanity);
//                startActivity(intent_humanity);
//                break;

            case R.id.btn_category_english:
                String data_english = "english";
                Intent intent_english = new Intent(CategoryActivity.this,TagActivity.class);
                intent_english.putExtra("category",data_english);
                startActivity(intent_english);
                break;
            default:
                break;
        }
    }


    /**
     * 判断网络状态
     *
     */
    private void DetemineNetworkstatus(){


        ConnectivityManager connectivityManager = (ConnectivityManager) CategoryActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        boolean iswificonn = networkInfo.isConnected();

        networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        boolean ismobileconn = networkInfo.isConnected();

        Log.d("网络",""+iswificonn+ismobileconn);



        if(!ismobileconn&&!iswificonn){
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try{
//                        makeCustomToast("请先联网再使用",Toast.LENGTH_LONG);
//                        Thread.sleep(1000);
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//
//                }
//            }).start();

//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//
//                        try{
//
//                            makeCustomToast("请先联网再使用",3000);
//                            Thread.sleep(1500);
//
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }
//                    }
//
//
//            });
            makeCustomToast("请先联网再使用", Toast.LENGTH_SHORT);

        }

    }


    /**
     * 设置自定义toast
     * @param text
     * @param duration
     */

    public void makeCustomToast(String text , int duration){
        View layout = getLayoutInflater().inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.custom_toast_layout_id));
        // set a message
        TextView toastText = (TextView) layout.findViewById(R.id.toasttext);
        toastText.setText(text);

        // Toast...
        Toast toast = new Toast(this);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.setDuration(duration);
        toast.setView(layout);
        toast.show();
    }



    /**
     * 强制更新
     *
     * @param context
     * @param appName
     * @param downUrl
     * @param updateinfo
     */
    private void forceUpdate(final Context context, final String appName, final String downUrl, final String updateinfo) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mDialog = new AlertDialog.Builder(context);
                mDialog.setTitle(appName + "又更新咯！");
                mDialog.setMessage(updateinfo);
                mDialog.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!canDownloadState()) {
                            showDownloadSetting();
                            return;
                        }

                        DownLoadApk.download(context, downUrl, updateinfo, appName);
//              AppInnerDownLoder.downLoadApk(MainActivity.this,downUrl,appName);

                    }
                }).setCancelable(false).create().show();
            }
        });

    }

    /**
     * 正常更新
     *
     * @param context
     * @param appName
     * @param downUrl
     * @param updateinfo
     */
    private void normalUpdate(final Context context, final String appName, final String downUrl, final String updateinfo) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mDialog = new AlertDialog.Builder(context);
                mDialog.setTitle(appName + "又更新咯！");
                mDialog.setMessage(updateinfo);
                mDialog.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!canDownloadState()) {
                            showDownloadSetting();
                            return;
                        }
                        // AppInnerDownLoder.downLoadApk(MainActivity.this,downUrl,appName);
                        DownLoadApk.download(MyApplication.getContext(), downUrl, updateinfo, appName);
                    }
                }).setNegativeButton("暂不更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setCancelable(false).show();
            }
        });

    }

    /**
     * 无需跟新
     *
     * @param context
     */
    private void noneUpdate(Context context) {
        mDialog = new AlertDialog.Builder(context);
        mDialog.setTitle("版本更新")
                .setMessage("当前已是最新版本无需更新")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(false).create().show();
    }

    private void showDownloadSetting() {
        String packageName = "com.android.providers.downloads";
        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + packageName));
        if (intentAvailable(intent)) {
            MyApplication.getContext().startActivity(intent);
        }
    }


    // 检查intent是否可用，防止应用闪退

    private boolean intentAvailable(Intent intent) {
        PackageManager packageManager = MyApplication.getContext().getPackageManager();
        List list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }


    private boolean canDownloadState() {
        try {
            int state = MyApplication.getContext().getPackageManager().getApplicationEnabledSetting("com.android.providers.downloads");

            if (state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED
                    || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER
                    || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED) {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


















    @Override
    protected void onResume(){
        DetemineNetworkstatus();
        super.onResume();

    }

}
