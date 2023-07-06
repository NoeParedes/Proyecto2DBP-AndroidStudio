package com.example.dbp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
// import com.android.volley.Request
import com.android.volley.Response

import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

// import android.util.Log

class RegisterActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val registerButton=findViewById<Button>(R.id.register_button)
        val tvGoLogin=findViewById<TextView>(R.id.tv_go_to_login)

        val name     = findViewById<EditText>(R.id.editTextName)
        val lastname = findViewById<EditText>(R.id.editTextLastname)
        val email    = findViewById<EditText>(R.id.editTextEmail)
        val password = findViewById<EditText>(R.id.editTextPassword)
        val repeated = findViewById<EditText>(R.id.editTextRepeatedPassword)

        registerButton.setOnClickListener {
            if (name.text.toString() == "" || lastname.text.toString() == "" || email.text.toString() == "" || password.text.toString() == "" || repeated.text.toString() == "") {
                Toast.makeText(this@RegisterActivity, "DEBE LLENAR TODOS LOS CAMPOS", Toast.LENGTH_SHORT).show()
            } else if (password.text.toString() != repeated.text.toString()) {
                Toast.makeText(this@RegisterActivity, "LAS CONTRASEÑAS DEBEN SER IGUALES", Toast.LENGTH_SHORT).show()
            } else {
                registerUser(name.text.toString(), lastname.text.toString(), email.text.toString(), password.text.toString())
                goToLogin()
            }
        }

        tvGoLogin.setOnClickListener{
            goToLogin()
        }
    }

    private fun goToLogin() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
        finish()
    }


    private fun registerUser(name: String, lastname: String, email: String, password: String) {
        val url = "http://192.168.0.20:5000/users"
        val requestQueue = Volley.newRequestQueue(this)

        val jsonParams = JSONObject()
        try {
            jsonParams.put("nombre", name)
            jsonParams.put("apellido", lastname)
            jsonParams.put("username", "$name.$lastname")
            jsonParams.put("correo", email)
            jsonParams.put("password", password)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val stringRequest = object : StringRequest(Method.POST, url,
            Response.Listener { response ->
                Toast.makeText(this@RegisterActivity, response, Toast.LENGTH_SHORT).show()
            },
            Response.ErrorListener { error ->
                Toast.makeText(this@RegisterActivity, "Error: $error", Toast.LENGTH_SHORT).show()
            }) {
            override fun getBodyContentType(): String {
                return "application/json"
            }

            override fun getBody(): ByteArray {
                return jsonParams.toString().toByteArray()
            }
        }
        requestQueue.add(stringRequest)
    }
}