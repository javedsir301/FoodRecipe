package com.example.foodrecipes

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.foodrecipes.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var rvAdapter: PopularAdapter
    private lateinit var dataList:ArrayList<Recipe>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        enableEdgeToEdge()
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setUpRecyclerView()
        binding.search.setOnClickListener{
            startActivity(Intent(this,SearchActivity::class.java))
        }

        binding.salad.setOnClickListener{
            val mySaladIntent=Intent(this,CategoryActivity::class.java)
            mySaladIntent.putExtra("tittle","Salad")
            mySaladIntent.putExtra("Category","Salad")
            startActivity(mySaladIntent)
        }

        binding.mainDish.setOnClickListener{
            val myMainDishIntent=Intent(this,CategoryActivity::class.java)
            myMainDishIntent.putExtra("tittle","Main Dish")
            myMainDishIntent.putExtra("Category","Dish")
            startActivity(myMainDishIntent)
        }

        binding.drinks.setOnClickListener{
            val myDrinksIntent=Intent(this,CategoryActivity::class.java)
            myDrinksIntent.putExtra("tittle","Drinks")
            myDrinksIntent.putExtra("Category","Drinks")
            startActivity(myDrinksIntent)
        }

        binding.desserts.setOnClickListener{
            val myDessertsIntent=Intent(this,CategoryActivity::class.java)
            myDessertsIntent.putExtra("tittle","Desserts").toString()
            myDessertsIntent.putExtra("Category","Desserts")
            startActivity(myDessertsIntent)
        }

        binding.moreDetails.setOnClickListener {
            val dialog =Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.bottom_sheet)
            dialog.show()
            dialog.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)

            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window!!.setGravity(Gravity.BOTTOM)
        }


    }

    private fun setUpRecyclerView() {
        dataList= ArrayList()
        binding.rvPopular.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        val db= Room.databaseBuilder(this@HomeActivity,AppDatabase::class.java,"db_name")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .createFromAsset("recipe.db")
            .build()

        val daoObject=db.getDao()
        val recipes=daoObject.getAll()
        for (i in recipes!!.indices){
            if (recipes[i]!!.category.contains("Popular")){
                dataList.add(recipes[i]!!)
            }
            rvAdapter=PopularAdapter(dataList,this)
            binding.rvPopular.adapter=rvAdapter
        }
    }
}