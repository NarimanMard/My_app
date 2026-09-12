package com.example.nari.crud_app.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.regex.Pattern;

public class EmailsValidator implements ConstraintValidator<ValidEmails, List<String>> {

    private static final String EMAIL_REGEXP = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*\\.[A-Za-z]{2,}$";
    private static final Pattern PATTERN = Pattern.compile(EMAIL_REGEXP);

    @Override
    public boolean isValid(List<String> emails, ConstraintValidatorContext context) {
        // За пустоту отвечает другая аннотация.
        if (emails == null) return true;
        // Стандартный паттерн: Ранний возврат (early return). Вместо stream: проверка всех элементов на наличие ошибки
        for(String email: emails){
            // Если элемент списка будет null или не пройдет проверку по шаблону.
            if((email == null) || !PATTERN.matcher(email).matches()){
                return false;
            }
        }
        // Если все успешно
        return true;
    }
}
