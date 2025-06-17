package com.example.login.com.example.login

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.login.R

class ImageAdapter(
    private val images: List<Int>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.gridImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.grid_item, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.imageView.setImageResource(images[position])


        holder.imageView.post {
            val width = holder.imageView.width
            holder.imageView.layoutParams.height = width
            holder.imageView.requestLayout()
        }

        // Clicking the image triggers opening fullscreen view with this image
        holder.imageView.setOnClickListener {
            onItemClick(images[position])
        }
    }

    override fun getItemCount(): Int = images.size
}
