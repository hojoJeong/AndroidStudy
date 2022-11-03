/*
 *  Copyright 2019, The Android Open Source Project
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.ssafy.template.src.main.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ssafy.template.R
import com.ssafy.template.config.BaseFragment
import com.ssafy.template.databinding.FragmentDetailBinding

/**
 * This [Fragment] will show the detailed information about a selected piece of Mars real estate.
 */
class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::bind, R.layout.fragment_detail) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        @Suppress("UNUSED_VARIABLE")
        val application = requireNotNull(activity).application
        binding.lifecycleOwner = this

        val marsProperty = DetailFragmentArgs.fromBundle(requireArguments()).selectedProperty

        val viewModelFactory = DetailViewModelFactory(marsProperty, application)
        binding.viewModel = ViewModelProvider(
            this, viewModelFactory).get(DetailViewModel::class.java)

    }
}