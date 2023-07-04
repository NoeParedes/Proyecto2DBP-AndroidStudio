package com.example.dbp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Button

import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray

class Category : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.category)

        val button3 = findViewById<Button>(R.id.buttonCategory3)
        val textView = findViewById<TextView>(R.id.textViewTest)

        button3.setOnClickListener {
            finish()
        }

        val requestQueue = Volley.newRequestQueue(this)
        val url = "http://192.168.0.20:5000/categorias"
        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET, url, null,
            { response ->
                parseResponse(response)
            }, { error ->
                if (error.toString() == "com.android.volley.TimeoutError") {
                    textView.text = "El servidor se encuentra inactivo"
                } else {textView.text = error.toString()}
            })
        requestQueue.add(jsonArrayRequest)
    }

    private fun parseResponse(response: JSONArray) {
        try {
            val layout = findViewById<LinearLayout>(R.id.categoryLayout)
            val textView = findViewById<TextView>(R.id.textViewTest)

            for (i in 0 until response.length()) {
                val jsonObject = response.getJSONObject(i)
                val id = jsonObject.getInt("id")
                val name = jsonObject.getString("nombre")

                val button = Button(this)
                button.text = name
                button.setOnClickListener {
                    textView.text = id.toString()
                }
                layout.addView(button)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}