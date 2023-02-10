package com.alex.sitealceu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.alex.sitealceu.model.User;

@Repository
@EnableJpaRepositories

public interface IUserRepository extends JpaRepository <User, Integer> {

}
