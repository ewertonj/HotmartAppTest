package com.ewerton.hotmartapplication.screens.locations

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ewerton.hotmartapplication.R
import com.ewerton.hotmartapplication.databinding.FragmentLocationsBinding
import com.ewerton.hotmartapplication.models.AllLocations
import com.ewerton.hotmartapplication.models.AllLocationsResult
import com.ewerton.hotmartapplication.platform.LiveDataWrapper
import com.ewerton.hotmartapplication.platform.navigateWithDefaultAnims
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.android.inject

class LocationsFragment : Fragment(), LocationsRecyclerViewAdapter.OnItemClickListener {

    private lateinit var viewModel: LocationsViewModel
    private lateinit var viewModelFactory: LocationsViewModelFactory
    private lateinit var binding: FragmentLocationsBinding
    private val mLocationsUseCase: LocationsUseCase by inject()
    lateinit var mRecyclerViewAdapter: LocationsRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_locations, container, false
        )
        viewModelFactory =
            LocationsViewModelFactory(Dispatchers.Main, Dispatchers.IO, mLocationsUseCase)
        viewModel = ViewModelProvider(this, viewModelFactory).get(LocationsViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        mRecyclerViewAdapter = LocationsRecyclerViewAdapter(requireContext(), arrayListOf(), this)

        binding.listRecyclerView.adapter = mRecyclerViewAdapter
        binding.listRecyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        viewModel.mLocationsResponse.observe(viewLifecycleOwner, Observer { result ->
            when (result?.responseStatus) {

                LiveDataWrapper.RESPONSESTATUS.LOADING -> {
                    binding.progressCircular.visibility = View.VISIBLE
                }
                LiveDataWrapper.RESPONSESTATUS.ERROR -> {
                    binding.layoutError.parentLayout.visibility = View.VISIBLE
                    binding.frameLayout.visibility = View.GONE
                    binding.progressCircular.visibility = View.GONE
                }
                LiveDataWrapper.RESPONSESTATUS.SUCCESS -> {
                    val mainItemReceived = result.response as AllLocations
                    val listItems = mainItemReceived.listLocations as ArrayList<AllLocationsResult>
                    updateData(listItems)
                    binding.progressCircular.visibility = View.GONE
                }
            }
        })

        binding.layoutError.tryAgainButton.setOnClickListener {
            binding.layoutError.parentLayout.visibility = View.GONE
            binding.frameLayout.visibility = View.VISIBLE
            viewModel.getLocations()
        }

        return binding.root
    }

    private fun updateData(listItems: ArrayList<AllLocationsResult>) {
        val update = Handler(Looper.getMainLooper())
        update.post {
            mRecyclerViewAdapter.updateListItems(listItems)
        }
    }

    override fun onItemClicked(model: AllLocationsResult) {
        val bundle = bundleOf("id" to model.id)
        this.findNavController().navigateWithDefaultAnims(R.id.locationDetailsFragment, bundle)
    }
}