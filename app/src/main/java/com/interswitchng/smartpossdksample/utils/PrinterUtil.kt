package com.interswitchng.smartpossdksample.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import androidx.annotation.DrawableRes
import com.interswitchng.smartpos.emv.pax.services.POSDeviceImpl
import com.interswitchng.smartpos.shared.models.posconfig.PrintObject
import com.interswitchng.smartpos.shared.models.posconfig.PrintStringConfiguration

@SuppressLint("StaticFieldLeak")
object PrinterUtil {

    /** Because the logo (this project has 4) is set in various places, this holds the device instance so it can be used to
     set the logo. This extra complexity is NOT needed for your use case since you have just one
     logo.
     ----
     You can simply set the logo once, at the point of configuring the terminal as specified in
     MainActivity.
    */
    private var posDeviceInstance: POSDeviceImpl? = null

    private var printObjects: MutableList<PrintObject> = mutableListOf()

    fun resetPrintData() = printObjects.clear()

    fun getPrintData() = printObjects.toList()

    fun addText(text: String, isTitle: Boolean) {
        printObjects.add(PrintObject.Data(text, if (isTitle) titleConfig() else fieldConfig()))
    }

    fun addNewLine() {
        printObjects.add(PrintObject.Data("\n", fieldConfig()))
    }

    fun addBitmap(@DrawableRes drawableResId: Int, isBitmapResource: Boolean, context: Context) {
        val imageBitmap = if (isBitmapResource) {
            BitmapFactory.decodeResource(context.resources, drawableResId)
        } else {
            vectorDrawableToBitmap(context, drawableResId)
        }

        imageBitmap?.let { printObjects.add(PrintObject.BitMap(it)) }
    }

    private fun titleConfig(): PrintStringConfiguration {
        return PrintStringConfiguration(
            isTitle = true,
            isBold = false,
            displayCenter = false
        )
    }

    private fun fieldConfig(): PrintStringConfiguration {
        return PrintStringConfiguration(
            isTitle = false,
            isBold = false,
            displayCenter = false
        )
    }

    fun setPosDeviceInstance(deviceImpl: POSDeviceImpl?) {
        posDeviceInstance = deviceImpl
    }

    fun getPosDeviceInstance() = posDeviceInstance
}