package com.movierest.dl.adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.movierest.dl.R
import com.movierest.dl.model.Qualification
import com.movierest.dl.restapi.ResoucesURL
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.adapter_user_calification.view.*

class UserCalificationAdapter(private val activity: Activity, private val movies: MutableList<Qualification>) :
    RecyclerView.Adapter<UserCalificationAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        return MyViewHolder(p0, activity)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(myViewHolder: MyViewHolder, index: Int) {
        myViewHolder.buildView(movies.get(index))
    }

    class MyViewHolder(parent: ViewGroup, private val activity: Activity) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.adapter_user_calification, parent, false)
    ) {

        fun buildView(qualification: Qualification) = with(itemView) {

            Picasso.get()
                .load(ResoucesURL.URL_RESOURCE_AVATAR + qualification.avatar)
                .error(R.mipmap.ic_launcher)
                .into(avatarIV);

//            containerCV.setOnClickListener {
//
//                var intent = Intent(activity, DetailMovieActivity::class.java)
//                intent.putExtra("movie_id", it.tag.toString().toInt())
//
//                //shared element activity - Transition
//                var p0: Pair<View,String> = Pair(movieTV as View, "movieTV")
//                var p1: Pair<View,String> = Pair(movieIV as View, "movieIV")
//                var option = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,p0,p1)
//
//                // iniciar actividad
//                activity.startActivity(intent,option.toBundle())
//            }

            containerCV.tag = qualification.movie_id
            movieTV.text = qualification.name
        }

    }
}

