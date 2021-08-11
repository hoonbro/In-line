package com.inline.sub2.api.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InviteDto {
    private Long roomId;
    private String userName; // 보낸 사람 이름
}
