package com.everis.rickmorty.ui.main

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.everis.rickmorty.databinding.ActivityDetailBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.URL

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val character = intent.getSerializableExtra("character") as? Results

        character?.let {
            binding.CharacterName.text = character.name
            binding.statusValueTv.text = character.status
            binding.speciesValueTv.text = character.species
            binding.genderValueTv.text = character.gender
            loadImageFromUrl(character.image,binding.imageView)
        }
    }

    private fun loadImageFromUrl(imageUrl: String?, imageView: ImageView) {
        GlobalScope.launch(Dispatchers.Main) {
            val bitmap = withContext(Dispatchers.IO) {
                try {
                    URL(imageUrl).openStream().use { inputStream ->
                        BitmapFactory.decodeStream(inputStream)
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                    null
                }
            }

            bitmap?.let {
                imageView.setImageBitmap(bitmap)
            }
        }
    }
}