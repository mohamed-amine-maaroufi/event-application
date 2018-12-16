package com.tn.blasti.listeners;


import com.tn.blasti.api.models.category.Category;

import java.util.List;

/**
 * Created by amine 15/12/2018.
 */
public interface OnCategoryListChangedListener {
    void onListChanged(List<Category> categories);
}
