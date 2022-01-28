package com.example.lap4

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RetrofitSer {
    //all_sports.php
@GET("all_sports.php")
    suspend fun getAllData():Response<sports>
    companion object
    {
        var retrofitSer:RetrofitSer?=null
        fun getInstance():RetrofitSer{

            if (retrofitSer==null) {
                val retrofit = Retrofit.Builder()
                         .baseUrl("https://www.thesportsdb.com/api/v1/json/2/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitSer = retrofit.create(RetrofitSer::class.java)
            }
            return  retrofitSer!!


            }
        }


}