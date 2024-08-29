package com.example.foodrecipes

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.foodrecipes.databinding.ActivityRecipeBinding

class RecipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecipeBinding
    private var imgCrop=true
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Glide.with(this).load(intent.getStringExtra("img")).into(binding.itemImg)
        binding.tittle.text=intent.getStringExtra("tittle")
        binding.stepData.text=intent.getStringExtra("des")


        val ing =intent.getStringExtra("ing")?.split("\n".toRegex())?.dropLastWhile { it.isEmpty() }
            ?.toTypedArray()
        binding.time.text=ing?.get(0)

        for (i in 1 until ing!!.size){
            binding.ingData.text="""${binding.ingData.text} 🟢 ${ing[i]}
                 
            """.trimIndent()
        }

        binding.stepBtn.background=null
        binding.stepBtn.setTextColor(getColor(R.color.black))

        binding.stepBtn.setOnClickListener {
            binding.stepBtn.setBackgroundResource(R.drawable.btn_ing)
            binding.stepBtn.setTextColor(getColor(R.color.white))
            binding.ingBtn.setTextColor(getColor(R.color.black))
            binding.ingBtn.background=null
            binding.stepScroll.visibility=View.VISIBLE
            binding.ingScroll.visibility=View.GONE

        }

        binding.ingBtn.setOnClickListener {
            binding.ingBtn.setBackgroundResource(R.drawable.btn_ing)
            binding.ingBtn.setTextColor(getColor(R.color.white))
            binding.stepBtn.setTextColor(getColor(R.color.black))
            binding.stepBtn.background=null
            binding.ingScroll.visibility=View.VISIBLE
            binding.stepScroll.visibility=View.GONE

        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.fullScreen.setOnClickListener {
            if (imgCrop){
                binding.itemImg.scaleType=ImageView.ScaleType.FIT_CENTER
                Glide.with(this).load(intent.getStringExtra("img")).into(binding.itemImg)
                binding.fullScreen.setColorFilter(Color.BLACK,PorterDuff.Mode.SRC_ATOP)
                binding.shade.visibility=View.GONE
                imgCrop=!imgCrop
            }else{
                binding.itemImg.scaleType=ImageView.ScaleType.CENTER_CROP
                Glide.with(this).load(intent.getStringExtra("img")).into(binding.itemImg)
                binding.fullScreen.colorFilter = null
                binding.shade.visibility=View.GONE
                imgCrop=!imgCrop
            }
        }

        binding.backBtn.setOnClickListener {
            finish()
        }
    }
}