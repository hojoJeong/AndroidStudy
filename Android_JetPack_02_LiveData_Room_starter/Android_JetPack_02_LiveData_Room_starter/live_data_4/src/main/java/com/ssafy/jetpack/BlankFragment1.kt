package com.ssafy.jetpack

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.ssafy.jetpack.databinding.FragmentBlank1Binding

class BlankFragment1 : Fragment() {

    private  val activityWithFragmentViewModel: NewActivityViewModel by activityViewModels()

    private lateinit var binding: FragmentBlank1Binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBlank1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeCount()
//        printCount()

        binding.buttonPlus.setOnClickListener {
            activityWithFragmentViewModel.increaseCount()
//            printCount()
        }
        binding.buttonMinus.setOnClickListener {
            activityWithFragmentViewModel.decreaseCount()
//            printCount()
        }
    }
    private fun observeCount(){
              activityWithFragmentViewModel.count.observe(viewLifecycleOwner){
                  binding.textResult.text = it.toString()
              }

            activityWithFragmentViewModel.timesText.observe(viewLifecycleOwner){
                binding.times1.text = it.toString()
            }
    }
//    fun printCount(){
//        binding.textResult.text = activityWithFragmentViewModel.count.toString()
//    }
}