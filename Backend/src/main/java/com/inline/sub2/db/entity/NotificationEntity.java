package com.inline.sub2.db.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Data
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "notification")
public class NotificationEntity {

    @Id
    @Column(name="notification_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    @Column(name="sender_id")
    private Long senderId;

    @Column(name="receive_id")
    private Long receiveId;

    @Column(name="room_id")
    private Long roomId;

    @Column(name="user_name")
    private String userName;

    @Column(name="invite_date")
    @Temporal(TemporalType.DATE)
    Date inviteDate;

    @Column(name="invite_time")
    @Temporal(TemporalType.TIME)
    Date inviteTime;
}
