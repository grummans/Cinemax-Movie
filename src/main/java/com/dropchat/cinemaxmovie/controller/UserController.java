package com.dropchat.cinemaxmovie.controller;

import com.dropchat.cinemaxmovie.converter.request.*;
import com.dropchat.cinemaxmovie.converter.response.ApiResponse;
import com.dropchat.cinemaxmovie.converter.response.AuthenticationResponse;
import com.dropchat.cinemaxmovie.converter.response.MessageResponse;
import com.dropchat.cinemaxmovie.converter.response.UserResponse;
import com.dropchat.cinemaxmovie.service.AuthenticationService;
import com.dropchat.cinemaxmovie.service.UserService;
import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@Data
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    //function Signup User
    @PostMapping("/signup")
    public String registerUser(@RequestBody @Valid UserRequest request) {
        return userService.registerUser(request);
    }

    @PutMapping("/login")
    public ApiResponse<AuthenticationResponse> loginUser(@RequestBody AuthenticationRequest request) {
        var result = userService.loginUser(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

    @PutMapping("/update-role")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public MessageResponse updateRoleUser(@RequestBody UpdateRoleRequest request) {
        return userService.changeRoleUser(request);
    }

    @PutMapping("/email-verify")
    public MessageResponse verifyUser(@RequestBody VerifyDataRequest request) {
        return authenticationService.verifyEmail(request.getOtp());
    }

    @PutMapping("/changePassword/{username}")
    public MessageResponse changePasswordUser(@PathVariable(value = "username") String username,
                                              @RequestBody ResetPasswordRequest request) {
        return userService.changePasswordUser(username, request);
    }

    @PutMapping("/updateUser")
    public MessageResponse updateUser(@RequestBody UpdateUserRequest request) {
        return userService.updateUser(request);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ApiResponse<List<UserResponse>> getUsers() {

        var authentication = SecurityContextHolder.getContext()
                .getAuthentication(); //Obtains the currently authenticated principal, or an authentication request token.

        log.info("Username: {}", authentication.getName()); //Returns the name of this principal.
        authentication.getAuthorities().forEach(
                grantedAuthority -> log.info(grantedAuthority.getAuthority())
        );
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getUsers())
                .build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> logoutUser(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        userService.logout(request);
        return ApiResponse.<Void>builder()
                .build();
    }

    @GetMapping("/{userId}")
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public UserResponse getUser(@PathVariable(value = "userId") int id) {
        return userService.getUserById(id);
    }

    @GetMapping("/my-info")
    public ApiResponse<UserResponse> getMyInfo() {

        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }

}
