package com.decagon.facilitymanagementapp_group_two.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Point
import android.graphics.Rect
import android.graphics.RectF
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.decagon.facilitymanagementapp_group_two.R

var currentAnimator: Animator? = null
var shortAnimationDuration: Int = 0

/**
 * Animation template for display the full version of the profile image when it is clicked.
 */
fun zoomImage(view: View, imageResId: String?, root: View) {
    currentAnimator?.cancel()
    root.findViewById<TextView>(R.id.fragment_profile_btn_logout).visibility = View.GONE
    root.findViewById<TextView>(R.id.fragment_profile_main_name).visibility = View.GONE
    root.findViewById<TextView>(R.id.fragment_profile_stack_squad_text).visibility = View.GONE
    root.findViewById<LinearLayout>(R.id.fragment_profile_linear_layout).visibility = View.GONE

    val bigImage: ImageView = root.findViewById(R.id.fragment_profile_big_iv)
    imageResId?.let { bigImage.loadImage(it) }
    val startBoundsInt = Rect()
    val finalBoundsInt = Rect()
    val globalOffset = Point()
    view.getGlobalVisibleRect(startBoundsInt)
    root.findViewById<View>(R.id.frameLayout4)
        .getGlobalVisibleRect(finalBoundsInt, globalOffset)
    startBoundsInt.offset(-globalOffset.x, -globalOffset.y)
    finalBoundsInt.offset(-globalOffset.x, -globalOffset.y)
    val startBounds = RectF(startBoundsInt)
    val finalBounds = RectF(finalBoundsInt)
    val startScale: Float
    if ((finalBounds.width() / finalBounds.height() > startBounds.width() / startBounds.height())) {
        startScale = startBounds.height() / finalBounds.height()
        val startWidth: Float = startScale * finalBounds.width()
        val deltaWidth: Float = (startWidth - startBounds.width()) / 2
        startBounds.left -= deltaWidth.toInt()
        startBounds.right += deltaWidth.toInt()
    } else {
        startScale = startBounds.width() / finalBounds.width()
        val startHeight: Float = startScale * finalBounds.height()
        val deltaHeight: Float = (startHeight - startBounds.height()) / 2f
        startBounds.top -= deltaHeight.toInt()
        startBounds.bottom += deltaHeight.toInt()
        view.alpha = 0f
        bigImage.visibility = View.VISIBLE
        bigImage.pivotX = 0f
        bigImage.pivotY = 0f
        currentAnimator = AnimatorSet().apply {
            play(
                ObjectAnimator.ofFloat(
                    bigImage,
                    View.X,
                    startBounds.left,
                    finalBounds.left
                )
            ).apply {
                with(ObjectAnimator.ofFloat(bigImage, View.Y, startBounds.top, finalBounds.top))
                with(ObjectAnimator.ofFloat(bigImage, View.SCALE_X, startScale, 1f))
                with(ObjectAnimator.ofFloat(bigImage, View.SCALE_Y, startScale, 1f))
            }
            duration = shortAnimationDuration.toLong()
            interpolator = DecelerateInterpolator()
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    currentAnimator = null
                }
                override fun onAnimationCancel(animation: Animator) {
                    currentAnimator = null
                }
            })
            start()
        }
        bigImage.setOnClickListener {
            currentAnimator?.cancel()
            root.findViewById<TextView>(R.id.fragment_profile_btn_logout).visibility = View.VISIBLE
            root.findViewById<LinearLayout>(R.id.fragment_profile_linear_layout).visibility = View.VISIBLE
            root.findViewById<TextView>(R.id.fragment_profile_main_name).visibility = View.VISIBLE
            root.findViewById<TextView>(R.id.fragment_profile_stack_squad_text).visibility = View.VISIBLE

            currentAnimator = AnimatorSet().apply {
                play(ObjectAnimator.ofFloat(bigImage, View.X, startBounds.left)).apply {
                    with(ObjectAnimator.ofFloat(bigImage, View.Y, startBounds.top))
                    with(ObjectAnimator.ofFloat(bigImage, View.SCALE_X, startScale))
                    with(ObjectAnimator.ofFloat(bigImage, View.SCALE_Y, startScale))
                }
                duration = shortAnimationDuration.toLong()
                interpolator = DecelerateInterpolator()
                addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        view.alpha = 1f
                        bigImage.visibility = View.GONE
                        currentAnimator = null
                    }
                    override fun onAnimationCancel(animation: Animator) {
                        view.alpha = 1f
                        bigImage.visibility = View.GONE
                        currentAnimator = null
                    }
                })
                start()
            }
        }
    }
}
