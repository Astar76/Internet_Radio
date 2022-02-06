package com.astar.myapplication.servicelocator


interface DependencyContainer {

    fun module(feature: Feature) : BaseModule<*>

    class Base(private val coreModule: CoreModule) : DependencyContainer {
        override fun module(feature: Feature): BaseModule<*> {
            return when(feature) {
                Feature.RADIO_LIST -> RadioListModule(coreModule)
                Feature.RADIO_CONTROL -> RadioControlModule(coreModule)
            }
        }
    }
}