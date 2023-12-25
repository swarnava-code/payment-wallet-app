package com.sclab.boot.paymentwalletapp.repository;

import com.sclab.boot.paymentwalletapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> { }