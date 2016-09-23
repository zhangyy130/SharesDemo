package com.learnice.sharesdemo.Json;

import com.learnice.sharesdemo.Stock.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;

/**
 * Created by Xuebin He on 2016/6/25.
 */
public class ParseNews {
    public static List<News> pareNews(JSONObject data){
        List<News> list=new ArrayList<News>();
        try {
            JSONArray array=data.getJSONArray("data");
            for (int i=0;i<array.length();i++){
                JSONObject obj=array.getJSONObject(i);
                News news=new News(obj.getString("title"),
                        obj.getString("author_name"),
                        obj.getString("url"));
                list.add(news);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }
}
