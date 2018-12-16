package com.tn.blasti.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tn.blasti.R;
import com.tn.blasti.listeners.ListItemClickListener;
import com.tn.blasti.models.FavouriteModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by amine 15/12/2018.
 */

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder> {

    private ArrayList<FavouriteModel> favPostList;
    private Context context;
    private ListItemClickListener itemClickListener;

    public FavouriteAdapter(Context context, ArrayList<FavouriteModel> favPostList) {
        this.context = context;
        this.favPostList = favPostList;
    }

    public void setItemClickListener(ListItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favourite_list, parent, false);
        return new ViewHolder(view, viewType, itemClickListener);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imgPost;
        private TextView tvPostTitle, tvPostCategory, tvPostDate;
        private ImageButton btnDelete;
        private RelativeLayout lytFavourite;

        private ListItemClickListener itemClickListener;


        public ViewHolder(View itemView, int viewType, ListItemClickListener itemClickListener) {
            super(itemView);

            this.itemClickListener = itemClickListener;

            // Find all views ids
            imgPost = (ImageView) itemView.findViewById(R.id.post_img);
            tvPostTitle = (TextView) itemView.findViewById(R.id.title_text);
            tvPostCategory = (TextView) itemView.findViewById(R.id.post_category);
            tvPostDate = (TextView) itemView.findViewById(R.id.date_text);
            btnDelete = (ImageButton) itemView.findViewById(R.id.btn_delete);
            lytFavourite = (RelativeLayout) itemView.findViewById(R.id.lyt_favourite);

            // Implement click listener over views that we need
            lytFavourite.setOnClickListener(this);
            btnDelete.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(getLayoutPosition(), view);
            }
        }
    }

    @Override
    public int getItemCount() {
        return (null != favPostList ? favPostList.size() : 0);
    }

    @Override
    public void onBindViewHolder(FavouriteAdapter.ViewHolder mainHolder, int position) {
        final FavouriteModel model = favPostList.get(position);


        // setting data over views
        String title = model.getPostTitle();
        mainHolder.tvPostTitle.setText(Html.fromHtml(title));


        String imgUrl = model.getPostImageUrl();

        if (imgUrl != null) {
            Glide.with(context)
                    .load(imgUrl)
                    .into(mainHolder.imgPost);
        }

        String category = model.getPostCategory();
        mainHolder.tvPostCategory.setText(Html.fromHtml(category));


        mainHolder.tvPostDate.setText(model.getFormattedDate());

    }

}
