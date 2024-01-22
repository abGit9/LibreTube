package com.github.libretube.ui.views

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_MOVE
import android.view.ViewConfiguration
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.github.libretube.helpers.ThemeHelper
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.elevation.SurfaceColors
import kotlin.math.abs

class CustomSwipeToRefresh(context: Context?, attrs: AttributeSet?) :
    SwipeRefreshLayout(context!!, attrs) {
    private val mTouchSlop: Int = ViewConfiguration.get(this.context).scaledTouchSlop
    private var mPrevX = 0f

    private var appBarLayout: AppBarLayout? = null
    private var isAppBarFullyExpanded = false

    init {
        setColorSchemeColors(
            ThemeHelper.getThemeColor(this.context, androidx.appcompat.R.attr.colorPrimary)
        )
        setProgressBackgroundColorSchemeColor(
            SurfaceColors.getColorForElevation(this.context, 20f)
        )
    }

    // Set the AppBarLayout reference
    fun setAppBarLayout(appBarLayout: AppBarLayout) {
        this.appBarLayout = appBarLayout
        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            // Check if the AppBarLayout is fully expanded

            isAppBarFullyExpanded = verticalOffset == 0
        })
    }

    @SuppressLint("Recycle")
    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        Log.d("Collapsing Toolbar","onInterceptTouchEvent")
        when (event.action) {


            MotionEvent.ACTION_DOWN ->{
                Log.d("Collapsing Toolbar","MotionEvent.ACTION_DOWN")

                mPrevX = MotionEvent.obtain(event).x
                // Check if the AppBarLayout is fully expanded
                if (!isAppBarFullyExpanded) {
                    return super.onInterceptTouchEvent(event)
                }
            }
            ACTION_MOVE -> {
                val eventX = event.x
                val xDiff = abs(eventX - mPrevX)
                if (xDiff > mTouchSlop) {
                    return false
                }
            }

        }
        return super.onInterceptTouchEvent(event)
    }


}
