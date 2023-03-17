package com.ars.groceriesapp.ui.home.favorites

import android.animation.ValueAnimator.AnimatorUpdateListener
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.LottieCompositionFactory
import com.airbnb.lottie.LottieDrawable
import com.airbnb.lottie.LottieResult
import com.ars.groceriesapp.R


class DeleteOnSwipe(
    private val context: Context
) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
    private var onSwiped: ((position: Int) -> Unit) = {}

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }


    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        onSwiped(viewHolder.bindingAdapterPosition)
    }


    fun addOnSwipedListener(onSwiped: (position: Int) -> Unit) {
        this.onSwiped = onSwiped
    }
}