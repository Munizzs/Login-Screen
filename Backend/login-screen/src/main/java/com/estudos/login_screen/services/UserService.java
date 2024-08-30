package com.estudos.login_screen.services;

import com.estudos.login_screen.models.Role;
import com.estudos.login_screen.models.user.*;
import com.estudos.login_screen.repositories.UserRepository;
import com.estudos.login_screen.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final UserMapper mapper;

    @Autowired
    public UserService(UserRepository repository, RoleRepository roleRepository, UserMapper mapper) {
        this.repository = repository;
        this.roleRepository = roleRepository;
        this.mapper = mapper;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserDTO> listAll(){
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserDTO save(UserCreationDTO userCreationDTO){
        User user = mapper.toEntity(userCreationDTO);

        //user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (repository.existsByName(user.getName())) {
            throw new IllegalArgumentException("Username já existe.");
        }

        repository.save(user);
        return mapper.toDTO(user);
    }

    public UserDTO updateById(Integer id, UserUpdateDTO userUpdateDTO){
        User user = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("User com id " + id + " não existe."));

        if(userUpdateDTO.getName()!=null)
            user.setName(userUpdateDTO.getName());

        if(userUpdateDTO.getPassword()!=null)
            user.setPassword(userUpdateDTO.getPassword());
            //user.setPassword(passwordEncoder.encode(userUpdateDTO.getPassword()));

        if(userUpdateDTO.getEnable()!=null)
            user.setEnable(userUpdateDTO.getEnable());

        if(userUpdateDTO.getRoles()!=null)
            user.setRoles(userUpdateDTO.getRoles());

        repository.save(user);
        return mapper.toDTO(user);
    }

    public void removeById(Integer id){
        if(repository.existsById(id))
            repository.deleteById(id);
        else
            throw new IllegalArgumentException("User com  id "+id +" não existe.");
    }

    public void addRoleToUser(Integer userId, Integer roleId) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User não encontrado"));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Role não encontrada"));

        user.getRoles().add(role);
        repository.save(user);
    }

    public void removeRoleFromUser(Integer userId, Integer roleId) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User não encontrado"));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Role não encontrada"));

        user.getRoles().remove(role);
        repository.save(user);
    }

    public Set<Role> getUserRoles(Integer userId) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User não encontrado"));

        return user.getRoles();
    }
}
