package com.movierest.dl.adapter




import actividades.DetailMovieActivity
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.movierest.dl.R
import com.movierest.dl.model.Movie
import com.movierest.dl.restapi.ResourceURL
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.adapter_movie.view.*




class MovieAdapter(private val activity: Activity,private val movies:MutableList<Movie>) : RecyclerView.Adapter<MovieAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        return MyViewHolder(p0,activity)
    }

    override fun onBindViewHolder(myViewholder: MyViewHolder, index: Int) {
        myViewholder.buildView (movies.get(index))
    }

    override fun getItemCount(): Int {
        return movies.size
    }
    class MyViewHolder(parent: ViewGroup,private val activity: Activity) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.adapter_movie,parent,false)
    ) {

        fun buildView(movie: Movie) = with(itemView) {
            Picasso.get()
                .load(ResourceURL.URL_RESOURCE_IMAGE+movie.imagen)
                .error(R.mipmap.ic_launcher)
                .into(movieIV);
            containerCV.setOnClickListener{
                var intent= Intent(activity,DetailMovieActivity::class.java)
                intent.putExtra("idPelicula",it.tag.toString().toInt())

                //Shared element activity  Transition
                var p0: Pair<View,String> = Pair(movieTV as View,"movieTV")
                var p1: Pair<View,String> = Pair(movieIV as View,"movieIV")
                var option =ActivityOptionsCompat.makeSceneTransitionAnimation(activity,p1,p0)

                activity.startActivity(intent,option.toBundle())

            }
            containerCV.tag=movie.idPelicula
            movieTV.text=movie.nombre

        }
    }
}
