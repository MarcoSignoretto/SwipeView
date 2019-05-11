package io.github.marcosignoretto.swipeview

import android.animation.ValueAnimator
import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.ViewCompat
import it.marcosignoretto.swipeview.R

/**
 * Created by
 * Marco Signoretto
 * Android Developer
 * on 2019-04-20.
 */
class SwipeView : ConstraintLayout {

    private var openingSize = 0.0f
    private var openingSide = 0
    private var frontViewClickListener: OnClickListener? = null
    private var backViewClickListener: OnClickListener? = null
    private var swipeListener: SwipeViewListener? = null

    private var isOpen = false
    private lateinit var frontView: View
    private lateinit var backView: View

    constructor(context: Context?) : super(context) {
        init(null)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        if (this.id == -1) {
            this.id = ViewCompat.generateViewId()
        }

        @LayoutRes
        var frontLayoutRes = R.layout.front_layout_default
        @LayoutRes
        var backLayoutRes = R.layout.back_layout_default
        openingSize = -Math.abs(context.resources.getDimension(R.dimen.opening_size_default))

        openingSide = 0
        if (attrs != null) {
            val ta = context.obtainStyledAttributes(attrs, R.styleable.SwipeView)
            frontLayoutRes = ta.getResourceId(
                R.styleable.SwipeView_front_layout,
                R.layout.front_layout_default
            )
            backLayoutRes = ta.getResourceId(
                R.styleable.SwipeView_back_layout,
                R.layout.back_layout_default
            )
            openingSize = -Math.abs(
                ta.getDimension(
                    R.styleable.SwipeView_opening_size,
                    context.resources.getDimension(R.dimen.opening_size_default)
                )
            )
            openingSide = ta.getInt(R.styleable.SwipeView_opening_side, 0)
            ta.recycle()
        }
        // Inflate views
        frontView = LayoutInflater.from(context).inflate(frontLayoutRes, this, false)
        if (frontView.id == -1) {
            frontView.id = ViewCompat.generateViewId()
        }
        backView = LayoutInflater.from(context).inflate(backLayoutRes, this, false)
        if (backView.id == -1) {
            backView.id = ViewCompat.generateViewId()
        }

        // Add views to layout
        addView(backView)
        addView(frontView)
        // Add constraints
        val constraintSet = ConstraintSet()
        constraintSet.clone(this)
        constraintSet.match(frontView, this)
        constraintSet.match(backView, this)
        this.setConstraintSet(constraintSet)

        backView.setOnClickListener {
            if (isOpen) {
                backViewClickListener?.onClick(it)
            }
        }
        frontView.setOnTouchListener(object : SwipeViewOnTouchListener(context) {
            override fun onSwipeLeft(distanceX: Float) {
                if (openingSide == 0) {
                    frontView.translationX =
                        if (frontView.translationX + distanceX < openingSize) openingSize else frontView.translationX + distanceX
                } else if (openingSide == 1) {
                    frontView.translationX =
                        if (frontView.translationX + distanceX < 0f) 0f else frontView.translationX + distanceX
                }
            }

            override fun onSwipeRight(distanceX: Float) {
                if (openingSide == 0) {
                    frontView.translationX =
                        if (frontView.translationX + distanceX > 0f) 0f else frontView.translationX + distanceX
                } else if (openingSide == 1) {
                    frontView.translationX =
                        if (frontView.translationX + distanceX > -openingSize) -openingSize else frontView.translationX + distanceX
                }

            }

            override fun onSwipeTop(distanceY: Float) {

            }

            override fun onSwipeBottom(distanceY: Float) {

            }

            override fun onClick() {
                if (!isOpen) {
                    frontViewClickListener?.onClick(frontView)
                } else {
                    close()
                }
            }

            override fun onSwipeComplete() {
                if (openingSide == 0) {
                    if (frontView.translationX > (openingSize / 2f)) { // close it
                        close()
                    } else { // open it
                        open()
                    }
                } else if (openingSide == 1) {
                    if (frontView.translationX < (-openingSize / 2f)) { // close it
                        close()
                    } else { // open it
                        open()
                    }
                }
            }
        })
    }

    override fun onSaveInstanceState(): Parcelable? {
        val savedState = SavedState(super.onSaveInstanceState())
        savedState.isOpen = isOpen
        return savedState
    }

    public override fun onRestoreInstanceState(state: Parcelable) {
        if (state is SavedState) {
            super.onRestoreInstanceState(state.superState)
            isOpen = state.isOpen
        } else {
            super.onRestoreInstanceState(state)
        }

        restore(isOpen)
    }

    private fun restore(isOpen: Boolean) {
        if (isOpen) {
            open()
        } else {
            close()
        }
    }

    override fun setOnClickListener(l: OnClickListener?) {
        frontViewClickListener = l
    }

    /**
     * Register a listener to handle click on the back view, the onClick on the backView
     * will be called only if the swipeView is open
     * @param l: ClickListener for the backView
     */
    fun setOnBackViewClickListener(l: OnClickListener?) {
        backViewClickListener = l
    }

    /**
     * Set the argument as a listener for the swipe view events
     * @param sl: SwipeViewListener to notify on swipe view events
     */
    fun setOnSwipeViewListener(sl: SwipeViewListener?) {
        swipeListener = sl
    }

    /**
     * Open the swipe view
     */
    fun open() {
        if (openingSide == 0) {
            val animator = ValueAnimator.ofFloat(frontView.translationX, openingSize).setDuration(200)
            animator.addUpdateListener {
                frontView.translationX = it.animatedValue as Float
            }
            animator.start()
            isOpen = true
            swipeListener?.onOpen()
        } else if (openingSide == 1) {
            val animator = ValueAnimator.ofFloat(frontView.translationX, -openingSize).setDuration(200)
            animator.addUpdateListener {
                frontView.translationX = it.animatedValue as Float
            }
            animator.start()
            isOpen = true
            swipeListener?.onOpen()
        }
    }

    /**
     * close the swipe view
     */
    fun close() {
        val animator = ValueAnimator.ofFloat(frontView.translationX, 0f).setDuration(200)
        animator.addUpdateListener {
            frontView.translationX = it.animatedValue as Float
        }
        animator.start()
        isOpen = false
        swipeListener?.onClose()
    }
}