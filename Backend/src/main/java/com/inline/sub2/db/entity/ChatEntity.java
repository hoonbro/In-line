package com.inline.sub2.db.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@Table(name ="chat")
public class ChatEntity {

    @Column(name="user_id")
    Long userId;

    @Column(name="office_id")
    Long officeId;


    @Column(name="user_name")
    String userName;

    @Column(name="content")
    String content;

    @Id
    @Column(name="send_time")
    @Temporal(TemporalType.TIME)
    Date sendTime;

    @Column(name="send_date")
    @Temporal(TemporalType.DATE)
    Date sendDate;


}