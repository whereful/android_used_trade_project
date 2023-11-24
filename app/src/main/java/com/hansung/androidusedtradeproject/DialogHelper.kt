package com.hansung.androidusedtradeproject

import android.content.Context
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AlertDialog

class DialogHelper(private val context: Context) {

    interface InputTextDialogListener {
        fun onInputText(text: String)
    }

    fun showInputDialog(title: String, listener: InputTextDialogListener) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_layout, null)
        val editText = dialogView.findViewById<EditText>(R.id.editText)

        AlertDialog.Builder(context)
            .setTitle(title)
            .setView(dialogView)
            .setPositiveButton("확인") { dialog, _ ->
                val inputText = editText.text.toString()
                listener.onInputText(inputText)
                dialog.dismiss()
            }
            .setNegativeButton("취소") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}
