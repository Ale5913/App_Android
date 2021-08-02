package com.movierest.dl.restapi.deserilizer

import android.provider.ContactsContract
import android.util.Log
import com.google.gson.*
import com.movierest.dl.model.Movie
import com.movierest.dl.restapi.model.FavoritesResponse
import com.movierest.dl.restapi.model.MoviesResponse
import com.movierest.dl.restapi.model.QualificationsResponse
import java.lang.reflect.Type

class QualificationsDeserilizer : JsonDeserializer<QualificationsResponse>{
    val TAG = "QualifsDeserilizer"
    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): QualificationsResponse {
        Log.i(TAG,json.toString())
        // construir nuestra respuesta
        // de una coleccion de JSON a un arrayLIST - moviesRespose
        return myDeserialize( json.asJsonArray)
    }

    companion object {

        fun myDeserialize(data: JsonArray) : QualificationsResponse{
            var qualificationsReponse = QualificationsResponse()

            for (jsonElement in data){
                val qualification = QualificationDeserilizer.myDeserialize(jsonElement.asJsonObject)
                qualificationsReponse.qualifications.add(qualification)
            }

            return qualificationsReponse
        }

    }

}

