package com.example.dbp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import android.widget.Button
import android.widget.PopupMenu
import android.view.MenuInflater
import android.view.SubMenu

class MainActivity : AppCompatActivity() {
    var isBuscarLibrosExpanded: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val updateMsgButton = findViewById<Button>(R.id.update_msg_button)
        val startNewActivityButton = findViewById<Button>(R.id.button2)
        val textView =  findViewById<TextView>(R.id.textView)

        updateMsgButton.setOnClickListener {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val current = LocalDateTime.now().format(formatter)
            textView.text = current
        }

        startNewActivityButton.setOnClickListener {
            val intent = Intent(this, Category::class.java)
            startActivity(intent)
        }

        val btnOpciones = findViewById<Button>(R.id.btnOpciones)
        val opcionesMenu = PopupMenu(this, btnOpciones)
        val inflater: MenuInflater = opcionesMenu.menuInflater
        inflater.inflate(R.menu.menu_opciones, opcionesMenu.menu)
        val buscarLibrosSubMenu: SubMenu? = opcionesMenu.menu.findItem(R.id.menu_buscar_libros).subMenu

        opcionesMenu.setOnMenuItemClickListener { item ->
            val opcionSeleccionada: String = item.title.toString()
            textView.text = opcionSeleccionada
            if (opcionSeleccionada == "Contactos") {
                textView.text = "Contactos"
            }
            true
        }
        btnOpciones.setOnClickListener { opcionesMenu.show() }
    }
}