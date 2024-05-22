package com.dropchat.cinemaxmovie.controller;

import com.dropchat.cinemaxmovie.converter.request.UserRequest;
import com.dropchat.cinemaxmovie.entity.User;
import com.dropchat.cinemaxmovie.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Data
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class UserController {

    private UserService userService;

    @PostMapping("/signup")
    public User createUser(@RequestBody UserRequest request){
        return userService.createUser(request);
    }

    @GetMapping
    public List<User> getUsers(){
        return userService.getUsers();
    }

}
