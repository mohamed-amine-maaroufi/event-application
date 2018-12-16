package com.tn.blasti.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tn.blasti.R;
import com.tn.blasti.api.models.posts.post.Post;
import com.tn.blasti.listeners.ListItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amine 15/12/2018.
 */

public class FeaturedPagerAdapter extends PagerAdapter {

    private List<Post> postList;
    private LayoutInflater inflater;
    private Context mContext;

    // Listener
    private ListItemClickListener mListener;

    public FeaturedPagerAdapter(Context context, List<Post> postList) {
        this.mContext = context;
        this.postList = postList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return postList.size();
    }


    @Override
    public Object instantiateItem(final ViewGroup view, final int position) {

        View rootView = inflater.inflate(R.layout.item_home_screen_view_pager, view, false);

        final ImageView mPagerImageView = (ImageView) rootView.findViewById(R.id.img_pager);
        final TextView mFeaturedPostTitleTextView = (TextView) rootView.findViewById(R.id.recent_post_title);
        final Post mPost = postList.get(position);


        String titleText = mPost.getTitle().getRendered();

        mFeaturedPostTitleTextView.setText(Html.fromHtml(titleText));

        String imgUrl = null;
        if (mPost.getEmbedded().getWpFeaturedMedias().size() >= 1) {
            imgUrl = mPost.getEmbedded().getWpFeaturedMedias().get(0).getMediaDetails().getSizes().getFullSize().getSourceUrl();
        }


        if (imgUrl != null) {
            Glide.with(mContext)
                    .load(imgUrl)
                    .into(mPagerImageView);
        }


        view.addView(rootView);

        mPagerImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onItemClick(position, view);
                }
            }
        });


        return rootView;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    public void setItemClickListener(ListItemClickListener mListener) {
        this.mListener = mListener;
    }

}