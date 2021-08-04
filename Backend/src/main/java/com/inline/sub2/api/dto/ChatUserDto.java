package com.inline.sub2.api.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatUserDto {
    private String name;
    private String token;

    @Builder
    public ChatUserDto(String name, String token){
        this.name = name;
        this.token = token;
    }
}
