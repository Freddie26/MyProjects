package org.example.service;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.example.controller.dto.AuthUserDto;
import org.example.entity.Role;
import org.example.entity.User;
import org.example.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final Set<String> ALL_ROLES = new HashSet<>(Arrays.asList("ROLE_LISTENER", "ROLE_PRESENTER"));

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User [%s] not found", username)));
    }

    public User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElse(new User());
    }

    public boolean saveUser(AuthUserDto userDto) {
        Optional<User> optUser = userRepository.findByUsername(userDto.getUsername());
        if (optUser.isPresent()) {
            return false;
        }

        String roleName = StringUtils.isNotEmpty(userDto.getRole()) && ALL_ROLES.contains(userDto.getRole())
                ? userDto.getRole()
                : "ROLE_USER";

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton(new Role(roleName)));
        userRepository.save(user);

        return true;
    }

    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }
}
