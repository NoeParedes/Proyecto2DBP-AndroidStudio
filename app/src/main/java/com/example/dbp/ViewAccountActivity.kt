package com.example.dbp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException

class ViewAccountActivity : AppCompatActivity() {

    private lateinit var textViewName     : TextView
    private lateinit var textViewLastname : TextView
    private lateinit var textViewUsername : TextView
    private lateinit var textViewEmail    : TextView

    private var userId: String? = null
    private var password: String? = null

    @SuppressLint("MissingInflatedId")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewaccount)

        textViewName     = findViewById(R.id.textViewName)
        textViewLastname = findViewById(R.id.textViewLastname)
        textViewUsername = findViewById(R.id.textViewUsername)
        textViewEmail    = findViewById(R.id.textViewEmail)

        val buttonBackMenu     = findViewById<Button>(R.id.buttonBack)
        val buttonShowPassword = findViewById<Button>(R.id.buttonShowPassword)

        userId = intent.getStringExtra("userId")

        buttonBackMenu.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
            finish()
        }

        buttonShowPassword.setOnClickListener {
            Toast.makeText(this@ViewAccountActivity, password, Toast.LENGTH_SHORT).show()
        }

        viewAccount()
    }

    private fun viewAccount() {
        val url = getString(R.string.URL) + "/users/$userId"
        val requestQueue = Volley.newRequestQueue(this)

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                try {
                    textViewName.text     = response.getString("nombre")
                    textViewLastname.text = response.getString("apellido")
                    textViewUsername.text = response.getString("username")
                    textViewEmail.text    = response.getString("correo")
                    password = response.getString("password")
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
            ) { error ->
            Toast.makeText(this@ViewAccountActivity, "Error: $error", Toast.LENGTH_SHORT).show()
            }
        requestQueue.add(jsonObjectRequest)
    }
}
