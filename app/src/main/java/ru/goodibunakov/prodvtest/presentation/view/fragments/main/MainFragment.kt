package ru.goodibunakov.prodvtest.presentation.view.fragments.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.bottom_sheet.*
import kotlinx.android.synthetic.main.fragment_main.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.goodibunakov.prodvtest.R
import ru.goodibunakov.prodvtest.data.MapperToString
import ru.goodibunakov.prodvtest.presentation.MyApp
import ru.goodibunakov.prodvtest.presentation.presenter.MainFragmentPresenter
import ru.goodibunakov.prodvtest.utils.Constants
import ru.goodibunakov.prodvtest.utils.ImageUtils
import ru.goodibunakov.prodvtest.utils.TranslateUtils
import ru.goodibunakov.prodvtest.web.dto.WeatherModel
import java.util.*

class MainFragment : MvpAppCompatFragment(R.layout.fragment_main), MainFragmentView {

    private lateinit var city: String
    private var imageId: Int = 0
    private var forecastTexts: Array<String> = Array(5) { "" }

    @InjectPresenter
    lateinit var mainPresenter: MainFragmentPresenter

    @ProvidePresenter
    fun provideMainFragmentPresenter(): MainFragmentPresenter {
        return MainFragmentPresenter(AndroidSchedulers.mainThread(), Schedulers.io(), MyApp.repository, MapperToString(activity!!.applicationContext))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            updateScreen()
        } else {
            setRestoredData(savedInstanceState)
        }
    }

    private fun setRestoredData(savedInstanceState: Bundle) {
        cityText!!.text = savedInstanceState.getString(Constants.KEY_CITY)
        tempText!!.text = savedInstanceState.getString(Constants.KEY_TEMP)
        if (savedInstanceState.getInt(Constants.KEY_IMAGE_ID) != 0) {
            imageView!!.setImageResource(savedInstanceState.getInt(Constants.KEY_IMAGE_ID))
        }

        forecastTexts = savedInstanceState.getStringArray(Constants.KEY_FORECAST_ARRAY)!!
        if (forecastTexts.size == 5) {
            sheetText1!!.text = forecastTexts[0]
            sheetText2!!.text = forecastTexts[1]
            sheetText3!!.text = forecastTexts[2]
            sheetText4!!.text = forecastTexts[3]
            sheetText5!!.text = forecastTexts[4]
        }
    }

    fun updateScreen() {
        mainPresenter.updateScreen()
    }

    override fun setCityName(cityName: String) {
        city = cityName
    }

    override fun showProgressBar() {
        progressBar!!.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progressBar!!.visibility = View.INVISIBLE
    }

    override fun showErrorToast() {
        Toast.makeText(activity, getString(R.string.download_error), Toast.LENGTH_SHORT).show()
    }

    override fun setData(data: WeatherModel) {
        Log.d("debug", "WeatherModel = $data")
        cityText!!.text = TranslateUtils.fromEngToRu(data.name!!)
        tempText!!.text = getString(R.string.gradus, String.format(Locale.getDefault(), "%.1f", data.main!!.temp))
        imageId = ImageUtils.getImageDrawable(data.weather!![0].description!!)
        imageView!!.setImageResource(imageId)
    }

    override fun fillSheet(forecast: Array<String>) {
        sheetText1!!.text = forecast[0]
        sheetText2!!.text = forecast[1]
        sheetText3!!.text = forecast[2]
        sheetText4!!.text = forecast[3]
        sheetText5!!.text = forecast[4]
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(Constants.KEY_CITY, cityText!!.text.toString())
        outState.putString(Constants.KEY_TEMP, tempText!!.text.toString())
        outState.putInt(Constants.KEY_IMAGE_ID, imageId)
        outState.putStringArray(Constants.KEY_FORECAST_ARRAY, forecastTexts)
    }
}