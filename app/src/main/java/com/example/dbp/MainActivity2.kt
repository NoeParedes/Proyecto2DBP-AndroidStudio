package com.example.dbp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray

class MainActivity2 : AppCompatActivity() {
    private val dataList = mutableListOf<DataItem>()
    private lateinit var requestQueue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val listView = findViewById<ListView>(R.id.postListView)
        val adapter = DataAdapter(dataList);
        listView.adapter = adapter;
        requestQueue = Volley.newRequestQueue(this)
        val url = "https://jsonplaceholder.typicode.com/posts"

        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET, url, null,
            { response ->
                val parsedData = parseResponse(response)
                dataList.addAll(parsedData)
                adapter.notifyDataSetChanged()
            },
            { error ->
                println(error.toString())
            })

        requestQueue.add(jsonArrayRequest)

    }

    // * Recibe un JSON y devuelve una lista con los elementos ya representados en clases

    private fun parseResponse(response: JSONArray): List<DataItem> {
        val dataList = mutableListOf<DataItem>()
        try {
            for (i in 0 until response.length()) {
                val jsonObject = response.getJSONObject(i)
                val userId = jsonObject.getInt("userId")
                val id = jsonObject.getInt("id")
                val title = jsonObject.getString("title")
                val body = jsonObject.getString("body")

                val dataItem = DataItem(userId, id, title, body)
                dataList.add(dataItem)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return dataList
    }
}