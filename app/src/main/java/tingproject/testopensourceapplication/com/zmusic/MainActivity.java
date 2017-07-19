package tingproject.testopensourceapplication.com.zmusic;

import android.app.Activity;
import android.content.Intent;
import android.icu.text.LocaleDisplayNames;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.PlayableModel;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.album.AlbumList;
import com.ximalaya.ting.android.opensdk.model.category.Category;
import com.ximalaya.ting.android.opensdk.model.category.CategoryList;
import com.ximalaya.ting.android.opensdk.model.tag.Tag;
import com.ximalaya.ting.android.opensdk.model.tag.TagList;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.model.track.TrackList;
import com.ximalaya.ting.android.opensdk.player.XmPlayerManager;
import com.ximalaya.ting.android.opensdk.player.service.IXmPlayerStatusListener;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayerException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ximalaya.ting.android.opensdk.player.constants.PlayerConstants.STATE_PAUSED;
import static com.ximalaya.ting.android.opensdk.player.constants.PlayerConstants.STATE_STARTED;

public class MainActivity extends Activity {

    private Button btn_pause;
    private Button btn_play;

    private TextView title;

    private int data;

    private static final String TAG = "zhangmin";
    private static final String APP_SECRET = "2d5e40f87904d6cb292b6388e82680a7";

    public List<Category> mCategoryList = new ArrayList<Category>();      //类别列表  有声书 、音乐、娱乐。。。。。
    public List<Tag> mTagList = new ArrayList<Tag>();                      //类别下的标签列表    悬疑、 言情  幻想  等
    public List<Album> mAlbumList = new ArrayList<Album>();              //标签后专辑
    public List<Track> mTrackList = new ArrayList<Track>();               //专辑下的声音

//    private Category mCategory;


    private XmPlayerManager mPlayerManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        initView();
        Intent intent = getIntent();
        data = intent.getIntExtra("position",0);
        Log.d("","标题栏"+TrackActivity.mTrackList.get(data).getTrackTitle()+"data:" +data);
        title.setText(TrackActivity.mTrackList.get(data).getTrackTitle());
        title.requestFocus();



        mPlayerManager = XmPlayerManager.getInstance(this);
        mPlayerManager.init();
        mPlayerManager.playList(TrackActivity.mTrackList,data);
        mPlayerManager.play();
        checkPlayerStatus();
        initEvent();


        // sdk初始化
//        CommonRequest.getInstanse().init(this, APP_SECRET);

        /**
         * 获取分类列表
         */

//        CommonRequest.getCategories(null, new IDataCallBack<CategoryList>() {
//            @Override
//            public void onSuccess(CategoryList list) {
//                mCategoryList = list.getCategories();
//                Log.d(TAG, "*****************所有分类列表**************");
//                for (Category c : mCategoryList) {
//                    Log.d(TAG, "CategoryName: " + c.getCategoryName() + ",Id: " + c.getId());
//                }
//            }
//
//            @Override
//            public void onError(int i, String s) {
//                Log.d(TAG, "获取喜马拉雅FM内容分类失败");
//            }
//        });


        /**
         * 获取标签列表
         *
         */

//        Map<String, String> tagDesc = new HashMap<String, String>();
//        tagDesc.put(DTransferConstants.CATEGORY_ID, "6");
//        tagDesc.put(DTransferConstants.TYPE, "0");
//        CommonRequest.getTags(tagDesc, new IDataCallBack<TagList>() {
//            @Override
//            public void onSuccess(TagList list) {
//                mTagList = list.getTagList();
//                Log.d(TAG, "*****************儿童分类标签列表**************");
//                for (Tag t : mTagList) {
//                    Log.d(TAG, "TagName: " + t.getTagName());
//                }
//            }
//
//            @Override
//            public void onError(int i, String s) {
//
//            }
//        });

        /**
         *
         * 获取专辑列表
         *
         */

//        Map<String, String> albumDesc = new HashMap<String, String>();
//        albumDesc.put(DTransferConstants.CATEGORY_ID, "6");
//        albumDesc.put(DTransferConstants.TAG_NAME, "儿歌大全");
//        albumDesc.put(DTransferConstants.CALC_DIMENSION, "1");
//        CommonRequest.getAlbumList(albumDesc, new IDataCallBack<AlbumList>() {
//            @Override
//            public void onSuccess(AlbumList list) {
//                mAlbumList = list.getAlbums();
//                Log.d(TAG, "*****************儿歌大全标签专辑列表**************");
//                for (Album a : mAlbumList) {
//                    Log.d(TAG, "AlbumTitle: " + a.getAlbumTitle() + ",Id: " + a.getId());
//                }
//            }
//
//            @Override
//            public void onError(int i, String s) {
//
//            }
//        });


