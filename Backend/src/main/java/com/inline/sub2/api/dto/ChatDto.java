package com.inline.sub2.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ChatDto {
    Long officeId;
    Long userId;
    String userName;
    String content;
    Date sendDate;
    Date sendTime;

}