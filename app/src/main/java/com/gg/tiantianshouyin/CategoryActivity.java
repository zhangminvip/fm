package com.gg.tiantianshouyin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by tom on 2017/7/4.
 */

public class CategoryActivity extends Activity implements View.OnClickListener{

    private String TAG = "CategoryActivity";

    private Button btn_category_music;
    private Button btn_category_children;
    private Button btn_category_humanity;
    private Button btn_category_english;

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





    }


    public void initView(){
//        btn_category_music = (Button)findViewById(R.id.btn_category_music);
        btn_category_children = (Button)findViewById(R.id.btn_category_children);
        btn_category_humanity = (Button)findViewById(R.id.btn_category_humanity);
        btn_category_english = (Button)findViewById(R.id.btn_category_english);
    }

    public void initEvent(){
//        btn_category_music.setOnClickListener(this);
        btn_category_children.setOnClickListener(this);
        btn_category_humanity.setOnClickListener(this);
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
            case R.id.btn_category_humanity:
                String data_humanity = "humanity";
                Intent intent_humanity = new Intent(CategoryActivity.this,TagActivity.class);
                intent_humanity.putExtra("category",data_humanity);
                startActivity(intent_humanity);
                break;

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

    @Override
    protected void onResume(){
        DetemineNetworkstatus();
        super.onResume();

    }

}
