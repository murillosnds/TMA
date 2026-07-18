package github.murillosnds.tma.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import github.murillosnds.tma.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}