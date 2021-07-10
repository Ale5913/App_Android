package com.movierest.dl

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.movierest.dl.adapter.MovieAdapter
import kotlinx.android.synthetic.main.activity_main.*
import com.movierest.dl.model.Movie
import com.movierest.dl.restapi.RestApiAdapter
import com.movierest.dl.restapi.model.MoviesResponse
import retrofit2.Call
import retrofit2.Response



class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"
    var movies = mutableListOf<Movie>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        _showMovies()
    }

    private fun _showMovies(){
        var restApiAdapter = RestApiAdapter()
        var gson = restApiAdapter.gsonDeserizerMovies()
        val endPointApi = restApiAdapter.conection(gson)
        val call = endPointApi.show()

        call.enqueue(object : retrofit2.Callback<MoviesResponse>{
            override fun onResponse(
                call: Call<MoviesResponse>,
                response: Response<MoviesResponse>
            ) {
                var data = response.body()
                with(data?.movies){
                    if(this != null){
//                        for (movie in this){
//                            Log.i(TAG,movie.name)
//                        }
                    movies= this
                        moviesRV.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL ,false )
                        moviesRV.adapter  = MovieAdapter(this)
                    }
                }
                moviesRV.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL ,false )
            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                Log.e(TAG, t.toString())
            }

        })


    }

}



