package com.tn.blasti.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tn.blasti.R;
import com.tn.blasti.api.models.category.Category;
import com.tn.blasti.holders.ItemTouchHelperViewHolder;
import com.tn.blasti.listeners.ItemTouchHelperListeners;
import com.tn.blasti.listeners.ListItemClickListener;
import com.tn.blasti.listeners.OnCategoryListChangedListener;
import com.tn.blasti.listeners.OnStartDragListener;

import java.util.ArrayList;
import java.util.Collections;
/**
 * Created by amine 15/12/2018.
 */

public class SelectableCategoryAdapter extends RecyclerView.Adapter<SelectableCategoryAdapter.ViewHolder> implements ItemTouchHelperListeners {

    private ArrayList<Category> categoryList;
    private Context context;
    private ListItemClickListener itemClickListener;
    private OnStartDragListener dragStartListener;
    private OnCategoryListChangedListener listChangedListener;

    public SelectableCategoryAdapter(Context context, ArrayList<Category> categoryList, OnStartDragListener dragStartListener,
                                     OnCategoryListChangedListener listChangedListener) {
        this.context = context;
        this.categoryList = categoryList;
        this.dragStartListener = dragStartListener;
        this.listChangedListener = listChangedListener;
    }

    public void setItemClickListener(ListItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public SelectableCategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selectable_category, parent, false);
        return new SelectableCategoryAdapter.ViewHolder(view, viewType, itemClickListener);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, ItemTouchHelperViewHolder {

        private ImageButton btnDragNDrop;
        private TextView tvCategoryTitle;
        private CheckBox cbSelectionStatus;
        private RelativeLayout lytCategoryItem;
        private ListItemClickListener itemClickListener;

        //public RelativeLayout mPostListRelativeLayout;


        public ViewHolder(View itemView, int viewType, ListItemClickListener itemClickListener) {
            super(itemView);

            this.itemClickListener = itemClickListener;
            // Find all views ids
            btnDragNDrop = (ImageButton) itemView.findViewById(R.id.btn_drag_n_drop);
            tvCategoryTitle = (TextView) itemView.findViewById(R.id.each_category_title);
            cbSelectionStatus = (CheckBox) itemView.findViewById(R.id.selection_status);
            lytCategoryItem = (RelativeLayout) itemView.findViewById(R.id.rl_home_cat);

            // Implement click listener over views that we need
            lytCategoryItem.setOnClickListener(this);

            //btnDragNDrop.setOnClickListener(this);
            cbSelectionStatus.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(getLayoutPosition(), view);
            }
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }

    @Override
    public int getItemCount() {
        return (null != categoryList ? categoryList.size() : 0);

    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(categoryList, fromPosition, toPosition);
        listChangedListener.onListChanged(categoryList);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {

    }

    @Override
    public void onBindViewHolder(final SelectableCategoryAdapter.ViewHolder mainHolder, int position) {
        final Category model = categoryList.get(position);


        // setting data over views
        mainHolder.tvCategoryTitle.setText(Html.fromHtml(model.getName()));

        mainHolder.cbSelectionStatus.setChecked(model.isCategorySelected());

        mainHolder.btnDragNDrop.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    dragStartListener.onStartDrag(mainHolder);
                }
                return false;
            }
        });

    }

}
