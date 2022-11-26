package com.proalekse1.wetherapp7.adapters

import android.view.View
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.proalekse1.wetherapp7.databinding.ListItemBinding

class WeatherAdapter : ListAdapter<WeatherModel, WeatherAdapter.Holder>() {

    class Holder(view: View) : RecyclerView.ViewHolder(view){ //хранит в себе разметк одного элемента
        val binding = ListItemBinding.bind(view)
        fun bind(item: WeatherModel) = with(binding){ // как будем заполнять ресайклер вью
            tvDate.text = item.time //заполняем из дата класса каждый элемент размеки
            tvCondition.text = item.condition
            tvTemp.text = item.currentTemp
        }
    }
}