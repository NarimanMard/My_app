package com.example.nari.crud_app.repository;

import com.example.nari.crud_app.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findByIdBetween(Long from, Long to);
//    save(person) → Сохранить нового человека в базу.
//    findById(id) → Найти человека по его номеру (ID).
//    findAll() → Вернуть список вообще всех людей из базы.
//    deleteById(id) → Удалить человека по номеру.
//    все это идет под капотом, благодаря JpaRepository<Class, type(PK)>
}
