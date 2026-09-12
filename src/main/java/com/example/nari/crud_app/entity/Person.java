package com.example.nari.crud_app.entity;


import com.example.nari.crud_app.validation.ValidEmails;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data // getter/setter from Lombok
@Entity // create table in Postgres
public class Person {

    @Id //primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // generated primary kay
    private Long id;
    //Проверка на пустоту + имя не состоит из одних пробелов (2; 50)
    @NotBlank(message = "Имя не может быть пустым") // Не может быть null или пустой строкой
    @Size(min = 2, max = 50, message = "Имя может содержать от 2 до 50 символов")
    @Column(name = "name", nullable = false)
    private String name;

    @Size(max = 50, message = "Фамилия не должна быть > 50 символов")
    @Column(name = "second_name", nullable = true)
    private String secondName;

    @NotBlank(message = "Введите телефон")
    @Size(min = 12, max = 12, message = "Телефон должен содержать 11 символов и знак +")
    @Pattern(regexp = "^\\+\\d{11}$", message = "Формат номера: +79933706247")
    @Column(name = "phone", nullable = false, unique = true)
    private String phone;

    @ValidEmails
    @NotEmpty(message = "Введите адрес электронной почты")
    @Column(name = "email")
    @ElementCollection
    @CollectionTable(name = "person_emails", joinColumns = @JoinColumn(name = "person_id"))
    private List<String> emails;

    @Column(name = "created_time")
    private Instant timeCreate;

    @PrePersist
    private void setTimeCreatePerson(){
        timeCreate = Instant.now();
    }


}
