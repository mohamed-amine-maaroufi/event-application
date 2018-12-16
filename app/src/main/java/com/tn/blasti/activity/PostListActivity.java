package com.tn.blasti.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdView;
import com.tn.blasti.R;
import com.tn.blasti.adapters.PostsAdapter;
import com.tn.blasti.api.http.ApiUtils;
import com.tn.blasti.api.models.posts.post.Post;
import com.tn.blasti.data.constant.AppConstant;
import com.tn.blasti.listeners.ListItemClickListener;
import com.tn.blasti.utility.ActivityUtils;
import com.tn.blasti.utility.AdUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by amine 15/12/2018.
 */
public class PostListActivity extends BaseActivity {
    private Activity mActivity;
    private Context mContext;
    private int selectedCategoryId;
    private String categoryTitle;
    private boolean isFromFeatured;
    private RecyclerView rvPosts;
    private PostsAdapter postsAdapter = null;
    private List<Post> postList;
    private RelativeLayout bottomLayout;
    private GridLayoutManager mLayoutManager;
    private boolean userScrolled = true;
    private int pastVisibleItems, visibleItemCount, totalItemCount;
    private int pageCount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVar();
        initView();
        initFunctionality();
        initListener();
        implementScrollListener();
    }

    private void initVar() {
        mActivity = PostListActivity.this;
        mContext = mActivity.getApplicationContext();

        postList = new ArrayList<>();

        Intent intent = getIntent();
        if (intent != null) {
            selectedCategoryId = intent.getIntExtra(AppConstant.BUNDLE_KEY_CATEGORY_ID, 0);
            categoryTitle = intent.getStringExtra(AppConstant.BUNDLE_KEY_CATEGORY_TITLE);
            isFromFeatured = intent.getBooleanExtra(AppConstant.BUNDLE_KEY_FEATURED, false);
        }
    }

    private void initView() {
        setContentView(R.layout.activity_post_list);

        bottomLayout = (RelativeLayout) findViewById(R.id.rv_itemload);
        rvPosts = (RecyclerView) findViewById(R.id.rvPosts);
        mLayoutManager = new GridLayoutManager(mActivity, 2, GridLayoutManager.VERTICAL, false);
        rvPosts.setLayoutManager(mLayoutManager);

        initLoader();

        initToolbar();
        setToolbarTitle(Html.fromHtml(categoryTitle).toString());
        enableBackButton();

    }

    private void initFunctionality() {

        postsAdapter = new PostsAdapter(mActivity, (ArrayList) postList);
        rvPosts.setAdapter(postsAdapter);

        showLoader();

        if (selectedCategoryId == AppConstant.DEFAULT_CATEGORY_ID && isFromFeatured) {
            loadAllFeaturedPosts();
        } else if (selectedCategoryId == AppConstant.DEFAULT_CATEGORY_ID && !isFromFeatured) {
            loadAllPosts();
        } else {
            loadCategoryWisePosts();
        }

        // show banner ads
        AdUtils.getInstance(mContext).showBannerAd((AdView) findViewById(R.id.adView));

    }

    public void initListener() {
        postsAdapter.setItemClickListener(new ListItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                int clickedPostId = postList.get(position).getID().intValue();
                switch (view.getId()) {
                    case R.id.card_view_top:
                        ActivityUtils.getInstance().invokePostDetails(mActivity, PostDetailsActivity.class, clickedPostId, false);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    public void loadCategoryWisePosts() {
        ApiUtils.getApiInterface().getPostsByCategory(pageCount, selectedCategoryId).enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    loadPosts(response);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                t.printStackTrace();
                showEmptyView();
            }
        });
    }


    public void loadAllPosts() {
        ApiUtils.getApiInterface().getRecentPosts(pageCount).enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    loadPosts(response);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                showEmptyView();
                t.printStackTrace();
            }
        });
    }


    public void loadAllFeaturedPosts() {
        ApiUtils.getApiInterface().getFeaturedPosts(pageCount).enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    loadPosts(response);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                t.printStackTrace();
                showEmptyView();
            }
        });
    }

    public void loadPosts(Response<List<Post>> response) {
        postList.addAll(response.body());
        if (postList.size() > 0) {
            postsAdapter.notifyDataSetChanged();
            hideLoader();
        } else {
            showEmptyView();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    // Implement scroll listener
    private void implementScrollListener() {
        rvPosts.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView,
                                             int newState) {

                super.onScrollStateChanged(recyclerView, newState);

                // If scroll state is touch scroll then set userScrolled
                // true
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    userScrolled = true;
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx,
                                   int dy) {

                super.onScrolled(recyclerView, dx, dy);
                // Here get the child count, item count and visibleitems
                // from layout manager

                visibleItemCount = mLayoutManager.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();

                // Now check if userScrolled is true and also check if
                // the item is end then update recycler view and set
                // userScrolled to false
                if (userScrolled && (visibleItemCount + pastVisibleItems) == totalItemCount) {
                    userScrolled = false;

                    updateRecyclerView();
                }

            }

        });

    }

    // Method for repopulating recycler view
    private void updateRecyclerView() {

        // Show Progress Layout
        bottomLayout.setVisibility(View.VISIBLE);

        // Handler to show refresh for a period of time you can use async task
        // while commnunicating serve

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                pageCount++;

                if (selectedCategoryId == AppConstant.DEFAULT_CATEGORY_ID && isFromFeatured) {
                    loadAllFeaturedPosts();
                } else if (selectedCategoryId == AppConstant.DEFAULT_CATEGORY_ID && !isFromFeatured) {
                    loadAllPosts();
                } else {
                    loadCategoryWisePosts();
                }

                // Toast for task completion
                //Toast.makeText(getActivity(), "Items Updated.", Toast.LENGTH_SHORT).show();

                // After adding new data hide the view.
                bottomLayout.setVisibility(View.GONE);
                userScrolled = true;
            }
        }, 5000);

    }

}