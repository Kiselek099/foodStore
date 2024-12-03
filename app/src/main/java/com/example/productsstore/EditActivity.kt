package com.example.productsstore

import android.app.ComponentCaller
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import androidx.appcompat.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.IOException
import kotlin.math.cos

class EditActivity : AppCompatActivity() {
    private  val GALLERY_REQUEST=302
    var bitmap: Bitmap?=null
    var foodList:MutableList<Food> = mutableListOf()
    lateinit var addImageIV:ImageView
    lateinit var nameET:EditText
    lateinit var costET:EditText
    lateinit var addBTN:Button
    lateinit var foodListLV:ListView
    lateinit var titleTB:Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        init()
        addImageIV.setOnClickListener{
            val photoPickerIntent=Intent(Intent.ACTION_PICK)
            photoPickerIntent.type="image/*"
            startActivityForResult(photoPickerIntent,GALLERY_REQUEST)
        }
        addBTN.setOnClickListener {
            createFood()
            val listAdapter=ListAdapter(this@EditActivity,foodList)
            foodListLV.adapter=listAdapter
            listAdapter.notifyDataSetChanged()
            clearEditFields()
        }
    }

    private fun createFood() {
        val name = nameET.text.toString()
        val cost = costET.text.toString()
        val image = bitmap
        if(name.isEmpty()||cost.isEmpty()) return
        val food = Food(name, cost, bitmap)
        foodList.add(food)
    }

    private fun clearEditFields() {
        nameET.text.clear()
        costET.text.clear()
        addImageIV.setImageResource(R.drawable.baseline_fastfood_24)
    }

    private fun init() {
        addImageIV = findViewById(R.id.addImageIV)
        nameET = findViewById(R.id.nameET)
        costET = findViewById(R.id.costET)
        addBTN = findViewById(R.id.addBTN)
        foodListLV = findViewById(R.id.foodListLV)
        titleTB=findViewById(R.id.titleTB)
        setSupportActionBar(titleTB)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        caller: ComponentCaller
    ) {
        super.onActivityResult(requestCode, resultCode, data, caller)
        when(resultCode){
            GALLERY_REQUEST->if(requestCode=== RESULT_OK){
                val selectedImage: Uri?=data?.data
                try {
                    bitmap=MediaStore.Images.Media.getBitmap(contentResolver,selectedImage)
                } catch (e:IOException){
                    e.printStackTrace()
                }
                addImageIV.setImageBitmap(bitmap)
            }
        }

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