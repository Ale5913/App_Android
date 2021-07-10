package com.movierest.dl.restapi


import com.movierest.dl.model.Movie
import com.movierest.dl.restapi.model.MoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface EndPointApi {
    @GET(ResourceURL.URL_GET_SHOW_ALL_MOVIES)
    fun show(): Call<MoviesResponse>

    @GET(ResourceURL.URL_GET_SHOW_MOVIE)
    fun descripcion(@Path("idPelicula") idPelicula:Int): Call<Movie>
}