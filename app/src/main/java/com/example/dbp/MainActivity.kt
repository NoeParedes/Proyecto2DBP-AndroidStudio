package com.example.dbp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    @SuppressLint("MissingInflatedId", "ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usernameEditText = findViewById(R.id.editTextEmail)
        passwordEditText = findViewById(R.id.editTextPassword)
        val loginButton = findViewById<Button>(R.id.btn_go_to_menu)
        val signupButton = findViewById<TextView>(R.id.tv_go_to_register)
        signupButton.isClickable = true

        signupButton.setOnClickListener {
            gotoRegister()
        }

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            if (username.isNotEmpty() && password.isNotEmpty()) {
                login(usernameEditText.text.toString(), passwordEditText.text.toString())
            } else {
                Toast.makeText(this@MainActivity, "Please enter username and password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun gotoRegister() {
        val i = Intent(this, RegisterActivity::class.java)
        startActivity(i)
    }
    private fun gotoMenu(id: String) {
        val i = Intent(this, MenuActivity::class.java)
        i.putExtra("userId", id)
        startActivity(i)
    }

    private fun login(username: String, password: String) {
        val url = getString(R.string.URL) + "/users/login"
        val requestQueue = Volley.newRequestQueue(this)
        val jsonParams = JSONObject()
        try {
            jsonParams.put("correo", username)
            jsonParams.put("password", password)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, jsonParams,
            { response ->
                try {
                    if (response.has("ERROR")) {
                        Toast.makeText(this@MainActivity, response.getString("ERROR"), Toast.LENGTH_SHORT).show()
                    } else {
                        gotoMenu(response.getString("id"))
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        ) { error ->
            Toast.makeText(this@MainActivity, "Error: $error", Toast.LENGTH_SHORT).show()
        }
        requestQueue.add(jsonObjectRequest)
    }
}