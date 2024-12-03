package com.example.productsstore

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class ListAdapter (context:Context,personList:MutableList<Food>):ArrayAdapter<Food>(context,R.layout.list_item,personList){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view=convertView
        val food=getItem(position)
        if(view==null){
            view=LayoutInflater.from(context).inflate(R.layout.list_item,parent,false)
        }
        val imageIV=view?.findViewById<ImageView>(R.id.imageIV)
        val nameTV=view?.findViewById<TextView>(R.id.nameTV)
        val costTV=view?.findViewById<TextView>(R.id.costTV)
        imageIV?.setImageBitmap(food?.image)
        nameTV?.text=food?.name
        costTV?.text=food?.cost
        return view!!
    }
}