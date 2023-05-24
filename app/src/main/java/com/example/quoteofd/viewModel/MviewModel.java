package com.example.quoteofd.viewModel;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quoteofd.MainActivity;
import com.example.quoteofd.QoutesData;
import com.example.quoteofd.networking.ErrorListener;
import com.example.quoteofd.networking.ResponseListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MviewModel extends AndroidViewModel {
Activity activity ;
ArrayList<QoutesData> quotesData;
MutableLiveData<ArrayList<QoutesData>> liveData;
int i=-1;
RequestQueue requestQueue;
    ResponseListener responseListener;

Context context;
    public MviewModel(Application application) {
        super(application);
        context = application.getApplicationContext();
        requestQueue = Volley.newRequestQueue(application);
//        try
//        {
//            this.quotesData = new ArrayList<>();
//            loadQoutes();
//
//        }
//        catch (IOException exception)
//        {
//            exception.printStackTrace();
//        }
        quotesData = new ArrayList<>();
        liveData  = new MutableLiveData<>();
        responseListener = new ResponseListener(liveData);
        makeNetworkCall();

    }


// public    void loadQoutes() throws IOException {
//        String json = null;
//        try {
//            InputStream is = context.getAssets().open("quotes.json");
//            int size = is.available();
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//            json = new String(buffer, "UTF-8");
//            System.out.println(json);
//        } catch (IOException ex) {
//            Log.e("error" , "exception");
//            ex.printStackTrace();
//
//        }
//        Gson gson = new Gson();
//     Type q = new TypeToken<ArrayList<QoutesData>>(){}.getType();
//     quotesData = gson.fromJson(json, q);
//
//
//    }

    public QoutesData nextQoute()
    {
        if(i==-1 || i==quotesData.size()-1)
        {
           for(QoutesData i: responseListener.getData())
           {
               quotesData.add(i);
           }

        }
        else if(i== quotesData.size()-3)
        {
            makeNetworkCall();
        }
        System.out.println(quotesData.size());
        System.out.println(i);
        return quotesData.get(++i);
    }
    public QoutesData prevQoute()
    {
        if(i<=0)
        {
         i= quotesData.size();
        }
        System.out.println(i);
     return quotesData.get(--i);
    }
    void makeNetworkCall()
    {
        String url="https://api.api-ninjas.com/v1/quotes?category=happiness&&limit=10";
        StringRequest stringRequest =  new StringRequest(Request.Method.GET ,url, responseListener , new ErrorListener())
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("X-Api-Key", "nhKE25YpSDbq0oUnpgwVEQ==mkPmu9kwF7vyWQVq");
                return headers;
            }
        };

//        System.out.println(temp.size());
//for (QoutesData i:temp)
//{
//    quotesData.add(i);
//}
        requestQueue.add(stringRequest);


    }

public MutableLiveData<ArrayList<QoutesData>> getLiveData()
    {
        return liveData;
    }


}
