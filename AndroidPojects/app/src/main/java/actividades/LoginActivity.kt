package actividades

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.movierest.dl.MainActivity
import com.movierest.dl.R
import com.movierest.dl.model.User
import com.movierest.dl.restapi.RestApiAdapter
import kotlinx.android.synthetic.main.activity_detail_movie.*
import kotlinx.android.synthetic.main.login_activity.*
import kotlinx.android.synthetic.main.login_activity.emailET
import kotlinx.android.synthetic.main.login_activity.passwordET
import kotlinx.android.synthetic.main.register_activity.*
import retrofit2.Call
import retrofit2.Response
import utils.UserPreferences


class LoginActivity : AppCompatActivity() {
    val TAG = "LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        loginButton.setOnClickListener{
            _login()
        }
        registerTV.setOnClickListener {
            var intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
    private fun _login(){
        var restApiAdapter = RestApiAdapter()
        var gson = restApiAdapter.gsonDeserizerUser()
        val endPointApi = restApiAdapter.conection(gson)

        val call = endPointApi.login(emailET.text.toString(),
            passwordET.text.toString()
        )

        call.enqueue(object : retrofit2.Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                var user = response.body()
                user?.let {
                    if (it.idusuario == 0) {
                        Toast.makeText(
                            this@LoginActivity,
                            "Usuario o contraseña incorrecta",
                            Toast.LENGTH_LONG
                        ).show()

                    } else {
                        UserPreferences.getUser(this@LoginActivity, user)
                        var intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)

                        Toast.makeText(
                            this@LoginActivity,
                            "Hola ${user.nombre}",
                            Toast.LENGTH_LONG
                        ).show()

                        finish()
                    }
                }
            }
            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e(TAG,t.toString())
            }
        })
    }
    }
