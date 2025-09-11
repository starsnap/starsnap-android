package com.photo.starsnap.main.utils.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.photo.starsnap.network.star.StarRepository
import com.photo.starsnap.network.star.dto.StarResponseDto

class StarPagingSource(
    private val starRepository: StarRepository
) : PagingSource<Int, StarResponseDto>() {

    override fun getRefreshKey(state: PagingState<Int, StarResponseDto>): Int? {
        return state.anchorPosition?.let { achorPosition ->
            state.closestPageToPosition(achorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(achorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, StarResponseDto> {
        return try {
            val page = params.key ?: 0

            val data = starRepository.getFeedSnap(
                size = PAGING_SIZE,
                page = page,
            )

            val snapList = data.content
            val endOfPaginationReached = data.last

            val prevKey = if (page == 0) null else page - 1
            val nextKey = if (endOfPaginationReached) null else page + 1
            Log.d(TAG, "prevKey: $prevKey, nextKey: $nextKey")

            LoadResult.Page(
                data = snapList,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    companion object {
        const val TAG = "StarPagingSource"
        const val PAGING_SIZE = 10
    }
}