package com.digitalmischief.smartir

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.google.android.things.contrib.driver.button.Button
import java.io.IOException

private val TAG = MainActivity::class.java.simpleName
private val gpioButtonPinName = "BUS NAME"

class MainActivity : Activity() {
    private lateinit var mButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupButton()
    }

    override fun onDestroy() {
        super.onDestroy()
        destroyButton()
    }

    private fun setupButton() {
        try {
            mButton = Button(gpioButtonPinName,
                    // high signal indicates the button is pressed
                    // use with a pull-down resistor
                    Button.LogicState.PRESSED_WHEN_HIGH
            )
            mButton.setOnButtonEventListener(object : Button.OnButtonEventListener {
                override fun onButtonEvent(button: Button, pressed: Boolean) {
                    // do something awesome
                }
            })
        } catch (e: IOException) {
            // couldn't configure the button...
        }

    }

    private fun destroyButton() {
        Log.i(TAG, "Closing button")
        try {
            mButton.close()
        } catch (e: IOException) {
            Log.e(TAG, "Error closing button", e)
        }
    }

}
