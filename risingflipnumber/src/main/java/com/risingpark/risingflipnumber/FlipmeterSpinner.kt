package com.risingpark.risingflipnumber

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout

class FlipmeterSpinner : RelativeLayout {

    var flipmeterSpinner: View? = null
        private set
    val currentDigit = 0
    private var flipNumber: FlipNumber? = null
    private val isRunning = false


    constructor(context: Context) : super(context) {
        initialize()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        initialize()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyle: Int
    ) : super(context, attrs, defStyle) {
        initialize()
    }

    private fun inflateLayout() {
        val layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        flipmeterSpinner = layoutInflater.inflate(R.layout.view_flipmeter_spinner, this)
    }

    fun setInAnimation(duration: Int) {
        flipNumber!!.setupAnim(duration)
    }

    fun setDigit(animateTo: Int, withAnimation: Boolean) {
        flipNumber!!.setDigit(animateTo, withAnimation)
    }

    /*
     * Initialize all of our class members and variables
     */
    private fun initialize() {
        inflateLayout()
        flipNumber = FlipNumber(context, id, flipmeterSpinner!!, null)
    }

}