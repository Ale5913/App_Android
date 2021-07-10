package com.movierest.dl.restapi


import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.movierest.dl.restapi.deserilizer.MoviesDeserilizer
import com.movierest.dl.restapi.model.MoviesResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RestApiAdapter {
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
}