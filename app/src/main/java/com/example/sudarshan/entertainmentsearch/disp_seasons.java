package com.example.sudarshan.entertainmentsearch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class disp_seasons extends AppCompatActivity {

    String imdb, seasons, url, url2;
    int sea;
    RecyclerView recyclerView;
    List<Episode> productList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disp_seasons);
        Intent intent = getIntent();
        imdb = intent.getStringExtra("imdb");
        seasons = intent.getStringExtra("seasons");
        sea = Integer.parseInt(seasons);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productList = new ArrayList<>();
        for (int i = 1; i <= sea; i++ ){
            url = "http://www.omdbapi.com/?i="+imdb+"&Season="+i+"&apikey=584ac277";
            call_api(url);
        }


    }
    public void call_api(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        add_to_list(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error response", error.toString());
                    }
                }
        );
        objectRequest.setRetryPolicy(new DefaultRetryPolicy(
                1000000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(objectRequest);
    }
    public void call_api2(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        disp(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error response", error.toString());
                    }
                }
        );
        objectRequest.setRetryPolicy(new DefaultRetryPolicy(
                1000000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(objectRequest);
    }
    public  void  add_to_list(JSONObject x) {
        try {
            String seas = x.getString("Season");
            JSONArray search_results = x.getJSONArray("Episodes");
            for(int j =1; j<=search_results.length(); j++) {
                url2 = "http://www.omdbapi.com/?i="+imdb+"&Season="+seas+"&Episode="+j+"&apikey=584ac277";
                call_api2(url2);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void disp(JSONObject y){

        try {
            String name = y.getString("Title");
            String season = y.getString("Season");
            String episode = y.getString("Episode");
            String title = "S"+season+"E"+episode+": "+name;
            String airdate = y.getString("Released");
            String plot = y.getString("Plot");
            String actors = y.getString("Actors");
            String genre = y.getString("Genre");
            String poster = y.getString("Poster");

            productList.add(
                new Episode(
                    title,
                    airdate,
                    plot,
                    actors,
                    genre,
                    poster
                ));


        } catch (JSONException e) {
            e.printStackTrace();
        }
        seasonAdapter adapter = new seasonAdapter(this, productList);
        recyclerView.setAdapter(adapter);

    }
}
