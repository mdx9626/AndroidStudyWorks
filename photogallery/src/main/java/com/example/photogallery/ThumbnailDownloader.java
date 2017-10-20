package com.example.photogallery;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by mdx on 2017/10/16.
 */

public class ThumbnailDownloader<T> extends HandlerThread {
    private static final String TAG="ThumbnailDownloader";
    private static final int MESSAGE_DOWNLOAD=0;

    private Boolean mHasQuit=false;
    private Handler mRequestHandler;
    private ConcurrentMap<T,String> mRequestMap=new ConcurrentHashMap<>();

    public ThumbnailDownloader(){
        super(TAG);
    }

    @Override
    public boolean quit() {
        mHasQuit=true;
        return super.quit();
    }

    public void queueThumbnail(T target,String url){
        Log.i(TAG,"Got a URL: "+url);

        if (url==null){
            mRequestMap.remove(target);
        }else {
            mRequestMap.put(target,url);
            mRequestHandler.obtainMessage(MESSAGE_DOWNLOAD,target)
                    .sendToTarget();
        }
    }
}
