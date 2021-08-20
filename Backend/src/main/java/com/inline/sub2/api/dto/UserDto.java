package com.inline.sub2.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class UserDto {
    private String officeName;
    private String email;
    private String name;
    private String password;
    private String profileImage;
    private String nickName;
    private String phone;
    private String address;
    private String auth;
    private boolean login;
    private int roomId;
    private int deptId;
    private int jobId;
    private int officeId;

}
