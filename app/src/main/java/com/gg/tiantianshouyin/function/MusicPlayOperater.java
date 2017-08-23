package com.gg.tiantianshouyin.function;

/**
 * Created by tom on 2017/8/16.
 */

public class MusicPlayOperater {
    private MusicPlayerListener mListener;

    public void setListener(MusicPlayerListener listener){
        mListener = listener;
    }

    public void dosomething(String string){
        if(mListener != null){
            if(string.equals("play")){
                mListener.onPlayStart();
            }else{
                mListener.onPlayPause();
            }

        }
    }
}
