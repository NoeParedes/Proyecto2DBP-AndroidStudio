package com.example.dbp

// Esta clase va a representar la información que recibimos en el JSON
data class DataItem(
    val autor: String,
    val descripcion: String,
    val id: Int,
    val id_category: Int,
    val id_usuario: Int,
    val precio: Double,
    val titulo: String
)
