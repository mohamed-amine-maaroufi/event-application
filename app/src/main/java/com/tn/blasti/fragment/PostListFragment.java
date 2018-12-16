package com.tn.blasti.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tn.blasti.R;
import com.tn.blasti.activity.PostDetailsActivity;
import com.tn.blasti.adapters.PostsAdapter;
import com.tn.blasti.api.http.ApiUtils;
import com.tn.blasti.api.models.posts.post.Post;
import com.tn.blasti.data.constant.AppConstant;
import com.tn.blasti.listeners.ListItemClickListener;
import com.tn.blasti.utility.ActivityUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by amine 15/12/2018.
 */

public class PostListFragment extends Fragment {

    private int selectedCategoryId;
    private String searchText;
    private ImageView imgFeatured;
    private TextView tvRecentPostTitle;
    private LinearLayout loadingView, noDataView;
    private RelativeLayout rlTopPost;
    private RecyclerView rvPosts;
    private PostsAdapter postsAdapter = null;

    private List<Post> postList;
    private Post firstPost = null;
    int pageCount = 1;

    private RelativeLayout bottomLayout;
    private GridLayoutManager mLayoutManager;
    private boolean userScrolled = true;
    int pastVisibleItems, visibleItemCount, totalItemCount;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_post_list, container, false);


        initVar();
        initView(rootView);
        initFunctionality(rootView);
        initListener();

        implementScrollListener();

        return rootView;
    }


    public void initVar() {

        postList = new ArrayList<>();

        Bundle bundle = getArguments();
        if (bundle != null) {
            selectedCategoryId = getArguments().getInt(AppConstant.BUNDLE_KEY_CATEGORY_ID);
            searchText = getArguments().getString(AppConstant.BUNDLE_KEY_SEARCH_TEXT);
        }
    }

    public void initView(View rootView) {
        rlTopPost = (RelativeLayout) rootView.findViewById(R.id.rl_top);
        imgFeatured = (ImageView) rootView.findViewById(R.id.img_pager);
        tvRecentPostTitle = (TextView) rootView.findViewById(R.id.recent_post_title);

        bottomLayout = (RelativeLayout) rootView.findViewById(R.id.rv_itemload);

        loadingView = (LinearLayout) rootView.findViewById(R.id.loadingView);
        noDataView = (LinearLayout) rootView.findViewById(R.id.noDataView);

        initLoader(rootView);

        rvPosts = (RecyclerView) rootView.findViewById(R.id.rvPosts);
        mLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        rvPosts.setLayoutManager(mLayoutManager);


    }

    public void initLoader(View rootView) {
        loadingView = (LinearLayout) rootView.findViewById(R.id.loadingView);
        noDataView = (LinearLayout) rootView.findViewById(R.id.noDataView);
    }

    public void showLoader() {
        if (loadingView != null) {
            loadingView.setVisibility(View.VISIBLE);
        }

        if (noDataView != null) {
            noDataView.setVisibility(View.GONE);
        }
    }

    public void hideLoader() {
        if (loadingView != null) {
            loadingView.setVisibility(View.GONE);
        }
        if (noDataView != null) {
            noDataView.setVisibility(View.GONE);
        }
    }

    public void showEmptyView() {
        if (loadingView != null) {
            loadingView.setVisibility(View.GONE);
        }
        if (noDataView != null) {
            noDataView.setVisibility(View.VISIBLE);
        }
    }


    public void initFunctionality(View rootView) {

        postsAdapter = new PostsAdapter(getActivity(), (ArrayList) postList);
        rvPosts.setAdapter(postsAdapter);

        showLoader();

        if (selectedCategoryId == AppConstant.DEFAULT_CATEGORY_ID) {
            loadSearchedPosts();
        } else {
            loadCategoryWisePosts();
        }
    }

    public void loadSearchedPosts() {
        ApiUtils.getApiInterface().getSearchedPosts(pageCount, searchText).enqueue(new Callback<List<Post>>() {
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

    public void loadPosts(Response<List<Post>> response) {
        postList.addAll(response.body());

        if (pageCount == 1 && postList.size() > 0 && getActivity() != null) {

            rlTopPost.setVisibility(View.VISIBLE);

            firstPost = postList.get(0);
            postList.remove(0);
            String title = firstPost.getTitle().getRendered();
            tvRecentPostTitle.setText(Html.fromHtml(title));

            String imgUrl = null;
            if (firstPost.getEmbedded().getWpFeaturedMedias().size() >= 1) {
                imgUrl = firstPost.getEmbedded().getWpFeaturedMedias().get(0).getMediaDetails().getSizes().getFullSize().getSourceUrl();
            }

            if (imgUrl != null) {
                Glide.with(getActivity())
                        .load(imgUrl)
                        .into(imgFeatured);
            } else {
                Glide.with(getActivity())
                        .load(R.color.imgPlaceholder)
                        .into(imgFeatured);
            }

            postsAdapter.notifyDataSetChanged();
            hideLoader();

        } else if (postList.size() == 0) {
            showEmptyView();
        }
        postsAdapter.notifyDataSetChanged();
    }

    public void initListener() {

        rlTopPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (firstPost != null) {
                    int firstPostId = firstPost.getID().intValue();
                    ActivityUtils.getInstance().invokePostDetails(getActivity(), PostDetailsActivity.class, firstPostId, false);
                }
            }
        });

        postsAdapter.setItemClickListener(new ListItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                int id = view.getId();
                int clickedPostId = postList.get(position).getID().intValue();

                if (id == R.id.card_view_top) {
                    ActivityUtils.getInstance().invokePostDetails(getActivity(), PostDetailsActivity.class, clickedPostId, false);
                }
            }
        });
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

                if (selectedCategoryId == AppConstant.DEFAULT_CATEGORY_ID) {
                    loadSearchedPosts();
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
