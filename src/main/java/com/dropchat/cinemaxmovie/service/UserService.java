package com.dropchat.cinemaxmovie.service;

import com.dropchat.cinemaxmovie.converter.EntityConverter;
import com.dropchat.cinemaxmovie.converter.request.UserRequest;
import com.dropchat.cinemaxmovie.converter.response.UserResponse;
import com.dropchat.cinemaxmovie.entity.User;
import com.dropchat.cinemaxmovie.repository.UserRepository;
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

    public User createUser(UserRequest request){
        User user = entityConverter.convertDTOToEntity(request);
        return userRepository.save(user);
    }

    public List<UserResponse> getUsers(){
        return userRepository.findAll()
                .stream()
                .map(entityConverter::convertEntityToDTO)
                .toList();
    }

    public UserResponse getUserById(int id){
        return entityConverter.convertEntityToDTO(userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("USER_NOT_FOUND")));
    }
}
