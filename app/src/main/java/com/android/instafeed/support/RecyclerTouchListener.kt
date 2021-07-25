package com.android.instafeed.support

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

class RecyclerTouchListener(context: Context, private val clickListener: OnItemClickListener) :
    RecyclerView.OnItemTouchListener {
    private val gestureDetector: GestureDetector =
        GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                return true
            }
        })

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        val child = rv.findChildViewUnder(e.x, e.y)
        if (child != null && rv.adapter?.getItemViewType(rv.getChildAdapterPosition(child)) == AppConstants.AdapterViewType.DATA_VIEW_TYPE && gestureDetector.onTouchEvent(
                e
            )
        ) {
            clickListener.onClick(child, rv.getChildAdapterPosition(child))
        }
        return false
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}

}