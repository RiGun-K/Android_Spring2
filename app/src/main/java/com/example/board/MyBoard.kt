package com.example.board

import com.google.gson.annotations.SerializedName
import java.sql.Date
import java.util.*

data class MyBoard(

    val boardId: Long?,

    val title: String?,

    val content: String?,

    val password: String?,

    val savedTime: String?,

    val boardViews: Int?
)
