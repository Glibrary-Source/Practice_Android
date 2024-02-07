package com.twproject.practice_recyclerview

import android.graphics.Canvas
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.twproject.practice_recyclerview.adapter.UserAdapter
import com.twproject.practice_recyclerview.model.User
import java.util.Collections
import kotlin.math.max
import kotlin.math.min

class ItemTouchSimpleCallback : ItemTouchHelper.SimpleCallback(
    ItemTouchHelper.UP or ItemTouchHelper.DOWN,
    ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
) {

    private var currentPosition: Int? = null
    private var previousPosition: Int? = null
    private var currentDx = 0f

    // 삭제 버튼 width를 넣을 값
    private var clamp = 0f

    interface OnItemMoveListener {
        fun onItemMove(from: Int, to: Int)
    }

    private var listener: OnItemMoveListener? = null

    fun setOnItemMoveListener(listener: OnItemMoveListener) {
        this.listener = listener
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {

        // 어댑터 획득
        val adapter = recyclerView.adapter as UserAdapter

        // 현재 포지션 획득
        val fromPosition = viewHolder.adapterPosition

        // 옮길 포지션 획득
        val toPosition = target.adapterPosition

        // adapter 리스트를 담기위한 변수 생성
        val list = arrayListOf<User>()

        // adapter가 가지고 있는 현재 리스트 획득
        list.addAll(adapter.differ.currentList)

        // 리스트 순서 바꿈
        Collections.swap(list, fromPosition, toPosition)

        // adapter.notifyItemMoved(fromPosition, toPosition)와 같은 역할
        // list를 adapter.differ.submitList()로 데이터 변경 사항 알림
        adapter.differ.submitList(list)

        // 추가적인 조치가 필요할 경우 인터페이스를 통해 해결
        listener?.onItemMove(fromPosition, toPosition)

        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}

    // 드래그 완료후 UI
    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)

        // 순서 조정 완료 후 투명도 다시 1f로 변경
        viewHolder.itemView.alpha = 1.0f

        getDefaultUIUtil().clearView(getView(viewHolder))
        previousPosition = viewHolder.adapterPosition
    }

    // 드래그 중 UI 변화
    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {

        if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
            // 순서 변경 시 alpha를 0.5f
            viewHolder?.itemView?.alpha = 0.5f
        }

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            viewHolder?.let {
                // 삭제 버튼 width 획득
                clamp = getViewWidth(viewHolder)
                // 현재 뷰홀더
                currentPosition = viewHolder.adapterPosition
                getDefaultUIUtil().onSelected(getView(it))
            }
        }

        super.onSelectedChanged(viewHolder, actionState)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            val view = getView(viewHolder)
            val isClamped = getTag(viewHolder)

            val x = clampViewPositionHorizontal(view, dX, isClamped, isCurrentlyActive)

            currentDx = x

            getDefaultUIUtil().onDraw(
                c, recyclerView, view, x, dY, actionState, isCurrentlyActive
            )
        }

        if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
    }

    // 삭제버튼 width 구하는 함수
    private fun getViewWidth(viewHolder: RecyclerView.ViewHolder): Float{
        val viewWidth = (viewHolder as UserAdapter.ViewHolder).itemView.findViewById<TextView>(R.id.remove_text_view).width
        return viewWidth.toFloat()
    }

    // swipe될 뷰 (우리가 스와이프할 시 움직일 화면)
    private fun getView(viewHolder: RecyclerView.ViewHolder): View {
        return (viewHolder as UserAdapter.ViewHolder).itemView.findViewById(R.id.swipe_view)
    }

    // view의 tag로 스와이프 고정됐는지 안됐는지 확인 (고정 == true)
    private fun getTag(viewHolder: RecyclerView.ViewHolder): Boolean {
        return viewHolder.itemView.tag as? Boolean ?: false
    }

    // view의 tag에 스와이프 고정됐으면 true, 안됐으면 false 값 넣기
    private fun setTag(viewHolder: RecyclerView.ViewHolder, isClamped: Boolean) {
        viewHolder.itemView.tag = isClamped
    }

    // 스와이프 될 가로(수평평) 길이
    private fun clampViewPositionHorizontal(
        view: View,
        dX: Float,  //
        isClamped: Boolean,
        isCurrentlyActive: Boolean
    ): Float {
        val maxSwipe: Float = -clamp * 1.5f

        val right = 0f

        val x = if (isClamped) {
            if (isCurrentlyActive) dX - clamp else -clamp
        } else dX

        return min(
            max(maxSwipe, x),
            right
        )
    }

    // 사용자가 Swipe 동작으로 간주할 최소 속도
    override fun getSwipeEscapeVelocity(defaultValue: Float): Float {
        return defaultValue * 10
    }

    // 사용자가 스와이프한 것으로 간주할 view 이동 비율
    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        setTag(viewHolder, currentDx <= -clamp)
        return 2f
    }

    // 다른 아이템 클릭 시 기존 swipe되어있던 아이템 원상복구
    fun removePreviousClamp(recyclerView: RecyclerView) {
        if (currentPosition == previousPosition)
            return
        previousPosition?.let {
            val viewHolder = recyclerView.findViewHolderForAdapterPosition(it) ?: return
            getView(viewHolder).translationX = 0f
            setTag(viewHolder, false)
            previousPosition = null
        }
    }

}