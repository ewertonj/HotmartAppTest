package com.ewerton.hotmartapplication.screens.locationdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.ewerton.hotmartapplication.R
import com.ewerton.hotmartapplication.databinding.FragmentLocationDetailsBinding
import com.ewerton.hotmartapplication.models.DaySchedule
import com.ewerton.hotmartapplication.models.LocationsDetailsResult
import com.ewerton.hotmartapplication.platform.LiveDataWrapper
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.android.synthetic.main.fragment_location_details.view.*
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.android.inject


@Suppress("DEPRECATION")
class LocationDetailsFragment : Fragment() {

    private lateinit var viewModel: LocationDetailsViewModel
    private lateinit var viewModelFactory: LocationDetailsViewModelFactory
    private lateinit var binding: FragmentLocationDetailsBinding
    private val mLocationDetailsUseCase: LocationDetailsUseCase by inject()
    private var id: Int? = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_location_details, container, false
        )
        id = arguments?.getInt("id")
        viewModelFactory = LocationDetailsViewModelFactory(
            Dispatchers.Main,
            Dispatchers.IO,
            mLocationDetailsUseCase,
            id
        )
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(LocationDetailsViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.mLocationDetailsResponse.observe(viewLifecycleOwner, Observer { result ->
            when (result?.responseStatus) {

                LiveDataWrapper.RESPONSESTATUS.LOADING -> {
                    binding.progressCircular.visibility = View.VISIBLE
                }
                LiveDataWrapper.RESPONSESTATUS.ERROR -> {
                    binding.layoutError.parentLayout.visibility = View.VISIBLE
                    binding.parentLayout.visibility = View.GONE
                    binding.progressCircular.visibility = View.GONE
                    binding.appBar.setExpanded(false)
                }
                LiveDataWrapper.RESPONSESTATUS.SUCCESS -> {
                    val mainItemReceived = result.response as LocationsDetailsResult
                    updateData(binding, mainItemReceived)
                    binding.progressCircular.visibility = View.GONE
                }
            }
        })

        binding.layoutError.tryAgainButton.setOnClickListener {
            binding.layoutError.parentLayout.visibility = View.GONE
            binding.parentLayout.visibility = View.VISIBLE
            viewModel.getLocationDetailsById(id)
        }

        val toolbarLayout =
            binding.collapsingToolbarLayout as CollapsingToolbarLayout
        toolbarLayout.setContentScrimColor(resources.getColor(R.color.topaz))
        toolbarLayout.setCollapsedTitleTextColor(resources.getColor(R.color.white))

        val mAppBarLayout = binding.appBar
        mAppBarLayout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var isShow = false
            var scrollRange = -1
            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = true
                    toolbarLayout.title = getString(R.string.title_details)
                } else if (isShow) {
                    isShow = false
                    toolbarLayout.title = " "
                }
            }
        })
        return binding.root
    }

    private fun updateData(binding: FragmentLocationDetailsBinding, data: LocationsDetailsResult) {
        binding.appBar.setExpanded(false)
        Glide.with(requireContext()).load("https://picsum.photos/id/${data.id}/500/300")
            .fallback(android.R.drawable.stat_notify_error)
            .timeout(4500)
            .into(binding.appBar.expandedImage)
        binding.appBar.setExpanded(true)

        binding.apply {
            textDetailsTitle.text = data.name
            ratingBarDetails.rating = data.review.toFloat()
            textRatingDetails.text = data.review.toString()
            textAbout.text = data.about
            textPhone.text = data.phone
            textAddress.text = data.address
        }

        val sunday = setScheduleData(data.schedule.sunday, R.string.sunday_format)
        val monday = setScheduleData(data.schedule.monday, R.string.monday_format)
        val tuesday = setScheduleData(data.schedule.tuesday, R.string.tuesday_format)
        val wednesday = setScheduleData(data.schedule.wednesday, R.string.wednesday_format)
        val thursday = setScheduleData(data.schedule.thursday, R.string.thursday_format)
        val friday = setScheduleData(data.schedule.friday, R.string.friday_format)
        val saturday = setScheduleData(data.schedule.saturday, R.string.saturday_format)
        binding.textSchedule.text = "$monday$tuesday$wednesday$thursday$friday$saturday$sunday"

    }

    private fun setScheduleData(day: DaySchedule?, resId: Int): String {
        return if (day != null) {
            resources.getString(
                resId,
                day.open,
                day.close
            )
        } else ""
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        view.findViewById<Toolbar>(R.id.toolbarDetails)
            .setupWithNavController(navController, appBarConfiguration)
    }
}