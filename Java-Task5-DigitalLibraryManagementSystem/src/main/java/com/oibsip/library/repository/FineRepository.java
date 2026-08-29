package com.oibsip.library.repository;

import com.oibsip.library.model.Fine;
import com.oibsip.library.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FineRepository extends JpaRepository<Fine, Long> {

    List<Fine> findByUser(User user);

    List<Fine> findByPaid(boolean paid);

    List<Fine> findByUserAndPaid(User user, boolean paid);

    Optional<Fine> findByIssueId(Long issueId);
}