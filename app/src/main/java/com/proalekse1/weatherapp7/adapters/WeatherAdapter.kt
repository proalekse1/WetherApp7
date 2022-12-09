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

class WeatherAdapter(val listener: Listener?) : ListAdapter<WeatherModel, WeatherAdapter.Holder>(Comparator()) {

    class Holder(view: View, val listener: Listener?) : RecyclerView.ViewHolder(view){ //хранит в себе разметк одного элемента
        val binding = ListItemBinding.bind(view)

        var itemTemp: WeatherModel? = null //переменная для listenera
        init { //для слушателя нажатий
            itemView.setOnClickListener{
                itemTemp?.let { it1 -> listener?.onClick(it1) }
            }
        }

        fun bind(item: WeatherModel) = with(binding){ // как будем заполнять ресайклер вью
            itemTemp = item //переменная для listenera
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
        return Holder(view, listener)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) { //элемент заполняется
        holder.bind(getItem(position))
    }

    interface Listener{ //слушатель нажатий на элементы списка days, чтобы переключалось по часам
        fun onClick(item: WeatherModel)
    }
}