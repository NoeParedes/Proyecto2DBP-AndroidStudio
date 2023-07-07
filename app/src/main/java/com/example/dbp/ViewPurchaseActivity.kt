package com.example.dbp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import android.widget.ListView
import android.widget.TextView

import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray

class ViewPurchaseActivity: AppCompatActivity() {
    private val dataList = mutableListOf<PurchaseItem>()
    private lateinit var requestQueue: RequestQueue
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

        getPurchases()
    }

    override fun onDestroy() {
        Toast.makeText(this@ViewPurchaseActivity, "Volviendo al menu", Toast.LENGTH_SHORT).show()
        super.onDestroy()
    }

    private fun getPurchases() {
        val url = getString(R.string.URL) + "/compras/usuario/$userId"
        val listView = findViewById<ListView>(R.id.listViewPurchases)
        val adapter = PurchaseItemDataAdapter(dataList)
        listView.adapter = adapter
        requestQueue = Volley.newRequestQueue(this)
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                val parsedData = parseResponse(response)
                dataList.addAll(parsedData)
                adapter.notifyDataSetChanged()
            },
            { error -> println(error.toString()) })
        requestQueue.add(jsonArrayRequest)
    }

    private fun parseResponse(response: JSONArray): List<PurchaseItem> {
        val dataList = mutableListOf<PurchaseItem>()
        try {
            for (i in 0 until response.length()) {
                val jsonObject   = response.getJSONObject(i)
                val id      = jsonObject.getInt("id")
                val user_id = jsonObject.getInt("user_id")
                val autor   = jsonObject.getString("autor")
                val title   = jsonObject.getString("title")
                val price   = jsonObject.getDouble("price")
                val day     = jsonObject.getString("day")
                val hour    = jsonObject.getString("hour")
                val dataItem     = PurchaseItem(id, user_id, autor, title, price, day, hour)
                dataList.add(dataItem)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return dataList
    }
}