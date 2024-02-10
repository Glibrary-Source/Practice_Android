package com.twproject.practice_recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.twproject.practice_recyclerview.R
import com.twproject.practice_recyclerview.model.User

class UserAdapter(
    private val mContext: Context,
): RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val swipeView: LinearLayout = itemView.findViewById(R.id.swipe_view)
        private val userImage: ImageView = itemView.findViewById(R.id.user_image)
        private val userNameText: TextView = itemView.findViewById(R.id.user_name_text)
        private val removeTextView: TextView = itemView.findViewById(R.id.remove_text_view)

        fun bind(user: User) {
            // 재사용 시 Swipe가 되어있다면 Swipe 원상복구
            swipeView.translationX = 0f

            userNameText.text = user.name

            removeTextView.setOnClickListener {
                val list = arrayListOf<User>()
                list.addAll(differ.currentList)
                list.remove(user)

                // 해당 아이템 삭제 adapter에 알리기기
                differ.submitList(list)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.user_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Differ 수정전
//        val user = mList[position]
//        holder.bind(user)

        // Differ 수정후
        val user = differ.currentList[position]
        holder.bind(user)
    }

    override fun getItemCount() = differ.currentList.size


    // 추가
    private val differCallback = object : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this ,differCallback)

}