package com.proalekse1.weatherapp7.fragments


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
import androidx.fragment.app.activityViewModels
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.tabs.TabLayoutMediator
import com.proalekse1.weatherapp7.MainViewModel
import com.proalekse1.weatherapp7.adapters.VpAdapter
import com.proalekse1.weatherapp7.adapters.WeatherModel
import com.proalekse1.weatherapp7.databinding.FragmentMainBinding
import com.proalekse1.weatherapp7.isPermissionGranted
import com.squareup.picasso.Picasso
import org.json.JSONObject

const val API_KEY = "98fe852f80e345c4b9a73953220412" //константа для api ключа

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
    private val model: MainViewModel by activityViewModels() //инициализируем MainViewModel

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
        updateCurrentCard() //обсервер
        requestWeatherData("Tula") //функция отправки и получения результата по api
    }

    private fun init() = with(binding){ //VpAdapter for ViewPager на разметке
        val adapter = VpAdapter(activity as FragmentActivity, fList)
        vp.adapter = adapter //vp-view pager в разметке
        TabLayoutMediator(tabLayout, vp){ // связываем tabLayout с ViewPager
            tab, pos -> tab.text = tList[pos]
        }.attach()

    }

    private fun updateCurrentCard() = with(binding){ //следит за тем чтобы вью уже было создано и тогда можно передать данные во вью
        model.liveDataCurrent.observe(viewLifecycleOwner){
            val maxMinTemp = "${it.maxTemp}°C/${it.minTemp}°C" //переменная для максмин температуры
            tvData.text = it.time //заполняем вьюшки Дата
            tvCity.text = it.city //город
            tvCurrentTemp.text = it.currentTemp.ifEmpty { maxMinTemp } //текущая погода
            tvCondition.text = it.condition //состояние погоды
            tvMaxMin.text = if (it.currentTemp.isEmpty()) "" else maxMinTemp//минимальная и максимальная температура

            //передаем иконку через Picasso. Обязательно добавить "https"
            Picasso.get().load("https:" + it.imageUrl).into(imWeather)
        }
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
                result -> parseWeatherData(result) //слушатель(в виде лямбды) который будет ждать результат который получим
            },
            {
               error -> Log.d("MyLog", "Error: $error") //слушатель(в виде лямбды) который будет ждать результат который получим
            }
        )
        queue.add(request) //добавляем в очередь запрос
    }

    private fun parseWeatherData(result: String){ //основная функция парсинга
        val mainObject = JSONObject(result) //создаем из результата json объект
        val list = parseDays(mainObject) // прогноз по дням
        parseCurrentData(mainObject, list[0]) //погода на большой карточке с текущей погодой
    }

    private fun parseDays(mainObject: JSONObject): List<WeatherModel>{// парсим прогноз погоды по дням
        val list = ArrayList<WeatherModel>() //создали список
        val daysArray = mainObject.getJSONObject("forecast").getJSONArray("forecastday") //получаем массив из JSON
        val name = mainObject.getJSONObject("location").getString("name") //достали из JSON название города
        for (i in 0 until daysArray.length()){ //перебираем массив с 0 элемента до конца массива
            val day = daysArray[i] as JSONObject
            //начинаем заполнять WeatherModel
            val item = WeatherModel(
                name, //название города
                day.getString("date"), //дата
                day.getJSONObject("day").getJSONObject("condition")
                    .getString("text"), //сосояние погоды
                "",//нет текущей температуры потому что прогноз
                day.getJSONObject("day").getString("maxtemp_c").toFloat().toInt().toString(), //максимальная температура
                day.getJSONObject("day").getString("mintemp_c").toFloat().toInt().toString(), //минимальная температура
                day.getJSONObject("day").getJSONObject("condition")
                    .getString("icon"), //иконка сосояния погоды
                day.getJSONArray("hour").toString() //прогноз погоды по часам получаем в виде стринга
            )
            list.add(item) //добавляем в список созданный item
        }
        model.liveDataList.value = list //передаем в майн вью модел
        return list //возвращаем список
    }


    private fun parseCurrentData(mainObject: JSONObject, weatherItem: WeatherModel){ //парсинг json формата для большой карточки

        val item = WeatherModel(
            mainObject.getJSONObject("location").getString("name"), //достали из JSON название города
            mainObject.getJSONObject("current").getString("last_updated"), //достали из JSON время и дату
            mainObject.getJSONObject("current")
                .getJSONObject("condition").getString("text"), //достали из JSON condition(условия-солнечно)
            mainObject.getJSONObject("current").getString("temp_c"), //достали из JSON температуру
            weatherItem.maxTemp, // максимальная температура
            weatherItem.minTemp, // минимальная темепература
            mainObject.getJSONObject("current")
                .getJSONObject("condition").getString("icon"), //достали из JSON condition(условия-солнечно) иконку
            weatherItem.hours //прогноз погоды по часам
        )

        model.liveDataCurrent.value = item // передали из weatherModel в observer даннные
        Log.d("MyLog", "City: ${item.maxTemp}") //покажем в логе иныфу из дата класса
        Log.d("MyLog", "Time: ${item.minTemp}")
        Log.d("MyLog", "Condition: ${item.hours}")


    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}