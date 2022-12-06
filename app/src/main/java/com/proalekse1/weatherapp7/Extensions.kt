package com.proalekse1.weatherapp7

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun Fragment.isPermissionGranted(p: String): Boolean { //проверяет включен или нет интернет

    return ContextCompat.checkSelfPermission( //возвращает 0 или -1 есть разрешение или нет разрешения
        activity as AppCompatActivity, p) == PackageManager.PERMISSION_GRANTED

}