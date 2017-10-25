package com.abid.paginationinandroid;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by abid on 10/25/17.
 */

public class DividerItemDecorator extends RecyclerView.ItemDecoration{
    private Drawable mdivider;
    DividerItemDecorator(Drawable drawable){
        mdivider=drawable;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getChildAdapterPosition(view) == 0) {
            return;
        }

        outRect.top = mdivider.getIntrinsicHeight();
    }
}
