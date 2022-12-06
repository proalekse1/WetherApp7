package com.proalekse1.weatherapp7.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.proalekse1.weatherapp7.R
import com.proalekse1.weatherapp7.databinding.ListItemBinding
import com.squareup.picasso.Picasso

class WeatherAdapter : ListAdapter<WeatherModel, WeatherAdapter.Holder>(Comparator()) {

    class Holder(view: View) : RecyclerView.ViewHolder(view){ //хранит в себе разметк одного элемента
        val binding = ListItemBinding.bind(view)
        fun bind(item: WeatherModel) = with(binding){ // как будем заполнять ресайклер вью
            tvDate.text = item.time //заполняем из дата класса каждый элемент размеки
            tvCondition.text = item.condition

            //если пусто, то савим эти значения
            tvTemp.text = item.currentTemp.ifEmpty { "${item.maxTemp}°C / ${item.minTemp}°C"}
            Picasso.get().load("https:" + item.imageUrl).into(im) //заполняем иконку
        }
    }

    class Comparator : DiffUtil.ItemCallback<WeatherModel>(){ //обновляет список
        override fun areItemsTheSame(oldItem: WeatherModel, newItem: WeatherModel): Boolean {
            return oldItem == newItem //сравнивает старый и новый элемент
        }

        override fun areContentsTheSame(oldItem: WeatherModel, newItem: WeatherModel): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder { //элемент создается
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) { //элемент заполняется
        holder.bind(getItem(position))
    }
}