package ru.rschir.hotelsbackend.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.rschir.hotelsbackend.dto.UserDTO;
import ru.rschir.hotelsbackend.entities.User;
import ru.rschir.hotelsbackend.repositories.UserRepository;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("user with username: " + username + " not found"));
    }

    public void createUser(UserDTO userDTO) {
        log.info("creating user");
        var role = userDTO.is_partner ? User.UserRole.PARTNER : User.UserRole.CLIENT;
        userRepository.save(User.builder()
                                    .publicName(userDTO.public_name)
                                    .username(userDTO.username)
                                    .password(passwordEncoder.encode(userDTO.password))
                                    .roles(List.of(role))
                                    .build());
    }
}
