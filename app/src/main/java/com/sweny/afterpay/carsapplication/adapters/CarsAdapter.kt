package com.sweny.afterpay.carsapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sweny.afterpay.carsapplication.R
import com.sweny.afterpay.carsapplication.api.ApiConstants
import com.sweny.afterpay.carsapplication.databinding.ItemCarBinding
import com.sweny.afterpay.carsapplication.model.CarData

class CarsAdapter(
    private val Cars: List<CarData>
) :
    RecyclerView.Adapter<CarsAdapter.CarViewHolder>() {

    inner class CarViewHolder(
        private val itemBinding: ItemCarBinding
    ) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(item: CarData) {

            with(itemBinding) {
                manufacturer.text = item.manufacturer
                model.text = item.model
                year.text = item.year.toString()
                price.text = "$" + item.price

                carImage.load(ApiConstants.BASE_URL + item.image) {

                    placeholder(R.drawable.outline_directions_car_24)
                    crossfade(750)
                    scale(coil.size.Scale.FILL)
                    size(80,80)
                    error(R.drawable.outline_directions_car_24)

                    build()
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CarViewHolder(
            ItemCarBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) =
        holder.bind(Cars[position])

    override fun getItemCount() = Cars.size

}