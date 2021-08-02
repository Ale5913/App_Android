package com.movierest.dl.restapi.deserilizer

import android.util.Log
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.movierest.dl.model.Favorite
import com.movierest.dl.model.Movie
import com.movierest.dl.model.User
import com.movierest.dl.restapi.model.MoviesResponse
import java.lang.reflect.Type

class UserDeserilizer: JsonDeserializer<User> {
        val TAG = "UserDeserilizer"
        override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): User {
            Log.i(TAG, json.toString())

            var jsonObject = json.asJsonObject

            val user = User()

            user.email = jsonObject.get("email").asString
            user.nombre = jsonObject.get("name").asString
            user.idusuario = jsonObject.get("user_id").asInt

            if (jsonObject.get("password") != null)
                user.password = jsonObject.get("password").asString
            return user

        }


    }