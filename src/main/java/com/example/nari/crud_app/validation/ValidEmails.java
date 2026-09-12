package com.example.nari.crud_app.validation;
// Кастомный валидатор, в частности для списка почт

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD}) // указываем куда можно крепить аннотацию: только на поля
@Retention(RetentionPolicy.RUNTIME) // аннотация работает пока запущена программа
@Constraint(validatedBy = EmailsValidator.class) // инспектор, который будет проверять поля
public @interface ValidEmails {
    // Текст ошибки по умолчанию
    String message() default "В списке есть ошибочный email";
    // Это стандартные элементы, которые требуются в любой кастомной аннотации для валидации (Hibernate Validator / Bean Validation API).
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
