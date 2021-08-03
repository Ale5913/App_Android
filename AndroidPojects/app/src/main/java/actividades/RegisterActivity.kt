package actividades

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
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


class RegisterActivity : AppCompatActivity() {
    val TAG = "LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_activity)

        registerButton.setOnClickListener{
            _register()
        }

    }

    private fun _register() {
        var restApiAdapter = RestApiAdapter()
        var gson = restApiAdapter.gsonDeserizerUser()
        val endPointsApi = restApiAdapter.conection(gson)

        val call = endPointsApi.register(
            nameET.text.toString(),
            emailET.text.toString(),
            passwordET.text.toString(),
            confirmPasswordET.text.toString()
        )

        call.enqueue(object : retrofit2.Callback<User> {

            override fun onResponse(call: Call<User>, response: Response<User>) {

                var user = response.body()

                user?.let {
                    if (it.idusuario == 0) {

                        errorTV.text = HtmlCompat.fromHtml(
                            user.nombre+user.email+user.password,
                            HtmlCompat.FROM_HTML_MODE_LEGACY
                        )

                    } else {
                        var intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                        startActivity(intent)

                        Toast.makeText(
                            this@RegisterActivity,
                            "Hola ${user.nombre}",
                            Toast.LENGTH_LONG
                        ).show()

                        finish()
                    }
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e(TAG, t.toString())
            }
        })
    }

}
