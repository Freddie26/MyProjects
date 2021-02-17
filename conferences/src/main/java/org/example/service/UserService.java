package org.example.service;

import lombok.AllArgsConstructor;
import org.example.controller.dto.AuthUserDto;
import org.example.entity.Role;
import org.example.entity.User;
import org.example.repository.RoleRepository;
import org.example.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    // TODO: сделать роли ENUM
    private final String ROLE_LISTENER = "ROLE_LISTENER";
    private final String ROLE_PRESENTER = "ROLE_PRESENTER";

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User [%s] not found", username)));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public boolean registerNewUser(AuthUserDto userDto) {
        Optional<User> optUser = userRepository.findByUsername(userDto.getUsername());
        if (optUser.isPresent()) {
            return false;
        }

        Role roleListener = roleRepository.findByName(ROLE_LISTENER);

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(roleListener);
        userRepository.save(user);

        return true;
    }

    public boolean makePresenter(Long userId) {
        Optional<User> optUser = findUserById(userId);
        optUser.ifPresent(user -> {
            Role userRole = user.getRole();
            if (!ROLE_PRESENTER.equals(userRole.getName())) {
                Role rolePresenter = roleRepository.findByName(ROLE_PRESENTER);
                user.setRole(rolePresenter);
            }
        });
        return optUser.isPresent();
    }
}
