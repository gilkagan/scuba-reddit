package com.gilka.scubareddit.custom

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

class LoadMoreScrollListener(private val loadMoreCallback: () -> Unit,
                             val layoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {

    private var isLoading = true
    private var loadedSoFar = 0
    private var totalItemsCount = 0

    private var firstVisible = 0
    private var visibleItemsCount = 0
    private var threshold = 12

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (dy <= 0) return // no change

        totalItemsCount = layoutManager.itemCount
        visibleItemsCount = recyclerView.childCount
        firstVisible = layoutManager.findFirstVisibleItemPosition()

        if (isLoading) {
            if (totalItemsCount > loadedSoFar) { // loading done
                isLoading = false
                loadedSoFar = totalItemsCount
            }
            return
        }

        if ((totalItemsCount - visibleItemsCount) <= (firstVisible + threshold)) { // need to load more
            loadMoreCallback()
            isLoading = true
        }
    }
}