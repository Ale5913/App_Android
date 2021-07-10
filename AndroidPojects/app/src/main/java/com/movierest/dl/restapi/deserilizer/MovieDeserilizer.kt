package com.movierest.dl.restapi.deserilizer

import android.util.Log
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.movierest.dl.model.Movie
import com.movierest.dl.restapi.model.MoviesResponse
import java.lang.reflect.Type

class MovieDeserilizer: JsonDeserializer<Movie> {
    val TAG="MovieDeserilizer"
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Movie {
        Log.i(TAG, json.toString())

        var jsonObject= json.asJsonObject

        //Construir respuesta de json a json object solo devuelve una pelicula

            val movie= Movie()

            movie.idPelicula= jsonObject.get("idPelicula").asInt
            movie.nombre= jsonObject.get("nombre").asString
            movie.anio= jsonObject.get("anio").asInt
            movie.descripcion= jsonObject.get("descripcion").asString
            movie.imagen= jsonObject.get("imagen").asString
            movie.idGenero= jsonObject.get("idPelicula").asInt
            movie.genero= jsonObject.get("genero").asString

        return movie
    }
}