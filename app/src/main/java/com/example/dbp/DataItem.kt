package com.example.dbp

data class DataItem(
    val autor: String,
    val descripcion: String,
    val id: Int,
    val id_category: Int,
    val id_usuario: Int,
    val precio: Double,
    val titulo: String
)

data class PurchaseItem(
    val id      : Int,
    val user_id : Int,
    val autor   : String,
    val title   : String,
    val price   : Double,
    val day     : String,
    val hour    : String
)
