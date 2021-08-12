package com.movierest.dl.utils

import android.content.Context
import android.util.Log
import com.movierest.dl.model.User

class UserPreferences {
    companion object {

        val user = "user"
        val userEmail = "user_email"
        val userName = "user_name"
        val userToken = "token"
        val userId = "user_id"
        val userAvatar = "user_avatar"

        fun closeSession(context: Context){
            setId(context,0)
            setEmail(context,"")
            setName(context,"")
            setToken(context,"")
        }

        fun getUser(context: Context, user: User){
            setId(context,user.user_id)
            setEmail(context,user.email)
            setName(context,user.name)
            setToken(context,user.token)
            Log.i("GETUSER", getToken(context))
        }

        fun getEmail(context: Context): String{
            var preferences = context.
                    getSharedPreferences(user,
                        Context.MODE_PRIVATE)

            return preferences.getString(userEmail,"")
        }

        fun setEmail(context: Context, email: String){
            var preferences = context.
                getSharedPreferences(user,
                    Context.MODE_PRIVATE)

            val editor = preferences.edit()
            editor.putString(userEmail,email)
            editor.commit()

        }

        fun getToken(context: Context): String{
            var preferences = context.
                getSharedPreferences(user,
                    Context.MODE_PRIVATE)

            return preferences.getString(userToken,"")
        }

        fun setToken(context: Context, token: String){
            var preferences = context.
                getSharedPreferences(user,
                    Context.MODE_PRIVATE)

            val editor = preferences.edit()
            editor.putString(userToken,token)
            editor.commit()

        }

        fun getName(context: Context): String{
            var preferences = context.
                getSharedPreferences(user,
                    Context.MODE_PRIVATE)

            return preferences.getString(userName,"")
        }

        fun setName(context: Context, name: String){
            var preferences = context.
                getSharedPreferences(user,
                    Context.MODE_PRIVATE)

            val editor = preferences.edit()
            editor.putString(userName,name)
            editor.commit()

        }

        fun getId(context: Context): Int{
            var preferences = context.
                getSharedPreferences(user,
                    Context.MODE_PRIVATE)

            return preferences.getInt(userId,0)
        }

        fun setId(context: Context, id: Int){
            var preferences = context.
                getSharedPreferences(user,
                    Context.MODE_PRIVATE)

            val editor = preferences.edit()
            editor.putInt(userId,id)
            editor.commit()

        }

        fun getAvatar(context: Context): String{
            var preferences = context.
                getSharedPreferences(user,
                    Context.MODE_PRIVATE)

            return preferences.getString(userAvatar,"")
        }

        fun setAvatar(context: Context, name: String){
            var preferences = context.
                getSharedPreferences(user,
                    Context.MODE_PRIVATE)

            val editor = preferences.edit()
            editor.putString(userAvatar,name)
            editor.commit()

        }

    }
}