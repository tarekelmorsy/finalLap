package com.example.lap4

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.net.ConnectivityManager

import android.net.NetworkInfo




class MainActivity : AppCompatActivity(),Communicator {
     val TAGFRAGMENT = "java_fragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    override fun setCounter(imag: String, data: String, fom: String) {
        val fragment: DetailsFragment? =
            supportFragmentManager.findFragmentById(R.id.frame_layout) as DetailsFragment?
        fragment?.setData(imag,data,fom)    }
}