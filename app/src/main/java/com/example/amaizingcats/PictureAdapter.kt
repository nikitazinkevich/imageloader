package com.example.amaizingcats

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import coil.transform.RoundedCornersTransformation
import kotlinx.coroutines.*

class PictureAdapter : ListAdapter<Picture, PictureAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.create(parent)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind()
    }


    fun adaptSinglePicture() {
        notifyItemChanged(currentList.lastIndex)
    }


    class ViewHolder private constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private val coroutineScope = CoroutineScope(Dispatchers.Main + Job())

        companion object {
            fun create(parent: ViewGroup): ViewHolder {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_recycler_view_holder, parent, false)
                return ViewHolder(itemView)
            }
        }

        fun bind() {
            val imageView = itemView.findViewById<ImageView>(R.id.single_image)
            coroutineScope.launch {
                withContext(Dispatchers.IO) {
                    imageView.load(BuildConfig.IMAGE_URL) {
                        placeholder(R.drawable.broken_image)
                        memoryCachePolicy(CachePolicy.DISABLED)
                        crossfade(750)
                        transformations(RoundedCornersTransformation(7f))
                    }
                }

            }
        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<Picture>() {

        override fun areItemsTheSame(oldItem: Picture, newItem: Picture): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Picture, newItem: Picture): Boolean {
            return oldItem.id == newItem.id
        }

    }
}