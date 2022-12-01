package com.proalekse1.wetherapp7.fragments


import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.tabs.TabLayoutMediator
import com.proalekse1.wetherapp7.adapters.VpAdapter
import com.proalekse1.wetherapp7.databinding.FragmentMainBinding
import com.proalekse1.wetherapp7.isPermissionGranted
const val API_KEY = "ee30c89e35434b60b29163436223011" //константа для api ключа

class MainFragment : Fragment() {
    private lateinit var pLauncher: ActivityResultLauncher<String> //для диалога и разрешений
    private lateinit var binding: FragmentMainBinding//подключаем байндинг
    private val fList = listOf( //для списка ViewPager
        HoursFragment.newInstance(),
        DaysFragment.newInstance()
    )
    private val tList = listOf( //для связывания таблайоут и дейс
        "Hours",
        "Days"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false) //подключаем байндинг
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission() // диалог разрешений
        init() //VpAdapter for ViewPager на разметке
        requestWeatherData("Tula")
    }

    private fun init() = with(binding){ //VpAdapter for ViewPager на разметке
        val adapter = VpAdapter(activity as FragmentActivity, fList)
        vp.adapter = adapter //vp-view pager в разметке
        TabLayoutMediator(tabLayout, vp){ // связываем tabLayout с ViewPager
            tab, pos -> tab.text = tList[pos]
        }.attach()

    }

    private fun permissionListener(){ // диалог для разрешения
        pLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()){
            Toast.makeText(activity, "Permission is $it", Toast.LENGTH_LONG).show() //временный тостик чтоб узнать работает функция или нет
        }
    }

    private fun checkPermission(){ //проверяем разрешение если нет то false с ! будет тру и код сработает
        if(!isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)){
            permissionListener()
            pLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun requestWeatherData(city: String){ //функция отправки и получения результата
        val url = "https://api.weatherapi.com/v1/forecast.json?key=" +
                API_KEY +
                "&q="+
                city+
                "&days="+
                "3"+
                "&aqi=no&alerts=no"
        val queue = Volley.newRequestQueue(context) //создаем очередь для запроса
        val request = StringRequest( //хотим получить поэтому get //создаем запрос
            Request.Method.GET,
            url,
            {
                result -> Log.d("MyLog", "Result: $result") //слушатель(в виде лямбды) который будет ждать результат который получим
            },
            {
               error -> Log.d("MyLog", "Error: $error") //слушатель(в виде лямбды) который будет ждать результат который получим
            }
        )
        queue.add(request) //добавляем в очередь запрос
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}