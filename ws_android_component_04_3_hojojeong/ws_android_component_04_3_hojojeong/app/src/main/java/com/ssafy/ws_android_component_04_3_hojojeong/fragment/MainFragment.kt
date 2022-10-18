package com.ssafy.ws_android_component_04_3_hojojeong.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ssafy.ws_android_component_04_3_hojojeong.databinding.FragmentMainBinding
import com.ssafy.ws_android_component_04_3_hojojeong.dto.StoreDto

private const val ARG_STORE= "store"
private const val TAG = "MainFragment"

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val storeItem = StoreDto("싸피벅스", "010-1234-5678", "36.10830144", "128.41827")
        binding.fmMainName.text = storeItem.title
        binding.fmMainCnt.text = "1"
    }

}