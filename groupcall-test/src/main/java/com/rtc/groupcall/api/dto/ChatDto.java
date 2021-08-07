package com.rtc.groupcall.api.dto;

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
    private String type;
    private Long officeId;
    private Long roomId;
    private Long userId;
    private String userName;
    private String content;
    private Date sendDate;
    private String sendTime;

}
