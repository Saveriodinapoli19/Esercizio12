package com.example.UnitTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @PostMapping("/create")
    public User createUser(@RequestBody User user){
        return userRepository.save(user);
    }
    @GetMapping("/allUser")
    public List<User> listUser(){
        return userRepository.findAll();
    }
    @GetMapping("/{id}")
    public User userById(@PathVariable Long id){
        return userRepository.findById(id).orElse(null);
    }
    @PutMapping("update/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User users){
        User upUser = userRepository.findById(id).orElse(null);
        if(upUser != null){
            upUser.setNome(users.getNome());
            upUser.setCognome(users.getCognome());
        }
        return userRepository.save(upUser);
    }
    @DeleteMapping("delete/{id}")
    public void deleteById(@PathVariable Long id){
        userRepository.deleteById(id);
    }
    @DeleteMapping("/deleteAll")
    public void deleteAll(){
        userRepository.deleteAll();
    }
}