        /**
         *
         * 获取播放列表
         *
         *
         */

//        Map<String, String> trackDesc = new HashMap<String, String>();
//        trackDesc.put(DTransferConstants.ALBUM_ID, "267101");
//        trackDesc.put(DTransferConstants.SORT, "asc");
//        CommonRequest.getTracks(trackDesc, new IDataCallBack<TrackList>() {
//            @Override
//            public void onSuccess(TrackList list) {
//                mTrackList = list.getTracks();
//                Log.d(TAG, "*****************天天听儿歌声音列表**************");
//                for (Track t : mTrackList) {
//                    Log.d(TAG, t.getTrackTitle() + "  " + t.getPlayUrlAmr());
//                }
//            }
//
//            @Override
//            public void onError(int i, String s) {
//
//            }
//        });
    }

    /**
     * 初始化事件,设置播放器监听器。
     */
    public void initEvent() {

        mPlayerManager.addPlayerStatusListener(new IXmPlayerStatusListener() {
            @Override
            public void onPlayStart() {

            }

            @Override
            public void onPlayPause() {

            }

            @Override
            public void onPlayStop() {

            }

            @Override
            public void onSoundPlayComplete() {

            }

            @Override
            public void onSoundPrepared() {

            }

            @Override
            public void onSoundSwitch(PlayableModel playableModel, PlayableModel playableModel1) {

            }

            @Override
            public void onBufferingStart() {

            }

            @Override
            public void onBufferingStop() {

            }

            @Override
            public void onBufferProgress(int i) {

            }

            @Override
            public void onPlayProgress(int i, int i1) {

            }

            @Override
            public boolean onError(XmPlayerException e) {
                return false;
            }
        });
    }



    public void initView() {
        btn_pause = (Button) findViewById(R.id.pause);
        btn_play = (Button) findViewById(R.id.play);
        title = (TextView)findViewById(R.id.music_title);
    }


    /**
     * 检查播放器状态,来改变状态显示图标。
     */
    public void checkPlayerStatus() {
        if (mPlayerManager.getPlayerStatus() == STATE_STARTED) {
            btn_play.setVisibility(View.INVISIBLE);
            btn_pause.setVisibility(View.VISIBLE);

            Log.d("Main", "" + mPlayerManager.getPlayerStatus());
        } else if (mPlayerManager.getPlayerStatus() == STATE_PAUSED) {
            btn_play.setVisibility(View.VISIBLE);
            btn_pause.setVisibility(View.INVISIBLE);
            Log.d("Main", "" + mPlayerManager.getPlayerStatus());
        }
    }



    public void play(View view) {
        Log.d(TAG, "play");
//        btn_pause.setVisibility(View.VISIBLE);
//        btn_play.setVisibility(View.INVISIBLE);
        mPlayerManager.playList(TrackActivity.mTrackList, data);
        mPlayerManager.play();
        checkPlayerStatus();
    }

    public void stop(View view) {
        Log.d(TAG, "stop");
        mPlayerManager.stop();
    }

    public void pause(View view) {
//        btn_pause.setVisibility(View.INVISIBLE);
//        btn_play.setVisibility(View.VISIBLE);
        Log.d(TAG, "pause");
        mPlayerManager.pause();
        checkPlayerStatus();
    }

    public void next(View view) {
        Log.d(TAG, "next");
        mPlayerManager.playNext();
        title.setText(TrackActivity.mTrackList.get(mPlayerManager.getCurrentIndex()).getTrackTitle());
        title.requestFocus();
        checkPlayerStatus();
    }

    public void preview(View view) {
        Log.d(TAG, "preview");
        mPlayerManager.playPre();
        title.setText(TrackActivity.mTrackList.get(mPlayerManager.getCurrentIndex()).getTrackTitle());
        title.requestFocus();
        checkPlayerStatus();
    }

    @Override
    protected void onDestroy() {
        mPlayerManager.release();
        super.onDestroy();
    }
}
