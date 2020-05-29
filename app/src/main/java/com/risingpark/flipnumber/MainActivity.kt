package com.risingpark.flipnumber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView() {
        input_btn.setOnClickListener {
            flip_meter.clear()
            flip_meter.setValue(getNumber(), true)
            number_edit.text.clear()
        }
    }

    private fun getNumber(): Long {
        return if (number_edit.text?.toString() == "") 0 else number_edit.text.toString().toLong()
    }
}
