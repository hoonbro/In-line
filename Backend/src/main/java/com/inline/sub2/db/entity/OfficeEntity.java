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
@Table(name = "office")
public class OfficeEntity {
    @Id
    @Column(name = "office_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) //기본키 생성을 db에 위임하는 방법(auto increment)
    private int officeId;

    @Column(name = "office_name")
    private String officeName;
}
