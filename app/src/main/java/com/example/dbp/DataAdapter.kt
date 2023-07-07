package com.example.dbp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class DataAdapter(private val dataList: List<DataItem>) : BaseAdapter() {

    override fun getCount(): Int {
        return dataList.size
    }

    override fun getItem(position: Int): Any {
        return dataList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(parent?.context).inflate(R.layout.list_item_data, parent, false)

        val dataItem = dataList[position]

        val imageViewBook       = view.findViewById<ImageView>(R.id.imageViewBook)
        val textViewTitle       = view.findViewById<TextView>(R.id.textViewTitle)
        val textViewAuthor      = view.findViewById<TextView>(R.id.textViewAuthor)
        val textViewDescription = view.findViewById<TextView>(R.id.textViewDescription)
        val textViewPrice       = view.findViewById<TextView>(R.id.textViewPrice)

        textViewTitle.text = dataItem.titulo
        textViewAuthor.text = "Autor: ${dataItem.autor}"
        textViewDescription.text = "Descripción: ${dataItem.descripcion}"
        textViewPrice.text = "Precio: ${dataItem.precio}"

        imageViewBook.setImageResource(R.drawable.tarzan)

        return view
    }
}

class PurchaseItemDataAdapter(private val dataList: List<PurchaseItem>) : BaseAdapter() {

    override fun getCount(): Int {
        return dataList.size
    }

    override fun getItem(position: Int): Any {
        return dataList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(parent?.context).inflate(R.layout.list_item_purchase, parent, false)
        val dataItem = dataList[position]

        val textViewTitle        = view.findViewById<TextView>(R.id.textViewTitle)
        val textViewDatePurchase = view.findViewById<TextView>(R.id.textViewDatePurchase)
        val textViewPrice        = view.findViewById<TextView>(R.id.textViewPrice)

        textViewTitle.text        = "Titulo: ${dataItem.title}"
        textViewPrice.text        = "Precio: ${dataItem.price}"
        textViewDatePurchase.text = "Fecha de compra: ${dataItem.day} - ${dataItem.hour}"
        return view
    }
}

/*
* class PurchaseItemAdapter(private val dataList: List<PurchaseItem>) : BaseAdapter() {

    override fun getCount(): Int {
        return dataList.size
    }

    override fun getItem(position: Int): Any {
        return dataList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(parent?.context).inflate(R.layout.list_item_data, parent, false)

        val dataItem = dataList[position]

        val imageViewBook       = view.findViewById<ImageView>(R.id.imageViewBook)
        val textViewTitle       = view.findViewById<TextView>(R.id.textViewTitle)
        val textViewAuthor      = view.findViewById<TextView>(R.id.textViewAuthor)
        val textViewDescription = view.findViewById<TextView>(R.id.textViewDescription)
        val textViewPrice       = view.findViewById<TextView>(R.id.textViewPrice)

        val id      : Int,
        val user_id : Int,
        val autor   : String,
        val title   : String,
        val price   : Float,
        val day     : String,
        val hour    : String

        textViewTitle.text = dataItem.titulo
        textViewAuthor.text = "Autor: ${dataItem.autor}"
        textViewDescription.text = "Descripción: ${dataItem.descripcion}"
        textViewPrice.text = "Precio: $${dataItem.precio}"

        imageViewBook.setImageResource(R.drawable.tarzan)

        return view
    }
}
* */
