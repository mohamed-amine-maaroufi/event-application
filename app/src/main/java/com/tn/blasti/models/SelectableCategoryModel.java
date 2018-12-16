package com.tn.blasti.models;

import java.util.UUID;

/**
 * Created by amine 15/12/2018.
 */

public class SelectableCategoryModel {
    int id;
    int categoryId;
    String categoryName;
    int categoryOrder;

    public SelectableCategoryModel(int id, int categoryId, String categoryName, int categoryOrder) {
        this.id = id;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryOrder = categoryOrder;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryOrder() {
        return categoryOrder;
    }

    public void setCategoryOrder(int categoryOrder) {
        this.categoryOrder = categoryOrder;
    }

}
