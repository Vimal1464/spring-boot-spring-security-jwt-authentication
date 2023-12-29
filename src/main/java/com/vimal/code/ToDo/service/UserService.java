package com.vimal.code.ToDo.service;

import com.vimal.code.ToDo.dto.req.UserRequestDto;
import com.vimal.code.ToDo.dto.resp.UserResponseDto;
import com.vimal.code.ToDo.enitiy.UserEnitiy;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<UserResponseDto> getAllUser();
    public UserResponseDto createUser(UserRequestDto userRequestDto);

}
