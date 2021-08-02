package com.movierest.dl.restapi.deserilizer

import android.util.Log
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.movierest.dl.model.Favorite
import com.movierest.dl.model.Movie
import com.movierest.dl.model.Qualification
import com.movierest.dl.restapi.model.MoviesResponse
import java.lang.reflect.Type

class QualificationDeserilizer: JsonDeserializer<Qualification> {
    val TAG="QualiDeserilizer"
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Qualification {
        Log.i(TAG, json.toString())

        var jsonObject = json.asJsonObject

        // construir nuestra respuesta
        // devuelve una pelicula-movie
        return myDeserialize(jsonObject)
    }

    companion object {

        fun myDeserialize(jsonObject: JsonObject): Qualification {
            val qualification = Qualification()

            qualification.idPelicula= jsonObject.get("idPelicula").asInt
            qualification.idCalificacion= jsonObject.get("idCalificacion").asInt
            qualification.idusuario= jsonObject.get("idUsuario").asInt

            return qualification
        }

        fun average(jsonObject: JsonObject): Float {

            var average = 0.0F
            if (!jsonObject.get("average").isJsonNull)
                average = jsonObject.get("average").asFloat

            return average
        }

    }
}

