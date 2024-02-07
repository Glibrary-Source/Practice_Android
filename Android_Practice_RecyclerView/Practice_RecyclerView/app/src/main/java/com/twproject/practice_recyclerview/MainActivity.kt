package com.twproject.practice_recyclerview

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.twproject.practice_recyclerview.adapter.UserAdapter
import com.twproject.practice_recyclerview.databinding.ActivityMainBinding
import com.twproject.practice_recyclerview.model.User

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UserAdapter
    private var userList = mutableListOf<User>()
    private val itemTouchSimpleCallback = ItemTouchSimpleCallback()
    private val itemTouchHelper = ItemTouchHelper(itemTouchSimpleCallback)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        for (i in 1 until 11) {
            val mUser = User(i, "user$i")
            userList.add(mUser)
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onResume() {
        super.onResume()

        initRecyclerView()
        setupEvents()
    }

    private fun initRecyclerView() {

        // RecyclerView에 리스트 추가 및 어댑터 연결
        adapter = UserAdapter(this)
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        binding.recyclerview.adapter = adapter

        // DiffUtil 적용 후 데이터 추가
        adapter.differ.submitList(userList)

        // itemTouchSimpleCallback 인터페이스로 추가 작업
        itemTouchSimpleCallback.setOnItemMoveListener(object : ItemTouchSimpleCallback.OnItemMoveListener {
            override fun onItemMove(from: Int, to: Int) {
                Log.d("MainActivity", "from Position : $from, to Position : $to")

                // userList에도 값이 변하는 걸 원한다면 Collections.swap으로 변경
                //Collections.swap(userList, from, to)

                // userList != adapter.differ.currentList
                // adapter.differ.currentList는 계속 값을 변경했지만 userList는 변경 전 값(왜냐면 우리는 변경한적이 없다.)
                Log.d("MainActivity", "userList: $userList")
                Log.d("MainActivity", "differ currentList: ${adapter.differ.currentList}")
            }
        })

        // itemTouchHelper와 recyclerview 연결
        itemTouchHelper.attachToRecyclerView(binding.recyclerview)

        // RecyclerView의 다른 곳을 터치하거나 Swipe 시 기존에 Swipe된 것은 제자리로 변경
        // 아래 코드가 경고 표시를 주는데 이것은 Annotation @SuppressLint("ClickableViewAccessibility")을 함수에 추가하면 됨
        // 또는, performClick 사용
        binding.recyclerview.setOnTouchListener { _, _ ->
            itemTouchSimpleCallback.removePreviousClamp(binding.recyclerview)
            false
        }
    }

    private fun setupEvents() {
        // 아이템 추가 버튼 클릭 시 이벤트(userList에 아이템 추가)
        binding.addButton.setOnClickListener {

            // 추가할 데이터 생성
            val mUser = User(userList.size+1, "added user ${userList.size+1}")

            // differ의 현재 리스트를 받아와서 newList에 넣기
            val newList = adapter.differ.currentList.toMutableList()

            // newList에 생성한 유저 추가
            newList.add(mUser)

            // adapter의 differ.submitList()로 newList 제출
            // submitList()로 제출하면 기존에 있는 oldList와 새로 들어온 newList를 비교하여 UI 반영
            adapter.differ.submitList(newList)

            // userList에도 추가 (꼭 안해줘도 되지만 userList가 필요할때가 있을 수도 있다.)
            // userList = adapter.differ.currentList 이렇게 사용하면 안됨
            userList.add(mUser)

            // 추가 메시지 출력
            Toast.makeText(this, "${mUser.name}이 추가되었습니다.", Toast.LENGTH_SHORT).show()

            // 추가된 포지션으로 스크롤 이동
            binding.recyclerview.scrollToPosition(userList.indexOf(mUser))
        }
    }
}