package com.risingpark.risingflipnumber

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.widget_flipmeter.view.*
import kotlin.math.pow

class RisingFlipMeter : LinearLayout {

    private val mMaxDigit = 8
    private var mIsVisibleComma = false
    private var mDigit = 6
    private var preProgress:Long = 0
    private var items = ArrayList<FlipmeterSpinner>()

    constructor(context: Context?) : super(context) {
        initialize()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
        getAttrs(attrs)
        initialize()
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyle: Int
    ) : super(context, attrs) {
        getAttrs(attrs, defStyle)
        initialize()
    }

    private fun getAttrs(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.RisingFlipMeter)
        setTypeArray(typedArray)
    }

    private fun getAttrs(attrs: AttributeSet?, defStyle: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.RisingFlipMeter, defStyle, 0)
        setTypeArray(typedArray)
    }

    private fun setTypeArray(typedArray: TypedArray) {
        mIsVisibleComma = typedArray.getBoolean(R.styleable.RisingFlipMeter_visible_comma, mIsVisibleComma)
        mDigit = typedArray.getInt(R.styleable.RisingFlipMeter_digit, mDigit)
        typedArray.recycle()
    }

    private fun initialize() {
        LayoutInflater.from(context).inflate(R.layout.widget_flipmeter, this, true)
        if(mDigit > mMaxDigit){
            mDigit = mMaxDigit
        }
        //add Digit
        for (i in 0 until mDigit) {
            val flip = FlipmeterSpinner(context)
            widget_flip_meter_layout.addView(flip)
            items.add(flip)
        }
        // add Comma
        if (mIsVisibleComma)
            for (i in 0 until mDigit) {
                if (mDigit > 3
                    && i != 0
                    && i%3 == 0) {
                    widget_flip_meter_layout.addView(CommaView(context), mDigit-i)
                }
            }
    }

    fun setValue(value: Long, withAnimation: Boolean) {
        var checkValue = value
        if (mDigit < value.toString().length){
            val end = value.toString().length - mDigit;
            checkValue = value.toString().removeRange(0, end).toLong()
        }
        startFlip(checkValue, withAnimation)
    }

    fun clear() {
        preProgress = 0;
        for (digitSpinner in items) {
            digitSpinner.setDigit(0, false)
        }
    }

    fun startFlip(progress: Long, withAnimation: Boolean) {
        if (progress < 1){
            clear()
            return
        }
        val animator = ValueAnimator()
        animator.setObjectValues(preProgress, progress)
        preProgress = progress
        animator.addUpdateListener { animation ->
            val num = animation.animatedValue as Int
            var result = 0
            for (i in num.toString().indices) {
                result = if (i == 0){
                    num % 10
                } else {
                    if (i == num.toString().length-1){
                        num / (10.toDouble().pow(i.toDouble())).toInt()
                    } else{
                        val tenNum = (10.toDouble().pow((i+1).toDouble())).toInt()
                        (num % tenNum) / (tenNum/10)
                    }
                }

                items[mDigit-1-i].setDigit(result, withAnimation)
            }

        }

        animator.setEvaluator { fraction, startValue, endValue ->
            startValue as Long
            endValue as Long
            Math.round(startValue + (endValue - startValue) * fraction) }

        animator.duration = 900
        animator.start()
    }
}