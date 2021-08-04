package com.inline.sub2.db.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "job")
public class JobEntity {
    @Id
    @Column(name = "job_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) //기본키 생성을 db에 위임하는 방법(auto increment)
    private Long jobId;

    @Column(name = "job_name")
    private String jobName;

    @Column(name = "office_id")
    private Long officeId;

}
