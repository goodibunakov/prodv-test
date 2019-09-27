package ru.goodibunakov.prodvtest.view.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_chose_city.*
import ru.goodibunakov.prodvtest.R
import ru.goodibunakov.prodvtest.adapters.CityAdapter
import ru.goodibunakov.prodvtest.utils.HawkHelper
import java.util.*


class ChoseCityFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chose_city, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.layoutManager = LinearLayoutManager(activity)
        recyclerView!!.addItemDecoration(DividerItemDecoration(Objects.requireNonNull(activity), DividerItemDecoration.VERTICAL))
        val adapter = CityAdapter(if (HawkHelper.getInstance().checkIfContain(HawkHelper.ITEMS)) HawkHelper.getInstance().getItem(HawkHelper.ITEMS) else ArrayList())
        recyclerView!!.adapter = adapter
    }
}