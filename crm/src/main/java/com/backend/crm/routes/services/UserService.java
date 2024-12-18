package com.backend.crm.routes.services;

import com.backend.crm.app.config.Mapper;
import com.backend.crm.app.domain.TokenService;
import com.backend.crm.app.models.response.types.Response;
import com.backend.crm.app.models.response.types.ResponseData;
import com.backend.crm.app.utils.PasswordUtils;
import com.backend.crm.routes.DTOs.*;
import com.backend.crm.routes.models.UserEntity;
import com.backend.crm.routes.models.UserRole;
import com.backend.crm.routes.models.WSUSer;
import com.backend.crm.routes.repositories.UserRepositories;
import com.backend.crm.routes.repositories.UserSpecifications;
import com.backend.crm.routes.repositories.WSUSerRepository;
import com.backend.crm.routes.repositories.WSUSerSpecifications;
import com.backend.crm.routes.response.ResponseUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
    private final UserRepositories repository;

    private final TokenService tokenService;

    private final Mapper mapper;

    /**
     * Заргестрировать пользователя
     * */

    public Response signup(AuthUserDto userDto) {
        try {
            if (userDto == null) {
                return new Response(HttpStatus.NO_CONTENT.value(), "Данные о пользователе пусты");
            }

            if (this.repository.findByLogin(userDto.getLogin()) != null) {
                return new Response(HttpStatus.CONFLICT.value(), "Пользователь с таким Login уже существует");
            }

            UserEntity user = new UserEntity();

            user.setLogin(userDto.getLogin());
            user.setPassword(PasswordUtils.encodePassword(userDto.getPassword()));
            user.setUserRole(UserRole.not_activated);
            user.setCreatedAt(LocalDateTime.now());

            this.repository.save(user);
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
            UserEntity user = this.repository.findByLogin(dto.getLogin());

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
     * Получить всех пользователей
     */

    public Response findAllBySort(SortDto dto, String token) {
        try {
            if (dto.getSort().isEmpty()){
                return new ResponseData<>(HttpStatus.OK.value(), "Успешно получено", this.repository.findAll());
            }

            PageRequest pageRequest;

            if (dto.getSort().getFirst().getSortDir().equals("asc")) {
                pageRequest = PageRequest.of(dto.getPage() - 1,
                        dto.getLimit(),
                        Sort.by(dto.getSort().getFirst().getField()).ascending());
            } else {
                pageRequest = PageRequest.of(dto.getPage() - 1,
                        dto.getLimit(),
                        Sort.by(dto.getSort().getFirst().getField()).descending());
            }

            Specification<UserEntity> spec = UserSpecifications.deletedAtIsNull();

            if (!dto.getSearch().isEmpty()) {
                spec = spec.and(UserSpecifications.search(dto.getSearch()));

                return new ResponseData<>(HttpStatus.OK.value(), "Успешно получено", this.repository.findAll(pageRequest).getContent());
            }

            return new ResponseData<>(HttpStatus.OK.value(), "Успешно получено", this.repository.findAll(pageRequest).getContent());
        } catch (Exception err) {
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }

    /**
     * Заблокировать пользователя (может сделать только админ)
     * */

    public Response banUser(String Authorization, BanUserDto BanReason){
        try {
//            Optional<UserEntity> CurrentAdmin = this.repository.findById(
//                    this.tokenService.getUserIdFromJWT(Authorization));

//            if (CurrentAdmin.isEmpty()){
//                return new Response(HttpStatus.UNAUTHORIZED.value(), "Проблема авторизации админа");
//            }
//
//            if (CurrentAdmin.get().getUserRole() != UserRole.ADMIN){
//                return new Response(HttpStatus.FORBIDDEN.value(), "Не является админом");
//            }

            UserEntity user = this.repository.findById(BanReason.getIdUser()).get();

            if (user == null){
                return new Response(HttpStatus.NOT_FOUND.value(), "Такого пользователя нет");
            }

            if (user.getBanned()){
                user.setBanned(false);
                user.setBanReason(null);

                this.repository.save(user);
                return new Response(HttpStatus.OK.value(), "Пользователь успешно разблокирован");
            }

            user.setBanned(true);
            user.setBanReason(BanReason.getBanReason());
            this.repository.save(user);

            return new Response(HttpStatus.OK.value(), "Пользователь успешно заблокирован");
        }catch (Exception err){
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }

    /**
     * Получить пользователя (Может сделать только админ)
     * */

    public Response getUser(String Authorization, Long id) {
        try {
            if (Authorization.isEmpty()) {
                return new Response(HttpStatus.NO_CONTENT.value(), "Токен отсуствует");
            }

            if (id == null){
                return new Response(HttpStatus.NO_CONTENT.value(), "id пользователя пустое");
            }


//            UserEntity admin = this.repository.findById(this.tokenService.getUserIdFromJWT(Authorization)).get();
//
//            if (admin.getUserRole() != UserRole.ADMIN){
//                return new Response(HttpStatus.FORBIDDEN.value(), "Нет прав на получение пользователя");
//            }

            UserEntity user = this.repository.findById(id).get();

            if (user == null){
                return new Response(HttpStatus.NOT_FOUND.value(), "Такого пользователя нет");
            }

            return new ResponseData<>(HttpStatus.OK.value(),
                    "Успешно получен",
                    user);
        } catch (Exception err) {
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }

    /**
     * Изменить пользователя
     */

    public Response saveEdit(Long id, UserDto dto) {
        try {
            Optional<UserEntity> current = this.repository.findById(id);

            if (current.isEmpty()) {
                return new Response(HttpStatus.NOT_FOUND.value(), "Такого пользователя нет");
            }

            UserEntity user = current.get();

            mapper.getMapper().map(dto, user);
            user.setUpdatedAt(LocalDateTime.now());

            this.repository.save(user);
            return new Response(HttpStatus.OK.value(), "Успешно сохранено");
        } catch (Exception err) {
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }

    /**
     * Удалить пользователя
     */

    public Response deleteById(Long id){
        try {
            Optional<UserEntity> current = this.repository.findById(id);

            if (current.isEmpty()){
                return new Response(HttpStatus.NOT_FOUND.value(), "Такого пользователя нет");
            }

            UserEntity user = current.get();

            if (user.getDeletedAt() != null){
                user.setDeletedAt(null);
                user.setUpdatedAt(LocalDateTime.now());
            }else {
                user.setDeletedAt(LocalDateTime.now());
            }

            this.repository.save(user);
            return new Response(HttpStatus.OK.value(), "Успешно удалено/востановлено");
        }catch (Exception err){
            System.out.println(err.getMessage());
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }

    public UserEntity getUserForAuth(Long id){
        try {
            return this.repository.findById(id).get();
        }catch (Exception err){
            System.out.println(err.getMessage());
            return null;
        }
    }
}
