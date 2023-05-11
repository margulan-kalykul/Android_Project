package com.example.finalproject.interfaces.category

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.finalproject.R

class OptionAdapter(private val activity: Activity, private val options: List<Option>) :
    ArrayAdapter<Option>(activity, R.layout.item_option, options) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(context)
        val view = convertView ?: inflater.inflate(R.layout.item_option, parent, false)

        val option = getItem(position)
        val text = view.findViewById<TextView>(R.id.optionTV)
        text.text = option?.text

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getView(position, convertView, parent)
    }
}