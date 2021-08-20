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
@Table(name = "commute")
public class CommuteEntity {

    @Id
    @Column(name="commute_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commuteId;


    @Column(name="come_in")
    @Temporal(TemporalType.TIME) // 시간
    private Date comeIn;

    @Column(name="come_out")
    @Temporal(TemporalType.TIME) // 시간
    private Date comeOut;

    @Column(name="ymd")
    @Temporal(TemporalType.DATE) // 년,월,일
    private Date ymd;

    @Column(name="user_id")
    private Long userId;

    @Column(name="office_id")
    private Long officeId;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UserEntity userEntity;
}
