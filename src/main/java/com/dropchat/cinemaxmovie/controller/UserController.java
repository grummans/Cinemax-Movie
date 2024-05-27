package com.dropchat.cinemaxmovie.controller;

import com.dropchat.cinemaxmovie.converter.request.AuthenticationRequest;
import com.dropchat.cinemaxmovie.converter.request.UserRequest;
import com.dropchat.cinemaxmovie.converter.request.VerifyDataRequest;
import com.dropchat.cinemaxmovie.converter.response.ApiResponse;
import com.dropchat.cinemaxmovie.converter.response.AuthenticationResponse;
import com.dropchat.cinemaxmovie.converter.response.MessageResponse;
import com.dropchat.cinemaxmovie.converter.response.UserResponse;
import com.dropchat.cinemaxmovie.entity.User;
import com.dropchat.cinemaxmovie.service.AuthenticationService;
import com.dropchat.cinemaxmovie.service.UserService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@Data
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class UserController {

    private UserService userService;
    private AuthenticationService authenticationService;

    //function Signup User
    @PostMapping("/signup")
    public String registerUser(@RequestBody @Valid UserRequest request){
        return userService.registerUser(request);
    }

    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> loginUser(@RequestBody AuthenticationRequest request){
        return ApiResponse.<AuthenticationResponse>builder()
                .result(AuthenticationResponse.builder()
                        .authenticate("Login Successfully")
                        .build())
                .build();
    }

    @PostMapping("/email-verify")
    public MessageResponse verifyUser(@RequestBody VerifyDataRequest request){
        return authenticationService.verifyEmail(request.getOtp());
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
