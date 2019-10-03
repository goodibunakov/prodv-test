package ru.goodibunakov.prodvtest.presentation.view.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import moxy.MvpAppCompatActivity
import ru.goodibunakov.prodvtest.R
import ru.goodibunakov.prodvtest.presentation.model.CityModel
import ru.goodibunakov.prodvtest.presentation.view.fragments.ChoseCityFragment
import ru.goodibunakov.prodvtest.presentation.view.fragments.MainFragment
import ru.goodibunakov.prodvtest.utils.HawkHelper
import java.util.*

class MainActivity : MvpAppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initHawk()

        setSupportActionBar(toolbar)
        supportFragmentManager.beginTransaction()
                .replace(R.id.mainFragmentContainer, MainFragment(), "main")
                .commit()

        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                toolbar!!.setTitle(R.string.choose_city)
                toolbar!!.setNavigationOnClickListener {
                    (supportFragmentManager.findFragmentByTag("main") as MainFragment).updateScreen()
                    onBackPressed()
                }
            } else {
                toolbar!!.setTitle(R.string.app_name)
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
            }
        }
    }

    private fun initHawk() {
        val hawkHelper = HawkHelper
        hawkHelper.init(applicationContext)
        if (!hawkHelper.checkIfContain(HawkHelper.ITEMS)) {
            val items = ArrayList<CityModel>()
            items.add(CityModel("Новосибирск", true))
            items.add(CityModel("Москва", false))
            items.add(CityModel("Томск", false))
            items.add(CityModel("Прага", false))
            items.add(CityModel("Париж", false))
            items.add(CityModel("Кемерово", false))
            hawkHelper.setItem(HawkHelper.ITEMS, items)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.settings) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.mainFragmentContainer, ChoseCityFragment(), null)
                    .addToBackStack(null)
                    .commit()
        }
        return true
    }
}