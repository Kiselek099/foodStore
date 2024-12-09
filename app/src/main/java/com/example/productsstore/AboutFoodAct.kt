package com.example.productsstore

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AboutFoodAct : AppCompatActivity() {
    private val GALLERY_REQUEST = 302
    var food: Food? = null
    var photoUri: Uri? = null
    lateinit var imageIV: ImageView
    lateinit var titleTB: Toolbar
    lateinit var nameET: EditText
    lateinit var costET: EditText
    lateinit var descriptionET: EditText

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_about_food)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        nameET = findViewById(R.id.nameET)
        costET = findViewById(R.id.costET)
        descriptionET = findViewById(R.id.descriptionET)
        titleTB = findViewById(R.id.titleTB)
        imageIV = findViewById(R.id.imageIV)
        setSupportActionBar(titleTB)
        food = intent.getSerializableExtra("food") as Food
        nameET.setText(food?.name)
        costET.setText(food?.cost)
        descriptionET.setText(food?.description)
        val image: Uri? = Uri.parse(food?.image)
        imageIV.setImageURI(image)
        imageIV.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, GALLERY_REQUEST)
        }

    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            GALLERY_REQUEST -> if (resultCode == RESULT_OK) {
                photoUri = data?.data
                imageIV.setImageURI(photoUri)
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.about_food_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.back -> {
                val foodList = intent.getSerializableExtra("foods")
                val item = intent.extras?.getInt("position")
                var check = intent.extras?.getBoolean("check")
                val photoUriNew=photoUri?: Uri.parse(food?.image)
                val food = Food(
                    nameET.text.toString(),
                    costET.text.toString(),
                    descriptionET.text.toString(),
                    photoUriNew.toString()
                )
                val list: MutableList<Food> = foodList as MutableList<Food>
                if (item != null) {
                    swap(item,food,foodList)
                }
                check=false
                val intent= Intent(this,EditActivity::class.java)
                intent.putExtra("list", list as ArrayList<Food>)
                intent.putExtra("newCheck",check)
                startActivity(intent)
                finish()
                true
            }

            R.id.exitApp -> {
                finishAffinity()
                Toast.makeText(this,"Программа завершена", Toast.LENGTH_SHORT).show()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun swap(item: Int, food: Food, foods: MutableList<Food>) {
        foods.add(item + 1, food)
        foods.removeAt(item)
    }
}