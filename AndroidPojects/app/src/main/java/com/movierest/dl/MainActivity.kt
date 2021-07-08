package com.movierest.dl

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.movierest.dl.adapter.MovieAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var movies = arrayListOf ("Lucah","Shazam!","Avengers")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        moviesRV.layoutManager= LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        moviesRV.adapter=MovieAdapter(movies)
    }


}