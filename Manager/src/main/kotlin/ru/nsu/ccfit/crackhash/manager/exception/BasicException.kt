package ru.nsu.ccfit.crackhash.manager.exception

abstract class BasicException protected constructor(val errorCode: Code, message: String) :
    RuntimeException(message) {

    protected constructor(errorCode: Code) : this(errorCode, errorCode.errorMessage)
}