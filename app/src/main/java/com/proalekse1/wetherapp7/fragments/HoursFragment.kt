package com.proalekse1.wetherapp7.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.proalekse1.wetherapp7.R
import com.proalekse1.wetherapp7.adapters.WeatherAdapter
import com.proalekse1.wetherapp7.adapters.WeatherModel
import com.proalekse1.wetherapp7.databinding.FragmentHoursBinding


class HoursFragment : Fragment() {
    private lateinit var binding: FragmentHoursBinding //подключили байндинг
    private lateinit var adapter: WeatherAdapter //инициализировали адаптер

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
    }

    private fun initRcView() = with(binding){ //инициализируем адаптер
        rcView.layoutManager = LinearLayoutManager(activity) //указываем как хотим чтобы ресайклер вью показывался
        adapter = WeatherAdapter()
        rcView.adapter = adapter
        val list = listOf( //временный список чтобы передать в submitList(), пока ничего с сервера не получаем
            WeatherModel("", "12:00",
                "Sunny", "25°C",
                "", "", "", ""),
            WeatherModel("", "13:00",
                "Sunny", "27°C",
                "", "", "", ""),
            WeatherModel("", "14:00",
                "Sunny", "35°C",
                "", "", "", "")
        )
        adapter.submitList(list)
    }

    companion object {

        @JvmStatic
        fun newInstance() = HoursFragment()

    }
}
