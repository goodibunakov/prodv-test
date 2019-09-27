package ru.goodibunakov.prodvtest.adapters

import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.goodibunakov.prodvtest.R
import ru.goodibunakov.prodvtest.model.CityModel
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

        private val cityText  = itemView.findViewById<TextView>(R.id.city_text)
        private val switchItem  = itemView.findViewById<Switch>(R.id.switch_item)

        fun bind(item: CityModel) {
            cityText.text = item.city
            switchItem.isChecked = item.isSelected
            switchItem.setOnClickListener {
                val switchClicked = it as Switch
                if (!switchClicked.isChecked) switchClicked.isChecked = true
                val position = adapterPosition
                for (i in items!!.indices) {
                    val cityModel = items[i]
                    cityModel.isSelected = i == position
                }
                HawkHelper.getInstance().setItem(HawkHelper.ITEMS, items)
                Handler().post { this@CityAdapter.notifyDataSetChanged() }
            }
        }
    }
}