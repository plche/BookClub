package com.codingdojo.peru.ft2022.bc.repositories;

import com.codingdojo.peru.ft2022.bc.models.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    List<Book> findAll();
}
