package com.movierest.dl.restapi


import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.movierest.dl.model.Movie
import com.movierest.dl.model.User
import com.movierest.dl.restapi.deserilizer.MovieDeserilizer
import com.movierest.dl.restapi.deserilizer.MoviesDeserilizer
import com.movierest.dl.restapi.deserilizer.SimpleDeserilizer
import com.movierest.dl.restapi.deserilizer.UserDeserilizer
import com.movierest.dl.restapi.model.MoviesResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RestApiAdapter {
    //Usuarios

    fun gsonDeserizerUser() : Gson{
        val gsonBuilder = GsonBuilder().setLenient()
        gsonBuilder.registerTypeAdapter(User::class.java, UserDeserilizer())
        return  gsonBuilder.create()
    }
    //Peliculas

    fun conection(gson: Gson) :EndPointApi{
        val retrofit = Retrofit.Builder()
            .baseUrl(ResourceURL.URL_BASE)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(EndPointApi::class.java)
    }

    fun gsonDeserizerMovies() : Gson{
        val gsonBuilder = GsonBuilder().setLenient()
        gsonBuilder.registerTypeAdapter(MoviesResponse::class.java,MoviesDeserilizer())
        return  gsonBuilder.create()
    }
    fun gsonDeserizerMovie() : Gson{
        val gsonBuilder = GsonBuilder().setLenient()
        gsonBuilder.registerTypeAdapter(Movie::class.java, MovieDeserilizer())
        return  gsonBuilder.create()
    }
    fun gsonDeserizerSimple() : Gson{
        val gsonBuilder = GsonBuilder().setLenient()
        gsonBuilder.registerTypeAdapter(String::class.java, SimpleDeserilizer())
        return  gsonBuilder.create()
    }
}