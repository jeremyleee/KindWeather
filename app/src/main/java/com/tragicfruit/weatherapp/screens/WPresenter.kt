package com.tragicfruit.weatherapp.screens

interface WPresenter<T> {
    var view: T

    fun resume() = Unit
    fun pause() = Unit
}