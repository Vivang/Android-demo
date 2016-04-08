package com.example.amour.mynotes;

/**
 * Created by Amour on 2016/4/8.
 */
public interface OnMoveOrSwipeListener {
     boolean onItemMove(int fromPosition,int toPosition);
    void onItemSwipe(int position);

}
