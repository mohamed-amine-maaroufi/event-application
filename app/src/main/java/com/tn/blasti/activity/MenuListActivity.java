package com.tn.blasti.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.ads.AdView;
import com.tn.blasti.R;
import com.tn.blasti.adapters.MenuListAdapter;
import com.tn.blasti.adapters.SubMenuAdapter;
import com.tn.blasti.api.http.ApiUtils;
import com.tn.blasti.api.models.category.Category;
import com.tn.blasti.api.models.menus.MainMenu;
import com.tn.blasti.api.models.menus.SubMenu;
import com.tn.blasti.api.models.menus.SubMenuItem;
import com.tn.blasti.api.params.HttpParams;
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

public class MenuListActivity extends BaseActivity {

    private Activity mActivity;
    private Context mContext;

    private List<MainMenu> mAllMainMenu;
    private List<SubMenuItem> mAllSubMenuItem;
    private List<Category> mAllCategory;
    private MenuListAdapter menusAdapter = null;
    private SubMenuAdapter menuItemsAdapter = null;
    private RecyclerView menusRecyclerView;
    private int mPerPage = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVar();
        initView();
        initFunctionality();
    }

    private void initVar() {
        mActivity = MenuListActivity.this;
        mContext = mActivity.getApplicationContext();

        mAllMainMenu = new ArrayList<>();
        mAllSubMenuItem = new ArrayList<>();
        mAllCategory = new ArrayList<>();
    }

    private void initView() {
        setContentView(R.layout.activity_menu_or_home_cat_list);

        menusRecyclerView = (RecyclerView) findViewById(R.id.rv_menus);
        menusRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        initLoader();

        initToolbar();
        setToolbarTitle(getString(R.string.menu_list));
        enableBackButton();

    }

    private void initFunctionality() {
        showLoader();
        loadCategories();

        // show banner ads
        AdUtils.getInstance(mContext).showBannerAd((AdView) findViewById(R.id.adView));
    }

    public void loadCategories() {
        mAllCategory.clear();
        ApiUtils.getApiInterface().getCategories(mPerPage).enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful()) {

                    int totalPages = Integer.parseInt(response.headers().get(HttpParams.HEADER_TOTAL_PAGE));

                    if (totalPages > 1) {
                        mPerPage = mPerPage * totalPages;
                        loadCategories();

                    } else {
                        mAllCategory.addAll(response.body());
                        loadMenus();
                    }
                    //hideLoader();
                } else {
                    showEmptyView();
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                hideLoader();
                showEmptyView();
                t.printStackTrace();
            }
        });

    }

    public void loadMenus() {
        mAllMainMenu.clear();
        ApiUtils.getApiInterface().getMenus().enqueue(new Callback<List<MainMenu>>() {
            @Override
            public void onResponse(Call<List<MainMenu>> call, Response<List<MainMenu>> response) {
                if (response.isSuccessful()) {

                    mAllMainMenu.addAll(response.body());

                    if (mAllMainMenu.size() > 1) {
                        menusAdapter = new MenuListAdapter(MenuListActivity.this, (ArrayList) mAllMainMenu);
                        menusRecyclerView.setAdapter(menusAdapter);// set adapter on recyclerview
                        menusAdapter.setItemClickListener(new ListItemClickListener() {
                            @Override
                            public void onItemClick(int position, View view) {
                                int clickedItemPosition = mAllMainMenu.get(position).getID().intValue();
                                ActivityUtils.getInstance().invokeSubMenuList(mActivity, SubMenuListActivity.class, (ArrayList) mAllCategory, clickedItemPosition, false);
                        }
                    });
                    hideLoader();
                } else {
                    loadSubmenu();
                }
            } else

            {
                showEmptyView();
            }
        }

        @Override
        public void onFailure (Call < List < MainMenu >> call, Throwable t){
            hideLoader();
            showEmptyView();
            t.printStackTrace();
        }
    });

}

    public void loadSubmenu() {
        int menuID = mAllMainMenu.get(0).getID().intValue();
        ApiUtils.getApiInterface().getSubMenus(menuID).enqueue(new Callback<SubMenu>() {
            @Override
            public void onResponse(Call<SubMenu> call, Response<SubMenu> response) {
                if (response.isSuccessful()) {
                    mAllSubMenuItem = response.body().getSubMenus();
                    menuItemsAdapter = new SubMenuAdapter(MenuListActivity.this, (ArrayList) mAllSubMenuItem);
                    menusRecyclerView.setAdapter(menuItemsAdapter);

                    menuItemsAdapter.setItemClickListener(new ListItemClickListener() {
                        @Override
                        public void onItemClick(int position, View view) {

                            SubMenuItem clickedSubMenu = mAllSubMenuItem.get(position);

                            if (clickedSubMenu.getSubMenuItems().size() == 0) {

                                switch (clickedSubMenu.getObject()) {
                                    case AppConstant.MENU_ITEM_CATEGORY:

                                        Category category = new Category(clickedSubMenu.getObjectID(), clickedSubMenu.getTitle(), 0.0, 0.0);
                                        List<Category> categories = new ArrayList<>();
                                        categories.add(category);
                                        ActivityUtils.getInstance().invokeSubCategoryList(mActivity, SubCategoryListActivity.class, (ArrayList) mAllCategory, (ArrayList) categories, false);
                                        break;

                                    case AppConstant.MENU_ITEM_PAGE:
                                    case AppConstant.MENU_ITEM_CUSTOM:
                                        ActivityUtils.getInstance().invokeCustomPostAndLink(mActivity, CustomLinkAndPageActivity.class, clickedSubMenu.getTitle(), clickedSubMenu.getUrl(), false);
                                        break;

                                    case AppConstant.MENU_ITEM_POST:
                                        ActivityUtils.getInstance().invokePostDetails(mActivity, PostDetailsActivity.class, clickedSubMenu.getObjectID().intValue(), false);
                                        break;

                                    default:
                                        break;
                                }

                            } else {
                                ActivityUtils.getInstance().invokeSubSubMenuList(mActivity, SubSubMenuListActivity.class, (ArrayList) mAllCategory, clickedSubMenu, false);
                            }
                        }
                    });
                    hideLoader();
                } else {
                    showEmptyView();
                }
            }

            @Override
            public void onFailure(Call<SubMenu> call, Throwable t) {
                hideLoader();
                showEmptyView();
                t.printStackTrace();
            }
        });

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
