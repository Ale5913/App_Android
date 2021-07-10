package com.movierest.dl.adapter




import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.movierest.dl.R
import com.movierest.dl.model.Movie
import kotlinx.android.synthetic.main.adapter_movie.view.*




class MovieAdapter(private val movies:ArrayList<Movie>) : RecyclerView.Adapter<MovieAdapter.MyViewHolder>() {

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

        fun buildView(movie: Movie) = with(itemView) {
            containerCV.setOnClickListener{
                Log.i("TAG","Hola mundo ${it.tag}")
            }
            containerCV.tag=movie.idPelicula
            movieTV.text=movie.nombre
        }
    }
}
