package com.proalekse1.weatherapp7

import android.app.AlertDialog
import android.content.Context

object DialogManager { //класс для диалога включен GPS или нет
    fun locationSettingsDialog(context: Context, listener: Listener){
        val builder = AlertDialog.Builder(context)
        val dialog = builder.create() // создаем диалог
        dialog.setTitle("Enable location?") //заголовок
        dialog.setMessage("GPS disable, do you want enable location?") //сообщение
        //позитивная кнопка
        listener.onClick()
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK"){ _,_ -> //_,_ -> чтобы не пользоваться сандартными переменными
            dialog.dismiss() //закрыть диалог
        }
        //негативная кнопка
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel"){ _,_ -> //_,_ -> чтобы не пользоваться сандартными переменными
            dialog.dismiss() //закрыть диалог
        }
        dialog.show()
    }
    interface Listener { //интерфейс для передачи в MainFragment
        fun onClick()
    }
}