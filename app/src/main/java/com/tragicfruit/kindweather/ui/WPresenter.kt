package com.tragicfruit.kindweather.ui

interface WPresenter<T> {
    var view: T

    fun resume() = Unit
    fun pause() = Unit
}