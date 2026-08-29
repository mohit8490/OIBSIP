package com.oibsip.library.repository;

import com.oibsip.library.model.Query;
import com.oibsip.library.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QueryRepository
        extends JpaRepository<Query, Long> {

    List<Query> findByUser(User user);

    List<Query> findByStatus(String status);
}