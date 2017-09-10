package com.digitalmischief.smartir

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.google.android.things.contrib.driver.button.Button
import com.google.android.things.pio.PeripheralManagerService

private val TAG = MainActivity::class.java.simpleName
private val gpioButtonPinName = "BUS NAME"
private val GPIO_IR_PORT = "BCM17"

val manager = PeripheralManagerService()

class MainActivity : Activity() {
    private lateinit var mButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupPeripherals()
    }

    override fun onDestroy() {
        super.onDestroy()
//        destroyButton()
    }

    private fun setupPeripherals() {
        val portList = manager.gpioList
        if (portList.isEmpty()) {
            Log.d(TAG, "Cannot find any GPIO ports")
        } else {
            Log.d(TAG, "List of available ports: $portList")
//            setupIRInput()
        }

    }
//
//    private fun setupIRInput() {
//        val receiverGpiomanager.openGpio(GPIO_IR_PORT)
//    }

//    private fun setupButton() {
//        try {
//            mButton = Button(gpioButtonPinName,
//                    // high signal indicates the button is pressed
//                    // use with a pull-down resistor
//                    Button.LogicState.PRESSED_WHEN_HIGH
//            )
//            mButton.setOnButtonEventListener(object : Button.OnButtonEventListener {
//                override fun onButtonEvent(button: Button, pressed: Boolean) {
//                    // do something awesome
//                }
//            })
//        } catch (e: IOException) {
//            // couldn't configure the button...
//        }
//
//    }
//
//    private fun destroyButton() {
//        Log.i(TAG, "Closing button")
//        try {
//            mButton.close()
//        } catch (e: IOException) {
//            Log.e(TAG, "Error closing button", e)
//        }
//    }

}
