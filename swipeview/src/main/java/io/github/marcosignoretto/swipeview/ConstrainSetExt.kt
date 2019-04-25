package io.github.marcosignoretto.swipeview

import android.view.View
import androidx.constraintlayout.widget.ConstraintSet

/**
 * Created by
 * Marco Signoretto
 * Android Developer
 * on 2019-04-20.
 */
fun ConstraintSet.match(view: View, viewToMatch: View) {
    this.connect(view.id, ConstraintSet.TOP, viewToMatch.id, ConstraintSet.TOP)
    this.connect(view.id, ConstraintSet.BOTTOM, viewToMatch.id, ConstraintSet.BOTTOM)
    this.connect(view.id, ConstraintSet.START, viewToMatch.id, ConstraintSet.START)
    this.connect(view.id, ConstraintSet.END, viewToMatch.id, ConstraintSet.END)
}