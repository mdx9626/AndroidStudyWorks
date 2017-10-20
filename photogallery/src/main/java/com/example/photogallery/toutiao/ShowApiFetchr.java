package com.example.photogallery.toutiao;

import android.net.Uri;
import android.util.Log;

import com.example.photogallery.GalleryItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mdx on 2017/10/13.
 */

public class ShowApiFetchr {
    private static final String TAG="ShowApiFetchr";
    private static final String API_KEY="1f245689595a458c84c309cf5bfaf81c";

    public byte[] getUrlBytes(String urlSpec) throws IOException{
        URL url=new URL(urlSpec);
        HttpURLConnection connection=(HttpURLConnection)url.openConnection();
        try{
            ByteArrayOutputStream out=new ByteArrayOutputStream();
            InputStream in=connection.getInputStream();

            if (connection.getResponseCode()!=HttpURLConnection.HTTP_OK){
                throw new IOException(connection.getResponseMessage()+
                ":with "+urlSpec);
            }
            int bytesRead=0;
            byte[] buffer=new byte[1024];
            while ((bytesRead=in.read(buffer))>0){
                out.write(buffer,0,bytesRead);
            }
            out.close();
            return out.toByteArray();
        }finally {
            connection.disconnect();
        }
    }

    public String getUrlString(String urlSpec) throws IOException{
        return new String(getUrlBytes(urlSpec));
    }

    public List<NewsItem> fetchItems(){
        List<NewsItem> items=new ArrayList<>();

        try{
            String url= Uri.parse("http://route.showapi.com/1443-1")
                    .buildUpon()
                    .appendQueryParameter("showapi_appid","47705")
                    .appendQueryParameter("showapi_sign",API_KEY)
                    .build().toString();

            String jsonString=getUrlString(url);
            Log.i(TAG,"Received JSON: "+jsonString);
            JSONObject jsonBody=new JSONObject(jsonString);//解析JSON数据
            parseItems(items,jsonBody);
        }catch (IOException ioe){
            Log.e(TAG,"Failed to fetch items",ioe);
        }catch (JSONException je){
            Log.e(TAG,"Failed to parse JSON",je);
        }
        return items;
    }

    //解析Flickr图片
    private void parseItems(List<NewsItem> items, JSONObject jsonBody) throws JSONException {
        JSONObject photosJsonObject=jsonBody.getJSONObject("showapi_res_body");
        JSONArray photoJsonArray=photosJsonObject.getJSONArray("list");
/*
        "title": "倒霉！新车无法上牌竟因发动机重号",
        "name": "信息时报",
        "image": "//p1.pstatp.com/list/300x170/31770003c0ca34b12be7",
        "day": "2017-10-13",
        "aid": "6468799645542777357",
        "url": "http://www.toutiao.com/i6468799645542777357"
     */
        for (int i=0;i<photoJsonArray.length();i++){
            JSONObject photoJsonObject=photoJsonArray.getJSONObject(i);
            NewsItem item=new NewsItem();
          /*  item.setId(photoJsonObject.getString("aid"));
            item.setCaption(photoJsonObject.getString("title"));*/


            item.setTitle(photoJsonObject.getString("title"));
            item.setName(photoJsonObject.getString("name"));
            if (!photoJsonObject.has("image")){
                continue;
            }
            item.setImage(photoJsonObject.getString("image"));
            item.setDay(photoJsonObject.getString("day"));
//            item.setAid(photoJsonObject.getString("aid"));

            if (!photoJsonObject.has("url")){
                continue;
            }

            item.setUrl(photoJsonObject.getString("url"));
            items.add(item);
        }
    }
}
