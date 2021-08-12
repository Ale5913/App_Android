package com.movierest.dl.restapi.deserilizer

import android.util.Log
import com.google.gson.JsonArray
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.movierest.dl.restapi.model.FavoritesReponse
import java.lang.reflect.Type

class FavoritesDeserilizer : JsonDeserializer<FavoritesReponse>{
    val TAG = "FavoritesDeserilizer"
    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): FavoritesReponse {
        Log.i(TAG,json.toString())
        // construir nuestra respuesta
        // de una coleccion de JSON a un arrayLIST - moviesRespose
        return myDeserialize( json.asJsonArray)
    }

    companion object {

        fun myDeserialize(data: JsonArray) : FavoritesReponse{
            var favoritesReponse = FavoritesReponse()

            for (jsonElement in data){
                val favorite = FavoriteDeserilizer.myDeserialize(jsonElement.asJsonObject)
                favoritesReponse.favorites.add(favorite)
            }

            return favoritesReponse
        }

    }

}