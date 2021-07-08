package com.movierest.dl.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.movierest.dl.R
import kotlinx.android.synthetic.main.adapter_movie.view.*




class MovieAdapter(private val movies:ArrayList<String>) : RecyclerView.Adapter<MovieAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
       return MyViewHolder(p0)
    }

    override fun onBindViewHolder(myViewholder: MyViewHolder, index: Int) {
        myViewholder.buildView (movies.get(index))
    }

    override fun getItemCount(): Int {
        return movies.size
    }
    class MyViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.adapter_movie,parent,false)
    ) {

        fun buildView(movie: String) = with(itemView) {
            movieTV.text=movie
        }
    }
}
