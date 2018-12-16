package com.tn.blasti.adapters;


import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tn.blasti.R;
import com.tn.blasti.listeners.ListItemClickListener;
import com.tn.blasti.models.NotificationModel;

import java.util.ArrayList;
/**
 * Created by amine 15/12/2018.
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private Context mContext;

    private ArrayList<NotificationModel> dataList;

    // handle interface for item listener
    private ListItemClickListener itemClickListener;

    public NotificationAdapter(Context context, ArrayList<NotificationModel> dataList) {
        this.mContext = context;
        this.dataList = dataList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvTitle, tvSubTitle;
        private ImageView ivRemoveNotification;

        // handle interface for item listener
        private ListItemClickListener itemClickListener;

        public ViewHolder(View itemView, int viewType, final ListItemClickListener itemClickListener) {
            super(itemView);

            this.itemClickListener = itemClickListener;
            itemView.setOnClickListener(this);

            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvSubTitle = (TextView) itemView.findViewById(R.id.tvSubTitle);
            ivRemoveNotification = (ImageView) itemView.findViewById(R.id.ivRemoveNotification);

            // Listener
            ivRemoveNotification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(getLayoutPosition(), view);
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(getLayoutPosition(), view);
                    }
                }
            });
        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(getLayoutPosition(), view);
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new ViewHolder(view, viewType, itemClickListener);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String title = dataList.get(position).getTitle();
        String message = dataList.get(position).getMessage();

        if (title != null) {
            if (dataList.get(position).isUnread()) {
                holder.tvTitle.setTypeface(null, Typeface.BOLD);
            } else {
                holder.tvTitle.setTypeface(null, Typeface.NORMAL);
            }
            holder.tvTitle.setText(title);
            holder.tvSubTitle.setText(message);
        }


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void setItemClickListener(ListItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
