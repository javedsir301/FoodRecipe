package com.example.foodrecipes

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.foodrecipes.databinding.ActivityCategoryBinding


class CategoryActivity : AppCompatActivity() {
    private lateinit var rvAdapter: CategoryAdapter
    private lateinit var dataList:ArrayList<Recipe>

    private val binding by lazy {
        ActivityCategoryBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        val intent = intent
        val strTemp = intent.getStringExtra("tempstring")
        binding.rvTittle.text = strTemp

        title=intent.getStringExtra("tittle")
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setUpRecyclerView()
        binding.catGoBackHome.setOnClickListener {
            finish()
        }
    }


    private fun setUpRecyclerView() {
        dataList= ArrayList()
        binding.rvCategory.layoutManager=
            LinearLayoutManager(this)
        val db= Room.databaseBuilder(this@CategoryActivity,AppDatabase::class.java,"db_name")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .createFromAsset("recipe.db")
            .build()

        val daoObject=db.getDao()
        val recipes=daoObject.getAll()
        for (i in recipes!!.indices){
            if (recipes[i]!!.category.contains(intent.getStringExtra("Category")!!)){
                dataList.add(recipes[i]!!)
            }
            rvAdapter=CategoryAdapter(dataList,this)
            binding.rvCategory.adapter=rvAdapter
        }
    }
}