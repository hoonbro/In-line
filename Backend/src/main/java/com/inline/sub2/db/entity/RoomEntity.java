package com.inline.sub2.db.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Data
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "room")
public class RoomEntity {
    @Id
    @Column(name = "room_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) //기본키 생성을 db에 위임하는 방법(auto increment)
    private int roomId;
    @Column(name = "room_name")
    private String roomName;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "office_id")
    private String officeId;
}
