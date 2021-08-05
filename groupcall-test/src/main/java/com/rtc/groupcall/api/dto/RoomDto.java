package com.rtc.groupcall.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomDto {
    private String roomName;
    private Long officeId;
    private Long userId;
    private Long roomId;
}
