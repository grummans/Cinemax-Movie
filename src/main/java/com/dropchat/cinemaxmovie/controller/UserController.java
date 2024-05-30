package com.dropchat.cinemaxmovie.controller;

import com.dropchat.cinemaxmovie.converter.request.*;
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

    @PutMapping("/login")
    public ApiResponse<AuthenticationResponse> loginUser(@RequestBody AuthenticationRequest request){
        var result = userService.loginUser(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

    @PutMapping("/update-role")
    public MessageResponse updateRoleUser(@RequestBody UpdateRoleRequest request){
        return userService.changeRoleUser(request);
    }

    @PutMapping("/email-verify")
    public MessageResponse verifyUser(@RequestBody VerifyDataRequest request){
        return authenticationService.verifyEmail(request.getOtp());
    }

    @PutMapping("/changePassword/{username}")
    public MessageResponse changePasswordUser(@PathVariable(value = "username") String username,
                                   @RequestBody ResetPasswordRequest request){
        return userService.changePasswordUser(username, request);
    }

    @PutMapping("/updateUser")
    public MessageResponse updateUser(@RequestBody UpdateUserRequest request){
        return userService.updateUser(request);
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
