package utils

import android.content.Context
import com.movierest.dl.model.User

class UserPreferences {
    class UserPreferences {
        companion object {

            val user = "user"
            val userEmail = "user_email"
            val userName = "user_name"
            val userId = "user_id"

            fun closeSession(context: Context){
                setId(context,0)
                setEmail(context,"")
                setName(context,"")
            }

            fun getUser(context: Context, user: User){
                setId(context,user.idusuario)
                setEmail(context,user.email)
                setName(context,user.nombre)
            }

            fun getEmail(context: Context): String?{
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

            fun getName(context: Context): String?{
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

        }
    }
}