package com.twproject.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.twproject.practice_gemini.R

class ChatListAdapter(
    private val context: Context,
    private val data: MutableList<List<String>>
): RecyclerView.Adapter<ChatListAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val chat: TextView = view.findViewById(R.id.text_item_chat)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_chat_list, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ChatListAdapter.ViewHolder, position: Int) {
        val item = data[position]
        holder.chat.text = "${item[0]}: ${item[1]}"
    }

    override fun getItemCount(): Int {
        return data.count()
    }

}