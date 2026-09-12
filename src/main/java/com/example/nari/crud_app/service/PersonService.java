package com.example.nari.crud_app.service;

import com.example.nari.crud_app.entity.Person;
import com.example.nari.crud_app.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository; // предоставляет CRUD+ методы для работы с бд
// Поиск:
    public List<Person> getAllPersons(){
        return personRepository.findAll();
    }

    public List<Person> getByRange(Long FromId, Long ToId ) {return personRepository.findByIdBetween(FromId, ToId);}

    public Person findById(Long id){ // возвращает Optional
        return personRepository.findById(id)
                //если объекта с таким id нет - выбрасываем ошибку
                .orElseThrow(()-> new RuntimeException("Человек с id " + id + " не найден"));
    }
// Создание и изменение:
    public Person createPerson(Person person){
        return personRepository.save(person);
    }

    public Person updatePerson(Long id, Person newPerson){
        Person person = personRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Человек с таким id не найден"));
        person.setName(newPerson.getName());
        person.setSecondName(newPerson.getSecondName());
        person.setPhone(newPerson.getPhone());
        // тк почта это отдельная таблица в бд, то у нее другая логика
        return personRepository.save(person);
    }

// Удаление:
    public void deletePerson(Long id){
        if(!personRepository.existsById(id)){
            throw new RuntimeException("Человек с таким id не найден для удаления");
        }
        personRepository.deleteById(id);
    }

// Работа с почтой:
    // Выносим в отдельный блок кода, тк почта это отдельная таблица в бд и для работы с ней необходимо иметь спец методы
    // иначе бы фронту пришлось писать каждый раз все почты заново. Если бы не вынесли в отдельный блок(чистота коа)
    // Добавление почты
    public Person addEmail(Long id, String newEmail){
        Person person = personRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Такой человек не найден. id: " + id));
        List<String> personEmails = person.getEmails();
        // Если почт нет
        if(personEmails == null){
            personEmails = new ArrayList<>();
            person.setEmails(personEmails);
        }
        // Если такая почта уже есть
        if(personEmails.contains(newEmail)){
            throw new RuntimeException("Почта: " + newEmail + " - уже существует" );
        }
        personEmails.add(newEmail);
        // Hibernate сам понимает, что нужно обновить таблицу person_emails
        return personRepository.save(person);
    }
    // Удаление почты
    public Person deleteEmails(Long id, String delEmail){
        Person person = personRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Такой человек не найден. id: " + id));
        List<String> personEmails = person.getEmails();
        if(personEmails == null || personEmails.isEmpty()){
            throw new RuntimeException("У человека по имени " + person.getName() + " - нет почты");
        }
        //Можно и было объединить с методом описанный выше,
        //но разделение сущ, для того чтобы понимать, есть ли у человека почта или она отсутствует совсем
        if(!personEmails.contains(delEmail)){
            throw new RuntimeException("Такой почты нет для удаления, попробуйте другую почту");
        }

        personEmails.remove(delEmail);
        return personRepository.save(person);
    }

}
