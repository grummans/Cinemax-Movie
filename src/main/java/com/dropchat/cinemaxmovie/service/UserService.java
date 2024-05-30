package com.dropchat.cinemaxmovie.service;

import com.dropchat.cinemaxmovie.configuration.ApplicationConfig;
import com.dropchat.cinemaxmovie.converter.EntityConverter;
import com.dropchat.cinemaxmovie.converter.request.*;
import com.dropchat.cinemaxmovie.converter.response.*;
import com.dropchat.cinemaxmovie.entity.Role;
import com.dropchat.cinemaxmovie.entity.User;
import com.dropchat.cinemaxmovie.exception.ErrorCode;
import com.dropchat.cinemaxmovie.repository.*;
import com.dropchat.cinemaxmovie.util.EmailUtil;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@RequiredArgsConstructor
@Builder
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final EntityConverter entityConverter;
    private final RoleRepository roleRepository;
    private final RankCustomerRepository rankCustomerRepository;
    private final UserStatusRepository userStatusRepository;
    private final EmailUtil emailUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationService authenticationService;
    private final ConfirmEmailRepository confirmEmailRepository;
    private final ApplicationConfig applicationConfig;

    /**
     * Method register User
     * @param request get data from client
     * @return user register with userStatus = false (because of no authenticate)
     */
    public String registerUser(UserRequest request){

        //Check if user is existed and verified in database
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new ApplicationException(ErrorCode.USER_EXISTED);
        } else if (userRepository.existsByUsername(request.getUsername())) {
            throw new ApplicationException(ErrorCode.USER_EXISTED);
        }else if (userRepository.existsByPhoneNumber(request.getPhoneNumber())){
            throw new ApplicationException(ErrorCode.USER_EXISTED);
        }
        User user = entityConverter.convertDTOToEntity(request); //convert userDTO to user
        user.setPassword(passwordEncoder.encode(user.getPassword())); //Encode password with Bcrypt Alg
        user.setPoint(20); //auto point = 20 when register new account
        user.setRole(roleRepository.findByRoleName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Role Not Found")));
        user.setUserStatus(userStatusRepository.findUserStatusByCode("False")
                .orElseThrow(() -> new RuntimeException("Data Not Found")));
        user.setRankCustomer(rankCustomerRepository.findRankNameByPoint(20)
                .orElseThrow(() -> new RuntimeException("Data Not Found")));

        userRepository.save(user);

         //Send Email Verify with OTP
        emailUtil.createConfirmEmail(user);
        return "Please check your mailbox to verify account";
    }

    public AuthenticationResponse loginUser(AuthenticationRequest request){

        if(!authenticationService.authenticatedLoginUser(request).isAuthenticate())
            throw new ApplicationException(ErrorCode.USER_INVALID);
        else {
            //Check if mail verified or not
            var user = userRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new ApplicationException(ErrorCode.DATA_NOT_FOUND));

            var currentEmail = confirmEmailRepository.findByUserId(user.getId())
                    .orElseThrow(() -> new ApplicationException(ErrorCode.DATA_NOT_FOUND));
            if(!currentEmail.isConfirm()){
                throw new ApplicationException(ErrorCode.USER_NOT_VERIFY);
            }

        }
        return authenticationService.authenticatedLoginUser(request);
    }

    public MessageResponse changePasswordUser(String username, ResetPasswordRequest request){

        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));
        if(!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())){
            return new MessageResponse("Password is not match. Please check again");
        }else if (request.getCurrentPassword().equals(request.getNewPassword())){
            return new MessageResponse("New password and current password must not be the same");
        } else if(!request.getNewPassword().equals(request.getConfirmPassword())){
            return new MessageResponse("Password are not the same");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        return new MessageResponse("Change password successfully");

    }

    public MessageResponse changeRoleUser(UpdateRoleRequest request){

        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));
        String currentRole = user.getRole().getRoleName();
        if(currentRole.equals(formatRole(request.getRole()))){
            return new MessageResponse("Update role failed. New role and current role are the same");
        }

        user.setRole(roleRepository.findByRoleName(formatRole(request.getRole()))
                .orElseThrow(() -> new ApplicationException(ErrorCode.DATA_NOT_FOUND)));

        userRepository.save(user);

        return new MessageResponse("Update role for " + request.getUsername() + " successfully");
    }

    private String formatRole(String role){
        switch (role){
            case "admin":
                role =  roleRepository.findByRoleName("ROLE_" + role.toUpperCase())
                        .orElseThrow(() -> new ApplicationException(ErrorCode.DATA_NOT_FOUND)).getRoleName();
                break;
            case "moderator":
                role = roleRepository.findByRoleName("ROLE_"+role.toUpperCase())
                        .orElseThrow(() -> new ApplicationException(ErrorCode.DATA_NOT_FOUND)).getRoleName();
                break;
            case "user":
                role = roleRepository.findByRoleName("ROLE_"+role.toUpperCase())
                        .orElseThrow(() -> new ApplicationException(ErrorCode.DATA_NOT_FOUND)).getRoleName();
                break;
        }
        return role;
    }

    public MessageResponse updateUser(UpdateUserRequest request) {
        if (userRepository.findByUsername(request.getUserName()).isPresent()) {
            throw new ApplicationException(ErrorCode.USER_EXISTED);
        }
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ApplicationException(ErrorCode.USER_EXISTED);
        }
        if (userRepository.findByPhoneNumber(request.getPhoneNumber()).isPresent()) {
            throw new ApplicationException(ErrorCode.USER_EXISTED);
        }
        var current = userRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Data not found !"));
        if (request.getPoint() == 0) request.setPoint(current.getPoint());
        var exists = userRepository.findAll().stream()
                .anyMatch(x ->
                        x.getUsername().equals(request.getUserName())
                                || x.getEmail().equals(request.getEmail())
                );
        if (exists) {
            return new MessageResponse("Fail");
        }
        BeanUtils.copyProperties(request, current, applicationConfig.getNullPropertyNames(request));
        userRepository.save(current);
        return new MessageResponse("Update data success !");
    }


    /**
     * Method to get all info of users in the database
     * @return List of users with customize properties
     */
    public List<UserResponse> getUsers(){
        return userRepository.findAll()
                .stream()
                .map(entityConverter::convertEntityToDTO)
                .toList();
    }

    /**
     * Method to get an user info from id
     * @param id to find
     * @return user info from database
     */
    public UserResponse getUserById(int id){
        return entityConverter.convertEntityToDTO(userRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND)));
    }
}
