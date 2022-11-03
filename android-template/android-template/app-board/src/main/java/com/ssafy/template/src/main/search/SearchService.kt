package com.ssafy.template.src.main.search

import com.ssafy.template.config.ApplicationClass
import com.ssafy.template.src.main.search.model.SearchResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchService(val view: SearchFragmentView) {

    fun searchBoard(keyword : String) {
        val searchRetrofitInterface = ApplicationClass.sRetrofit.create(SearchRetrofitInterface::class.java)
        searchRetrofitInterface.searchBoard(keyword).enqueue(object : Callback<MutableList<SearchResult>> {
            override fun onResponse(call: Call<MutableList<SearchResult>>, response: Response<MutableList<SearchResult>>) {
                view.onSearchSuccess(response.body() as MutableList<SearchResult>)
            }

            override fun onFailure(call: Call<MutableList<SearchResult>>, t: Throwable) {
                view.onSearchFailure(t.message ?: "통신 오류")
            }
        })
    }
}