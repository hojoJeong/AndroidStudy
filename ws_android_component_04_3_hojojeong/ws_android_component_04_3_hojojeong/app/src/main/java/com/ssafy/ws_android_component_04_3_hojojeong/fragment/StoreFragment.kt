package com.ssafy.ws_android_component_04_3_hojojeong.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ssafy.ws_android_component_04_3_hojojeong.databinding.FragmentStoreBinding
import com.ssafy.ws_android_component_04_3_hojojeong.dto.StoreDto
import com.ssafy.ws_android_component_04_3_hojojeong.dto.StuffDto
import com.ssafy.ws_android_component_04_3_hojojeong.stuff.StuffActivity

private const val ARG_STORE = "storeList"
private const val ARG_STUFF = "stuffList"
private const val TAG = "StoreFragment 싸피"
class StoreFragment : Fragment() {
    private lateinit var binding: FragmentStoreBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: check")
        
        val storeItem = StoreDto("싸피벅스", "010-1234-5678", "36.10830144", "128.41827")

        Log.d(TAG, "onViewCreated: $storeItem")
        binding.txtMainTitle.text = storeItem.title
        binding.txtMainNumber.text = storeItem.number
        binding.txtMainLat.text = storeItem.lat
        binding.txtMainLong.text = storeItem.long

        binding.txtMainTitle.setOnClickListener {
            val intent = Intent(requireContext(), StuffActivity::class.java)
            startActivity(intent)
        }
    }

}