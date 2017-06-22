package com.shuvojitkar.recylerview_implementaion.Logic;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.shuvojitkar.recylerview_implementaion.Data.DataItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by SHOBOJIT on 6/22/2017.
 */

public class FakeDataSource {

    public interface FakeInterface{
       void setupAdapterView(List<DataItem>data);
    }


    Context cn ;
    public FakeInterface fakein;
    public static final List <DataItem> datasource = new ArrayList<>(); ;
    AlertDialog dialog;
    private OkHttpClient client = new OkHttpClient();
    String Url ="http://javaloverbd.000webhostapp.com/teacher_detail.txt";

    public FakeDataSource(Activity listeningActivity, AlertDialog dialog,FakeInterface fake) {
        this.cn = listeningActivity;
       fakein = fake;
        this.dialog = dialog;
        getDATA();
    }


      void getDATA(){
        //  datasource = new ArrayList<>();
            Request re = new Request.Builder().url(Url).build();
         /*  Response response = client.newCall(re).execute();
           return response.body().string();*/
            client.newCall(re).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    getDATA();
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        JSONObject json = new JSONObject(response.body().string());
                        JSONArray jsr = json.getJSONArray("cse");
                        for (int i = 0 ;i<jsr.length();i++){
                            DataItem dataItem = new DataItem(jsr.getJSONObject(i).getString("name").toString(),
                                    jsr.getJSONObject(i).getString("designation").toString(),
                                    jsr.getJSONObject(i).getString("mob").toString(),
                                    jsr.getJSONObject(i).getString("image").toString());
                            datasource.add(dataItem);
                        }

                        fakein.setupAdapterView(datasource);
                        if(dialog.isShowing()){
                            dialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

           Log.d("----Data Size----", String.valueOf(datasource.size()));

         //  return datasource;
        }


/*
    @Override
    public List<DataItem> getListOfData() {

        return getDATA();
    }*/
}
