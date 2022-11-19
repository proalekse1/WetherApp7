package com.proalekse1.wetherapp7.fragments


import android.Manifest
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.proalekse1.wetherapp7.databinding.FragmentMainBinding
import com.proalekse1.wetherapp7.isPermissionGranted


class MainFragment : Fragment() {
    private lateinit var pLauncher: ActivityResultLauncher<String> //для диалога и разрешений
    private lateinit var binding: FragmentMainBinding//подключаем байндинг

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

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}