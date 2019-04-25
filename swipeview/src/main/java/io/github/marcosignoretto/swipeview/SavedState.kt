package io.github.marcosignoretto.swipeview

import android.os.Parcel
import android.os.Parcelable
import android.view.View

/**
 * Created by
 * Marco Signoretto
 * Android Developer
 * on 2019-04-24.
 */
class SavedState : View.BaseSavedState {

    var isOpen: Boolean = false

    constructor(source: Parcel) : super(source) {
        isOpen = source.readByte().toInt() != 0
    }

    constructor(superState: Parcelable?) : super(superState)

    override fun writeToParcel(out: Parcel, flags: Int) {
        super.writeToParcel(out, flags)
        out.writeByte((if (isOpen) 1 else 0).toByte())
    }

    @JvmField
    val CREATOR: Parcelable.Creator<SavedState> = object : Parcelable.Creator<SavedState> {

        override fun createFromParcel(source: Parcel): SavedState {
            return SavedState(source)
        }

        override fun newArray(size: Int): Array<SavedState?> {
            return arrayOfNulls(size)
        }
    }
}