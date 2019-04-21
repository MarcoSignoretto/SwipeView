package it.marcosignoretto.swipeview

import android.content.Context
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

/**
 * Created by
 * Marco Signoretto
 * Android Developer
 * on 2019-04-20.
 */
internal open class SwipeViewOnTouchListener(context: Context) : View.OnTouchListener {
    private val gestureDetector: GestureDetector

    init {
        gestureDetector = GestureDetector(context, GestureListener())
    }

    open fun onSwipeLeft(distanceX: Float) {}

    open fun onSwipeRight(distanceX: Float) {}

    open fun onSwipeComplete() {}

    open fun onClick() {}

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        var res = gestureDetector.onTouchEvent(event)
        if (event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_CANCEL) {
            onSwipeComplete()
            res = true
        }
        return res
    }

    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {

        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            onClick()
            return true
        }

        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onScroll(e1: MotionEvent, e2: MotionEvent, scrollDistanceX: Float, scrollDistanceY: Float): Boolean {
            val distanceX = e2.x - e1.x
            Log.i("TAG", "distanceX : $distanceX, lastAction: ${e2.action}")
            if (distanceX > 0) {
                onSwipeRight(distanceX)
            } else {
                onSwipeLeft(distanceX)
            }
            return true
        }
    }
}