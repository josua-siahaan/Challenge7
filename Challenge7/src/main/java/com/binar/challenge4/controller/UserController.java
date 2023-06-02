package com.binar.challenge4.controller;

import com.binar.challenge4.model.User;
import com.binar.challenge4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;


    @GetMapping("/getAllUser")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUser(){
            return userService.getAllUser();
        }

    @GetMapping("/getAllUserPagination")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Page<User> getAllUsersPagination(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return userService.getAllUsersPagination(page, size);
    }

    @GetMapping("/getUserById/{userId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<User> getUserById(@PathVariable(value = "userId") Long id) {
        Optional<User> userData = userService.getUserById(id);
        if (userData.isPresent()) {
            return new ResponseEntity<>(userData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateUser")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> updateUser(@RequestParam(value = "userId") Long id, @RequestBody User user) throws SQLException {
        userService.updateUser(id,user);
        return new ResponseEntity<>("Data berhasil di update", HttpStatus.OK);
    }

    @DeleteMapping("/deleteUserById/{userId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> deleteUserById(@RequestParam(name = "userId") Long id) throws SQLException {
        userService.deleteUser(id);
        return ResponseEntity.ok("Data Berhasil dihapus");
    }
}

