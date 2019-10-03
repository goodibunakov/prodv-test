package ru.goodibunakov.prodvtest.presentation

import android.app.Application
import ru.goodibunakov.prodvtest.api.ApiService
import ru.goodibunakov.prodvtest.data.Repository

class MyApp : Application() {

    companion object {
        private val apiService = ApiService.create()
        val repository = Repository(apiService)
    }
}