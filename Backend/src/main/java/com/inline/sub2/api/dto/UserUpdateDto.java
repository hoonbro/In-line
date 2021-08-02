package com.inline.sub2.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDto {
    private Long userId;
    private String deptName;
    private String jobName;
    private String name;
    private String nickName;
    private String phone;
    private String profileImage;
}
