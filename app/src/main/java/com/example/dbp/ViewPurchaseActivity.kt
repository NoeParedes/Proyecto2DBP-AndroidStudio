package com.example.dbp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import android.widget.ListView

import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray

//import java.time.LocalDateTime
//import java.time.format.DateTimeFormatter

class ViewPurchaseActivity: AppCompatActivity() {
    // private val dataList = mutableListOf<DataItem>()
    private var userId: String? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewpurchase)

        userId = intent.getStringExtra("userId")
        val buttonBack = findViewById<Button>(R.id.buttonBack)

        buttonBack.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
            finish()
        }
    }

    override fun onDestroy() {
        Toast.makeText(this@ViewPurchaseActivity, "Volviendo al menu", Toast.LENGTH_SHORT).show()
        super.onDestroy()
    }

    private fun getPurchases() {
        var url = getString(R.string.URL) + "/books/categorias/$userId"
    }

}