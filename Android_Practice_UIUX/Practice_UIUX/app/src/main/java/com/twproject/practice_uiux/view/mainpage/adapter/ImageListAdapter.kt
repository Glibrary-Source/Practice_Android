package com.twproject.practice_uiux.view.mainpage.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.twproject.practice_uiux.R

class ImageListAdapter(
    private val context: Context,
    private val imageList: List<Drawable?>
) : RecyclerView.Adapter<ImageListAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.img_item_main)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.img_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return imageList.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = imageList[position]
        holder.imageView.setImageDrawable(item)
    }
}