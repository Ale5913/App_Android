package com.movierest.dl.restapi.deserilizer

import android.provider.ContactsContract
import android.util.Log
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.movierest.dl.model.Movie
import com.movierest.dl.restapi.model.MoviesResponse
import java.lang.reflect.Type

class MoviesDeserilizer :JsonDeserializer<MoviesResponse>{
    val TAG="MoviesDeserilizer"
    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): MoviesResponse {
        Log.i(TAG,json.toString())

        var data = json.asJsonArray

        var moviesReponse = MoviesResponse()

        // construir nuestra respuesta
        // de una coleccion de JSON a un arrayLIST - moviesRespose

        for (jsonElement in data){
            val jsonObject = jsonElement.asJsonObject

            val movie = Movie()
            movie.idPelicula= jsonObject.get("idPelicula").asInt
            movie.nombre= jsonObject.get("nombre").asString
            movie.anio= jsonObject.get("anio").asInt
            movie.imagen= jsonObject.get("imagen").asString
            movie.idGenero= jsonObject.get("idPelicula").asInt
            movie.genero= jsonObject.get("genero").asString

            moviesReponse.movies.add(movie)
        }
        return moviesReponse
    }


}
