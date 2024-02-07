package com.twproject.practice_mediastore

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class TestAdapter(
    private val data: MutableList<Uri>
    ) :
RecyclerView.Adapter<TestAdapter.ViewHolder>(){

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val testImage: ImageView = view.findViewById(R.id.img_test_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.test_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: TestAdapter.ViewHolder, position: Int) {
        val item = data[position]
        holder.testImage.setImageURI(item)
    }

    override fun getItemCount(): Int {
        return data.count()
    }
}