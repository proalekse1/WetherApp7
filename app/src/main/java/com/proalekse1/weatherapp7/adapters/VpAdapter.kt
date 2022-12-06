package com.proalekse1.weatherapp7.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class VpAdapter(fa: FragmentActivity, private val list: List<Fragment>) : FragmentStateAdapter(fa) { //класс для ViewPager
    override fun getItemCount(): Int { //выбирает элемент
        return list.size //размер списка
    }

    override fun createFragment(position: Int): Fragment { //выбирает фрагмент
        return list[position] //позиция в списке
    }
}