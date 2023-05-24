package com.example.quoteofd.networking;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Response;
import com.example.quoteofd.QoutesData;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ResponseListener implements Response.Listener<String> {

    ArrayList<QoutesData> quotesData;
MutableLiveData<ArrayList<QoutesData>> liveQouteData;

    public ResponseListener(MutableLiveData<ArrayList<QoutesData>> data)
    {
      quotesData = new ArrayList<>();
  liveQouteData = data;

    }


    @Override
    public void onResponse(String response) {
        Log.i("success" , "response is hit by network");
        Gson gson = new Gson();
        Type q = new TypeToken<ArrayList<QoutesData>>(){}.getType();
     quotesData = gson.fromJson(response, q);
     liveQouteData.setValue(quotesData);

    }
    public ArrayList<QoutesData> getData()
    {
        return quotesData;
    }



}
