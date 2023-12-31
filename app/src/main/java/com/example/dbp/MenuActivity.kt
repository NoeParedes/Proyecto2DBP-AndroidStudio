package com.example.dbp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.Button

class MenuActivity : AppCompatActivity() {
    private var userId: String? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        userId = intent.getStringExtra("userId")
        val buttonViewAccount  = findViewById<Button>(R.id.viewAccountButton)
        val buttonBuyBooks     = findViewById<Button>(R.id.buyBooksButton)
        val buttonViewPurchase = findViewById<Button>(R.id.viewPurchaseButton)
        val buttonLogout       = findViewById<Button>(R.id.logoutButton)

        buttonViewAccount.setOnClickListener() {
            val intent = Intent(this, ViewAccountActivity::class.java)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }

        buttonBuyBooks.setOnClickListener {
            val intent = Intent(this, BuyBooksActivity::class.java)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }

        buttonViewPurchase.setOnClickListener {
            val intent = Intent(this, ViewPurchaseActivity::class.java)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }

        buttonLogout.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
            finish()
        }

        Toast.makeText(this@MenuActivity, "Bienvenido usuario $userId", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        Toast.makeText(this@MenuActivity, "Cierre de sesión", Toast.LENGTH_SHORT).show()
        super.onDestroy()
    }
}
