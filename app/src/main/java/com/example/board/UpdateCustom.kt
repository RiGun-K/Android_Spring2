package com.example.board

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_update_custom.*

class UpdateCustom(private val context: Context) {
    private val dialog = Dialog(context)

    fun myData() {
        val view = LayoutInflater.from(context).inflate(R.layout.activity_update_custom, null)
        dialog.setContentView(view)

        dialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                                  WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)

        // 패스워드 입력값
        val edit = view.findViewById<TextInputEditText>(R.id.password_et)
        // 수정버튼
        val update = view.findViewById<TextView>(R.id.updateBoard_et)
        // 취소버튼
        val cancel = view.findViewById<TextView>(R.id.CancelBoard_et)

        update.setOnClickListener {
            onClickListener.onClicked(edit.text.toString())
            dialog.dismiss()
        }
        cancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }

    interface ButtonClickListener {
        fun onClicked(password: String)
    }

    private lateinit var onClickListener: ButtonClickListener

    fun setOnClickedListener(listener: ButtonClickListener) {
        onClickListener = listener
    }
}