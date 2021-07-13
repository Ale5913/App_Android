package com.movierest.dl.restapi


import com.movierest.dl.model.Movie
import com.movierest.dl.restapi.model.MoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface EndPointApi {
    @GET(ResourceURL.URL_GET_SHOW_ALL_MOVIES)
    fun show(@Query("offset") offset: Int): Call<MoviesResponse>

    @GET(ResourceURL.URL_GET_SHOW_ALL_MOVIES)
    fun search(
        @Query("nombre") nombre: String,
        @Query("anio") anio: String,
        @Query("offset") offset: Int
    ): Call<MoviesResponse>

    @GET(ResourceURL.URL_GET_SHOW_MOVIE)
    fun detailMovie(@Path("idPelicula") idPelicula: Int): Call<Movie>
}