package com.proalekse1.wetherapp7

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.proalekse1.wetherapp7.databinding.ActivityMainBinding

const val API_KEY = "1e0303fe33a14c4d9f0141630221411" //делаем из ключа с сайта константу
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding //подключили байндинг

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) //подключили байндинг
        setContentView(binding.root)

        binding.bGet.setOnClickListener { //слушатель нажатий на кнопку get
            getResult("Tula")
        }
    }

    private fun getResult(name: String){ //функция отправки и получения результата
        val url = "https://api.weatherapi.com/v1/current.json" +
        "?key=$API_KEY&q=$name&aqi=no" //url из сайта wether api

        val queue = Volley.newRequestQueue(this) //создаем очередь для запроса
        val stringRequest = StringRequest(Request.Method.GET,//хотим получить поэтому get //создаем запрос
            url, //url
            {
                    response->  //слушатель(в виде лямбды) который будет ждать результат который получим
                        Log.d("MyLog", "Volley error: $response")
            },
            {
                    Log.d("MyLog", "Volley error: $it")            //слушатель ошибок
            }

        )
        queue.add(stringRequest) //добавляем в очередь запрос
    }
}