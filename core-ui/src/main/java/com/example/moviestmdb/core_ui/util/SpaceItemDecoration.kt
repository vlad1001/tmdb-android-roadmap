package com.example.moviestmdb.core_ui.util

import android.graphics.Rect
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SpaceItemDecoration(
    val marging: Int = 0,
    val marginEdges: Int = 0
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
        if (itemPosition == RecyclerView.NO_POSITION) {
            return
        }

        val orientation = getOrientation(parent)
        val itemCount = parent.childCount

        var left = 0
        var top = 0
        var right = 0
        var bottom = 0

        if (orientation == LinearLayoutManager.HORIZONTAL) {
            /** All positions  */
            left = marging
            right = marging
            /** First position  */
            if (itemPosition == 0) {
                left += marginEdges
            } else if (itemCount > 0 && itemPosition == itemCount - 1) {
                right += marginEdges
            }
        } else {
            /** All positions  */
            top = marging
            bottom = marging
            /** First position  */
            if (itemPosition == 0) {
                top += marginEdges
            } else if (itemCount > 0 && itemPosition == itemCount - 1) {
                bottom += marginEdges
            }
        }

        if (!isReverseLayout(parent)) {
            outRect.set(left, top, right, bottom)
        } else {
            outRect.set(right, bottom, left, top)
        }
    }

    private fun isReverseLayout(parent: RecyclerView): Boolean {
        return (parent.layoutManager as? LinearLayoutManager)?.reverseLayout!!
    }

    private fun getOrientation(parent: RecyclerView): Int {
        return (parent.layoutManager as? LinearLayoutManager)?.orientation!!
    }
}