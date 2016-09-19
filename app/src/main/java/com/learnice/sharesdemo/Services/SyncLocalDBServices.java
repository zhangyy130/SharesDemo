package com.learnice.sharesdemo.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.service.voice.VoiceInteractionService;
import android.util.Log;

import com.learnice.sharesdemo.Database.DbServices;
import com.learnice.sharesdemo.Http.HttpRequestImpl;
import com.learnice.sharesdemo.Http.HttpResponse;
import com.learnice.sharesdemo.MyServerHttp.IMyServerDataResult;
import com.learnice.sharesdemo.MyServerHttp.MyServerHttpRequestImpl;
import com.learnice.sharesdemo.MyServerHttp.MyServerHttpResponseList;
import com.learnice.sharesdemo.Stock.Shares;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class SyncLocalDBServices extends Service {
    public SyncLocalDBServices() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String name=intent.getStringExtra("name");
        getMyserverData(name);
        return super.onStartCommand(intent, flags, startId);
    }
    public void getMyserverData(String name){
        new MyServerHttpRequestImpl().getServerData("200", name, new Shares("sh", "00001"), new MyServerHttpResponseList(new IMyServerDataResult() {
            @Override
            public void resultString(Object data) {
                String mdata = data.toString();
                try {
                    JSONArray jsonArray = new JSONArray(mdata);
                    for (int i = 0; i < jsonArray.length() - 1; i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String type=object.getString("type");
                        String name=object.getString("name");
                        new DbServices(SyncLocalDBServices.this).add(new Shares(type,name));
                    }
                sendBroadcast(new Intent("SYNCOK"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void resultSayList(String list) {

            }
        }));
    }
}
