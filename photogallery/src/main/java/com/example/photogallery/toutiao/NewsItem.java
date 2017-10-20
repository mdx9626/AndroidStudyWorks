package com.example.photogallery.toutiao;

import android.net.Uri;

import java.io.Serializable;

/**
 * Created by mdx on 2017/10/13.
 */

public class NewsItem implements Serializable{
    /* title,id,url_s,owner
        "title": "倒霉！新车无法上牌竟因发动机重号",
        "name": "信息时报",
        "image": "//p1.pstatp.com/list/300x170/31770003c0ca34b12be7",
        "day": "2017-10-13",
        "aid": "6468799645542777357",
        "url": "http://www.toutiao.com/i6468799645542777357"
     */
   /* private String mCaption;
    private String mId;
    private String mUrl;

    public String getCaption() {
        return mCaption;
    }

    public void setCaption(String caption) {
        mCaption = caption;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    @Override
    public String toString() {
        return mCaption;
    }*/
     private String title;
    private String name;
    private String image;
    private String day;
   // private String aid;
    private String url;

    public NewsItem() {
    }

    public NewsItem(String title, String name, String image, String day, String aid, String url) {
        this.title = title;
        this.name = name;
        this.image = image;
        this.day = day;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

/*    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }*/

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "NewsItem{" +
                "title='" + title + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", day='" + day + '\'' +
//                ", aid='" + aid + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
    public Uri getPhotoPageUri(){

        return Uri.parse(url)
                .buildUpon()
                .build();
    }

}
