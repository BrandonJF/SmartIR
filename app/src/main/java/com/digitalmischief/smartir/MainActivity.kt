package com.digitalmischief.smartir

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.google.android.things.contrib.driver.button.Button
import com.google.android.things.pio.Gpio
import com.google.android.things.pio.GpioCallback
import com.google.android.things.pio.PeripheralManagerService

private val TAG = MainActivity::class.java.simpleName
private val gpioButtonPinName = "BUS NAME"
private val GPIO_BUTTON_PORT = "BCM13"
private val GPIO_LED_PORT = "BCM17"

val manager = PeripheralManagerService()
var buttonGpio: Gpio? = null
var ledGpio: Gpio? = null

class MainActivity : Activity() {
    private lateinit var mButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupPeripherals()
    }

    override fun onDestroy() {
        super.onDestroy()
        destroyGpio()
    }

    private fun setupPeripherals() {
        val portList = manager.gpioList
        if (portList.isEmpty()) {
            Log.d(TAG, "Cannot find any GPIO ports")
        } else {
            Log.d(TAG, "List of available ports: $portList")
            setupGPIO()
        }

    }

    private fun setupGPIO() {


        buttonGpio = manager.openGpio(GPIO_BUTTON_PORT)
        buttonGpio?.let{
            it.setDirection(Gpio.DIRECTION_IN)
            it.setEdgeTriggerType(Gpio.EDGE_FALLING)
            it.registerGpioCallback(mGpioCallback)
        }

        ledGpio = manager.openGpio(GPIO_LED_PORT)
        ledGpio?.let {
            it.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW)
        }




    }

    private val mGpioCallback = object : GpioCallback() {
        override fun onGpioEdge(gpio: Gpio?): Boolean {
            // Read the active low pin state
            if (gpio!!.value) {
                Log.d(TAG, "Reading a low value.")
            } else {
                // Pin is HIGH
                Log.d(TAG, "Reading a high value.")
                ledGpio!!.value = !ledGpio!!.value
            }

            // Continue listening for more interrupts
            return true
        }

        override fun onGpioError(gpio: Gpio?, error: Int) {
            Log.w(TAG, gpio.toString() + ": Error event " + error)
        }
    }
    private fun destroyGpio() {
        buttonGpio?.let {
            it.close()
            it.unregisterGpioCallback(mGpioCallback)
            buttonGpio = null

        }

    }

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
