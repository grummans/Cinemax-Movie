package com.dropchat.cinemaxmovie.controller;

import com.dropchat.cinemaxmovie.converter.request.UserRequest;
import com.dropchat.cinemaxmovie.converter.response.UserResponse;
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

    //function Signup User
    @PostMapping("/signup")
    public UserResponse createUser(@RequestBody UserRequest request){
        return userService.registerUser(request);
    }

    @GetMapping
    public List<UserResponse> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/{userId}")
    public UserResponse getUser(@PathVariable(value = "userId") int id){
        return userService.getUserById(id);
    }

}
