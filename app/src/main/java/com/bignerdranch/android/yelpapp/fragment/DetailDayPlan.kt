package com.bignerdranch.android.yelpapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bignerdranch.android.yelpapp.R
import com.bignerdranch.android.yelpapp.database.DayPlan
import com.bignerdranch.android.yelpapp.viewmodel.RestauratViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_detail_day_plan.*


class DetailDayPlan : Fragment() {

    private lateinit var myViewModel: RestauratViewModel
    private val args by navArgs<DetailDayPlanArgs>()
    private lateinit var dayPlan: DayPlan
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail_day_plan, container, false)
        myViewModel = ViewModelProvider(this).get(RestauratViewModel::class.java)
        bindData()
        return view
    }

    fun bindData() {
        myViewModel.searchDayPlan(args.id).observe(viewLifecycleOwner,
            Observer {
                if (it != null) {
                    descriptionedittext.setText(it.listDescription)
                    it.listDescription = descriptionedittext.text.toString()
                    dayPlan = DayPlan(
                        it.yelpId,
                        it.name,
                        it.rating,
                        it.phone,
                        it.is_closed,
                        it.numReviews,
                        it.distanceInMeters,
                        it.imageUrl,
                        it.categories,
                        it.coordinates,
                        it.listDescription
                    )
                    nametextview.text = it.name
                    Glide.with(resturantimageView)
                        .load(it.imageUrl).apply(
                            RequestOptions().transforms(
                                CenterCrop(), RoundedCorners(20)
                            )
                        ).into(resturantimageView)

                    savebutton.setOnClickListener {
                        dayPlan.listDescription = descriptionedittext.text.toString()
                        Toast.makeText(context, R.string.saved_msg, Toast.LENGTH_LONG).show()
                        myViewModel.updateDayPlan(dayPlan)
                        val action = DetailDayPlanDirections.actionDetailDayPlanToDayPlanList()
                        findNavController().navigate(action)
                    }
                    Detail.setOnClickListener {
                        val action=DetailDayPlanDirections.actionDetailDayPlanToWeatherFragment(args.id)
                        findNavController().navigate(action)
                    }
                }
            })
    }
}
