package actividades

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.movierest.dl.R
import com.movierest.dl.model.Movie
import com.movierest.dl.restapi.RestApiAdapter
import kotlinx.android.synthetic.main.activity_detail_movie.*
import retrofit2.Call
import retrofit2.Response

class DetailMovieActivity : AppCompatActivity(){
    var idPelicula: Int=0
    val TAG="DetailMovieActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        val extras = intent.extras
        if(extras !=null){
            idPelicula= extras.getInt("idPelicula")
        }
        Log.i(TAG,"idPelicula: $idPelicula")
        super.onCreate(savedInstanceState)
        _showMovie()
        setContentView(R.layout.activity_detail_movie)

    }


    private fun _showMovie(){
        var restApiAdapter = RestApiAdapter()
        var gson = restApiAdapter.gsonDeserizerMovie()
        val endPointApi = restApiAdapter.conection(gson)
        val call = endPointApi.show(idPelicula)

        call.enqueue(object : retrofit2.Callback<Movie>{
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                var movie = response.body()
                    if(movie != null){
                        movieTV.text= movie.nombre
                        descriptionTV.text= movie.descripcion
                    }
                }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                Log.e(TAG,t.toString())
            }
        })
    }
}