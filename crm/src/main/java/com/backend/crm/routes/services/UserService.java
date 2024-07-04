package com.backend.crm.routes.services;

import com.backend.crm.app.config.Mapper;
import com.backend.crm.app.domain.TokenService;
import com.backend.crm.app.models.response.types.Response;
import com.backend.crm.app.models.response.types.ResponseData;
import com.backend.crm.routes.DTOs.AuthUserDto;
import com.backend.crm.routes.DTOs.SignupUserDto;
import com.backend.crm.routes.models.UserEntity;
import com.backend.crm.routes.repositories.UserRepositories;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.pulsar.PulsarProperties;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepositories repositories;

    private final TokenService tokenService;

    private final Mapper mapper;

    public Response signup(SignupUserDto userDto) {
        try {
            if (userDto == null) {
                return new Response(HttpStatus.NO_CONTENT.value(), "Данные о пользователе пусты");
            }

            if (this.repositories.findByLogin(userDto.getLogin()) != null) {
                System.out.println("Пользователь с таким логином уже существует");
                return new Response(HttpStatus.CONFLICT.value(), "Пользователь с таким Login уже существует");
            }

            UserEntity user = create(userDto);
            this.repositories.save(user);

            return new ResponseData<>(HttpStatus.OK.value(),
                    "Успешно зарегестрирован",
                    tokenService.generateToken(user));
        } catch (Exception err) {
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }

    private UserEntity create(SignupUserDto userDto) {
        UserEntity user = new UserEntity();

        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setPatronymic(userDto.getPatronymic());
        user.setDob(userDto.getDob());
        user.setLogin(userDto.getLogin());
        user.setPassword(userDto.getPassword());

        return user;
    }
}
