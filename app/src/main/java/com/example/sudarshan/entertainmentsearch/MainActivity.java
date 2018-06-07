package com.example.sudarshan.entertainmentsearch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //a list to store all the products
    List<Product> productList;
    String msg = "TEst";

    //the recyclerview
    RecyclerView recyclerView;
    SearchView searchView;
    TextView imdb, type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String flag1 = query.replaceAll(" ", "%20");
                String URL = "http://www.omdbapi.com/?s="+flag1+"&apikey=584ac277";
                Log.d("Test", msg);
                call_api(URL);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        //getting the recyclerview from xml
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
                        Log.e("Rest response", response.toString());
//                        textView.setText(response.toString());
                        add_to_list(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error response", error.toString());
//                        textView.setText(error.toString());
                    }
                }
        );
        objectRequest.setRetryPolicy(new DefaultRetryPolicy(
                1000000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(objectRequest);
    }
    public  void  add_to_list(JSONObject x){
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //initializing the productlist
        productList = new ArrayList<>();
        try {
            JSONArray search_results = x.getJSONArray("Search");
            for(int i =0; i<search_results.length(); i++) {
                JSONObject single_result = search_results.getJSONObject(i);
                productList.add(
                new Product(
                        single_result.getString("imdbID"),
                        single_result.getString("Title"),
                        single_result.getString("Year"),
                        single_result.getString("Type"),
                        single_result.getString("Poster")
                ));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //creating recyclerview adapter
        ProductAdapter adapter = new ProductAdapter(this, productList);
//
//        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);
    }
    public void newpage(View v)
    {
        imdb = (TextView)findViewById(R.id.imdb);
        String imdb1 = imdb.getText().toString();
        type = (TextView)findViewById(R.id.type);
        String type1 = type.getText().toString();
        if(type1 == "movie"){
            Intent myIntent = new Intent(MainActivity.this, Eachdata.class);
            myIntent.putExtra("imdb", imdb1);
            myIntent.putExtra("type", type1);//Optional parameters
            MainActivity.this.startActivity(myIntent);
        }else{
            Intent myIntent = new Intent(MainActivity.this, seriesEachActivity.class);
            myIntent.putExtra("imdb", imdb1);
            myIntent.putExtra("type", type1);//Optional parameters
            MainActivity.this.startActivity(myIntent);
        }

    }
}