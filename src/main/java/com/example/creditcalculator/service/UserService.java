package com.example.creditcalculator.service;

import com.example.creditcalculator.domain.Role;
import com.example.creditcalculator.domain.User;
import com.example.creditcalculator.model.UserDTO;
import com.example.creditcalculator.repos.RoleRepository;
import com.example.creditcalculator.repos.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(final UserRepository userRepository, final RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll(Sort.by("id")).stream().map(user -> mapToDTO(user, new UserDTO())).collect(Collectors.toList());
    }

    public UserDTO get(final Long id) {
        return userRepository.findById(id).map(user -> mapToDTO(user, new UserDTO())).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


    public Long create(final UserDTO userDTO) {
        final User user = new User();
        mapToEntity(userDTO, user);
        return userRepository.save(user).getId();
    }

    public void update(final Long id, final UserDTO userDTO) {
        final User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(userDTO, user);
        userRepository.save(user);
    }

    public void delete(final Long id) {
        userRepository.deleteById(id);
    }

    private UserDTO mapToDTO(final User user, final UserDTO userDTO) {
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setPassword(user.getPassword());
        userDTO.setUserPermissions(user.getUserPermissionRoles() == null ? null : user.getUserPermissionRoles().stream().map(role -> role.getId()).collect(Collectors.toList()));
        return userDTO;
    }

    private User mapToEntity(final UserDTO userDTO, final User user) {
        user.setName(userDTO.getName());
        user.setPassword(userDTO.getPassword());
        final List<Role> userPermissions = roleRepository.findAllById(userDTO.getUserPermissions() == null ? Collections.emptyList() : userDTO.getUserPermissions());
        if (userPermissions.size() != (userDTO.getUserPermissions() == null ? 0 : userDTO.getUserPermissions().size())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "one of userPermissions not found");
        }
        user.setUserPermissionRoles(userPermissions.stream().collect(Collectors.toSet()));
        return user;
    }

    public boolean nameExists(final String name) {
        return userRepository.existsByNameIgnoreCase(name);

    }




}
