package com.onix.internship.arch.component.device

import android.os.Build

class LibDeviceInfo : AndroidDeviceInfo() {
    override val manufacturer: String = Build.MANUFACTURER
    override val model: String = Build.MODEL
    override val systemVersion: String = Build.VERSION.RELEASE
    override val device: String = Build.DEVICE
    override val versionCode: Int = 1
    override val versionName: String = "1.0.0"
}