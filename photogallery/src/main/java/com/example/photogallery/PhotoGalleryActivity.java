package com.example.photogallery;

import android.support.v4.app.Fragment;

import com.example.photogallery.toutiao.NewsGalleryFragment;

public class PhotoGalleryActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment() {
        return NewsGalleryFragment.newInstance();
    }

}
