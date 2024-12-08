package com.example.productsstore

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class MyAlertDialog:DialogFragment() {
    private var updatable:Updatable?=null
    private var removable:Removable?=null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val food = requireArguments().getSerializable("food")
        val builder=AlertDialog.Builder(
            requireActivity()
        )
        removable=context as Removable?
        updatable=context as Updatable?
        return builder
            .setTitle("Внимамние!")
            .setMessage("Предполагаемые действия")
            .setPositiveButton("Удалить"){dialog,which->
                removable?.remove(food as Food)
            }
            .setNegativeButton("Описание"){dialog,which->
                updatable?.update(food as Food)
            }
            .setNeutralButton("Отмена",null)
            .create()
    }
}