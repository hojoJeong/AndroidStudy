package com.ssafy.template.src.main.overview

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ssafy.template.R
import com.ssafy.template.config.BaseFragment
import com.ssafy.template.databinding.FragmentOverviewBinding
import com.ssafy.template.src.main.network.MarsApiFilter

/**
 * This fragment shows the the status of the Mars real-estate web services transaction.
 */
class OverviewFragment : BaseFragment<FragmentOverviewBinding>(FragmentOverviewBinding::bind, R.layout.fragment_overview) {

    /**
     * Lazily initialize our [OverviewViewModel].
     */
    private val viewModel: OverviewViewModel by lazy {
        ViewModelProvider(this).get(OverviewViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel

//        binding.photosGrid.adapter = PhotoGridAdapter()
        binding.photosGrid.adapter = PhotoGridAdapter(PhotoGridAdapter.OnClickListener {
            viewModel.displayPropertyDetails(it)
        })
        setHasOptionsMenu(true)

        viewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, Observer {
            if ( null != it ) {
                this.findNavController().navigate(
                    OverviewFragmentDirections.actionShowDetail(it)
                )
                viewModel.displayPropertyDetailsComplete()
            }
        })

    }


    /**
     * Inflates the overflow menu that contains filtering options.
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        viewModel.updateFilter(
            when (item.itemId) {
                R.id.show_rent_menu -> MarsApiFilter.SHOW_RENT
                R.id.show_buy_menu -> MarsApiFilter.SHOW_BUY
                else -> MarsApiFilter.SHOW_ALL
            }
        )
        return true
    }
}
