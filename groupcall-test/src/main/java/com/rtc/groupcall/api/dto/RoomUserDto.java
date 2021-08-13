package com.rtc.groupcall.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class RoomUserDto {
    private Long userId;
    private String userName;
    private Long roomId;
    private String roomName;
    private Long officeId;
}
