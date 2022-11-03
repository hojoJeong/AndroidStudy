package com.ssafy.template.src.main.search.model

data class SearchResult(
    var content: String,
    val no: Int,
    val regtime: String,
    var title: String,
    var writer: String
) {
    constructor() : this("",0,"","","")
}