package com.proalekse1.weatherapp7.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.proalekse1.weatherapp7.MainViewModel
import com.proalekse1.weatherapp7.adapters.WeatherAdapter
import com.proalekse1.weatherapp7.adapters.WeatherModel
import com.proalekse1.weatherapp7.databinding.FragmentDaysBinding


class DaysFragment : Fragment(), WeatherAdapter.Listener {
    private lateinit var adapter: WeatherAdapter //Инициализируем WeatherAdapter
    private lateinit var binding: FragmentDaysBinding //подключили байндинг
    private val model: MainViewModel by activityViewModels() //передаем класс

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDaysBinding.inflate(inflater, container, false)//подключили байндинг
        return binding.root //подключили байндинг
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        model.liveDataList.observe(viewLifecycleOwner){ //подключили обсервер
            adapter.submitList(it.subList(1, it.size)) //показываем  списке по дням от 1 позиции массива до последней,
                                                        //чтобы не повторять текущую дату
        }

    }

    private fun init() = with(binding){
        adapter = WeatherAdapter(this@DaysFragment) // подключаем к ресайклер вью наш адаптер
        rcView.layoutManager = LinearLayoutManager(activity) //инициаизируем ресайклер вью
        rcView.adapter = adapter // подключаем к ресайклер вью наш адаптер
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            DaysFragment()
    }

    override fun onClick(item: WeatherModel) {
        model.liveDataCurrent.value = item
    }
}
