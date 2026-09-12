package com.example.nari.crud_app.controller;

import com.example.nari.crud_app.entity.Person;
import com.example.nari.crud_app.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // мы пишем Api, а не сайт, поэтому RESTController
@RequestMapping("/api/person")// начальный адрес
public class PersonController {

    @Autowired // берем объект PersonService, не создаем новый, а используем тот что работает от Spring
    PersonService personService;
//    PostMapping - запрос на создание
//    GetMapping - запрос на получение


// Поиск:
    @GetMapping()// список всех пользователей
    List<Person> getAllPersons(){
        return personService.getAllPersons();
    }

    @GetMapping("/{id}")
    Person getPersonById(@PathVariable Long id){
        return personService.findById(id);
    }

    @GetMapping("/range")
    List<Person> getPersonByRange(
            @RequestParam("from")
            Long FromId,
            @RequestParam("to")
            Long ToId )
    { return  personService.getByRange(FromId, ToId);}

// Создание и изменение:
    // Человек:
    @PostMapping()
    Person createPerson(
            @Valid
            @RequestBody // взять данные из тела JSON
            Person person
            ){
        return personService.createPerson(person);
    }

    @PutMapping("/{id}")
    Person updatePerson(
            @PathVariable
            Long id,
            @Valid
            @RequestBody
            Person newPerson
            ){
        return personService.updatePerson(id, newPerson);
    }
    // Почта
    @PostMapping("/{id}/emails")
    Person addEmail(
            @PathVariable
            Long id,
            @Valid
            @RequestBody
            String email
            ){
        return personService.addEmail(id, email);
    }

// Удаление:

    @DeleteMapping("/{id}")
    String deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);
        return "Человек с id " + id + " удален";
    }

    @DeleteMapping("/{id}/emails/{email}")
    Person deleteEmail(
            @PathVariable
            Long id,
            @PathVariable
            String email
            ){
        return personService.deleteEmails(id, email);
    }
}
