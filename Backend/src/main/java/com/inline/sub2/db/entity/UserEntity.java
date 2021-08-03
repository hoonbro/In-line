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
@Table(name = "user")

public class UserEntity {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) //기본키 생성을 db에 위임하는 방법(auto increment)
    private Long userId;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Transient
    @Column(name = "password")
    private String password;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "join_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date joinDate;

    @Column(name = "auth")
    private String auth;

    @Column(name = "login")
    private boolean login;

    @Column(name = "room_id")
    private Long roomId;

    @Column(name = "dept_id")
    private Long deptId;

    @Column(name = "job_id")
    private Long jobId;

    @Column(name = "office_id")
    private Long officeId;

    @ManyToOne
    @JoinColumn(name = "room_id", insertable = false, updatable = false)
    private RoomEntity roomEntity;

    @ManyToOne
    @JoinColumn(name = "job_id", insertable = false, updatable = false)
    private JobEntity jobEntity;

    @ManyToOne
    @JoinColumn(name = "dept_id", insertable = false, updatable = false)
    private DeptEntity deptEntity;

    @ManyToOne
    @JoinColumn(name = "office_id", insertable = false, updatable = false)
    private OfficeEntity officeEntity;
}