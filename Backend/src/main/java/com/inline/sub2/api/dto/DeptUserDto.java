package com.inline.sub2.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DeptUserDto {
    private String deptName;
    private Long cnt;

    public DeptUserDto(String deptName, Long cnt){
        this.deptName = deptName;
        this.cnt = cnt;
    }
}
