package com.backend.crm.routes.services;

import com.backend.crm.app.domain.TokenService;
import com.backend.crm.app.models.response.types.Response;
import com.backend.crm.app.models.response.types.ResponseData;
import com.backend.crm.app.utils.PasswordUtils;
import com.backend.crm.routes.DTOs.AuthUserDto;
import com.backend.crm.routes.DTOs.SignupUserDto;
import com.backend.crm.routes.models.UserEntity;
import com.backend.crm.routes.models.UserRole;
import com.backend.crm.routes.models.WSUSer;
import com.backend.crm.routes.repositories.UserRepositories;
import com.backend.crm.routes.repositories.WSUSerRepository;
import com.backend.crm.routes.response.ResponseUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * ## Сервис пользователей
 *
 * @author Горелов Дмитрий
 * */

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepositories repositories;

    private final TokenService tokenService;

    /**
     * Заргестрировать пользователя
     * */

    public Response signup(AuthUserDto userDto) {
        try {
            if (userDto == null) {
                return new Response(HttpStatus.NO_CONTENT.value(), "Данные о пользователе пусты");
            }

            if (this.repositories.findByLogin(userDto.getLogin()) != null) {
                return new Response(HttpStatus.CONFLICT.value(), "Пользователь с таким Login уже существует");
            }

            UserEntity user = new UserEntity();

            user.setLogin(userDto.getLogin());
            user.setPassword(PasswordUtils.encodePassword(userDto.getPassword()));
            user.setUserRole(UserRole.NOT_ACTIVATED);

            this.repositories.save(user);
            return new ResponseData<>(HttpStatus.OK.value(),
                    "Успешно зарегестрирован",
                    this.tokenService.generateToken(user));
        } catch (Exception err) {
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }

    /**
     * Авторизовать пользователя
     * */

    public Response loginUser(AuthUserDto dto){
        try {
            UserEntity user = this.repositories.findByLogin(dto.getLogin());

            if (user == null){
                return new Response(HttpStatus.NO_CONTENT.value(), "Пользователя с таким login нет");
            }

            if (!PasswordUtils.matches(dto.getPassword(), user.getPassword())){
                return new Response(HttpStatus.UNAUTHORIZED.value(), "Неверный пароль");
            }

            return new ResponseData<>(HttpStatus.OK.value(), "Успешно авторизован", this.tokenService.generateToken(user));
        }catch (Exception err){
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }

    /**
     * Заблокировать пользователя (может сделать только админ)
     * */

//    public Response banUser(String Authorization, Long id, String BanReason){
//        try {
//            Optional<UserEntity> CurrentAdmin = this.repositories.findById(
//                    this.tokenService.getUserIdFromJWT(Authorization));
//
//            if (CurrentAdmin.isEmpty()){
//                return new Response(HttpStatus.UNAUTHORIZED.value(), "Проблема авторизации админа");
//            }
//
//            if (CurrentAdmin.get().getUserRole() != UserRole.ADMIN){
//                return new Response(HttpStatus.FORBIDDEN.value(), "Не является админом");
//            }
//
//            UserEntity user = this.repositories.findById(id).get();
//
//            if (user == null){
//                return new Response(HttpStatus.NOT_FOUND.value(), "Такого пользователя нет");
//            }
//
//            user.setBanned(true);
//            user.setBanReason(BanReason);
//            this.repositories.save(user);
//
//            return new Response(HttpStatus.OK.value(), "Пользователь успешно заблокирован");
//        }catch (Exception err){
//            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
//        }
//    }

    /**
     * Получить пользователя (Может сделать только админ)
     * */

//    public Response getUser(String Authorization, Long id) {
//        try {
//            if (Authorization.isEmpty()) {
//                return new Response(HttpStatus.NO_CONTENT.value(), "Токен отсуствует");
//            }
//
//            if (id == null){
//                return new Response(HttpStatus.NO_CONTENT.value(), "id пользователя пустое");
//            }
//
//            if (!this.tokenService.validateToken(Authorization)){
//                return new Response(HttpStatus.UNAUTHORIZED.value(), "Токен истек");
//            }
//
//            UserEntity admin = this.repositories.findById(this.tokenService.getUserIdFromJWT(Authorization)).get();
//
//            if (admin.getUserRole() != UserRole.ADMIN){
//                return new Response(HttpStatus.FORBIDDEN.value(), "Нет прав на получение пользователя");
//            }
//
//            UserEntity user = this.repositories.findById(id).get();
//
//            if (user == null){
//                return new Response(HttpStatus.NOT_FOUND.value(), "Такого пользователя нет");
//            }
//
//            return new ResponseData<>(HttpStatus.OK.value(),
//                    "Успешно получен",
//                    new ResponseUserInfo(user));
//        } catch (Exception err) {
//            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
//        }
//    }

    private UserEntity create(SignupUserDto userDto) {
        UserEntity user = new UserEntity();

        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setPatronymic(userDto.getPatronymic());
        user.setDob(userDto.getDob());
        user.setLogin(userDto.getLogin());

        return user;
    }
}
