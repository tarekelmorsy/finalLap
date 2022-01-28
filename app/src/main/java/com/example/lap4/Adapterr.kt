package com.example.lap4

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class Adapterr(var listModel:List<Sport>) : RecyclerView.Adapter<Adapterr.ViewHolder>() {

  private lateinit var onItemClick:OnItemClick
    interface OnItemClick {
        fun onClick(position: Int)
    }

    fun setOnItemClick(onItem: OnItemClick){

        onItemClick=onItem
    }
    inner class ViewHolder(val view:View,onItemClick: OnItemClick):RecyclerView.ViewHolder(view){


        val idSport:TextView
            get() =view.findViewById(R.id.idSport)
        val image:ImageView
            get() =view.findViewById(R.id.imageView)

        val constructor:ConstraintLayout
            get() =view.findViewById(R.id.container)

        val nameSport:TextView
            get() =view.findViewById(R.id.strSport)


        init {
            itemView.setOnClickListener {
                onItemClick.onClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viwe:View=LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false)
        return ViewHolder(viwe,onItemClick)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.idSport.text= listModel.get(position).idSport
        holder.nameSport.text= listModel.get(position).strSport

        Glide.with(holder.image.context).load(listModel.get(position).strSportIconGreen).into(holder.image)

    }

    override fun getItemCount(): Int =listModel.size
}