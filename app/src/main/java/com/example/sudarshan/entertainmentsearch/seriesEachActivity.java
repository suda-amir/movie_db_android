package com.example.sudarshan.entertainmentsearch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class seriesEachActivity extends AppCompatActivity {

    TextView t1, t2, t3, t4, t5, t6, t7, t8, t9, t10;
    String imdb, type, url, seasons;
    ImageView im;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series_each);
        Intent intent = getIntent();
        imdb = intent.getStringExtra("imdb");
        type = intent.getStringExtra("type");
        url = "http://www.omdbapi.com/?i="+imdb+"&Plot=full&apikey=584ac277";
        call_api(url);

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
                        add_to_list(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error", "Error");
                        //t2.setText(error.toString());
                    }
                }
        );
        objectRequest.setRetryPolicy(new DefaultRetryPolicy(
                1000000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(objectRequest);
    }
    public void get_season_details(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Rest response", response.toString());
                        displaySeason(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error", "Error");
                    }
                }
        );
        objectRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(objectRequest);
    }

    public void displaySeason(JSONObject y) {
        String season_num;
        String episode_num;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.weight = 1.0f;
        int numberOfButtonsPerRow = 2;
        String disp;
        int k = 0;



        final LinearLayout verticalLayout= (LinearLayout)findViewById(R.id.linear);
        try {
            JSONArray search_results = y.getJSONArray("Episodes");
            season_num = y.getString("Season");
            for(int i =0; i <=search_results.length() + 1; i++) {
                LinearLayout newLine = new LinearLayout(this);
                newLine.setLayoutParams(params);
                newLine.setOrientation(LinearLayout.HORIZONTAL);
                for(int j=0;j<numberOfButtonsPerRow;j++){
                    JSONObject single_result = search_results.getJSONObject(k);
                    k++;
                    if(single_result.getString("Episode") == ""){
                        episode_num = single_result.getString("Episode");

                        Button button=new Button(this);

                        button.setLayoutParams(params);
                        button.setTag(single_result.getString("imdbID"));
                        disp = "S"+season_num+"E"+episode_num;
                        button.setText(disp);

//                     button.setOnClickListener(new View.OnClickListener() {
//                     public void onClick(View view) {
//
//                        Intent is = new Intent(getApplicationContext(), someOtherApplication.class);
//                        is.putExtra("buttonVariable", buttonIdNumber);
//                        startActivity(is);
//                    }
//                });

                        newLine.addView(button);
                    }

                }
                verticalLayout.addView(newLine);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public  void  add_to_list(JSONObject x) {
        try {
            String title = x.getString("Title");
            String released = x.getString("Released");
            String released1 = "<b>Released</b>: " + released;
            String genre = x.getString("Genre");
            String genre1 = "<b>Genre</b>: " + genre;
            String writer = x.getString("Writer");
            String writer1= "<b>Writer</b>: " + writer;
            String director = x.getString("Director");
            String director1 = "<b>Director</b>: " + director;
            String actors= x.getString("Actors");
            String actors1 = "<b>Actors</b>: " + actors;
            String plot = x.getString("Plot");
            String imdbRating = x.getString("imdbRating");
            String imdbRating1= "<b>Rating</b>: " + imdbRating;
            String poster = x.getString("Poster");

            t1 = (TextView)findViewById(R.id.heading);
            t3 = (TextView)findViewById(R.id.rating);
            t4 = (TextView)findViewById(R.id.cast);
            t5 = (TextView)findViewById(R.id.directors);
            t6 = (TextView)findViewById(R.id.writers);
            t7 = (TextView)findViewById(R.id.genre);
            t9 = (TextView)findViewById(R.id.content);
            t10 = (TextView)findViewById(R.id.released);
            im = (ImageView)findViewById(R.id.main_img);

            t1.setText(title);
            t3.setText(Html.fromHtml(imdbRating1));
            t4.setText(Html.fromHtml(actors1));
            t5.setText(Html.fromHtml(director1));
            t6.setText(Html.fromHtml(writer1));
            t7.setText(Html.fromHtml(genre1));
            t9.setText(plot);
            t10.setText(Html.fromHtml(released1));
            Picasso.with(this).load(poster).into(im);

            seasons = x.getString("totalSeasons");
            int s = Integer.parseInt(seasons);
            for (int i = 0; i <= s; i++){
                url = "http://www.omdbapi.com/?i="+imdb+"&Season="+i+"&apikey=584ac277";
                get_season_details(url);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
