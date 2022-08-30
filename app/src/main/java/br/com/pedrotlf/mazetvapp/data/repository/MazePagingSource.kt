package br.com.pedrotlf.mazetvapp.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import br.com.pedrotlf.mazetvapp.data.api.MazeTvApi
import br.com.pedrotlf.mazetvapp.data.dto.MazeShowDTO
import retrofit2.HttpException
import java.io.IOException

private const val MAZETV_STARTING_PAGE_INDEX = 1
const val MAZETV_PAGE_SIZE = 250

class MazePagingSource(
    private val mazeTvApi: MazeTvApi,
    private val query: String
): PagingSource<Int, MazeShowDTO>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MazeShowDTO> {
        val position = params.key ?: MAZETV_STARTING_PAGE_INDEX

        return try {
            if(query.length < 2) {
                val response = mazeTvApi.allShows(position)
                LoadResult.Page(
                    data = response,
                    prevKey = if (position == MAZETV_STARTING_PAGE_INDEX) null else position - 1,
                    nextKey = if (response.isEmpty()) null else position + 1
                )
            } else {
                val response = mazeTvApi.searchShowByName(query).mapNotNull { it.show }
                LoadResult.Page(
                    data = response,
                    prevKey = null,
                    nextKey = null
                )
            }
        } catch (ex: IOException) {
            LoadResult.Error(ex)
        } catch (ex: HttpException) {
            LoadResult.Error(ex)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MazeShowDTO>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
