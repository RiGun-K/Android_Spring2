package com.example.board

import android.content.Intent
import android.widget.Toast
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog
import com.example.board.databinding.ActivityDetailBinding
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.sign

class RegisterActivity : AppCompatActivity() {

    val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val retrofit = Retrofit.Builder()
//            .baseUrl("http://172.26.25.200:9009")
            .baseUrl("http://10.0.2.2:9009")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val signupService: SignupService = retrofit.create(SignupService::class.java)

//        val writeBtn = findViewById<Button>(R.id.regB_button)
//        findByViewId 안쓰고 바로 id 땡겨와짐 !

        regB_button.setOnClickListener {

            val title = title_et.text.toString()
            val content = content_et.text.toString()
            val password = password_et.text.toString()

            val board = Board()
            board.title = title
            board.content = content
            board.password = password

            Log.d("BUTTON CLICKED", "TITLE: " + board.title + ", CONTENT: " + board.content + ", PASSWORD: " + board.password)

            signupService.addBoard(board).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if(response.isSuccessful) {
//                        Log.d("RESPONSE: ", response.body().toString() )
                        Toast.makeText(this@RegisterActivity, "게시글이 등록되었습니다.", Toast.LENGTH_LONG).show()
                        val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@RegisterActivity, "게시글이 등록되었습니다.", Toast.LENGTH_LONG).show()
                        val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                        startActivity(intent)
                    }

                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.d("CONNECTION FAILURE: ", t.localizedMessage )

                }
            })

//            signupService.addBoard(title, content, password).enqueue(object: Callback<SignupBoard> {
//                override fun onResponse(call: Call<SignupBoard>, response: Response<SignupBoard>) {
//                    signupBoard = response.body()
//                    Log.d("SUCCESS TITLE", "게시글 제목 : " + signupBoard?.title)
//                    Log.d("SUCCESS CONTENT", "게시글 내용 : " + signupBoard?.content)
//
//                    var dialog = AlertDialog.Builder(this@RegisterActivity)
//                    dialog.setTitle(signupBoard?.title)
//                    dialog.setMessage(signupBoard?.content)
//                    dialog.show()
//
//                }
//
//                override fun onFailure(call: Call<SignupBoard>, t: Throwable) {
//                    Log.e("WRITE ERROR", t.message.toString())
//
//                    var dialog = AlertDialog.Builder(this@RegisterActivity)
//                    dialog.setTitle("에러")
//                    dialog.setMessage("게시글 등록실패")
//                    dialog.show()
//
//                }
//            })





//            var dialog = AlertDialog.Builder(this@RegisterActivity)
//            dialog.setTitle("작성한 게시글")
//            dialog.setMessage("제목 : " + title.text.toString())
//            dialog.setMessage("내용 : " + content.text.toString())
//            dialog.show()

        }


    }

}
