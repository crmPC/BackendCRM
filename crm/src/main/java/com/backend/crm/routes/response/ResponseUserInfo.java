package com.backend.crm.routes.response;

import com.backend.crm.routes.models.UserEntity;
import com.backend.crm.routes.models.UserRole;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Data
public class ResponseUserInfo {
    private Long id_user;

    private String name;

    private String surname;

    private String patronymic;

    private Date dob;

    private boolean banned;

    private String banReason;

    private UserRole userRole;

    public ResponseUserInfo(UserEntity user){
        this.id_user = user.getIdUser();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.patronymic = user.getPatronymic();
        this.dob = user.getDob();
        this.banned = user.getBanned();
        this.banReason = user.getBanReason();
        this.userRole = user.getUserRole();
    }
}
