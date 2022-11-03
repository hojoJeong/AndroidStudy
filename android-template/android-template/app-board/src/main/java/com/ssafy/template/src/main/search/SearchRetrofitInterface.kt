package com.ssafy.template.src.main.search

import com.ssafy.template.src.main.search.model.SearchResult
import retrofit2.Call
import retrofit2.http.*

interface SearchRetrofitInterface {
    @GET("api/board/search")
    fun searchBoard( @Query("keyword") keyword : String ): Call<MutableList<SearchResult>>


    /*******************/
    @GET("api/board")
    fun selectAll(): Call<MutableList<SearchResult>>

    @GET("api/board/{no}")
    fun selectBoard(@Path("no") no:String): Call<SearchResult>

    @DELETE("api/board/{no}")
    fun deleteBoard(@Path("no") no:String): Call<Unit>

    @PUT("api/board/{no}")
    fun updateBoard(@Path("no") no:String, @Body board: SearchResult): Call<Unit>

    @POST("api/board")
    fun insertBoard(@Body board: SearchResult): Call<Unit>

}
