package com.ssafy.template.src.main.search

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.template.R
import com.ssafy.template.config.BaseFragment
import com.ssafy.template.databinding.FragmentSearchBinding
import com.ssafy.template.databinding.ListItemBinding
import com.ssafy.template.src.main.search.model.SearchResult


private const val TAG = "SearchFragment_싸피"
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::bind, R.layout.fragment_search), SearchFragmentView {
    //adapter구성
    var mAdapter = MyAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //조회
        binding.searchFragRecyclerView.apply{
            this.adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }

        // IME 키패드에서 검색 버튼 클릭 이벤트
        binding.searchFragEt.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> { // 검색버튼 클릭
                    val keyword = binding.searchFragEt.text.toString()
                    showLoadingDialog(context!!)
                    SearchService(this).searchBoard( keyword )

                    //keyboard  내리기
                    val imm = requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?
                    imm?.hideSoftInputFromWindow(binding.searchFragEt.getWindowToken(), 0)

                    true
                }

                else -> false //기타키 동작 없음.
            }
        }
    }

    override fun onSearchSuccess(response: MutableList<SearchResult>) {
        dismissLoadingDialog()
        Log.d(TAG, "onSearchSuccess: $response")
        mAdapter.list = response
        mAdapter.notifyDataSetChanged()
    }

    override fun onSearchFailure(message: String) {
        dismissLoadingDialog()
        Toast.makeText(requireContext(), "onSearchFailure : ${message}", Toast.LENGTH_SHORT).show()
    }


    class MyAdapter: RecyclerView.Adapter<MyAdapter.CustomViewHolder>(){
        var list = mutableListOf<SearchResult>()

//        class CustomViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
        class CustomViewHolder (val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root) {

            fun bindInfo(data: SearchResult){
                binding.apply{
                    searchResult = data
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
//            val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
            Log.d(TAG, "onCreateViewHolder: ")
            val binding:ListItemBinding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

            return CustomViewHolder(binding).apply{
                binding.root.setOnClickListener{
                    Log.d(TAG, "onCreateViewHolder: adapterPosition:${adapterPosition}, layoutPosition: ${layoutPosition}")
                    Toast.makeText(parent.context, "onCreateViewHolder: adapterPosition:${adapterPosition}, layoutPosition: ${layoutPosition}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
            Log.d(TAG, "onBindViewHolder: ${list.get(position)}")
            holder.bindInfo(list.get(position))
        }

        override fun getItemCount(): Int {
            return list.size
        }
    }
}

