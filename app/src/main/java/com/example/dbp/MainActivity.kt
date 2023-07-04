package com.example.dbp

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import android.widget.Button
import android.widget.PopupMenu

import android.view.Menu
import android.widget.ListView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray

class MainActivity : AppCompatActivity() {
    private val dataList = mutableListOf<DataItem>()
    private lateinit var requestQueue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.textView)
        val btnIniciarSesion = findViewById<Button>(R.id.btnIniciarSesion)

        btnIniciarSesion.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        val btnOpciones = findViewById<Button>(R.id.btnOpciones)
        btnOpciones.setOnClickListener { view ->
            val popupMenu = PopupMenu(this, view)
            popupMenu.inflate(R.menu.menu_opciones)

            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menu_buscar_libros -> {
                        if (popupMenu.menu.findItem(R.id.menu_buscar_todos) == null) {
                            popupMenu.menu.add(Menu.NONE, R.id.menu_buscar_todos, Menu.NONE, "Buscar todos")
                            popupMenu.menu.add(Menu.NONE, R.id.menu_matematicas, Menu.NONE, "Matemáticas")
                            popupMenu.menu.add(Menu.NONE, R.id.menu_programacion, Menu.NONE, "Programación")
                            popupMenu.menu.add(Menu.NONE, R.id.menu_comunicacion, Menu.NONE, "Comunicación")
                        } else {
                            popupMenu.menu.removeItem(R.id.menu_buscar_todos)
                            popupMenu.menu.removeItem(R.id.menu_matematicas)
                            popupMenu.menu.removeItem(R.id.menu_programacion)
                            popupMenu.menu.removeItem(R.id.menu_comunicacion)
                        }
                    }
                    R.id.menu_buscar_todos -> { getBooks() }
                    R.id.menu_matematicas -> { getBooks(1) }
                    R.id.menu_programacion -> { getBooks(2) }
                    R.id.menu_comunicacion -> { getBooks(3) }
                    R.id.menu_contactos -> { textView.text = item.title.toString() }
                }
                true
            }
            popupMenu.show()
        }
        getBooks()
    }

    private fun getBooks() {
        consultRoute("http://192.168.0.20:5000/books")
    }

    private fun getBooks(idCategory: Int) {

        consultRoute("http://192.168.0.20:5000/books/categorias/" + idCategory.toString())
    }

    private fun consultRoute(url: String) {
        val listView = findViewById<ListView>(R.id.postListView)
        val adapter = DataAdapter(dataList)
        listView.adapter = adapter
        requestQueue = Volley.newRequestQueue(this)
        val textView = findViewById<TextView>(R.id.textView)
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                val parsedData = parseResponse(response)
                textView.text = url
                dataList.clear()
                dataList.addAll(parsedData)
                adapter.notifyDataSetChanged()
            },
            { error -> println(error.toString()) })
        requestQueue.add(jsonArrayRequest)
    }

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
