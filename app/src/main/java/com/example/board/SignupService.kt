package com.example.board

import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface SignupService {

    @Headers("Content-Type: application/json")
    @GET("/api/board_List")
    fun getFirstBoard(): Call <List<MyBoard>>

    @Headers("Content-Type: application/json")
    @GET("/api/board_ViewList")
    fun getCountBoard(): Call <List<MyBoard>>

    @Headers("Content-Type: application/json")
    @GET("/api/board_DetailList/{id}")
    fun getDetailBoard(@Path("id") id:Long): Call<MyBoard>


    @Headers("Content-Type: application/json")
    @GET("/api/board_Search/{title}")
    fun getSearchBoard(@Path("title") title:String): Call <List<MyBoard>>


//    @Headers("Content-Type: application/json")
//    @FormUrlEncoded
//    @POST("/api/board_Signup")
//    fun addBoard(@Field("title") title: String,
//                 @Field("content") content: String,
//                 @Field("password") password: String): Call<SignupBoard> // Call 은 흐름처리 기능을 제공해줌

    @Headers("Content-Type: application/json")
//    @FormUrlEncoded
    @POST("/api/board_Signup")
    fun addBoard(@Body board: Board): Call<String> // Call 은 흐름처리 기능을 제공해줌


    @Headers("Content-Type: application/json")
    @PUT("/api/board_Update")
    fun updateBoard(@Body board: Board): Call<String> // Call 은 흐름처리 기능을 제공해줌

    @Headers("Content-Type: application/json")
    @DELETE("/api/board_Delete/{boardId}")
    fun deleteBoard(@Path("boardId") boardId:Long): Call<Void>


}