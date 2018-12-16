package com.tn.blasti.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.tn.blasti.R;

import com.tn.blasti.data.constant.AppConstant;
import com.tn.blasti.fragment.PostListFragment;

/**
 * Created by amine 15/12/2018.
 */
public class SearchActivity extends BaseActivity {

    private Activity mActivity;
    private Context mContext;
    private Fragment postListFragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVar();
        initView();
    }

    private void initVar() {
        mActivity = SearchActivity.this;
        mContext = mActivity.getApplicationContext();

    }

    private void initView() {
        setContentView(R.layout.activity_search);

        fragmentManager = getSupportFragmentManager();
        postListFragment = fragmentManager.findFragmentById(R.id.fragment_container);

        initToolbar();
        enableBackButton();
        setToolbarTitle(getString(R.string.search));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint(getString(R.string.search));
        searchView.postDelayed(new Runnable() {
            @Override
            public void run() {
                searchView.setIconifiedByDefault(true);
                searchView.setFocusable(true);
                searchView.setIconified(false);
                searchView.requestFocusFromTouch();
            }
        }, 200);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //some texts here

                Bundle args = new Bundle();
                postListFragment = new PostListFragment();
                args.putInt(AppConstant.BUNDLE_KEY_CATEGORY_ID, -1);
                args.putString(AppConstant.BUNDLE_KEY_SEARCH_TEXT, newText);
                postListFragment.setArguments(args);
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, postListFragment)
                        .commit();

                return false;
            }
        });


        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
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

}
