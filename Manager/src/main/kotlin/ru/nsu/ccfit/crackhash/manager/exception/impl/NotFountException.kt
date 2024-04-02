package ru.nsu.ccfit.crackhash.manager.exception.impl

import ru.nsu.ccfit.crackhash.manager.exception.BasicException
import ru.nsu.ccfit.crackhash.manager.exception.Code

class NotFountException : BasicException {
    constructor() : super(Code.NOT_FOUND)
    constructor(message: String) : super(Code.NOT_FOUND, message)
}