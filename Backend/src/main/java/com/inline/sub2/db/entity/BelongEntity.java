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
@Table(name = "belong")
public class BelongEntity {
    @Id
    @Column(name = "belong_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) //기본키 생성을 db에 위임하는 방법(auto increment)
    private int belongId;
    @Column(name = "belong_name")
    private String belongName;
    @Column(name = "office_id")
    private int officeId;
}
