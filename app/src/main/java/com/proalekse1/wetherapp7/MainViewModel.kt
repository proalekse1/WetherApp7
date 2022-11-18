package com.proalekse1.wetherapp7

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    val liveDataCurrent = MutableLiveData<String>() //для обновления большого фрагмента с погодой
    val liveDataList = MutableLiveData<List<String>>() //для обновления списка с погодой
}