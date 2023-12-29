package com.vimal.code.ToDo.service.imp;

import com.vimal.code.ToDo.config.AuthConfig;
import com.vimal.code.ToDo.config.SecurityConfig;
import com.vimal.code.ToDo.dto.req.UserRequestDto;
import com.vimal.code.ToDo.dto.resp.UserResponseDto;
import com.vimal.code.ToDo.enitiy.UserEnitiy;
import com.vimal.code.ToDo.exp.UserAlreadyExistsException;
import com.vimal.code.ToDo.repo.UserRepo;
import com.vimal.code.ToDo.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserImp implements UserService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AuthConfig authConfig;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEnitiy user = userRepo.findByEmail(username).orElseThrow(()->new RuntimeException("User not found"));
        System.out.println("Retrived Data");
        System.out.println(user.getPassword()+"Retrived Password");
        System.out.println(user.getUsername());
        System.out.println(user.getId());
        System.out.println(user.getEmail());
        System.out.println("-----");
        return user;
    }

    @Override
    public List<UserResponseDto> getAllUser() {
        List<UserEnitiy> userEnitiys = userRepo.findAll();
        List<UserResponseDto> userResponseDtoList = userEnitiys.stream().map(user->this.userEntityToUserRespDto(user)).collect(Collectors.toList());
        return userResponseDtoList;


    }
    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        Optional<UserEnitiy> foundUser = this.userRepo.findByEmail(userRequestDto.getEmail());
        if (foundUser.isEmpty()) {
            UserEnitiy user = this.userReqDtoToUserEntity(userRequestDto);
            user.setPassword(authConfig.passwordEncoder().encode(user.getPassword()));
            UserEnitiy createdUser = userRepo.save(user);
            return this.userEntityToUserRespDto(createdUser);
        } else {
            // User already exists, throw an exception
            throw new UserAlreadyExistsException("User with email " + userRequestDto.getEmail() + " already exists");
        }
    }


    public UserEnitiy userReqDtoToUserEntity(UserRequestDto userReqDto){
        UserEnitiy user = this.modelMapper.map(userReqDto,UserEnitiy.class);
        return user;
    }
    public UserResponseDto userEntityToUserRespDto(UserEnitiy user){
        UserResponseDto userRespDto = this.modelMapper.map(user,UserResponseDto.class);
        return userRespDto;
    }
}
