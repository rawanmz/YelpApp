package com.bignerdranch.android.yelpapp

import android.content.Context
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

abstract class SwipeDelete(context: Context,dragDir:Int,Swipedir:Int)
    :ItemTouchHelper.SimpleCallback(dragDir,Swipedir) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
           return false
    }
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
    }
}