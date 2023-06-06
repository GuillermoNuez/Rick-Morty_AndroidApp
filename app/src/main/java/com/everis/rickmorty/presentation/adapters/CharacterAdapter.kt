package com.everis.rickmorty.presentation.adapters

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.everis.rickmorty.R
import com.everis.rickmorty.ui.main.Results
import kotlinx.coroutines.*
import java.io.IOException
import java.net.URL

class CharacterAdapter(
    private val characterList: ArrayList<Results>,
    private val onCharacterClick: (Results) -> Unit
) :
    RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.character_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = characterList[position]
        holder.bind(character)

        holder.itemView.setOnClickListener {
            onCharacterClick(character)
        }
    }

    override fun getItemCount(): Int {
        return characterList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var image: ImageView = itemView.findViewById(R.id.image)
        private var name: TextView = itemView.findViewById(R.id.Name)
        fun bind(character: Results) {
            name.text = character.name
            loadImageFromUrl(character.image, image)
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