package com.gg.tiantianshouyin.qqmusic;

/**
 * Created by tom on 2017/7/18.
 */

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gg.tiantianshouyin.MyApplication;
import com.gg.tiantianshouyin.R;
import com.gg.tiantianshouyin.SearchPlayService;
import com.gg.tiantianshouyin.function.FastBlurUtil;

import static com.gg.tiantianshouyin.SearchPlayService.mp;


public class SearchActivity  extends Activity implements View.OnClickListener {
    private static final String TAG = "searchactivity";

    public static String music_name = "宠爱";
    private Button play;
    private Button pause;
    private Button preview;
    private Button next;
    private TextView title;
    private LinearLayout layout;

    private MediaPlayer mPlayer ;

    private IntentFilter mIntentFilter;
    private ServiceReceiver mServiceReceiver;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_qq);


        // 模糊背景
        layout = (LinearLayout)this.findViewById(R.id.mainactivity_qq);
        setBackground(R.drawable.background);




        Intent intent = getIntent();
        music_name = intent.getStringExtra("music_name");

        initView();
        title.setText(music_name);
        title.requestFocus();
        initEvent();
        Intent playintent = new Intent(this, SearchPlayService.class);
        startService(playintent);

        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("com.gg.tiantianshouyin.SearchActivity");
        mServiceReceiver = new ServiceReceiver();
        registerReceiver(mServiceReceiver,mIntentFilter);
        Log.d(TAG,"onCreate");
//        searchSong();
    }


    /**
     * 设置毛玻璃背景
     */
    @SuppressWarnings("deprecation")
    private void setBackground(int id){


        Bitmap originbmp = null;

        originbmp = BitmapFactory.decodeResource(getResources(),id);

        Bitmap thumbnailBitmap = Bitmap.createScaledBitmap(originbmp,originbmp.getWidth()/10,originbmp.getHeight()/10,false);

        final Bitmap blurBmp = FastBlurUtil.doBlur(thumbnailBitmap,8,false);
        final Drawable newBitmapDrawable = new BitmapDrawable(blurBmp);

        layout.post(new Runnable() {
            @Override
            public void run() {
                layout.setBackground(newBitmapDrawable);
            }
        });
    }



    private void initView() {
        play = (Button) findViewById(R.id.play_qq);
        pause = (Button) findViewById(R.id.pause_qq);
//        next = (Button) findViewById(R.id.next_qq);
//        preview = (Button) findViewById(R.id.previous_qq);
        title = (TextView)findViewById(R.id.music_title_qq);
    }

    private void initEvent() {
        play.setOnClickListener(this);
        pause.setOnClickListener(this);
//        next.setOnClickListener(this);
//        preview.setOnClickListener(this);

    }




    public void makeCustomToast( final  String text , final int duration){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                View layout = getLayoutInflater().inflate(R.layout.custom_toast,
                        (ViewGroup) findViewById(R.id.custom_toast_layout_id));
                // set a message
                TextView toastText = (TextView) layout.findViewById(R.id.toasttext);
                toastText.setText(text);

                // Toast...
                Toast toast = new Toast(MyApplication.getContext());
                toast.setGravity(Gravity.BOTTOM, 0, 0);
                toast.setDuration(duration);
                toast.setView(layout);
                toast.show();
            }
        });

    }


    /**
     * 在子线程中发起Toast提醒
     * @param msg 提醒内容
     */
    private void remind(final String msg) {
        runOnUiThread(new Thread(){
            @Override
            public void run() {
                Toast.makeText(SearchActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }




    int i = 0;

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.play_qq:
                mPlayer = mp;            // 这个很重要,mp每次会变化，所以MianActivity的mPlayer也要用时附最新的值。
                mPlayer.start();
                checkPlayerStatus();
                break;
            case R.id.pause_qq:
                i = 1;
                mPlayer = mp;
                mPlayer.pause();
                checkPlayerStatus();
                break;
            default:
                break;
        }

    }

    public void checkPlayerStatus() {
        if (mPlayer.isPlaying() == true) {
            play.setVisibility(View.INVISIBLE);
            pause.setVisibility(View.VISIBLE);

            Log.d(TAG, "");
        } else if (mPlayer.isPlaying() == false) {
            play.setVisibility(View.VISIBLE);
            pause.setVisibility(View.INVISIBLE);
            Log.d(TAG, "");
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();


        Intent intent = getIntent();
        music_name = intent.getStringExtra("music_name");
        Log.d(TAG," music_name::::" +music_name);
//
//
//
//        title.setText(music_name);
//        title.requestFocus();
//
//        Intent playintent = new Intent(this, SearchPlayService.class);
//        startService(playintent);

//        searchSong();

        Log.d(TAG,"onRestart");

    }


    @Override
    protected void onStart(){
        super.onStart();
        Log.d(TAG,"onStart");
    }



    @Override
    protected void onResume() {

        super.onResume();






        Log.d(TAG,"onResume,music_name="+music_name);
    }



    @Override
    protected void onStop(){

        super.onStop();
//        Log.d("生命周期","onStop");
//        if (mp != null) {
//            mp.stop();
//            mp.release();
//        }
//        finish();    // 在此处调用onDestroy并不能finish掉activity，或许onDestroy只是一个回调，处理一些返回键的事情，点桌面键必须手动finish()....

    }




    public  class ServiceReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent){
            String result = intent.getStringExtra("todo");
            Log.d(TAG,"接收到广播，result = "+result);
            if(result.equals("toast")){
                Log.d(TAG,"result == toast");
                makeCustomToast("正在为您播放歌曲"+music_name,Toast.LENGTH_SHORT);
                Log.d(TAG,"执行完maketoast");
            }

            if(result.equals("showfail")){
                makeCustomToast("播放失败",Toast.LENGTH_SHORT);
            }


        }

    }


    protected void onDestroy() {

        super.onDestroy();

        Intent intent = new Intent(this,SearchPlayService.class);
        stopService(intent);
        unregisterReceiver(mServiceReceiver);
        Log.d(TAG,"onDestory");
    }
}
