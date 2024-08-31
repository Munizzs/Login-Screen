package com.estudos.login_screen.controllers;

import com.estudos.login_screen.models.Role;
import com.estudos.login_screen.models.user.UserCreationDTO;
import com.estudos.login_screen.models.user.UserDTO;
import com.estudos.login_screen.models.user.UserUpdateDTO;
import com.estudos.login_screen.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController @RequestMapping("admin/user")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    //Crud da Role do User

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{userId}/roles/{roleId}")
    public ResponseEntity<String> addRoleToUser(@PathVariable Integer userId, @PathVariable Integer roleId) {
        service.addRoleToUser(userId, roleId);
        return ResponseEntity.ok("Role adicionada ao User com sucesso.");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}/roles/{roleId}")
    public ResponseEntity<String> removeRoleFromUser(@PathVariable Integer userId, @PathVariable Integer roleId) {
        service.removeRoleFromUser(userId, roleId);
        return ResponseEntity.ok("Role removida do User com sucesso.");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{userId}/roles")
    public ResponseEntity<Set<Role>> getUserRoles(@PathVariable Integer userId) {
        Set<Role> roles = service.getUserRoles(userId);
        return ResponseEntity.ok(roles);
    }

    //Crud User

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAllUser(){
        var listAll = service.listAll();
        return ResponseEntity.ok(listAll);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody UserCreationDTO userCreationDTO){
        UserDTO userDTO = service.save(userCreationDTO);
        return ResponseEntity.status(201).build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping({"/{id}"})
    public ResponseEntity updateUser(@PathVariable Integer id, @RequestBody UserUpdateDTO userUpdateDTO){
        var dentistDTO = service.updateById(id, userUpdateDTO);
        return ResponseEntity.ok(dentistDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping({"/{id}"})
    public ResponseEntity<?> removeUser(@PathVariable Integer id){
        service.removeById(id);
        return ResponseEntity.noContent().build();
    }
}
