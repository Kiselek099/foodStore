package com.example.productsstore
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.IOException


class EditActivity : AppCompatActivity(),Removable,Updatable{
    private  val GALLERY_REQUEST=302
    var food:Food?=null
    var photoUri:Uri?=null
    var foodList:MutableList<Food> = mutableListOf()
    lateinit var addImageIV:ImageView
    lateinit var nameET:EditText
    lateinit var costET:EditText
    lateinit var addBTN:Button
    lateinit var foodListLV:ListView
    lateinit var titleTB:Toolbar
    lateinit var descriptionET:EditText
    var check=true
    var listAdapter:ListAdapter?=null
    var item:Int?=null
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
            listAdapter=ListAdapter(this@EditActivity,foodList)
            foodListLV.adapter=listAdapter
            listAdapter?.notifyDataSetChanged()
            clearEditFields()
        }
        foodListLV.onItemClickListener=
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val food=listAdapter!!.getItem(position)
                item=position
                val dialog=MyAlertDialog()
                val args=Bundle()
                args.putSerializable("food",food)
                dialog.arguments=args
                dialog.show(supportFragmentManager,"custom")
            }
    }

    private fun createFood() {
        val name = nameET.text.toString()
        val cost = costET.text.toString()
        val description=descriptionET.text.toString()
        val image = photoUri.toString()
        if(name.isEmpty()||cost.isEmpty()) return
        val food = Food(name, cost, description ,image)
        foodList.add(food!!)

    }

    private fun clearEditFields() {
        nameET.text.clear()
        costET.text.clear()
        descriptionET.text.clear()
        addImageIV.setImageResource(R.drawable.baseline_fastfood_24)
        photoUri=null
    }

    private fun init() {
        addImageIV = findViewById(R.id.addImageIV)
        nameET = findViewById(R.id.nameET)
        costET = findViewById(R.id.costET)
        descriptionET=findViewById(R.id.descriptionET)
        addBTN = findViewById(R.id.addBTN)
        foodListLV = findViewById(R.id.foodListLV)
        titleTB=findViewById(R.id.titleTB)
        setSupportActionBar(titleTB)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            GALLERY_REQUEST->if(resultCode== RESULT_OK){
                photoUri=data?.data
                addImageIV.setImageURI(photoUri)
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
                Toast.makeText(this,"Программа завершена", Toast.LENGTH_SHORT).show()
                true
            }
            else->super.onOptionsItemSelected(item)
        }
    }
    override fun remove(food: Food) {
        listAdapter?.remove(food)
    }
    override fun update(food: Food) {
        val intent=Intent(this,AboutFoodAct::class.java)
        intent.putExtra("food",food)
        intent.putExtra("photoUri", food.image)
        intent.putExtra("foods",this.foodList as ArrayList<Food>)
        intent.putExtra("position",item)
        intent.putExtra("check",check)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        check=intent.extras?.getBoolean("newCheck")?:true
        if (!check){
            foodList=intent.getSerializableExtra("list") as MutableList<Food>
            listAdapter= ListAdapter(this,foodList)
            check=true
        }
        foodListLV.adapter=listAdapter
    }
}