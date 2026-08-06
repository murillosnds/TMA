package github.murillosnds.tma.service;

import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import github.murillosnds.tma.entity.User;
import github.murillosnds.tma.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id); 
    }

    public User create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User delete(User user) {
        userRepository.delete(user);
        return user;
    }

    public User update(User user) {
        return userRepository.save(user);
    }

    public Page<User> listAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}