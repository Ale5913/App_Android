package com.movierest.dl.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.movierest.dl.model.TypeMovie

class TypeMoviesSpinnerAdapter(context: Context, resource: Int, val typesMovie: ArrayList<TypeMovie>) :
    ArrayAdapter<TypeMovie>(context, resource, typesMovie) {

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {

        var textView = TextView(context)
        textView.text = typesMovie[position].name

        return textView
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var textView = TextView(context)
        textView.text = typesMovie[position].name

        return textView
    }

}