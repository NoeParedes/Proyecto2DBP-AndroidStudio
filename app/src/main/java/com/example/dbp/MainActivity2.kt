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
        val url = "http://192.168.18.118:5001/books"

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
                val autor = jsonObject.getString("autor")
                val descripcion = jsonObject.getString("descripcion")
                val id = jsonObject.getInt("id")
                val idCategory = jsonObject.getInt("id_category")
                val idUsuario = jsonObject.getInt("id_usuario")
                val precio = jsonObject.getDouble("precio")
                val titulo = jsonObject.getString("titulo")

                val dataItem = DataItem(autor, descripcion, id, idCategory, idUsuario, precio, titulo)
                dataList.add(dataItem)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return dataList
    }
}
