package com.example.board

import android.content.Intent
import com.example.board.databinding.ActivityMainBinding
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    val myBoard = MutableLiveData<MyBoard>()
    private val adapter = BoardAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        val retrofit = Retrofit.Builder()
//            .baseUrl("http://172.26.25.200:9009")
            .baseUrl("http://10.0.2.2:9009/")
            //.addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val signupService: SignupService = retrofit.create(SignupService::class.java)


        // 기본 최신순리스트 정렬

        signupService.getFirstBoard().enqueue(object : Callback<List<MyBoard>> {
            override fun onResponse(
                call: Call<List<MyBoard>>,
                response: Response<List<MyBoard>>
            ) {
                if (response.isSuccessful()) {
                    Log.d("SUCCESS: ", response.body().toString())
                }

                val data = response.body()
                if (data != null) {
                    adapter.setData(data)
                }

                //  data.setText(response.body().toString())


            }

            override fun onFailure(call: Call<List<MyBoard>>, t: Throwable) {
                Log.d("CONNECTION FAILURE: ", t.localizedMessage)

            }


        })


        // 상세페이지 조회
        adapter.setListener { v, position ->
            val data = adapter.getItem(position)

            val board = data.boardId?.toLong()
            Log.d("dataaa","${data.boardId}")
            if (board != null) {
                signupService.getDetailBoard(board).enqueue(object : Callback<MyBoard> {
                    override fun onResponse(call: Call<MyBoard>, response: Response<MyBoard>) {
                        if (response.isSuccessful()) {
                            Log.d("SUCCESS: ", response.body().toString())
                        }
                        myBoard.value= response.body()
                        Log.d("databb","${myBoard.value.toString()}")
                        //  data.setText(response.body().toString())

                    }

                    override fun onFailure(call: Call<MyBoard>, t: Throwable) {
                        Log.d("CONNECTION FAILURE: ", t.localizedMessage)

                    }


                })
            }

            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("boardId", data.boardId)
            intent.putExtra("title", data.title)
            intent.putExtra("content", data.content)
            intent.putExtra("boardViews", data.boardViews)
            intent.putExtra("savedTime", data.savedTime)
            intent.putExtra("password", data.password)
            startActivity(intent)
        }


        // 최신순 조회
        search2_button.setOnClickListener {

            signupService.getFirstBoard().enqueue(object : Callback<List<MyBoard>> {
                override fun onResponse(
                    call: Call<List<MyBoard>>,
                    response: Response<List<MyBoard>>
                ) {
                    if (response.isSuccessful()) {
                        Log.d("SUCCESS: ", response.body().toString())
                    }

                    val data = response.body()
                    if (data != null) {
                        adapter.setData(data)
                    }

                    //  data.setText(response.body().toString())


                }

                override fun onFailure(call: Call<List<MyBoard>>, t: Throwable) {
                    Log.d("CONNECTION FAILURE: ", t.localizedMessage)

                }


            })
        }

        // 조회순 조회
        search3_button.setOnClickListener {

            signupService.getCountBoard().enqueue(object : Callback<List<MyBoard>> {
                override fun onResponse(
                    call: Call<List<MyBoard>>,
                    response: Response<List<MyBoard>>
                ) {
                    if (response.isSuccessful()) {
                        Log.d("SUCCESS: ", response.body().toString())
                    }

                    val data = response.body()
                    if (data != null) {
                        adapter.setData(data)
                    }

                    //  data.setText(response.body().toString())


                }

                override fun onFailure(call: Call<List<MyBoard>>, t: Throwable) {
                    Log.d("CONNECTION FAILURE: ", t.localizedMessage)

                }


            })
        }

        search_button.setOnClickListener {

            val title = search_et.text.toString()

            signupService.getSearchBoard(title).enqueue(object : Callback<List<MyBoard>> {
                override fun onResponse(
                    call: Call<List<MyBoard>>,
                    response: Response<List<MyBoard>>
                ) {
                    if (response.isSuccessful()) {
                        Log.d("SUCCESS: ", response.body().toString())
                    }

                    val data = response.body()
                    if (data != null) {
                        adapter.setData(data)
                    }

                    //  data.setText(response.body().toString())


                }

                override fun onFailure(call: Call<List<MyBoard>>, t: Throwable) {
                    Log.d("CONNECTION FAILURE: ", t.localizedMessage)

                }


            })
        }




        reg_button.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }
}