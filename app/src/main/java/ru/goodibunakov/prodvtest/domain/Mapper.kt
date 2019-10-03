package ru.goodibunakov.prodvtest.domain

interface Mapper<From, To> {
    fun convert(from: From): To
}