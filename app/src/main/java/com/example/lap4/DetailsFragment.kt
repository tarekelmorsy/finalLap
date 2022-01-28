package com.example.lap4

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.*
import java.util.ArrayList

class DetailsFragment : Fragment() {

    lateinit var details: TextView
    lateinit var tvFrom: TextView
    lateinit var image: ImageView
    var data: String = ""
    var from: String = ""
     var imag: String = ""
     @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         // Inflate the layout for this fragment
         val view = inflater.inflate(R.layout.fragment_static, container, false)
         details = view.findViewById(R.id.tvDescription)
         image = view.findViewById(R.id.imageView2)
         tvFrom = view.findViewById(R.id.tvFrom)



         if (savedInstanceState != null) {

             data = savedInstanceState.getString("data", "null")
             from = savedInstanceState.getString("from", "null")
             imag = savedInstanceState.getString("image","")
             details.text = data
             Glide.with(activity!!).load(imag).into(image)
             tvFrom.text = from

         }else {
             data = activity?.intent?.getStringExtra("data") ?: "null"
             from = activity?.intent?.getStringExtra("from") ?: "null"
             imag = activity?.intent?.getStringExtra("image2") ?: "null"
             details.text = data
             Glide.with(activity!!).load(imag).into(image)
             tvFrom.text = from

         }
             // val intent = Intent(activity)

             return view
         }

         @SuppressLint("UseRequireInsteadOfGet")
         fun setData(imag:String,data:String,fom:String) {
             //   data = RecycleRopo.getListModel().get(position).strSportDescription
             // from = RecycleRopo.getListModel().get(position).strFormat
             this.data=data
             this.from=from
             this.imag=imag
             details.text = data
             tvFrom.text=fom
             //imag = RecycleRopo.getListModel().get(position).strSportIconGreen
             Glide.with(activity!!).load(imag).into(image)

         }

         override fun onSaveInstanceState(outState: Bundle) {
             super.onSaveInstanceState(outState)
             outState.putString("data", data)
             outState.putString("image", imag)
             outState.putString("from", from)

         }


}