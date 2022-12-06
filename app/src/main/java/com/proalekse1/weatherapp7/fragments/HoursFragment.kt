package com.proalekse1.weatherapp7.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.proalekse1.weatherapp7.MainViewModel
import com.proalekse1.wetherapp7.R
import com.proalekse1.weatherapp7.adapters.WeatherAdapter
import com.proalekse1.weatherapp7.adapters.WeatherModel
import com.proalekse1.wetherapp7.databinding.FragmentHoursBinding
import org.json.JSONArray
import org.json.JSONObject


class HoursFragment : Fragment() {
    private lateinit var binding: FragmentHoursBinding //подключили байндинг
    private lateinit var adapter: WeatherAdapter //инициализировали адаптер
    private val model: MainViewModel by activityViewModels() //подключаем MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHoursBinding.inflate(inflater, container, false)//подключили байндинг
        return binding.root//подключили байндинг
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { //создается фрагмент
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        model.liveDataCurrent.observe(viewLifecycleOwner){ //подключаем обсервер
            adapter.submitList(getHoursList(it)) //передаем в обсервер спикок с нужными данными
        }
    }

    private fun initRcView() = with(binding){ //инициализируем адаптер
        rcView.layoutManager = LinearLayoutManager(activity) //указываем как хотим чтобы ресайклер вью показывался
        adapter = WeatherAdapter()
        rcView.adapter = adapter


    }

    private fun getHoursList(wItem: WeatherModel): List<WeatherModel>{ //для доставания нужного из массива с погодой по часам
        val hoursArray = JSONArray(wItem.hours) //превращаем стринг в JSONArray(массив)
        val list = ArrayList<WeatherModel>() //собираем список из WeatherModel
        for(i in 0 until hoursArray.length()){ //прогоняем массив с помощью цикла
            val item = WeatherModel(
                wItem.city,
                (hoursArray[i] as JSONObject).getString("time"), //получаем с 0 позиции массива time
                (hoursArray[i] as JSONObject).getJSONObject("condition").getString("text"), //получаем с 0 позиции массива состояние
                (hoursArray[i] as JSONObject).getString("temp_c"),
                "",
                "",
                (hoursArray[i] as JSONObject).getJSONObject("condition").getString("icon"),
                ""
            )
            list.add(item) //как только разобрали, все передаем в список
        }
        return list //функция возвращает заполненный список
    }

    companion object {

        @JvmStatic
        fun newInstance() = HoursFragment()

    }
}
