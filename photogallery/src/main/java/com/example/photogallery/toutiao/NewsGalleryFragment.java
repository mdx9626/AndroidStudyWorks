package com.example.photogallery.toutiao;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.photogallery.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by mdx on 2017/10/13.
 */

public class NewsGalleryFragment extends Fragment {
    private static final String TAG="NewsGallleryFragment";

    private RecyclerView mPhotoRecyclerView;
    private List<NewsItem> mItems=new ArrayList<>();
    private ThumbnailDownloader<PhotoHolder> mThumbnailDownloader;

    public static NewsGalleryFragment newInstance(){
        return new NewsGalleryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        new FetchItemsTask().execute();

        Handler responseHandler=new Handler();
        mThumbnailDownloader=new ThumbnailDownloader<>(responseHandler);
        mThumbnailDownloader.setThumbnailDownloadListener(
                new ThumbnailDownloader.ThumbnailDownloadListener<PhotoHolder>(){

                    @Override
                    public void onThumbnailDownloaded(PhotoHolder photoHolder, Bitmap bitmap) {
                        Drawable drawable=new BitmapDrawable(getResources(),bitmap);
                        photoHolder.bindDrawable(drawable);
                    }
                }
        );
        mThumbnailDownloader.start();
        mThumbnailDownloader.getLooper();
        Log.i(TAG,"Background thread started");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_photo_gallery,container,false);

        mPhotoRecyclerView=(RecyclerView)v.findViewById(R.id.photo_recycler_view);
        mPhotoRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));

        setupAdapter();

        return v;
    }

    //调用清理方法
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mThumbnailDownloader.clearQueue();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mThumbnailDownloader.quit();
        Log.i(TAG,"Background thread destroyed");
    }

    private void setupAdapter(){
        if (isAdded()){
            mPhotoRecyclerView.setAdapter(new PhotoAdapter(mItems));
        }
    }

    private class PhotoHolder extends RecyclerView.ViewHolder{
       // private TextView mTitleTextView;
        private ImageView mItemImageView;

        public PhotoHolder(View itemView) {
            super(itemView);
           // mTitleTextView=(TextView)itemView;
            mItemImageView=(ImageView) itemView.findViewById(R.id.item_image_view);
        }

       /* public void bindGalleryItem(NewsItem item){
            mTitleTextView.setText(item.toString());
        }*/
       public void bindDrawable(Drawable drawable){
           mItemImageView.setImageDrawable(drawable);
       }
    }
    private class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder>{
        private List<NewsItem> mGalleryItems;
        public PhotoAdapter(List<NewsItem> galleryItems){
            mGalleryItems=galleryItems;
        }

        @Override
        public PhotoHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            /*TextView textView=new TextView(getActivity());
            return new PhotoHolder(textView);*/

            LayoutInflater inflater=LayoutInflater.from(getActivity());
            View view=inflater.inflate(R.layout.list_item_gallery,viewGroup,false);
            return new PhotoHolder(view);
        }

        @Override
        public void onBindViewHolder(PhotoHolder photoHolder, int position) {
            NewsItem newsItem=mGalleryItems.get(position);
            /*photoHolder.bindGalleryItem(newsItem);*/

            Drawable placeholder=getResources().getDrawable(R.drawable.bill_up_close);
            photoHolder.bindDrawable(placeholder);
            mThumbnailDownloader.queueThumbnail(photoHolder,newsItem.getImage());
        }

        @Override
        public int getItemCount() {
            return mGalleryItems.size();
        }
    }

    //实现AsynTask工具类方法
    private class FetchItemsTask extends AsyncTask<Void,Void,List<NewsItem>>{
        @Override
        protected List<NewsItem> doInBackground(Void... params) {
            /*try{
                String result=new FlickrFetchr()
                        .getUrlString("http://www.bignerdranch.com");
                Log.i(TAG,"Fetched contents of URL: "+result);
            }catch (IOException ioe){
                Log.e(TAG,"Failed to fetch URL: ",ioe);
            }*/
            return new ShowApiFetchr().fetchItems();
           // return null;
        }

        @Override
        protected void onPostExecute(List<NewsItem> items) {
            mItems=items;
            setupAdapter();
        }
    }
}
