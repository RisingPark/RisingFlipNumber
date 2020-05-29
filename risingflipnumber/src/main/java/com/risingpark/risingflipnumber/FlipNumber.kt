package com.risingpark.risingflipnumber

import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.widget.ImageView


internal class FlipNumber(
    private val context: Context,
    private val id: Int,
    view: View,
    private val onAnimComplete: OnAnimationComplete?
) : AnimationListener {
    private var flipImage_BackUpper: ImageView? = null
    private var flipImage_BackLower: ImageView? = null
    private var flipImage_FrontUpper: ImageView? = null
    private var flipImage_FrontLower: ImageView? = null
    private var animation1: Animation? = null
    private var animation2: Animation? = null
    private var animateTo = 0
    private var animateFrom = 0

    interface OnAnimationComplete {
        fun onComplete(id: Int)
    }

    fun setDigit(digit: Int, withAnimation: Boolean) {
        var digit = digit
        if (digit < 0) digit = 0
        if (digit > 9) digit = 9
        animateTo = digit
        if (withAnimation) animateDigit(true) else setDigitImageToAll(digit)
    }

    private fun animateDigit(isUpper: Boolean) {
        animateFrom = getLastDigit(isUpper)
        startAnimation()
    }

    private fun init() {
        flipImage_BackUpper!!.tag = 0
        flipImage_BackLower!!.tag = 0
        flipImage_FrontUpper!!.tag = 0
        flipImage_FrontLower!!.tag = 0
        animation1 = AnimationUtils.loadAnimation(context, R.anim.flip_point_to_middle)
        animation1?.setAnimationListener(this)
        animation2 = AnimationUtils.loadAnimation(context, R.anim.flip_point_from_middle)
        animation2?.setAnimationListener(this)
    }

    fun setupAnim(duration: Int) {
        animation1!!.duration = duration.toLong()
        animation2!!.duration = duration.toLong()
    }

    private fun startAnimation() {
        if (animateTo == animateFrom) {
            flipImage_FrontUpper!!.visibility = View.INVISIBLE
            setDigit(animateTo, false)
            onAnimComplete?.onComplete(id)
        } else {
            startDigitAnimation(true)
        }
    }

    private fun startDigitAnimation(isUpper: Boolean) {
        if (isUpper) {
            flipImage_FrontUpper!!.clearAnimation()
            flipImage_FrontUpper!!.animation = animation1
            flipImage_FrontUpper!!.startAnimation(animation1)
        } else {
            flipImage_FrontLower!!.clearAnimation()
            flipImage_FrontLower!!.animation = animation2
            flipImage_FrontLower!!.startAnimation(animation2)
        }
    }

    private fun incrementFromCode() {
        animateFrom++
        if (animateFrom < 0) animateFrom = 0
        if (animateFrom > 9) animateFrom = 9
    }

    private fun getLastDigit(isUpper: Boolean): Int {
        var digit = 0
        try {
            digit =
                if (isUpper) flipImage_FrontUpper!!.tag as Int else flipImage_FrontLower!!.tag as Int
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (digit > 9) digit = 0
        return digit
    }

    private val digitToShow: Int
        private get() = if (animateFrom + 1 > 9) 0 else animateFrom + 1

    private fun setDigitImageToAll(digit: Int) {
        setDigitImage(digit, true, flipImage_BackUpper)
        setDigitImage(digit, false, flipImage_BackLower)
        setDigitImage(digit, true, flipImage_FrontUpper)
        setDigitImage(digit, false, flipImage_FrontLower)
    }

    private fun setDigitImage(
        digitToShow: Int,
        isUpper: Boolean,
        image: ImageView?
    ) {
        image!!.tag = digitToShow
        var resource = 0
        when (digitToShow) {
            0 -> resource = if (isUpper) R.drawable.digit_0_upper else R.drawable.digit_0_lower
            1 -> resource = if (isUpper) R.drawable.digit_1_upper else R.drawable.digit_1_lower
            2 -> resource = if (isUpper) R.drawable.digit_2_upper else R.drawable.digit_2_lower
            3 -> resource = if (isUpper) R.drawable.digit_3_upper else R.drawable.digit_3_lower
            4 -> resource = if (isUpper) R.drawable.digit_4_upper else R.drawable.digit_4_lower
            5 -> resource = if (isUpper) R.drawable.digit_5_upper else R.drawable.digit_5_lower
            6 -> resource = if (isUpper) R.drawable.digit_6_upper else R.drawable.digit_6_lower
            7 -> resource = if (isUpper) R.drawable.digit_7_upper else R.drawable.digit_7_lower
            8 -> resource = if (isUpper) R.drawable.digit_8_upper else R.drawable.digit_8_lower
            9 -> resource = if (isUpper) R.drawable.digit_9_upper else R.drawable.digit_9_lower
        }
        image.setImageResource(resource)
    }

    override fun onAnimationEnd(animation: Animation) {
        if (animation === animation1) {
            flipImage_FrontUpper!!.visibility = View.INVISIBLE
            startDigitAnimation(false)
        } else if (animation === animation2) {
            flipImage_FrontUpper!!.visibility = View.VISIBLE
            setDigitImage(digitToShow, true, flipImage_FrontUpper)
            setDigitImage(digitToShow, false, flipImage_BackLower)
            incrementFromCode()
            animateDigit(false)
        }
    }

    override fun onAnimationRepeat(arg0: Animation) {}
    override fun onAnimationStart(animation: Animation) {
        if (animation === animation1) {
            flipImage_FrontLower!!.visibility = View.INVISIBLE
            flipImage_FrontUpper!!.visibility = View.VISIBLE
            setDigitImage(digitToShow, false, flipImage_FrontLower)
            setDigitImage(digitToShow, true, flipImage_BackUpper)
        } else if (animation === animation2) {
            flipImage_FrontLower!!.visibility = View.VISIBLE
        }
    }

    init {
        flipImage_BackUpper =
            view.findViewById<View>(R.id.FlipMeterSpinner_image_back_upper) as ImageView
        flipImage_BackLower =
            view.findViewById<View>(R.id.FlipMeterSpinner_image_back_lower) as ImageView
        flipImage_FrontUpper =
            view.findViewById<View>(R.id.FlipMeterSpinner_image_front_upper) as ImageView
        flipImage_FrontLower =
            view.findViewById<View>(R.id.FlipMeterSpinner_image_front_lower) as ImageView
        init()
    }
}