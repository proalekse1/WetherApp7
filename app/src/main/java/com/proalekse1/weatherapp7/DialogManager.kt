package com.proalekse1.weatherapp7

import android.app.AlertDialog
import android.content.Context
import android.widget.EditText

object DialogManager {//класс для диалога включен GPS или нет
    fun locationSettingsDialog(context: Context, listener: Listener){
        val builder = AlertDialog.Builder(context)
        val dialog = builder.create() // создаем диалог
        dialog.setTitle("Enable location?") //заголовок
        dialog.setMessage("GPS disabled") //сообщение
    //позитивная кнопка
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK"){_,_ -> //_,_ -> чтобы не пользоваться сандартными переменными
            listener.onClick(null)
            dialog.dismiss() //закрыть диалог
        }
    //негативная кнопка
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL"){_,_ -> //_,_ -> чтобы не пользоваться сандартными переменными
            dialog.dismiss() //закрыть диалог
        }
        dialog.show()
    }

    fun searchByNameDialog(context: Context, listener: Listener){ //диалог для поиска по имени города
        val builder = AlertDialog.Builder(context)
        val edName = EditText(context) //добавляем edit text
        builder.setView(edName) //передаем edit text в builder
        val dialog = builder.create() // создаем диалог
        dialog.setTitle("City name?") //заголовок
        //позитивная кнопка
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK"){_,_ -> //_,_ -> чтобы не пользоваться сандартными переменными
            listener.onClick(edName.text.toString()) //передали город
            dialog.dismiss() //закрыть диалог
        }
        //негативная кнопка
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL"){_,_ -> //_,_ -> чтобы не пользоваться сандартными переменными
            dialog.dismiss() //закрыть диалог
        }
        dialog.show()
    }

    interface Listener{ //интерфейс для передачи в MainFragment
        fun onClick(name: String?)
    }
}