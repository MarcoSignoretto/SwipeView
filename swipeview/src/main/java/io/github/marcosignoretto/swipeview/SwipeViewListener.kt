package io.github.marcosignoretto.swipeview

/**
 * Created by
 * Marco Signoretto
 * Android Developer
 * on 2019-04-20.
 */
interface SwipeViewListener {
    /**
     * This function will be called when the back view is now visible
     */
    fun onOpen()

    /**
     * This function will be called when the view come back to the original closed state,
     * back view is not now visible
     */
    fun onClose()
}