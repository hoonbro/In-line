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
@Table(name = "onboard")
public class OnBoardEntity {
    @Id
    @Column(name = "onboard_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) //기본키 생성을 db에 위임하는 방법(auto increment)
    private Long onboardId;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "dept_id")
    private Long deptId;

    @Column(name = "job_id")
    private Long jobId;

    @Column(name = "office_id")
    private Long officeId;

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
