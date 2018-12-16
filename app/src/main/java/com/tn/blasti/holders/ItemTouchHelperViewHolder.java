package com.tn.blasti.holders;
/**
 * Created by amine 15/12/2018.
 */
public interface ItemTouchHelperViewHolder {
    /**
     * Implementations should update the item view to indicate it's active state.
     */
    void onItemSelected();


    /**
     * state should be cleared.
     */
    void onItemClear();
}
