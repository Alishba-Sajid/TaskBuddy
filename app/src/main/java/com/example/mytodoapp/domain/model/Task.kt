package com.example.mytodoapp.domain.model

data class Task(

    val id: Long = 0,

    val title: String,

    val description: String = "",

    val createdAt: Long = System.currentTimeMillis()

)