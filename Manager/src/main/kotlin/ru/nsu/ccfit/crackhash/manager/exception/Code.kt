package ru.nsu.ccfit.crackhash.manager.exception

import org.springframework.http.HttpStatus

enum class Code(
    val httpStatus: HttpStatus,
    val errorMessage: String
) {
    OK(HttpStatus.OK, "УСПЕШНЫЙ ЗАПРОС"), BAD_REQUEST(HttpStatus.BAD_REQUEST, "НЕВЕРНЫЙ ЗАПРОС"), UNAUTHORIZED(
        HttpStatus.UNAUTHORIZED, "ПОЛЬЗОВАТЕЛЬ НЕ АВТОРИЗОВАН"
    ),
    FORBIDDEN(HttpStatus.FORBIDDEN, "ЗАПРОС ЗАПРЕЩЕН"), NOT_FOUND(
        HttpStatus.NOT_FOUND, "ОТСУТСТВУЮТ ПРАВА ДОСТУПА"
    ),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "ВНУТРЕНЯЯ ОШИБКА СЕРВЕРА"), SERVICE_UNAVAILABLE(
        HttpStatus.SERVICE_UNAVAILABLE, "СЕРВИС НЕДОСТУПЕН"
    ),
    GATEWAY_TIMEOUT(HttpStatus.GATEWAY_TIMEOUT, "ВРЕМЯ ОЖИДАНИЯ ИСТЕКЛО");
}