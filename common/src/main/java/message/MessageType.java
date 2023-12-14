package ru.cft.focus.common.message;

public enum MessageType {
    AUTHORIZATION, //клиент - запрос авторизации
    LOGOUT, //клиент - выход из системы
    MESSAGE_REQUEST, //обмен сообщениями между сервером и клиентами и вывод на экран клиента
    MESSAGE_NOTIFICATION, //сервер - отчет о доставке сообщения
    LIST_USERS, //сервер - отправляет актуальный список контактов
    CONNECTION_REFUSED, //сервер - отказ в соединении
    CONNECTION_ACCEPTED, //сервер - подтверждает соединение
    INFO //сервер - уведомляет о присоединении/отключении клиента
}
