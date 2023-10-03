package com.samdoree.fieldgeolog.User.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.samdoree.fieldgeolog.User.Entity.User;

@EnableJpaAuditing
public interface UserRepository extends JpaRepository<User, Long> {
}