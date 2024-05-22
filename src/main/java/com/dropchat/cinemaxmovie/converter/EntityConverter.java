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

@Data
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
@Builder
@Component
public class EntityConverter {

    private ModelMapper modelMapper;

    private UserResponse convertEntityToDTO(User user){
        var userResponse = modelMapper.map(user, UserResponse.class);
        
    }

    public User convertDTOToEntity(UserRequest request){
        return modelMapper.map(request, User.class);
    }

}
