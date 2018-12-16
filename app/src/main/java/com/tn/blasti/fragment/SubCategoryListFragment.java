package com.tn.blasti.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tn.blasti.R;
import com.tn.blasti.activity.SubCategoryListActivity;
import com.tn.blasti.adapters.SubCategoryAdapter;
import com.tn.blasti.api.models.category.Category;
import com.tn.blasti.data.constant.AppConstant;
import com.tn.blasti.listeners.ListItemClickListener;
import com.tn.blasti.utility.ActivityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amine 15/12/2018.
 */

public class SubCategoryListFragment extends Fragment {

    ArrayList<Category> categoryList;
    ArrayList<Category> subCategoryList;
    private RecyclerView rvSubCategories;
    private SubCategoryAdapter subCategoriesAdapter = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.common_recycler_view, container, false);


        initVar();
        initView(rootView);
        initFunctionality(rootView);
        initListener();


        return rootView;
    }

    public void initVar() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            categoryList = getArguments().getParcelableArrayList(AppConstant.BUNDLE_KEY_CATEGORY_LIST);
            subCategoryList = getArguments().getParcelableArrayList(AppConstant.BUNDLE_KEY_SUB_CATEGORY_LIST);
        }
    }

    public void initView(View rootView) {
        rvSubCategories = (RecyclerView) rootView.findViewById(R.id.rvFavourite);
        rvSubCategories.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    }

    public void initFunctionality(View rootView) {
        subCategoriesAdapter = new SubCategoryAdapter(getActivity(), (ArrayList) categoryList, (ArrayList) subCategoryList);
        rvSubCategories.setAdapter(subCategoriesAdapter);
    }

    public void initListener() {
        subCategoriesAdapter.setItemClickListener(new ListItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                int id = view.getId();
                final Category model = subCategoryList.get(position);
                switch (id) {
                    case R.id.rl_menu_or_cat:
                        ArrayList<Category> mAllSubSubCategoryList = new ArrayList<>();
                        mAllSubSubCategoryList.clear();
                        for (Category category : categoryList) {
                            if (model.getID().intValue() == category.getParent().intValue()) {
                                mAllSubSubCategoryList.add(category);
                            }
                        }

                        if (mAllSubSubCategoryList.size() > 0) {
                            ActivityUtils.getInstance().invokeSubCategoryList((Activity) getActivity(), SubCategoryListActivity.class, (ArrayList) categoryList, (ArrayList) mAllSubSubCategoryList, false);
                        } else {
                            List<Category> theList = new ArrayList<>();
                            theList.add(model);
                            ActivityUtils.getInstance().invokeSubCategoryList((Activity) getActivity(), SubCategoryListActivity.class, (ArrayList) categoryList, (ArrayList) theList, false);
                        }
                        break;
                }
            }

        });
    }

}
