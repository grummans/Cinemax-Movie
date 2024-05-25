package com.dropchat.cinemaxmovie.service;

import com.dropchat.cinemaxmovie.converter.EntityConverter;
import com.dropchat.cinemaxmovie.converter.request.UserRequest;
import com.dropchat.cinemaxmovie.converter.response.UserResponse;
import com.dropchat.cinemaxmovie.entity.User;
import com.dropchat.cinemaxmovie.repository.RankCustomerRepository;
import com.dropchat.cinemaxmovie.repository.RoleRepository;
import com.dropchat.cinemaxmovie.repository.UserRepository;
import com.dropchat.cinemaxmovie.repository.UserStatusRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
@Builder
public class UserService {

    private UserRepository userRepository;
    private EntityConverter entityConverter;
    private RoleRepository roleRepository;
    private RankCustomerRepository rankCustomerRepository;
    private UserStatusRepository userStatusRepository;

    /**
     * Method register User
     * @param request get data from client
     * @return user register with userStatus = false (because of no authenticate)
     */
    public String registerUser(UserRequest request){

        //Check if user is existed and verified in database

        if(userRepository.existsByEmail(request.getEmail()))
            throw new RuntimeException("User existed");
        User user = entityConverter.convertDTOToEntity(request); //convert userDTO to user

        user.setPoint(20); //auto point = 20 when register new account
        user.setRole(roleRepository.findByRoleName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Role Not Found")));
        user.setUserStatus(userStatusRepository.findUserStatusByCode("False")
                .orElseThrow(() -> new RuntimeException("Data Not Found")));
        user.setRankCustomer(rankCustomerRepository.findRankNameByPoint(20)
                .orElseThrow(() -> new RuntimeException("Data Not Found")));

        userRepository.save(user);


        return "Please check your mailbox to verify account";
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
                .orElseThrow(() -> new RuntimeException("USER_NOT_FOUND")));
    }
}
