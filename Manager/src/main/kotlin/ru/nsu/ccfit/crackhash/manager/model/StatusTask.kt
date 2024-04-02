package ru.nsu.ccfit.crackhash.manager.model

data class StatusTask(
    var ready: Int = 0,
    var error:Int = 0,
    var timeOut: Int = 0
){
    val sum
        get() = ready+error+timeOut
}