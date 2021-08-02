package com.movierest.dl.restapi.deserilizer

import android.provider.ContactsContract
import android.util.Log
import com.google.gson.*
import com.movierest.dl.model.Movie
import com.movierest.dl.restapi.model.FavoritesResponse
import com.movierest.dl.restapi.model.MoviesResponse
import java.lang.reflect.Type

class FavoritesDeserilizer :JsonDeserializer<FavoritesResponse>{
    val TAG="FavoritesReponse"
    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): FavoritesResponse {
        Log.i(TAG,json.toString())

        return myDeserialize( json.asJsonArray)
    }

    companion object {

        fun myDeserialize(data: JsonArray) : FavoritesResponse{
            var favoritesReponse = FavoritesResponse()

            for (jsonElement in data){
                val favorite = FavoriteDeserilizer.myDeserialize(jsonElement.asJsonObject)
                favoritesReponse.favorites.add(favorite)
            }

            return favoritesReponse
        }

    }

}


