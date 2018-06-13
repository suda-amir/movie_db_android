package com.example.sudarshan.entertainmentsearch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import org.json.JSONException;
import org.json.JSONObject;


public class Eachdata extends AppCompatActivity {

    TextView t1, t2, t3, t4, t5, t6, t7, t8, t9, t10;
    String imdb, type, url;
    ImageView im;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eachdata);
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
    public  void  add_to_list(JSONObject x) {
        try {
            String title = x.getString("Title");
            String released = x.getString("Released");
            String released1 = "<b>Released</b>: " + released;
            String runtime = x.getString("Runtime");
            String runtime1 = "<b>Runtime</b>: " + runtime;
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
            t8 = (TextView)findViewById(R.id.run_time);
            t9 = (TextView)findViewById(R.id.content);
            t10 = (TextView)findViewById(R.id.released);
            im = (ImageView)findViewById(R.id.main_img);

            t1.setText(title);
            t3.setText(Html.fromHtml(imdbRating1));
            t4.setText(Html.fromHtml(actors1));
            t5.setText(Html.fromHtml(director1));
            t6.setText(Html.fromHtml(writer1));
            t7.setText(Html.fromHtml(genre1));
            t8.setText(Html.fromHtml(runtime1));
            t9.setText(plot);
            t10.setText(Html.fromHtml(released1));
            Picasso.get().load(poster).into(im);



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
