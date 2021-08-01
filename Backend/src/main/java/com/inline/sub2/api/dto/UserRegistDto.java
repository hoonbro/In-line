package com.inline.sub2.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistDto {
    private String officeName;
    private String email;
    private String deptName;
    private String jobName;
    private String name;
    private String phone;
    private String password;
    private Long deptId;
    private Long jobId;
    private Long officeId;
}
