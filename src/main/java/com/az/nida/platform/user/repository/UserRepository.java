package com.az.nida.platform.user.repository;

import com.az.nida.platform.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUniqueId(String uniqueId);

    boolean existsByEmail(String email);

    boolean existsByUniqueId(String uniqueId);
}
