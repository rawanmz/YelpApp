package com.bignerdranch.android.yelpapp.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.yelpapp.R
import com.bignerdranch.android.yelpapp.ServiceLocator
import com.bignerdranch.android.yelpapp.SwipeDelete
import com.bignerdranch.android.yelpapp.database.DayPlan
import com.bignerdranch.android.yelpapp.databinding.FragmentDayPlanListBinding
import com.bignerdranch.android.yelpapp.databinding.ListItemDayPlanBinding
import com.bignerdranch.android.yelpapp.viewmodel.MyViewModelFactory
import com.bignerdranch.android.yelpapp.viewmodel.RestauratViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.list_item_day_plan.view.*
import java.util.*


class DayPlanList : Fragment() {

    private lateinit var viewModell: RestauratViewModel
    private lateinit var restaurantViewModelFactory: MyViewModelFactory
    var list: MutableList<DayPlan>? = null
    var singlePlan = mutableListOf<DayPlan>()
    private var places = emptyList<DayPlan>()
    var adapter = RestaurantAdapter(places)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentDayPlanListBinding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_day_plan_list, container, false)
        restaurantViewModelFactory =
            MyViewModelFactory(ServiceLocator.yelpResponse, ServiceLocator.weatherResponse)
        viewModell =
            ViewModelProvider(this, restaurantViewModelFactory).get(RestauratViewModel::class.java)
        viewModell.planDays.observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
        })
        val item = object : SwipeDelete(requireContext(), 0, ItemTouchHelper.LEFT) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                super.onSwiped(viewHolder, direction)
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle(R.string.delete_place)
                builder.setMessage(R.string.delete_confirmation)
                builder.setPositiveButton(R.string.yes) { dialogInterface: DialogInterface, i: Int ->
                    adapter.delete(viewHolder.adapterPosition)
                    adapter?.notifyDataSetChanged()
                }
                builder.setNegativeButton(R.string.no,{ dialogInterface: DialogInterface, i: Int -> })
                adapter?.notifyDataSetChanged()
                builder.setIcon(R.drawable.ic_baseline_delete_24)
                builder.show()

            }

            }

        SwipeFunction(binding, item)
        return binding.root
    }

    private fun SwipeFunction(binding: FragmentDayPlanListBinding, item: SwipeDelete) {
        binding.dayplanRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@DayPlanList.adapter

            val itemtouchhelper = ItemTouchHelper(item)
            itemtouchhelper.attachToRecyclerView(binding.dayplanRecyclerView)

            val touchhelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.DOWN, 0
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    val sourcePosition = viewHolder.adapterPosition
                    val targetPosition = target.adapterPosition
                    Collections.swap(viewModell.planDays.value, sourcePosition, targetPosition)

                    adapter?.notifyItemMoved(sourcePosition, targetPosition)

                    return true
                }
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    adapter?.notifyDataSetChanged()

                }
            })
            binding.dayplanRecyclerView.adapter = adapter
            touchhelper.attachToRecyclerView(binding.dayplanRecyclerView)
        }
    }
    inner class DayPlanHolder(private val binding: ListItemDayPlanBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(restaurant: DayPlan) {
            binding.FavName.text = restaurant.name
            binding.FavRate.rating = restaurant.rating.toFloat()
            binding.FavCategory.text = restaurant.categories[0].title
            binding.FavDistance.text = restaurant.displayDistance()
            Glide.with(binding.FavImageView)
                .load(restaurant.imageUrl).apply(
                    RequestOptions().transforms(
                        CenterCrop(), RoundedCorners(20)
                    )
                ).into(binding.FavImageView)
        }
    }
    inner class RestaurantAdapter(private var restaurant: List<DayPlan>) :
        RecyclerView.Adapter<DayPlanHolder>() {
        fun delete(position: Int) {
            viewModell.deleteDayPlan(restaurant[position])
            singlePlan = restaurant.toMutableList()
            singlePlan.removeAt(position)
            Toast.makeText(requireContext(), R.string.delete_msg, Toast.LENGTH_LONG).show()
            notifyDataSetChanged()
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayPlanHolder {
            val binding: ListItemDayPlanBinding = DataBindingUtil
                .inflate(layoutInflater, R.layout.list_item_day_plan, parent, false)
            return DayPlanHolder(binding)
        }
        override fun getItemCount(): Int = restaurant.size
        override fun onBindViewHolder(holder: DayPlanHolder, position: Int) {
            val restaurants = restaurant[position]
            holder.bind(restaurants)
            holder.itemView.day_plan_list_item.setOnClickListener {
                val action =
                    DayPlanListDirections.actionDayPlanListToDetailDayPlan(restaurants.yelpId)
                findNavController().navigate(action)
            }
        }
        fun setData(restaurant: List<DayPlan>) {
            this.restaurant = restaurant
            notifyDataSetChanged()
        }
    }
}


