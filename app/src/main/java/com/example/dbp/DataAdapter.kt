package com.example.dbp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

// Esta clase adapter nos sirve para poder manejar el contenido de la lista de Views que queremos mostrar
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

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(parent?.context).inflate(R.layout.list_item_data, parent, false)

        val dataItem = dataList[position]

        val textViewTitle = view.findViewById<TextView>(R.id.textViewTitle)
        val textViewBody = view.findViewById<TextView>(R.id.textViewBody)

        textViewTitle.text = dataItem.title
        textViewBody.text = dataItem.body

        return view
    }
}