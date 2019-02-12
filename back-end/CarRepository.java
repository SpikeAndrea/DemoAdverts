package com.example.demo;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//E' la classe che si occupa automaticamente delle operazioni CRUD nel database.

//Per operazioni più dettagliate, posso utilizzare la CarRepositoryCustom, un interfaccia che
//permette di implementare dei metodi alternativi a quelli messi a disposizione in automatico da CrudRepository

@Repository
public interface CarRepository  extends CrudRepository<Car,Integer>,CarRepositoryCustom {
   Iterable<Car> findAll(Sort sort);
   Iterable<Car> findByTitleIgnoreCaseContaining(String title);
}
