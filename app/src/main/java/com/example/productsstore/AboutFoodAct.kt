package com.example.productsstore

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AboutFoodAct : AppCompatActivity() {
    var food:Food?=null
    lateinit var imageIV:ImageView
    lateinit var titleTB: Toolbar
    lateinit var nameTV:TextView
    lateinit var costTV:TextView
    lateinit var descriptionTV:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_about_food)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        nameTV=findViewById(R.id.nameTV)
        costTV=findViewById(R.id.costTV)
        descriptionTV=findViewById(R.id.descriptionTV)
        titleTB=findViewById(R.id.titleTB)
        imageIV=findViewById(R.id.imageIV)
        setSupportActionBar(titleTB)
        food=intent.getSerializableExtra("food") as Food
        nameTV.text=food?.name
        costTV.text=food?.cost
        descriptionTV.text=food?.description
        val photoUriString = intent.getStringExtra("photoUri")
        val photoUri = Uri.parse(photoUriString)
        imageIV.setImageURI(photoUri)

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_menu,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.exitApp->{
                finishAffinity()
                true
            }
            else->super.onOptionsItemSelected(item)
        }
    }
}