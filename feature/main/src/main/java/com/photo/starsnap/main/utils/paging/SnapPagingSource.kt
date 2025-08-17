package com.photo.starsnap.main.utils.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.photo.starsnap.network.snap.SnapRepository
import com.photo.starsnap.network.snap.dto.SnapResponseDto
import kotlinx.coroutines.runBlocking

class SnapPagingSource(
    private val snapRepository: SnapRepository,
    private val tag: List<String>,
    private val title: String,
    private val userId: String,
    private val starId: List<String>,
    private val starGroupId: List<String>
) : PagingSource<Int, SnapResponseDto>() {

    override fun getRefreshKey(state: PagingState<Int, SnapResponseDto>): Int? {
        return state.anchorPosition?.let { achorPosition ->
            state.closestPageToPosition(achorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(achorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SnapResponseDto> {
        return try {
            val page = params.key ?: 1
            val data =
                runBlocking {
                    snapRepository.getSnap(
                        size = PAGING_SIZE,
                        page = page - 1,
                        tag = tag,
                        title = title,
                        userId = userId,
                        starId = starId,
                        starGroupId = starGroupId
                    )
                }
            val snap = data.content
            val endOfPaginationReached = data.last

            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (endOfPaginationReached) null else page + 1
            Log.d(TAG, "prevKey: $prevKey, nextKey: $nextKey")

            LoadResult.Page(snap, prevKey, nextKey)
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    companion object {
        const val TAG = "SnapPagingSource"
        const val PAGING_SIZE = 50
    }
}