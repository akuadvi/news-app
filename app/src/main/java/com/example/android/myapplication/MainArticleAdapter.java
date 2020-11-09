package com.example.android.myapplication;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;


import com.squareup.picasso.Picasso;

import org.htmlcleaner.HtmlCleaner;

import java.util.List;

public class MainArticleAdapter extends RecyclerView.Adapter<MainArticleAdapter.ViewHolder> {
    List<Article> articleArrayList;
    private onNoteListener obj;
    private CharSequence removeHtmlFrom(String html) {
        return new HtmlCleaner().clean(html).getText();
    }

    public MainArticleAdapter(List<Article> articleArrayList) {

        this.articleArrayList = articleArrayList;
    }



    @NonNull
    @Override
    public MainArticleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_main_article_adapter, parent, false);
        return new ViewHolder(view,obj);
    }

    @Override
    public void onBindViewHolder(@NonNull MainArticleAdapter.ViewHolder holder, int position) {
        final Article articleModel = articleArrayList.get(position);
        holder.titleText.setText(articleModel.getTitle());
        holder.descriptionText.setText(articleModel.getDescription());
        String imageUri = articleModel.getUrlToImage();
         Picasso.get().load(imageUri).fit().centerCrop().
        into(holder.img);


    }

    @Override
    public int getItemCount() {
        return articleArrayList.size();

    }

    public interface onNoteListener {
        void onClick(int pos);
    }
    public void setOnItemClickListener(onNoteListener Listener){
        obj=Listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        int position;
        private TextView titleText;
        private TextView descriptionText;
        private ImageView img;
        public ViewHolder(@NonNull View itemView, final onNoteListener Listener) {
            super(itemView);
            titleText = itemView.findViewById(R.id.article_adapter_tv_title);
            descriptionText = itemView.findViewById(R.id.article_adapter_tv_description);
            img=itemView.findViewById(R.id.img);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Listener!=null){
                         position =getAdapterPosition();
                         Listener.onClick(position);

                }
            }
        });
    }
    }
}