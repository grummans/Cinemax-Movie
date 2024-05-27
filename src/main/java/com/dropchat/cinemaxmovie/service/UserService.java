package com.dropchat.cinemaxmovie.service;

import com.dropchat.cinemaxmovie.converter.EntityConverter;
import com.dropchat.cinemaxmovie.converter.request.AuthenticationRequest;
import com.dropchat.cinemaxmovie.converter.request.UserRequest;
import com.dropchat.cinemaxmovie.converter.response.ApiResponse;
import com.dropchat.cinemaxmovie.converter.response.ApplicationException;
import com.dropchat.cinemaxmovie.converter.response.AuthenticationResponse;
import com.dropchat.cinemaxmovie.converter.response.UserResponse;
import com.dropchat.cinemaxmovie.entity.User;
import com.dropchat.cinemaxmovie.exception.ErrorCode;
import com.dropchat.cinemaxmovie.repository.*;
import com.dropchat.cinemaxmovie.util.EmailUtil;
import jakarta.mail.internet.InternetAddress;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Builder
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

    public boolean loginUser(AuthenticationRequest request){

        if(!authenticationService.authenticatedUser(request))
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

//            var user = userRepository.findByUsername(request.getUsername())
//                    .orElseThrow(() -> new RuntimeException("User Not Found"));
//            user.setUserStatus(userStatusRepository.findNameByCode("True")
//                    .orElseThrow(() -> new RuntimeException("Data Not Found")));
//            user.setActive(true);
//            userRepository.save(user);

        }
        return authenticationService.authenticatedUser(request);
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
