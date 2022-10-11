package com.ssafy.ws_android_component_03_3_hojojeong.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ssafy.ws_android_component_03_3_hojojeong.databinding.FragmentMainBinding
import com.ssafy.ws_android_component_03_3_hojojeong.dto.StoreDto

private const val ARG_STORE= "store"
private const val TAG = "MainFragment"

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private var storeList: ArrayList<StoreDto> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            storeList = it.getSerializable(ARG_STORE) as ArrayList<StoreDto>
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView: ${container?.javaClass}")
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fmMainName.text = storeList[0].title
        binding.fmMainCnt.text = storeList.size.toString()
    }

    companion object {
        @JvmStatic
        fun newInstance(store: ArrayList<StoreDto>) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_STORE, store)
                }
            }
    }
}