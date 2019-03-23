package com.tragicfruit.kindweather.screens

interface WPresenter<T> {
    var view: T

    fun resume() = Unit
    fun pause() = Unit
}