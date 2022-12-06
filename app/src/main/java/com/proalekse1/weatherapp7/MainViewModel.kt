package com.proalekse1.weatherapp7

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.proalekse1.weatherapp7.adapters.WeatherModel

class MainViewModel: ViewModel() {
    val liveDataCurrent = MutableLiveData<WeatherModel>() //для обновления большого фрагмента с погодой
    val liveDataList = MutableLiveData<List<WeatherModel>>() //для обновления списка с погодой
}