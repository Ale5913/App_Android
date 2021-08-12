package com.movierest.dl.restapi.deserilizer

import android.util.Log
import com.google.gson.*
import com.movierest.dl.model.Favorite
import com.movierest.dl.model.Movie
import com.movierest.dl.model.TypeMovie
import com.movierest.dl.restapi.model.MoviesReponse
import com.movierest.dl.restapi.model.QualificationsReponse
import com.movierest.dl.restapi.model.TypesMovieReponse
import java.lang.reflect.Type

class TypeMovieDeserilizer : JsonDeserializer<TypesMovieReponse> {
    val TAG = "TypesMovieReponse"
    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): TypesMovieReponse {
        Log.i(TAG, json.toString())

        // construir nuestra respuesta
        // devuelve una pelicula-movie
        return TypeMovieDeserilizer.myDeserialize(json.asJsonArray)
    }

    companion object {

        fun myDeserialize(data: JsonArray) : TypesMovieReponse {
            var typesMovieReponse = TypesMovieReponse()

            for (jsonElement in data){
                var jsonObject = jsonElement.asJsonObject
                val typeMovie = TypeMovie()

                typeMovie.type_movie_id = jsonObject.get("type_movie_id").asInt
                typeMovie.name = jsonObject.get("name").asString

                typesMovieReponse.typesMovie.add(typeMovie)
            }

            return typesMovieReponse
        }

    }

}