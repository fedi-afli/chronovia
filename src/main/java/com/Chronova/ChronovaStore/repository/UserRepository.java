package com.Chronova.ChronovaStore.repository;

import com.Chronova.ChronovaStore.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
