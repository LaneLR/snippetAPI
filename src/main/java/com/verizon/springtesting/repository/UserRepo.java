package com.verizon.springtesting.repository;

import com.verizon.springtesting.models.DBUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<DBUser, Integer> {
}
