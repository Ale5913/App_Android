package com.movierest.dl.restapi

import com.movierest.dl.model.Movie
import com.movierest.dl.model.TypeMovie
import com.movierest.dl.model.User
import com.movierest.dl.restapi.model.MoviesReponse
import com.movierest.dl.restapi.model.TypesMovieReponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface EndPointApi {


    /*
    * Usuarios
    * */

    @FormUrlEncoded
    @POST(ResoucesURL.URL_POST_USER_LOGIN)
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<User>

    @FormUrlEncoded
    @POST(ResoucesURL.URL_POST_USER_REGISTER)
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("confirm_password") confirm_password: String
    ): Call<User>

    @GET(ResoucesURL.URL_GET_IS_LOGIN)
    fun isLogin(@Path("token") token: String): Call<String>

    @Multipart
    @POST(ResoucesURL.URL_POST_USER_UPLOAD_AVATAR)
    fun uploadAvatar(@Part image: MultipartBody.Part, @Path("user_id") user_id: Int): Call<String>



    /*
    * Peliculas
    * */
    @GET(ResoucesURL.URL_GET_SHOW_ALL_MOVIES)
    fun show(@Query("offset") offset: Int): Call<MoviesReponse>

    @GET(ResoucesURL.URL_GET_SHOW_ALL_MOVIES)
    fun search(
        @Query("name") name: String,
        @Query("year") year: String,
        @Query("offset") offset: Int
    ): Call<MoviesReponse>

    @GET(ResoucesURL.URL_GET_TYPES_MOVIE)
    fun typesMovies(): Call<TypesMovieReponse>

    @GET(ResoucesURL.URL_GET_SHOW_MOVIE)
    fun detailMovie(@Path("movie_id") movie_id: Int,
                    @Query("user_id") user_id: Int?): Call<Movie>

    @FormUrlEncoded
    @POST(ResoucesURL.URL_POST_FAVORITE)
    fun favorite(
        @Field("user_id") user_id: Int,
        @Field("token") token: String,
        @Field("movie_id") movie_id: Int,
        @Field("favorite") favorite: Int
    ): Call<String>

    @FormUrlEncoded
    @POST(ResoucesURL.URL_POST_QUALIFICATION)
    fun qualification(
        @Field("user_id") user_id: Int,
        @Field("token") token: String,
        @Field("movie_id") movie_id: Int,
        @Field("qualification") qualification: Float
    ): Call<String>

    /*
    * API
    * */

    @GET(ResoucesURL.URL_GET_IMAGE_PROMOTIONAL)
    fun imagePromotional(): Call<String>
}