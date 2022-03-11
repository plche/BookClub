package com.codingdojo.peru.ft2022.bc.repositories;

import org.springframework.data.repository.CrudRepository;

import com.codingdojo.peru.ft2022.bc.models.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
