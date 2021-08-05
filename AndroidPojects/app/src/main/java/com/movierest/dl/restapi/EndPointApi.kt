package com.movierest.dl.restapi


import com.movierest.dl.model.Movie
import com.movierest.dl.model.User
import com.movierest.dl.restapi.model.MoviesResponse
import retrofit2.Call
import retrofit2.http.*


interface EndPointApi {
    //Usuarios
    @FormUrlEncoded
    @POST(ResourceURL.URL_POST_USER_LOGIN)
    fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<User>

    @FormUrlEncoded
    @POST(ResourceURL.URL_POST_USER_REGISTER)
    fun register(
    @Field("nombre") nombre: String,
    @Field("email") email: String,
    @Field("password") password: String,
    @Field("confirmar_password") confirmar_password: String
    ): Call<User>

    //Peliculas
    @GET(ResourceURL.URL_GET_SHOW_ALL_MOVIES)
    fun show(@Query("offset") offset: Int): Call<MoviesResponse>

    @GET(ResourceURL.URL_GET_SHOW_ALL_MOVIES)
    fun search(
        @Query("nombre") nombre: String,
        @Query("anio") anio: String,
        @Query("offset") offset: Int
    ): Call<MoviesResponse>

    @GET(ResourceURL.URL_GET_SHOW_MOVIE)
    fun detailMovie(@Path("idPelicula") idPelicula: Int,
    @Query("idusuario") idusuario: Int?,): Call<Movie>

    @FormUrlEncoded
    @POST(ResourceURL.URL_POST_FAVORITE)
    fun favorite(
        @Field("idusuario") idusuario: Int,
        @Field("idPelicula") idPelicula: Int,
        @Field("favorito") favorito: Int,
    ): Call<String>

    @FormUrlEncoded
    @POST(ResourceURL.URL_POST_QUALIFICATION)
    fun qualification(
        @Field("idusuario") idusuario: Int,
        @Field("idPelilcua") idPelicula: Int,
        @Field("qualification") qualification: Float,
    ): Call<String>
    //Api
    @GET(ResourceURL.URL_GET_IMAGE_PROMOTIONAL)
    fun imagePromotional(): Call<String>


}