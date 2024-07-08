package com.backend.crm.routes.services;

import com.backend.crm.app.config.Mapper;
import com.backend.crm.app.domain.TokenService;
import com.backend.crm.app.models.response.types.Response;
import com.backend.crm.app.models.response.types.ResponseData;
import com.backend.crm.routes.DTOs.SignupUserDto;
import com.backend.crm.routes.models.UserEntity;
import com.backend.crm.routes.models.UserRole;
import com.backend.crm.routes.repositories.UserRepositories;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
                    this.tokenService.generateToken(user));
        } catch (Exception err) {
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }

    public Response getUser(String Authorization, Long id) {
        try {
            if (Authorization.isEmpty()) {
                return new Response(HttpStatus.NO_CONTENT.value(), "Токен отсуствует");
            }

            if (id == null){
                return new Response(HttpStatus.NO_CONTENT.value(), "id пользователя пустое");
            }

            if (!this.tokenService.validateToken(Authorization)){
                return new Response(HttpStatus.UNAUTHORIZED.value(), "Токен истек");
            }

            UserEntity admin = this.repositories.findById(this.tokenService.getUserIdFromJWT(Authorization)).get();

            if (admin.getUserRole() != UserRole.ADMIN){
                return new Response(HttpStatus.FORBIDDEN.value(), "Нет прав на получение пользователя");
            }

            return new ResponseData<>(HttpStatus.OK.value(),
                    "Успешно получен",
                    this.repositories.findById(id));
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
