package com.example.amour.mynotes;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by Amour on 2016/4/8.
 */
public class MyItemTouchHelperCallback extends ItemTouchHelper.Callback {
    private OnMoveOrSwipeListener listener;

    public MyItemTouchHelperCallback(OnMoveOrSwipeListener listener) {
        this.listener = listener;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        /*
        设置为只能侧滑
         */
        int dragFlags=ItemTouchHelper.UP|ItemTouchHelper.DOWN;
        int swipeFlags=ItemTouchHelper.START;

        return makeMovementFlags(dragFlags,swipeFlags);
    }

    /*
    item被拖拽或侧滑时会回调onMove和onSwied方法，这时需要更改Adapter中的数据，所以Adapter需要实现一个回调接口
    onMoveAndSwipedListener（）

     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        listener.onItemMove(viewHolder.getAdapterPosition(),target.getAdapterPosition());
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        listener.onItemSwipe(viewHolder.getAdapterPosition());

    }
}
