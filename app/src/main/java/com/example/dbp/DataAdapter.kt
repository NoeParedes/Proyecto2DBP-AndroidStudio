package com.example.dbp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Button
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class DataAdapter(private val dataList: List<DataItem>, private val userId: String) : BaseAdapter() {

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
        val buttonBuyBook        = view.findViewById<Button>(R.id.buttonBuyBooks)

        textViewTitle.text = dataItem.titulo
        textViewAuthor.text = "Autor: ${dataItem.autor}"
        textViewDescription.text = "Descripción: ${dataItem.descripcion}"
        textViewPrice.text = "Precio: ${dataItem.precio}"
        imageViewBook.setImageResource(R.drawable.tarzan)

        buttonBuyBook.setOnClickListener {
            val url = parent?.context?.getString(R.string.URL) + "/compras"
            val requestQueue = Volley.newRequestQueue(parent?.context)

            val jsonParams = JSONObject()
            try {
                jsonParams.put("user_id", userId.toInt())
                jsonParams.put("autor", dataItem.autor)
                jsonParams.put("title", dataItem.titulo)
                jsonParams.put("price", dataItem.precio.toString())
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            val stringRequest = object : StringRequest(
                Method.POST, url,
                Response.Listener { response ->
                    Toast.makeText(parent?.context, response, Toast.LENGTH_SHORT).show()
                },
                Response.ErrorListener { error ->
                    Toast.makeText(parent?.context, "Error: $error", Toast.LENGTH_SHORT).show()
                }) {
                override fun getBodyContentType(): String {
                    return "application/json"
                }

                override fun getBody(): ByteArray {
                    return jsonParams.toString().toByteArray()
                }
            }
            requestQueue.add(stringRequest)
        }

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
