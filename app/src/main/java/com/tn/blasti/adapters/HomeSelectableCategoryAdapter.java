package com.tn.blasti.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tn.blasti.R;
import com.tn.blasti.activity.PostDetailsActivity;
import com.tn.blasti.api.models.posts.post.Post;
import com.tn.blasti.listeners.ListItemClickListener;
import com.tn.blasti.models.SelectableCategoryModel;
import com.tn.blasti.utility.ActivityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amine 15/12/2018.
 */

public class HomeSelectableCategoryAdapter extends RecyclerView.Adapter<HomeSelectableCategoryAdapter.ViewHolder> {

    private ArrayList<SelectableCategoryModel> selectableCategoryList;
    private List<List<Post>> categoryWisePostList;
    private Context context;
    private Activity activity;
    private ListItemClickListener itemClickListener;

    public HomeSelectableCategoryAdapter(Context context, Activity activity, ArrayList<SelectableCategoryModel> selectableCategoryList, List<List<Post>> categoryWisePostList) {
        this.context = context;
        this.activity = activity;
        this.selectableCategoryList = selectableCategoryList;
        this.categoryWisePostList = categoryWisePostList;
    }

    public void setItemClickListener(ListItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public HomeSelectableCategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selectable_cat_first_list, parent, false);
        return new HomeSelectableCategoryAdapter.ViewHolder(view, viewType, itemClickListener);
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvCategoryTitle;
        private TextView tvSeeMore;
        private RecyclerView rvCatWisePosts;
        private ListItemClickListener itemClickListener;

        private HomeSelectableCatWisePostAdapter catWisePostAdapter;
        private ArrayList<Post> posts;

        public ViewHolder(View itemView, int viewType, ListItemClickListener itemClickListener) {
            super(itemView);

            this.itemClickListener = itemClickListener;
            // Find all views ids
            tvCategoryTitle = (TextView) itemView.findViewById(R.id.tv_category_title);
            tvSeeMore = (TextView) itemView.findViewById(R.id.txt_see_more_category);
            rvCatWisePosts = (RecyclerView) itemView.findViewById(R.id.rvSelectableCategories);

            posts = new ArrayList<>();
            catWisePostAdapter = new HomeSelectableCatWisePostAdapter(context, posts);
            rvCatWisePosts.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            rvCatWisePosts.setAdapter(catWisePostAdapter);

            // Implement click listener over layout
            catWisePostAdapter.setItemClickListener(new ListItemClickListener() {
                @Override
                public void onItemClick(int position, View view) {
                    int clickedPostId = posts.get(position).getID().intValue();
                    switch (view.getId()) {
                        case R.id.card_view_top:
                            ActivityUtils.getInstance().invokePostDetails(activity, PostDetailsActivity.class, clickedPostId, false);
                            break;
                        default:
                            break;
                    }
                }
            });

            // Implement click listener over views that we need
            tvSeeMore.setOnClickListener(this);

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
        return (null != selectableCategoryList ? selectableCategoryList.size() : 0);

    }

    @Override
    public void onBindViewHolder(HomeSelectableCategoryAdapter.ViewHolder mainHolder, int position) {
        final SelectableCategoryModel model = selectableCategoryList.get(position);


        // setting data over views
        mainHolder.tvCategoryTitle.setText(Html.fromHtml(model.getCategoryName()));


        //bind the horizontal post list for each category
        mainHolder.posts.clear();
        if (!categoryWisePostList.isEmpty()) {
            mainHolder.posts.addAll(categoryWisePostList.get(position));
        }
        mainHolder.catWisePostAdapter.notifyDataSetChanged();

    }

}
