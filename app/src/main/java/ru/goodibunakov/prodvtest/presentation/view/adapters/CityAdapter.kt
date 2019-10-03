package ru.goodibunakov.prodvtest.presentation.view.adapters

import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_city.view.*
import ru.goodibunakov.prodvtest.R
import ru.goodibunakov.prodvtest.presentation.model.CityModel
import ru.goodibunakov.prodvtest.utils.HawkHelper

internal class CityAdapter(private val items: List<CityModel>?) : RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.item_city, parent, false)
        return CityViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val item = items!![position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    internal inner class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: CityModel) {
            itemView.city_text.text = item.city
            itemView.switch_item.isChecked = item.isSelected
            itemView.switch_item.setOnClickListener {
                val switchClicked = it as Switch
                if (!switchClicked.isChecked) switchClicked.isChecked = true
                val position = adapterPosition
                for (i in items!!.indices) {
                    val cityModel = items[i]
                    cityModel.isSelected = i == position
                }
                HawkHelper.setItem(HawkHelper.ITEMS, items)
                Handler().post {
                    this@CityAdapter.notifyDataSetChanged()
                }
            }
        }
    }
}