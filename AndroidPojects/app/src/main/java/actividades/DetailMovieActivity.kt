package actividades

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RatingBar
import androidx.appcompat.app.AppCompatActivity
import com.movierest.dl.R
import com.movierest.dl.model.Movie
import com.movierest.dl.restapi.ResourceURL
import com.movierest.dl.restapi.RestApiAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_movie.*
import kotlinx.android.synthetic.main.activity_detail_movie.movieTV
import kotlinx.android.synthetic.main.adapter_movie.*
import kotlinx.android.synthetic.main.adapter_movie.view.*
import retrofit2.Call
import retrofit2.Response
import utils.UserPreferences
import kotlinx.android.synthetic.main.adapter_movie.movieIV as movieIV1

class DetailMovieActivity : AppCompatActivity(){
    var idPelicula: Int=0
    val TAG="DetailMovieActivity"
    var favorite=0
    override fun onCreate(savedInstanceState: Bundle?) {
        val extras = intent.extras
        if(extras !=null){
            idPelicula= extras.getInt("idPelicula")
        }
        Log.i(TAG,"idPelicula: $idPelicula")

        //if (UserPreferences.getId(this)==0){
        //   favoriteIV.visibility= View.GONE
        //favoriteTV.visibility=View.GONE
        //}

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)
        _showMovie()
        shareIV.setOnClickListener{_share()}
        favoriteIV.setOnClickListener{
            _favorite()
        }
        ratingBar.setOnRatingBarChangeListener{
                ratingBar: RatingBar, rating: Float, b: Boolean ->
            Log.i(TAG,rating.toString())
            _qualification(rating)
        }


    }
    private fun _share(){
        var intent= Intent()
        intent.action= Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_SUBJECT, movieTV.text.toString())
        intent.putExtra(Intent.EXTRA_TEXT, descriptionTV.text.toString())

        intent.type="text/plain"
        startActivity(Intent.createChooser(intent,"Compartir"))
    }


    private fun _showMovie(){

        var idusuario: Int?=null
       // if (UserPreferences.getId(this)>0){
       //     idusuario = UserPreferences.getId(this)
       // }
        var restApiAdapter = RestApiAdapter()
        var gson = restApiAdapter.gsonDeserizerMovie()
        val endPointApi = restApiAdapter.conection(gson)
        val call = endPointApi.detailMovie(idPelicula, idusuario)

        call.enqueue(object : retrofit2.Callback<Movie>{
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                var movie = response.body()
                    if(movie != null){
                        collapsingToolbarLayout.title=movie.nombre
                        movieTV.text= movie.nombre
                        descriptionTV.text= movie.descripcion
                        qualificationTV.text=getString(R.string.qualification)+": ${movie.average}"

                        if (movie.favorito?.idFavorito != null){
                            favorite=1
                            favoriteIV.setImageResource(R.drawable.ic_favorite_40dp)
                        }
                        Picasso.get()
                            .load(ResourceURL.URL_RESOURCE_IMAGE+movie.imagen)
                            .error(R.mipmap.ic_launcher)
                            .into(movieIV);

                    }
                }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                Log.e(TAG,t.toString())
            }
        })
    }
    private fun _favorite(){

        if (favorite == 0)
            favorite = 1
        else
            favorite = 0

        var restApiAdapter = RestApiAdapter()
        var gson = restApiAdapter.gsonDeserizerSimple()
        val endPointsApi = restApiAdapter.conection(gson)

        val call = endPointsApi.favorite(UserPreferences.getId(this), idPelicula, favorite)

        call.enqueue(object : retrofit2.Callback<String>{

            override fun onResponse(call: Call<String>, response: Response<String>) {

                var res = response.body()

                if(res == "ok"){
                    if (favorite==0){
                        favoriteIV.setImageResource(R.drawable.ic_favorite_border_40dp)
                    }
                    else{
                        favoriteIV.setImageResource(R.drawable.ic_favorite_40dp)
                    }
                }

            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e(TAG,t.toString())
            }
        })
    }
    private fun _qualification(qualification: Float){
        var restApiAdapter = RestApiAdapter()
        var gson = restApiAdapter.gsonDeserizerSimple()
        val endPointsApi = restApiAdapter.conection(gson)

        val call = endPointsApi.qualification(UserPreferences.getId(this), idPelicula, qualification)

        call.enqueue(object : retrofit2.Callback<String>{

            override fun onResponse(call: Call<String>, response: Response<String>) {

                var res = response.body()

                if(res == "ok"){

                }

            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e(TAG,t.toString())
            }
        })
    }
}


