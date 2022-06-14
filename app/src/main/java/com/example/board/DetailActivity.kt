package com.example.board

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.board.databinding.ActivityDetailBinding
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_update.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailActivity : AppCompatActivity() {

    val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val retrofit = Retrofit.Builder()
//            .baseUrl("http://172.26.25.200:9009")
            .baseUrl("http://10.0.2.2:9009/")
            //.addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val signupService: SignupService = retrofit.create(SignupService::class.java)

        // count 는 getIntExtra
        intent.getStringExtra("title")?.let {
            title_detail.text = it
        }
        intent.getStringExtra("content")?.let {
            content_detail.text = it
        }
        intent.getIntExtra("boardViews", 0)?.let {
            boardViews_detail.text = it.toString()
        }
        intent.getStringExtra("savedTime")?.let {
            savedTime_detail.text = it
        }

        val pass = intent.getStringExtra("password")
        val boardId = intent.getLongExtra("boardId", 0)
        var a:String? = null

        update_button.setOnClickListener {
            val dialog = UpdateCustom(this)
            dialog.myData()

            // 다이얼로그에서 정의한 interface를 통해 데이터를 받아옴
            dialog.setOnClickedListener(object : UpdateCustom.ButtonClickListener {
                override fun onClicked(password: String) {
                    if (password.equals(pass) ) {
                        Log.d("SAME", "비밀번호가 일치합니다.")
                        getPW_et.text = password

                        val intent = Intent(this@DetailActivity, UpdateActivity::class.java)
                        intent.putExtra("boardId", boardId)
                        startActivity(intent)
                        Log.d("게시글 번호", "는 : boardId ")

                    } else {
                        Log.d("FALSE", "비밀번호가 다릅니다.")
                    }

                }
            })

        }

        delete_button.setOnClickListener {
            val dialog = UpdateCustom(this)
            dialog.myData()

            dialog.setOnClickedListener(object : UpdateCustom.ButtonClickListener {
                override fun onClicked(password: String) {
                    if (password.equals(pass) ) {
                        Log.d("SAME", "비밀번호가 일치합니다.")
                        getPW_et.text = password

//                        val board_id = boardId.toString()

                        signupService.deleteBoard(boardId).enqueue(object : Callback<Void> {
                            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                if(response.isSuccessful()) {
                                    Toast.makeText(this@DetailActivity, "게시글이 삭제되었습니다.", Toast.LENGTH_LONG).show()
                                    val intent = Intent(this@DetailActivity, MainActivity::class.java)
                                    startActivity(intent)
                                } else {
                                    // 통신성공 but 응답 실패
                                    Log.d("RESPONSE: ", "FAILURE" )
                                }

                            }

                            override fun onFailure(call: Call<Void>, t: Throwable) {
                                Log.d("CONNECTION FAILURE: ", t.localizedMessage )

                            }
                        })


                    } else {
                        Log.d("FALSE", "비밀번호가 다릅니다.")
                    }

                }
            })


        }

    }


}