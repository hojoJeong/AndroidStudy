package com.ssafy.template.src.main.search

import com.ssafy.template.src.main.search.model.SearchResult

interface SearchFragmentView {

    fun onSearchSuccess(response: MutableList<SearchResult>)

    fun onSearchFailure(message: String)
}