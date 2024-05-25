package com.dropchat.cinemaxmovie.converter;

import com.dropchat.cinemaxmovie.converter.request.UserRequest;
import com.dropchat.cinemaxmovie.converter.response.UserResponse;
import com.dropchat.cinemaxmovie.entity.User;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
@Builder
@Component
public class EntityConverter {

    private ModelMapper modelMapper;

    //Covert user to UserDTO using modelMapper
    public UserResponse convertEntityToDTO(User user){
        var userResponse = modelMapper.map(user, UserResponse.class);
        userResponse.setRole(user.getRole().getRoleName());
        userResponse.setRank(user.getRankCustomer().getName());
        return userResponse;
    }

    //Convert userDTO to User, userDTO get info from client
    public User convertDTOToEntity(UserRequest request){
        return modelMapper.map(request, User.class);
    }

}
