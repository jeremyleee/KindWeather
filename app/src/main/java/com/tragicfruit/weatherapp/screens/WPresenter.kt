package com.tragicfruit.weatherapp.screens

interface WPresenter<T> {
    var view: T

    fun start() = Unit
    fun stop() = Unit
}