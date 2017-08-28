package com.gg.tiantianshouyin;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.gg.tiantianshouyin.function.MusicPlayOperater;
import com.gg.tiantianshouyin.function.MusicPlayerListener;
import com.gg.tiantianshouyin.qqmusic.SearchResult;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.gg.tiantianshouyin.qqmusic.SearchActivity.music_name;

public class SearchPlayService extends Service {
    private static final String TAG = "searchplayservice";
    public static MediaPlayer mp;
    public static MusicPlayOperater sMusicPlayOperater;
    private IntentFilter mIntentFilter;

    private UIReceiver mUIReceiver;

    public SearchPlayService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "onCreate");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mp = new MediaPlayer();                                        //不能写在mp定义的地方  也就是上面   不然seachsong会用到mp,如果写在上面，第二次启动service，会找不到mp.因为不会实例化，写在上面执行不到。

        sMusicPlayOperater = new MusicPlayOperater();
        sMusicPlayOperater.setListener(new MusicPlayerListener() {
            @Override
            public void onPlayStart() {

            }

            @Override
            public void onPlayPause() {

            }
        });



        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("com.gg.tiantianshouyin.SearchService");
        mUIReceiver = new UIReceiver();
        registerReceiver(mUIReceiver, mIntentFilter);
        Log.d(TAG, "执行完广播接收器的注册");
        searchSong();
        Log.d(TAG, "searchsong执行完毕");
        return super.onStartCommand(intent, flags, startId);
    }


    /**
     * 搜索歌曲
     */
    private void searchSong() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d(TAG, "进入searchsong方法");
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://s.music.qq.com/fcgi-bin/music_search_new_platform?t=0&n=1&aggr=1&cr=1&loginUin=0&format=json&inCharset=GB2312&outCharset=utf-8&notice=0&platform=jqminiframe.json&needNewCode=0&p=1&catZhida=0&remoteplace=sizer.newclient.next_song&w=" + music_name)
                            .build();
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {


                        String responseData = response.body().string();
                        Log.d(TAG, "1");
                        // 播放歌曲
                        mp.setDataSource("http://ws.stream.qqmusic.qq.com/" + getSongId(responseData) + ".m4a?fromtag=46");
                        Log.d(TAG, "2");
                        mp.prepare();
                        Log.d(TAG, "3");
                        mp.start();
                        Intent intent = new Intent("com.gg.tiantianshouyin.SearchActivity");
                        intent.putExtra("todo", "toast");
                        sendBroadcast(intent);
                        Log.d(TAG, "播放开始");
                    } else {

                        Intent intent = new Intent("com.gg.tiantianshouyin.SearchActivity");
                        intent.putExtra("todo", "showfail");
                        sendBroadcast(intent);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }


    /**
     * 获取歌曲的id
     *
     * @param jsonData
     * @return
     */
    private String getSongId(String jsonData) {
        Gson gson = new Gson();
        SearchResult search = gson.fromJson(jsonData, SearchResult.class);
        String[] params = search.getData().getSong().getList().get(0).getF().split("\\|");
//        for (String s : params) {
//            Log.d("json", s);
//        }
        String id = params[0];
        Log.d("json", params[1] + "id: " + id);
        return id;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    // 广播接收器
    public class UIReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

        }

    }


    @Override
    public void onDestroy() {
        mp.release();
        unregisterReceiver(mUIReceiver);
        super.onDestroy();
        Log.d(TAG, "onDestory");
    }




}