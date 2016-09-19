package com.learnice.sharesdemo.Json;

import com.learnice.sharesdemo.Stock.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xuebin He on 2016/6/25.
 */
public class ParseNews {
    public static List<News> pareNews(String data){
        List<News> list=new ArrayList<News>();
        try {
            JSONObject object=new JSONObject(data);
            JSONArray detail=object.getJSONArray("detail");
            for (int i=0;i<detail.length();i++){
                JSONObject obj=detail.getJSONObject(i);
                News news=new News(obj.getString("title"),
                        obj.getString("source"),
                        obj.getString("article_url"));
                list.add(news);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }
}
