package ru.goodibunakov.prodvtest.presentation.view.fragments.choose

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_chose_city.*
import ru.goodibunakov.prodvtest.R
import ru.goodibunakov.prodvtest.presentation.view.adapters.CityAdapter
import ru.goodibunakov.prodvtest.utils.HawkHelper
import java.util.*

class ChoseCityFragment : Fragment(R.layout.fragment_chose_city) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (recyclerView != null){
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = LinearLayoutManager(activity)
            recyclerView.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
            val adapter = CityAdapter(if (HawkHelper.checkIfContain(HawkHelper.ITEMS)) HawkHelper.getItem(HawkHelper.ITEMS) else ArrayList())
            recyclerView.adapter = adapter
        }
    }
}