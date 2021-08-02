package com.movierest.dl.restapi.deserilizer

import android.util.Log
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.movierest.dl.model.Favorite
import com.movierest.dl.model.Movie
import com.movierest.dl.restapi.model.MoviesResponse
import java.lang.reflect.Type

class FavoriteDeserilizer: JsonDeserializer<Favorite> {
    val TAG="FavoriteDeserilizer"
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Favorite {
            Log.i(TAG, json.toString())

            var jsonObject = json.asJsonObject

            // construir nuestra respuesta
            // devuelve una pelicula-movie
            return myDeserialize(jsonObject)
        }

        companion object {

            fun myDeserialize(jsonObject: JsonObject): Favorite {
                val favorite = Favorite()

                favorite.idPelicula = jsonObject.get("idPelicula").asInt
                favorite.idFavorito = jsonObject.get("idFavorito").asInt
                favorite.idusuario = jsonObject.get("idUsuario").asInt
                return favorite
            }
        }
    }