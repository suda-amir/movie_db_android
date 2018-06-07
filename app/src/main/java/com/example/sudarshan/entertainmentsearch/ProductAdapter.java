package com.example.sudarshan.entertainmentsearch;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.List;

/**
 * Created by Belal on 10/18/2017.
 */


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {


    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<Product> productList;

    //getting the context and product list with constructor
    public ProductAdapter(Context mCtx, List<Product> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_products, null);
        return new ProductViewHolder(view);
    }
    private class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView = imageView;
        }

        protected Bitmap doInBackground(String... urls) {
            String imageURL = urls[0];
            Bitmap bimage = null;
            try {
                InputStream in = new java.net.URL(imageURL).openStream();
                bimage = BitmapFactory.decodeStream(in);

            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }
            return bimage;
        }

        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        //getting the product of the specified position
        Product product = productList.get(position);

        holder.textViewTitle.setText(product.getTitle());
        holder.type.setText(product.getType());
        holder.year.setText(product.getYear());
        holder.imdb.setText(product.getId());
        String img  = product.getPoster();
        if(img == ""){
            img = "https://www.google.co.in/imgres?imgurl=http%3A%2F%2Feugenerugby.com%2Fimg%2Fno-image-available.png&imgrefurl=http%3A%2F%2Feugenerugby.com%2Fplayers.html&docid=5mdanHR0JRvslM&tbnid=2vuSUUaKlfSa1M%3A&vet=10ahUKEwjEpMjetrDbAhXLs48KHSEbCVAQMwg8KAcwBw..i&w=200&h=200&bih=959&biw=1920&q=no%20image%20icon&ved=0ahUKEwjEpMjetrDbAhXLs48KHSEbCVAQMwg8KAcwBw&iact=mrc&uact=8";
        }
        new DownloadImageFromInternet(holder.imageView).execute(img);
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, year, type, imdb;
        ImageView imageView;
        Button button;

        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            year = itemView.findViewById(R.id.year);
            type = itemView.findViewById(R.id.type);
            imdb = itemView.findViewById(R.id.imdb);
            imageView = itemView.findViewById(R.id.imageView);
            button = itemView.findViewById(R.id.next_button);
        }
    }
}