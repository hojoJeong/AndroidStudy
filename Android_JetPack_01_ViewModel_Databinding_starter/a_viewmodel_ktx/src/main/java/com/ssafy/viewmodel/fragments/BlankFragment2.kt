package com.ssafy.viewmodel.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.ssafy.viewmodel.databinding.FragmentBlank2Binding

class BlankFragment2 : Fragment() {
    private val activityViewModel by activityViewModels<NewActivityViewModel>()

//    var listener : CommunicationCallback? = null

    private lateinit var binding: FragmentBlank2Binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBlank2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        printCount()

        binding.buttonPlus.setOnClickListener {
            activityViewModel.increaseCount()
            printCount()
        }
        binding.buttonMinus.setOnClickListener {
            activityViewModel.decreaseCount()
            printCount()
        }
    }

    private fun printCount(){
        binding.textResult.text = activityViewModel.count.toString()
    }
}
