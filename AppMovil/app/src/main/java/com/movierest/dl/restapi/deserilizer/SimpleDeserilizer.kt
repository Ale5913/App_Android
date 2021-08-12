package com.movierest.dl.restapi.deserilizer

import android.util.Log
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.movierest.dl.model.Favorite
import com.movierest.dl.model.Movie
import com.movierest.dl.restapi.model.MoviesReponse
import java.lang.reflect.Type

class SimpleDeserilizer : JsonDeserializer<String> {
    val TAG = "SimpleDeserilizer"
    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): String {
        Log.i(TAG, json.toString())

        var jsonObject = json.asJsonObject

        // construir nuestra respuesta
        // devuelve una pelicula-movie
        return jsonObject.get("res").asString
    }


}