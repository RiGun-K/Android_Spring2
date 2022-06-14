package com.example.board

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.board.databinding.ActivityDetailBinding
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_update.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.*

class UpdateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        intent.getLongExtra("boardId", 0)?.let {
            boardId.text = it.toString()
        }

        val retrofit = Retrofit.Builder()
//            .baseUrl("http://172.26.25.200:9009")
            .baseUrl("http://10.0.2.2:9009/")
            //.addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val signupService: SignupService = retrofit.create(SignupService::class.java)

        // @id 가 레이아웃 끼리 겹치면 못 잡아줌.
        update_bt.setOnClickListener {
            val title = title_up.text.toString()
            val content = content_up.text.toString()
            val boardId = boardId.text.toString()

            val board = Board()
            board.title = title
            board.content = content
            board.boardId = boardId

            Log.d("BUTTON CLICKED", "TITLE: " + board.title + ", CONTENT: " + board.content + ", boardId: " + board.boardId)

            signupService.updateBoard(board).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                        Toast.makeText(this@UpdateActivity, "게시글이 수정되었습니다.", Toast.LENGTH_LONG).show()
                        val intent = Intent(this@UpdateActivity, MainActivity::class.java)
                        startActivity(intent)
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.d("CONNECTION FAILURE: ", t.localizedMessage )

                }
            })
        }

    }
}