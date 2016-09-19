package com.learnice.sharesdemo.Json;

import com.learnice.sharesdemo.Http.MyURL;
import com.learnice.sharesdemo.Stock.Stock;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xuebin He on 2016/6/18.
 */
public class SuperJson {
    public static List<Stock> parseFromJuheRetuenList(String type, String result) {
        List<Stock> list = new ArrayList<Stock>();

        if (type.equals(MyURL.SHALL)) {
            try {
                JSONObject object = new JSONObject(result);
                JSONObject result_result = object.getJSONObject("result");
                JSONArray data = result_result.getJSONArray("data");
                for (int i = 0; i < data.length(); i++) {
                    JSONObject stockResult = data.getJSONObject(i);
                    Stock stock = new Stock(MyURL.SHONE, stockResult.getString("name"),
                            stockResult.getString("symbol"), stockResult.getString("trade"),
                            stockResult.getString("pricechange"), stockResult.getString("changepercent"));
                    list.add(stock);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else if (type.equals(MyURL.SZALL)) {
            try {
                JSONObject object = new JSONObject(result);
                JSONObject result_result = object.getJSONObject("result");
                JSONArray data = result_result.getJSONArray("data");
                for (int i = 0; i < data.length(); i++) {
                    JSONObject stockResult = data.getJSONObject(i);
                    Stock stock = new Stock(MyURL.SZONE, stockResult.getString("name"),
                            stockResult.getString("symbol"), stockResult.getString("trade"),
                            stockResult.getString("pricechange"), stockResult.getString("changepercent"));
                    list.add(stock);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (type.equals(MyURL.HKALL)) {
            try {
                JSONObject object = new JSONObject(result);
                JSONObject result_result = object.getJSONObject("result");
                JSONArray data = result_result.getJSONArray("data");
                for (int i = 0; i < data.length(); i++) {
                    JSONObject stockResult = data.getJSONObject(i);
                    Stock stock = new Stock(MyURL.HKONE, stockResult.getString("name"),
                            "hk"+stockResult.getString("symbol"), stockResult.getString("lasttrade"),
                            stockResult.getString("pricechange"), stockResult.getString("changepercent"));
                    list.add(stock);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (type.equals(MyURL.USALL)) {
            try {
                JSONObject object = new JSONObject(result);
                JSONObject result_result = object.getJSONObject("result");
                JSONArray data = result_result.getJSONArray("data");
                for (int i = 0; i < data.length(); i++) {
                    JSONObject stockResult = data.getJSONObject(i);
                    Stock stock = new Stock(MyURL.USONE, stockResult.getString("cname"),
                            stockResult.getString("symbol").toLowerCase(), stockResult.getString("price"),
                            stockResult.getString("diff"), stockResult.getString("chg"));
                    list.add(stock);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static Stock parseFromJuheRetuenObj(String type, String result) {
        Stock stock = null;
        if (type.equals(MyURL.SHONE)) {
            try {
                JSONObject object = new JSONObject(result);
                JSONArray result_result = object.getJSONArray("result");
                JSONObject result_data = result_result.getJSONObject(0);
                JSONObject data = result_data.getJSONObject("data");
                stock = new Stock(MyURL.SHONE, data.getString("name"),
                        data.getString("gid"), data.getString("nowPri"),
                        data.getString("increase"), data.getString("increPer"),
                        data.getString("yestodEndPri"), data.getString("todayStartPri"),
                        data.getString("traAmount"), data.getString("traNumber"),
                        "...", data.getString("date"),data.getString("todayMin"),data.getString("todayMax"));//...为未知
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (type.equals(MyURL.SZONE)) {
            try {
                JSONObject object = new JSONObject(result);
                JSONArray result_result = object.getJSONArray("result");
                JSONObject result_data = result_result.getJSONObject(0);
                JSONObject data = result_data.getJSONObject("data");
                stock = new Stock(MyURL.SZONE, data.getString("name"),
                        data.getString("gid"), data.getString("nowPri"),
                        data.getString("increase"), data.getString("increPer"),
                        data.getString("yestodEndPri"), data.getString("todayStartPri"),
                        data.getString("traAmount"), data.getString("traNumber"),
                        "...", data.getString("date"),data.getString("todayMin"),data.getString("todayMax"));//...为未知
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (type.equals(MyURL.HKONE)) {
            try {
                JSONObject object = new JSONObject(result);
                JSONArray result_result = object.getJSONArray("result");
                JSONObject result_data = result_result.getJSONObject(0);
                JSONObject data = result_data.getJSONObject("data");
                stock = new Stock(MyURL.HKONE, data.getString("name"),
                        data.getString("gid"), data.getString("lastestpri"),
                        data.getString("uppic"), data.getString("limit"),
                        data.getString("formpri"), data.getString("openpri"),
                        data.getString("traAmount"), data.getString("traNumber"),
                        "...", data.getString("date"),data.getString("minpri"),data.getString("maxpri"));//...为未知
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (type.equals(MyURL.USONE)) {
            try {
                JSONObject object = new JSONObject(result);
                JSONArray result_result = object.getJSONArray("result");
                JSONObject result_data = result_result.getJSONObject(0);
                JSONObject data = result_data.getJSONObject("data");
                stock = new Stock(MyURL.USONE, data.getString("name"),
                        data.getString("gid"), data.getString("lastestpri"),
                        data.getString("uppic"), data.getString("limit"),
                        data.getString("formpri"), data.getString("openpri"),
                        "...", data.getString("traAmount"),
                        data.getString("markValue"), data.getString("chtime"),data.getString("minpri"),data.getString("maxpri"));//...为未知
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return stock;
    }
}
