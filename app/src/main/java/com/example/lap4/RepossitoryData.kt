package com.example.lap4

class RepossitoryData constructor(private val retrofitSer: RetrofitSer) {

    suspend fun getAllData2()=retrofitSer.getAllData()
}