package com.ssafy.ws_android_component_03_3_hojojeong.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ssafy.ws_android_component_03_3_hojojeong.databinding.FragmentStoreBinding
import com.ssafy.ws_android_component_03_3_hojojeong.dto.StoreDto
import com.ssafy.ws_android_component_03_3_hojojeong.dto.StuffDto
import com.ssafy.ws_android_component_03_3_hojojeong.stuff.StuffActivity

private const val ARG_STORE = "storeList"
private const val ARG_STUFF = "stuffList"

class StoreFragment : Fragment() {
    private lateinit var binding: FragmentStoreBinding
    private var storeList:ArrayList<StoreDto> = arrayListOf()
//    private var stuffList: ArrayList<StuffDto> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            storeList = it.getSerializable(ARG_STORE) as ArrayList<StoreDto>
//            stuffList = it.getSerializable(ARG_STUFF) as ArrayList<StuffDto>
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtMainTitle.text = storeList[0].title
        binding.txtMainNumber.text = storeList[0].number
        binding.txtMainLat.text = storeList[0].lat
        binding.txtMainLong.text = storeList[0].long

        binding.txtMainTitle.setOnClickListener {
            val intent = Intent(requireContext(), StuffActivity::class.java)
//            intent.putExtra("stuff", stuffList)
            startActivity(intent)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(stuff: ArrayList<StuffDto>, store: ArrayList<StoreDto>) =
            StoreFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_STORE, store)
                    putSerializable(ARG_STUFF, stuff)
                }
            }
    }
}