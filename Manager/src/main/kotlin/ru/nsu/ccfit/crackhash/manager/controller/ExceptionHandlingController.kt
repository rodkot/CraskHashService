package ru.nsu.ccfit.crackhash.manager.controller

import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import ru.nsu.ccfit.crackhash.manager.exception.BasicException
import ru.nsu.ccfit.crackhash.manager.exception.Code
import ru.nsu.ccfit.crackhash.manager.model.dto.ErrorDto
import ru.nsu.ccfit.crackhash.manager.model.dto.StatusDto
import ru.nsu.ccfit.crackhash.manager.model.dto.StatusResponseDto

@ResponseBody
@ControllerAdvice
class ExceptionHandlingController {
    @ExceptionHandler(Exception::class)
    fun handleError(exception: Exception): ResponseEntity<ErrorDto> {
        return getResponseWithError(Code.INTERNAL_SERVER_ERROR, exception)
    }

    @ExceptionHandler(BasicException::class)
    fun handleBasicException(exception: BasicException): ResponseEntity<ErrorDto> {
        return getResponseWithError(exception.errorCode, exception)
    }

    private fun getResponseWithError(code: Code, exception: Exception): ResponseEntity<ErrorDto> {
        return ResponseEntity.status(code.httpStatus)
            .body(ErrorDto(code.name, exception.message))
    }
}