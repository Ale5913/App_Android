package actividades.user

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.movierest.dl.MainActivity
import com.movierest.dl.R
import kotlinx.android.synthetic.main.user_activity.*
import utils.UserPreferences


class UserActivity : AppCompatActivity() {
    val TAG = "UserActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_activity)

        closeSessionIV.setOnClickListener{
            UserPreferences.closeSession(this)
            Toast.makeText(this, "Sesión finalizada exitosamente",Toast.LENGTH_LONG).show()
            var intent=Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        emailET.setText(UserPreferences.getEmail(this))
        nameET.setText(UserPreferences.getName(this))
    }

}
