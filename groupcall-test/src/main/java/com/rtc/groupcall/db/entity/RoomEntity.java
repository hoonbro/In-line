package com.rtc.groupcall.db.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "room")
public class RoomEntity {
    @Id
    @Column(name = "room_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) //기본키 생성을 db에 위임하는 방법(auto increment)
    private Long roomId;
    @Column(name = "room_name")
    private String roomName;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "office_id")
    private Long officeId;
}
