package com.example.nari.crud_app.exeption;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

// Ловим все ошибки которые прилетают со всех контроллеров
@ControllerAdvice
public class GlobalExeptionHandler {
    // Метод сработает если где-то вылетит ошибка RuntimeException
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handlerRuntimeException(RuntimeException ex){
        // возвращаем наш текст ошибки (ex.getMessage())
        // и HTTP КОД 404 (NOT FOUND)
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handlerNotValidException(MethodArgumentNotValidException ex){
        // Нужно вернуть список ошибок, ex.getMessage()-а нету, MANVE возвращает объект со списком ошибок
        // (BindingResult- это контейнер, который содержит ошибки клиента)
        Map<String, String> errors = new HashMap<>();
        for(var error: ex.getBindingResult().getFieldErrors()){
            String fieldName  = error.getField(); // Получили поле ошибки
            String errorMessage = error.getDefaultMessage(); // Сообщение об ошибке
//            Object errorRejectedValue = error.getRejectedValue(); // То что прислал клиент, на случай если надо проверить что-то в ручную(Логи)
            // Добавили в словарь со всеми ошибками
            errors.put(fieldName, errorMessage);
        }
        return  new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
